package com.globits.wl.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class NumberUtils {
	public static Double round(Double val, int newScale) {
		if(val!=null && val>0) {
			 return new BigDecimal(val.toString()).setScale(newScale,RoundingMode.HALF_UP).doubleValue();
		}else {
			return val;
		}
	   
	}
	public static Double roundM(Double val, int newScale) {
		if(val!=null && val>0) {
			 return new BigDecimal(val.toString()).setScale(newScale,RoundingMode.HALF_UP).doubleValue();
		}else {
			double a=val;
			a=Math.floor(a*100)/100;
			return a;
		}
	   
	}
	//hàm chuyển số nguyên sang số la mã (I,II,III,...)
	 public static String RomanNumerals(int Int) {
	    LinkedHashMap<String, Integer> roman_numerals = new LinkedHashMap<String, Integer>();
	    roman_numerals.put("M", 1000);
	    roman_numerals.put("CM", 900);
	    roman_numerals.put("D", 500);
	    roman_numerals.put("CD", 400);
	    roman_numerals.put("C", 100);
	    roman_numerals.put("XC", 90);
	    roman_numerals.put("L", 50);
	    roman_numerals.put("XL", 40);
	    roman_numerals.put("X", 10);
	    roman_numerals.put("IX", 9);
	    roman_numerals.put("V", 5);
	    roman_numerals.put("IV", 4);
	    roman_numerals.put("I", 1);
	    String res = "";
	    for(Map.Entry<String, Integer> entry : roman_numerals.entrySet()){
	      int matches = Int/entry.getValue();
	      res += repeat(entry.getKey(), matches);
	      Int = Int % entry.getValue();
	    }
	    return res;
	  }
	  public static String repeat(String s, int n) {
	    if(s == null) {
	        return null;
	    }
	    final StringBuilder sb = new StringBuilder();
	    for(int i = 0; i < n; i++) {
	        sb.append(s);
	    }
	    return sb.toString();
	  }
	  //hàm chuyển số nguyên sang bảng chữ cái
	  public static String CharNumerals(int Int) {
		    LinkedHashMap<Integer , String> char_numerals = new LinkedHashMap<Integer, String>();
		    char_numerals.put(1,"A");
		    char_numerals.put( 2,"B");
		    char_numerals.put(3,"C");
		    char_numerals.put(4,"D");
		    char_numerals.put( 5,"E");
		    char_numerals.put(6,"F");
		    char_numerals.put(7,"G");
		    char_numerals.put(8,"H");
		    char_numerals.put(9,"I");
		    char_numerals.put(10,"J");
		    char_numerals.put(11,"K");
		    char_numerals.put(12,"L");
		    char_numerals.put(13,"M");
		    char_numerals.put(14,"N");
		    char_numerals.put(15,"O");
		    char_numerals.put(16,"P");
		    char_numerals.put(17,"Q");
		    char_numerals.put(18,"R");
		    char_numerals.put(19,"S");
		    char_numerals.put(20,"T");
		    char_numerals.put(21,"U");
		    char_numerals.put(22,"V");
		    char_numerals.put(23,"W");
		    char_numerals.put(24,"X");
		    char_numerals.put(25,"Y");
		    char_numerals.put(26,"Z");
		    String res = "";
		     res = (String) char_numerals.get(Int);
		    return res;
		  }
		  public static String convertNumberToString (Long number){
			  Locale localeVN = new Locale("vi", "VN");
			  NumberFormat vn = NumberFormat.getInstance(localeVN);
			  String str2 = vn.format(number);
			  return str2;
		  }
		  
		 
}
