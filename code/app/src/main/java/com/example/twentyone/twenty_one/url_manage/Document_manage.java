package com.example.twentyone.twenty_one.url_manage;

import android.util.Log;

import com.example.twentyone.twenty_one.Model.SearchResult;
import com.example.twentyone.twenty_one.Model.example;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/****
 * dom解析
 * @author Annz_2
 */
public class Document_manage {
   public SearchResult getEnglishkey_result(String input_key) {
      List<example> ex = new ArrayList();
      List<String> accep = new ArrayList<>();
      SearchResult rs = new SearchResult();
      try {
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         DocumentBuilder builder = factory.newDocumentBuilder();
         String input_url = "http://dict-co.iciba.com/api/dictionary.php?w="
                 + input_key + "&type=xml&key=F2127118763399817CCD845A1B6316F5";
         Document doc = builder.parse(input_url);
         String key = doc.getElementsByTagName("key").item(0).getFirstChild().getNodeValue().toString();
         rs.setKey(key);
         NodeList acceptation_node = doc.getElementsByTagName("acceptation");
         if (acceptation_node != null) {
            for (int i = 0; i < acceptation_node.getLength(); i++) {
               String actemp = doc.getElementsByTagName("acceptation").item(i).getFirstChild().getNodeValue().toString();
               accep.add(actemp);
            }

            rs.setAcceptation(accep);

            String us_url = doc.getElementsByTagName("pron").item(0).getFirstChild().getNodeValue().toString();
            String uk_url = doc.getElementsByTagName("pron").item(1).getFirstChild().getNodeValue().toString();
            rs.setUs_sound(us_url);
            rs.setUK_sound(uk_url);

            NodeList sent = doc.getElementsByTagName("sent");
            for (int i = 0; i < sent.getLength(); i++) {
               String an = doc.getElementsByTagName("orig").item(i).getFirstChild().getNodeValue().toString();
               String en = doc.getElementsByTagName("trans").item(i).getFirstChild().getNodeValue().toString();
               example ex_ref = new example(an, en);
               ex.add(ex_ref);
            }
            rs.setExample(ex);
         } else
            return null;
      }
      catch (Exception e) {
         Log.e("wrong","输入的单词"+input_key+"不符合规范");
         return null;
      }
      return rs;
   }
}