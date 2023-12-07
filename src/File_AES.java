package src;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Base64;
public class File_AES {

    public static byte[] generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    public static SecretKey getKeyFromPassword(String password, String salt)
    throws NoSuchAlgorithmException, InvalidKeySpecException {
    
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
            .getEncoded(), "AES");
        return secret;
    }

    public static String encrypt(String algorithm, String input, SecretKey key,
        IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
        BadPaddingException, IllegalBlockSizeException {
        
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] cipherText = cipher.doFinal(input.getBytes());
            return Base64.getEncoder()
                .encodeToString(cipherText);
        } catch (Exception e) {
            return null;
        }
    }

    public static String decrypt(String algorithm, String cipherText, SecretKey key,
        IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
        BadPaddingException, IllegalBlockSizeException {
        
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
            return new String(plainText);
        } catch (Exception e) {
            return null;
        }
        
    }


    static String Key_Gen(String r_id, String s_id) 
        throws InvalidKeySpecException, NoSuchAlgorithmException, 
        InvalidAlgorithmParameterException, NoSuchPaddingException {
        
        SecretKey key = null;
        String keyString;

        try {
            
            key = File_AES.getKeyFromPassword(r_id,s_id);
            //String plainText = "hello";
            //String algorithm = "AES/CBC/PKCS5Padding";
            //String cipherText = File_AES.encrypt(algorithm, plainText, key, ivParameterSpec);
            //String decryptedCipherText = File_AES.decrypt(algorithm,cipherText, key, ivParameterSpec);
            //System.out.println(decryptedCipherText);
            
        } catch (Exception e) {
            
        }
        keyString = Base64.getEncoder().encodeToString(key.getEncoded());
        return keyString;
    }

    static IvParameterSpec IVGen(byte[] iv){
        return new IvParameterSpec(iv);
    }

    static String EncDriver(String key_String, String fileString, byte[] iv) throws NoSuchPaddingException{
        String encFile;
        
        try {
            String algorithm = "AES/CBC/PKCS5Padding";

            //String key_String = File_AES.Key_Gen(r_id,s_id);

            byte[] decodedKey = Base64.getDecoder().decode(key_String);
            SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES"); 

            //byte[] iv = File_AES.generateIv();
            IvParameterSpec ivParameterSpec = File_AES.IVGen(iv);

            encFile = File_AES.encrypt(algorithm, fileString, key, ivParameterSpec);

        } catch (Exception e) {
            return null;
        }
        return encFile;

    }

    static String DecDriver(String keyString, String EncFileString, byte[] iv) throws NoSuchPaddingException{
        String decFile;
        System.out.println("j");
        try {
            String algorithm = "AES/CBC/PKCS5Padding";

            byte[] decodedKey = Base64.getDecoder().decode(keyString);
            SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
            
            IvParameterSpec ivParameterSpec = File_AES.IVGen(iv);
            System.out.println("ff");
            decFile = File_AES.decrypt(algorithm, EncFileString, key, ivParameterSpec);
            //System.out.println(EncFileString);

        } catch (Exception e) {
            System.out.println("err");
            return null;
        }
        return decFile;


    }
    public static void main(String[] args) {
        String s_id = "DL0KG6WZwu";
        String r_id = "ifkhtCNnIc";
        try {

            Path file_path = Paths.get("E:\\College\\Sem_7\\LY_Project\\Encapsulation\\test_pdf.pdf");
            String file_ext = file_path.toString();
            file_ext = file_ext.substring(file_ext.indexOf("."));

            byte[] enc_fileb = Files.readAllBytes(file_path);
            String enc_file = Base64.getEncoder().encodeToString(enc_fileb);

            System.out.println(enc_file);
            String keyString = File_AES.Key_Gen(r_id, s_id);
            byte[] iv = File_AES.generateIv();


            enc_file = File_AES.EncDriver(keyString, enc_file, iv);

            String dec_File = File_AES.DecDriver(keyString, enc_file,iv);

            System.out.println(dec_File);

            byte[] dec = Base64.getDecoder().decode(dec_File);
            
            Path decap_path = Paths.get(String.format("decapepap_file%s",file_ext));
            Files.write(decap_path, dec);

            //Key_Gen(r_id,s_id);
        } catch (Exception e) {
            // TODO: handle exception
        }
        
        
    }


}
