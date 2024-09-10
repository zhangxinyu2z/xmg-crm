// $Id: EncryptHelper.java 878 2017-03-04 09:29:42Z admin $
package com._520it.crm.utils;

import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.util.*;

/**
 * This helper class supports various of encryption methods.
 * 
 * @version $Revision: 878 $ $Date: 2017-03-04 17:29:42 +0800 (周六, 04 三月 2017) $
 */
public class EncryptHelper {
	/**
	 * 主要是把所有的参数拼装成字符串，然后再加密。字符串的形式都是keyA=value1:keyB=value2...
	 * 
	 * @param xhm
	 * @return
	 */
	public static String getCheckSum(Map<String, String> nvMap) {

		final String SEPARATOR = ":";
		String checksumString = "";
		String computedChecksum = null;

		// extract everything in the received map and put into alphabetical
		// order according to the key into a String

		TreeMap<String, String> treeMap = new TreeMap<String, String>(nvMap);
		Set<String> keys = treeMap.keySet();
		Collection<String> values = treeMap.values();

		if (keys.size() != values.size()) {
			// System.out.println("key/value count mismatch " + keys.size() +
			// " "
			// + values.size());
		}

		Iterator<String> itKeys = keys.iterator();
		Iterator<String> itValues = values.iterator();

		StringBuffer sb = new StringBuffer();

		while (itKeys.hasNext()) {
			sb.append(((String) itKeys.next()) + "=" + ((String) itValues.next()) + SEPARATOR);
		}

		// strip off last SEPARATOR
		if (sb.length() > 0) {
			checksumString = sb.substring(0, sb.length() - 1);
		}
		// compute MD5 on the String for required Request and response.
		// This is one of the Hash algorithm to digest the message and send
		// across the network.
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			computedChecksum = toHex(md.digest(checksumString.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return computedChecksum;
	}

	/**
	 * MD5加密
	 * 
	 * @param ba
	 * @return
	 */
	public static String toHex(byte[] ba) {
		char hexdigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
				'e', 'f' };
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < ba.length; i++) {
			sb.append(hexdigit[(ba[i] >> 4) & 0x0F]);
			sb.append(hexdigit[ba[i] & 0x0F]);
		}

		return sb.toString();
	}

