package por.ayf.eng.sp.security;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encrypter {
	
	public static boolean isEncrypted = false;
	
	public static void aesEncrypt (String keyWord, String path) throws Exception {
	    // Para que la clave de encriptación AES tiene que tener al menos 16 bytes/caracteres. De otra forma, no será una clave válida.
	    Key key = new SecretKeySpec(keyWord.getBytes(),  0, 16, "AES");
		
		Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
        aes.init(Cipher.ENCRYPT_MODE, key);
        
        File fileDatabase = new File(path);
        byte[] bytesEncrypted = aes.doFinal(Files.readAllBytes(fileDatabase.toPath()));

		if (bytesEncrypted != null) {
			FileOutputStream fos = new FileOutputStream(fileDatabase);
			fos.write(bytesEncrypted);
			fos.close();
		}
	}
	
	public static void aesDecrypt (String keyWord, String path) throws Exception {
		 // Para que la clave de encriptación AES tiene que tener al menos 16 bytes/caracteres. De otra forma, no será una clave válida.
	    Key key = new SecretKeySpec(keyWord.getBytes(),  0, 16, "AES");
		
		Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
        aes.init(Cipher.DECRYPT_MODE, key);

		File fileDatabase = new File(path);
		byte[] bytesDecrypted = aes.doFinal(Files.readAllBytes(fileDatabase.toPath()));
		 
		if (bytesDecrypted != null) {
			FileOutputStream fos = new FileOutputStream(fileDatabase);
			fos.write(bytesDecrypted);
			fos.close();
		}
	}
}
