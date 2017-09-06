package cn.convenience.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;

/**
 * 工具
 * @author DerrickZheng
 *
 */
public class Util {
	
	/*
	 * 生成随机字符串
	 * length表示生成字符串的长度
	 */
	public static String getRandomString(int length) { 
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	}   
	
	/*
	 * signature	鉴权签名    请求拉去证件库接口使用
	 * 		把appId，appSecret，nonce，timestamp值进行ASCII码从小到大排序
	 */
	public static String getSignatureSort(String[] args){
		String signature = "";
		
		Arrays.sort(args);
		
		for (String string : args) {
			signature+=string;
		}
		System.out.println("排序结果--"+signature);
		return getSha1(signature);
	}
	
	/*
	 * signature	鉴权签名   实名核身接口使用
	 */
	public static String getSignature(String secretKey, String plainText) 
			throws Exception{
		System.out.println("plainText:"+plainText);
	    byte[] bin = HashHmac_Sha1.getSignature(plainText, secretKey);
		byte[] all = new byte[bin.length + plainText.getBytes().length];
		System.arraycopy(bin, 0, all, 0, bin.length);
		System.arraycopy(plainText.getBytes(), 0, all, bin.length
				, plainText.getBytes().length);
		return new String(Base64.encodeBase64(all));
	}
	
	/*
	 * sig串拼接 md5加密后
	 */
	public static String getSig(String[] args) 
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		String s = "";
		for (String string : args) {
			s+=string+"-";
		}
		s+="authkey";
		
		return MD5.md5LowerCase(s);
	}
	
	/*
	 * 生成Unix的时间戳 (不含毫秒)
	 */
	public static Long getCurrentTime() {
        //毫秒时间转成分钟
        double doubleTime = (Math.floor(System.currentTimeMillis() / 60000L));
        //往下取整 1.9=> 1.0
        long floorValue = new Double(doubleTime).longValue();
        return floorValue * 60;
    }
	
	/*
	 * sha1 加密
	 */
	public static String getSha1(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];      
            }
            return new String(buf);
        } catch (Exception e) {
        	System.out.println("sha1异常:  "+e.getMessage());
            return null;
        }
    }
	/**
	 * base64解码
	 * @param data
	 * @param key
	 * @return
	 */
	public static String decodeBase(String data,String key){
		String str="";
		try {
			str=AES.decrypt(Base64.decodeBase64(data),"26cb3f325891d42bec10efdeec9a4f95".getBytes()).toString();
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