	public static String md5(String str) {
		if (str == null)
			return null;
		
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(str.getBytes(), 0, str.length());
			String hashedPass = new BigInteger(1, messageDigest.digest()).toString(16);
			if (hashedPass.length() < 32) {
				hashedPass = "0" + hashedPass;
			}
			return hashedPass.toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	public static String md5(byte[] bytes) {
		if (bytes == null)
			return null;

		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(bytes, 0, bytes.length);
			String hashedPass = new BigInteger(1, messageDigest.digest()).toString(16);
			if (hashedPass.length() < 32) {
				hashedPass = "0" + hashedPass;
			}
			return hashedPass.toUpperCase();
		}
		catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	private static final String passwordStr = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static String getRandomPassword() {

		StringBuffer sb = new StringBuffer();
		Random r = new Random();

		for (int i = 0; i < 8; i++) {
			sb.append(passwordStr.charAt(r.nextInt(passwordStr.length())));
		}
		return sb.toString();
	}

	/**
	 * Encrypt the given string by MD5 arithmetic.
	 * 
	 * @param data
	 * @return
	 */
	public static String md5_old(String data) {
		if (data == null)
			return null;

		StringBuffer buf = new StringBuffer(32);
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(data.getBytes("ISO-8859-1"));
			byte bytes[] = md5.digest();
			for (int i = 0; i < bytes.length; i++) {
				buf.append(Integer.toHexString((0x000000ff & bytes[i]) | 0xffffff00).substring(6));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return buf.toString();
	}

	/*   *//**
	 * normalize url and calculate its md5 code for it
	 * 
	 * @param url
	 * @return
	 */
	/*
	 * public static String normalizeUrlMd5(String url) { if ((url == null) ||
	 * (url.length() == 0)) { return "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF"; } else
	 * { UrlParser up = null; String md5 = null; try { up = new UrlParser(url);
	 * String normalizeUrl = up.getNormalizeUrl(); md5 =
	 * md5(normalizeUrl.toLowerCase()); } catch (MalformedURLException ex) {
	 * System.out.println("URL:" + url); System.out.println(ex.getMessage());
	 * ex.printStackTrace(); md5 = md5(url); } return md5; } }
	 */

	private static String SECRET_KEY = "B@4f9fdc&1ETCde";

	/**
	 * Encrypt the given string with the given key in DES-3 arithmetic.
	 * 
	 * @param data
	 *            the given data to be encrypted
	 * @param key
	 *            the given key to encrypt the given data
	 * @return
	 */
	public static String encrypt3DES(String data, byte[] key) {
		if (data == null || key == null)
			return data;

		try {
			byte[] enData = doCrypt("DESede", Cipher.ENCRYPT_MODE, key, data.getBytes("UTF-16LE"));
			return byte2hex(enData);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Encrypt the given string with the default secret key in DES-3 arithmetic.
	 * 
	 * @param data
	 *            the given data to be encrypted
	 * @return
	 */
	public static String encrypt3DES(String data) {
		try {
			return encrypt3DES(data, SECRET_KEY.getBytes("ISO-8859-1"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Decrypt the given string with the given key in DES-3 arithmetic.
	 * 
	 * @param data
	 *            the given data to be decrypted
	 * @param key
	 *            the given key to decrypt the given data
	 * @return
	 */
	public static String decrypt3DES(String data, byte[] key) {
		if (data == null || key == null)
			return data;

		try {
			byte[] enData = hex2byte(data);
			byte[] deData = doCrypt("DESede", Cipher.DECRYPT_MODE, key, enData);
			return new String(deData, "UTF-16LE");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Decrypt the given string with the default secret key in DES-3 arithmetic.
	 * 
	 * @param data
	 *            the given data to be decrypted
	 * @return
	 */
	public static String decrypt3DES(String data) {
		try {
			return decrypt3DES(data, SECRET_KEY.getBytes("ISO-8859-1"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// Private Methods ---------------------------------------------------------
	private static byte[] doCrypt(String algorithm, int mode, byte[] key, byte[] data) {

		byte[] result = new byte[] {};
		try {
			byte[] md5Key = md5(new String(key, "ISO-8859-1")).substring(0, 24).getBytes(
					"ISO-8859-1");
			SecretKey deskey = new javax.crypto.spec.SecretKeySpec(md5Key, algorithm);
			Cipher c1 = Cipher.getInstance(algorithm);
			c1.init(mode, deskey);
			result = c1.doFinal(data);
		} catch (UnsupportedEncodingException e) {
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Convert a byte array to 16 bits encoded string
	 * 
	 * @param byte[] b bytes to be converted
	 * @return
	 */
	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + "";
		}
		return hs.toUpperCase();
	}

	/**
	 * Convert a 16 bits encoded string to a byte array.
	 * 
	 * @param s
	 * @return
	 */
	private static byte[] hex2byte(String s) {

		int length = s.length() / 2;
		byte[] bs = new byte[length];

		for (int i = 0; i < length; i++) {

			String substr = s.substring(i * 2, (i + 1) * 2);
			bs[i] = (byte) Integer.parseInt(substr, 16);
		}
		return bs;
	}

	public static void main(String[] args) {
		System.out.println(EncryptHelper.md5("admin"));
		System.out.println(EncryptHelper.md5("123"));
		System.out.println(EncryptHelper.md5("xxxxalkjsdlkfj��ð�����-"));
	}

	public static void main2(String[] args) throws Exception {
		String inputData = "Hello, China !";
		String enData = EncryptHelper.encrypt3DES(inputData, "MY_SECRET_KEY".getBytes());
		String deData = EncryptHelper.decrypt3DES(enData, "MY_SECRET_KEY".getBytes());

		System.out.println("Source data: " + inputData + "[length:" + inputData.length() + "]");
		System.out.println("Encrypt data: " + enData + "[length:" + enData.length() + "]");
		System.out.println("Decrypt data: " + deData + "[length:" + deData.length() + "]");
	}
}
