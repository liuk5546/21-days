package com.example.twentyone.twenty_one.Model;

/***
 * @author Annz_2
 * 翻译中英文组合类
 */
public class example {
   private String Chinese_example;
   private String English_example;
   public String getChinese_example() {
      return Chinese_example;
   }
   public void setChinese_example(String chinese_example) {
      Chinese_example = chinese_example;
   }
   public String getEnglish_example() {
      return English_example;
   }
   public void setEnglish_example(String english_example) {
      English_example = english_example;
   }
   public example(String Chinese_example,String English_example)
   {
      this.Chinese_example=Chinese_example;
      this.English_example =English_example;
   }
}
