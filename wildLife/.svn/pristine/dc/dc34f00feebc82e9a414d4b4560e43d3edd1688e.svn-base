package com.globits.wl.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream; 

public class WLStringUtil {
	/**
	 * Kiểm tra xem string a có chứa string b hay không
	 * @param a
	 * @param b
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Boolean compareUTF8String(String a,String b) throws UnsupportedEncodingException {
		String str1 = a.toLowerCase();//convertToBinary(a.toLowerCase(),"UTF-8");
	    String str2 = b.toLowerCase();//convertToBinary(b.toLowerCase(),"UTF-8");
//	    byte[] arr = str1.getBytes("UTF-8");
//	    byte[] brr = str2.getBytes("UTF-8");
////	    System.out.println(arr.toString());
//	    String arr1="";
//	    String brr1="";
//	    for(byte z: arr) {
//	    	arr1+=z;
////	         System.out.println(z);
//	      }
////	      System.out.println(brr.toString());
//	    for(byte z: brr) {
//	    	brr1+=z;
////	         System.out.println(z);
//	      }
//	    System.out.println(arr.toString().contains(brr.toString()));
//	    System.out.println(arr1.contains(brr1));
	    return str1.contains(str2);
	}
	
//	public static String convertToBinary(String input, String encoding) 
//		      throws UnsupportedEncodingException {
//		    byte[] encoded_input = Charset.forName(encoding)
//		      .encode(input)
//		      .array();  
//		    return IntStream.range(0, encoded_input.length)
//		        .map(i -> encoded_input[i])
//		        .mapToObj(e -> Integer.toBinaryString(e ^ 255))
//		        .map(e -> String.format("%1$" + Byte.SIZE + "s", e).replace(" ", "0"))
//		        .collect(Collectors.joining(" "));
//		}
}
