package me.tsccoding.mongodb;
import java.util.Random;
import java.util.Scanner;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.logging.Level;
import org.bson.conversions.Bson;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Register {
    public static void main(String args[]) throws NoSuchAlgorithmException {

        String uri = "mongodb+srv://admin:admin@testcluster.yvs3ank.mongodb.net/?retryWrites=true&w=majority";
        MongoClientURI clientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(clientURI);

        MongoDatabase mongoDatabase = mongoClient.getDatabase("users");
        MongoCollection collection = mongoDatabase.getCollection("auth");
        
        java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);

        //Register
        Scanner input3 = new Scanner(System.in);
        System.out.println("Enter Username : ");
        String username = input3.next();

        Scanner input4 = new Scanner(System.in);
        System.out.println("Enter Password : ");
        String password = input4.next();

        // Password Hashing
        byte[] salt = generateSalt();
        String str_salt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashPassword(password, salt);

    //___________ Unique ID Generation START_________

    String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
    String numbers = "0123456789";


    String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;


    StringBuilder sb = new StringBuilder();


    Random random = new Random();

 
    int length = 10;

    for(int i = 0; i < length; i++) {

 
      int index = random.nextInt(alphaNumeric.length());


      char randomChar = alphaNumeric.charAt(index);

      sb.append(randomChar);
    }

    String randomString = sb.toString();


 //___________ Unique ID Generation END____________

        //Find user in DB
        Document founduser = (Document) collection.find(new Document("username", username)).first();
        Document foundid = (Document) collection.find(new Document("uniqueid", randomString)).first();
        if(founduser != null)
        {    
            System.out.println("Username already Exists!");
        }
       else
        {
            //ADD user details to DB
        if(foundid == null)
        {
        Document document = new Document ("username", username) ;
        document.append ("password", hashedPassword);
        document.append ("salt", str_salt);
        document.append ("uniqueid", randomString);
        collection.insertOne(document);
        System.out.println("User successfully created!");
        }
        else
        {
        System.out.println("ID already Exists!");
        }
        }
    }

// __________________________PASSWORD HASHING & SALT GEN____________________________________
            

            // Salt generation
            private static byte[] generateSalt() 
            {
                byte[] salt = new byte[16];
                new SecureRandom().nextBytes(salt);
                return salt;
            }
        
            // Password Hashing
            private static String hashPassword(String password, byte[] salt) 
            {
                String hashedPassword = null;
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    md.update(salt);
                    byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
        
                    // Convert the byte array to a hexadecimal string
                    hashedPassword = bytesToHex(hashedBytes);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                return hashedPassword;
            }
        
            // Convert byte array to hexadecimal string
            private static String bytesToHex(byte[] bytes) 
            {
                StringBuilder hexStringBuilder = new StringBuilder(2 * bytes.length);
                for (byte b : bytes) {
                    hexStringBuilder.append(String.format("%02x", b & 0xff));
                }
                return hexStringBuilder.toString();
            }
}


            //Bson updatedvalue = new Document("Age", 26);
            //Bson updateoperation = new Document("$set", updatedvalue);
            //collection.updateOne(found,updateoperation);
            //System.out.println("User Updated");