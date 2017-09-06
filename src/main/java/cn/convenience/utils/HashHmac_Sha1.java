package cn.convenience.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * HmacSHA1 加密
 * @author DerrickZheng
 *
 */
public class HashHmac_Sha1 {

	private static final String HMAC_SHA1 = "HmacSHA1";

	public static byte[] getSignature(String data, String key) throws Exception {
		Mac mac = Mac.getInstance(HMAC_SHA1);
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes("UTF-8"),
				mac.getAlgorithm());
		mac.init(signingKey);

		return mac.doFinal(data.getBytes("UTF-8"));
	}
}
