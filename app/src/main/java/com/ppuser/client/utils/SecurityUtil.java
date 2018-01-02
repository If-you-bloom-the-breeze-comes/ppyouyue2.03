package com.ppuser.client.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SecurityUtil {
	public static String encrypt(String input, String key){
	  byte[] crypted = null;
	  try{
	    SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
	      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	      cipher.init(Cipher.ENCRYPT_MODE, skey);
	      crypted = cipher.doFinal(input.getBytes());
	    }catch(Exception e){
	    	System.out.println(e.toString());
	    }
	    return new String(Base64.encode(new String(Base64.encode(crypted,Base64.DEFAULT)).getBytes(),Base64.DEFAULT));
	}

	public static String decrypt(String input, String key){
	    byte[] output = null;
	    try{
	        SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
	        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	        cipher.init(Cipher.DECRYPT_MODE, skey);
	        output = cipher.doFinal(Base64.decode(input,Base64.DEFAULT));
	    }catch(Exception e){
	        System.out.println(e.toString());
	    }
		if (output==null){
			return null;
		}else {
			return new String(output);
		}
	}
	
	public static void main(String[] args) {
	  String key = "1234567891234567";
	  String data = "{\"username\":\"test1\",\"password\":\"123456\",\"service\":\"Login.Login\"}";
	  System.out.println(SecurityUtil.decrypt(SecurityUtil.encrypt(data, key), key));
	  System.out.println(SecurityUtil.encrypt(data, key));
	  String deCodeData = "gZNIbRlOm456RTbdwukxXe4rrvWc/bslGeW528+s4H0KYzhdii3ktIdNJknen3eAfVrGY1t3mposE+hMjrpUrX3o3HAtij3oN1+zfQmdv5S+rWw9vTbTt22bCLVFz9hEu4RWaoWe79eLrKvZk3K8XuqPjJ3DFECQN4pNdsVM9N+lS3xl2Wr3b7GfkMCnGfGjxr0VMgek1gmKBPh8S+4rrdnsqVz40/PAO8+YIm+li4Q=";
	  System.out.println(SecurityUtil.decrypt(deCodeData, "1234567891234567"));
	}
}