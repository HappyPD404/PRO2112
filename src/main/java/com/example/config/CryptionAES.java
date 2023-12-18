package com.example.config;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CryptionAES {
	private static SecretKeySpec secretKey;
	private static byte[] key;

	public static void setKey(final String myKey) {
		MessageDigest sha = null;
		try {
			// khoi tao set key de lam khoa bi mat va lu vao bien key
			key = myKey.getBytes("UTF-8");
			// dung MessageDigest voi thuat toan SHA-1 de ma hoa va luu ket qua
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
	        // cat key chi lay 16 dau vaf luu ket qua
			key = Arrays.copyOf(key, 16);
			// tao khoa secretKey sd key va KIEU MA HOA AES DE LUU BIEN VAO SECRETKEY
			secretKey = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static String encrypt(final String strToEncrypt, final String secret) {
		try {
			// tao set key
			setKey(secret);
			// tao cipher voi thuat toan AES/ECB/PKCS5Padding
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			// tao cipher o cd EN va khoa setcretkey
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			// ma hoa chuoi strToEncrypt thanh byte roi encode sang Base 64 roi tra ve
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}
// giai chuoi ma hoa tu strToDecrypt thanh Base64 thanh by roi tra ve chuoi
	public static String decrypt(final String strToDecrypt, final String secret) {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}
}