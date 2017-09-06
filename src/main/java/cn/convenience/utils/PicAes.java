package cn.convenience.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;


public class PicAes {
	
	/**
	 * 针对图片的AES加密
	 * Base64.encodeBase64
	 * @param url 图片地址
	 * @param keyBytes 加密密钥
	 * @author DerrickZheng
	 * @return
	 */
	public static String picEncrypt(String url, byte[] keyBytes) {
		String pic = null;

		FileInputStream fis = null;// 创建文件输入流
		ByteArrayOutputStream bos = null;
		byte[] bytes = new byte[20 * 1024];// 创建一个缓冲区
		int b; // 记录读数据时的末尾位置
		try {
			fis = new FileInputStream(url);
			bos = new ByteArrayOutputStream();
			while ((b = fis.read(bytes)) != -1) {
				bos.write(bytes, 0, b);
			}
			pic = AES.encrypt(bos.toByteArray(), keyBytes);	

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fis) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return pic;
	}

	/**
	 * 针对图片AES解密
	 * @param content 	密文
	 * @param keyBytes  密钥	
	 * @param url 		解密文件存放路径
	 * @author DerrickZheng
	 */
	public static void picDecrypt(String content, byte[] keyBytes, String url) {
		byte[] aes = AES.decrypt(Base64.decodeBase64(content.getBytes()), keyBytes);
		FileOutputStream imageOutput = null;
		try {
			imageOutput = new FileOutputStream(new File(url));
			imageOutput.write(aes);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != imageOutput) {
					imageOutput.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
