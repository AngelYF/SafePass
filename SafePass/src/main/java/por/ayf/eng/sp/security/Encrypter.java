package por.ayf.eng.sp.security;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Encrypter {
	
	public static boolean isEncrypted = false;
	
	private static SecretKey generateKey(int keySize) throws NoSuchAlgorithmException {
	    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
	    keyGenerator.init(keySize);
	    SecretKey key = keyGenerator.generateKey();
	    return key;
	}
	
	private static IvParameterSpec generateIv() {
	    byte[] iv = new byte[16];
	    new SecureRandom().nextBytes(iv);
	    return new IvParameterSpec(iv);
	}
	
	public static void aesEncrypt (String keyWord, String path) throws Exception {
		
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);

		KeySpec spec = new PBEKeySpec(keyWord.toCharArray(), salt, 65536, 256); // AES-256
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] key = f.generateSecret(spec).getEncoded();
		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
		IvParameterSpec ivParameterSpec = Encrypter.generateIv();
		
		Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aes.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
        
        File fileDatabase = new File(path);
        byte[] bytesEncrypted = aes.doFinal(Files.readAllBytes(fileDatabase.toPath()));

		if (bytesEncrypted != null) {
			FileOutputStream fos = new FileOutputStream(fileDatabase);
			fos.write(bytesEncrypted);
			fos.close();
		}
	}
	
	public static void aesDecrypt (String keyWord, String path) throws Exception {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);

		KeySpec spec = new PBEKeySpec(keyWord.toCharArray(), salt, 65536, 256); // AES-256
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] key = f.generateSecret(spec).getEncoded();
		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
		IvParameterSpec ivParameterSpec = Encrypter.generateIv();
		
		Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
		aes.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

		File fileDatabase = new File(path);
		byte[] bytesDecrypted = aes.doFinal(Files.readAllBytes(fileDatabase.toPath()));
		 
		if (bytesDecrypted != null) {
			FileOutputStream fos = new FileOutputStream(fileDatabase);
			fos.write(bytesDecrypted);
			fos.close();
		}
	}
}
