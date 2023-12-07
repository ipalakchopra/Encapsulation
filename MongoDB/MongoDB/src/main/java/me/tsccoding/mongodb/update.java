package me.tsccoding.mongodb;
import static com.mongodb.client.model.Filters.eq;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import java.util.Base64;
import org.bson.Document;
import java.util.logging.Level;
import org.bson.conversions.Bson;

public class update {
    public static void main(String args[]) throws NoSuchAlgorithmException {

        String uri = "mongodb+srv://admin:admin@testcluster.yvs3ank.mongodb.net/";
        MongoClientURI clientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(clientURI);

        MongoDatabase mongoDatabase = mongoClient.getDatabase("users");
        MongoCollection collection = mongoDatabase.getCollection("auth");
        
        java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);

        //Login
        Scanner input1 = new Scanner(System.in);
        System.out.println("Enter Current Username : ");
        String username = input1.next();

        Document founduser = (Document) collection.find(new Document("username", username)).first();
        if(founduser == null)
       {
            System.out.println("User not found");
            System.exit(0);
       }

        Scanner input2 = new Scanner(System.in);
        System.out.println("Enter Current Password : ");
        String password = input2.next();

        String stored_salt = find(username);
        String hashed_password=hashPassword(password, stored_salt);

        Document foundpass = (Document) collection.find(new Document("password", hashed_password)).first();;

       if(founduser == null)
       {
            System.out.println("User not found");
       }
       else if(founduser != null && foundpass == null){
        System.out.println("Inncorrect password! ");
       }
       else{
            System.out.println("Authentication Success!");

            System.out.println("Enter new Username : ");
            String new_username = input1.next();

           System.out.println("Enter new Password : ");
           String new_password = input2.next();
           String hashed_new_password=hashPassword(new_password, stored_salt);

            Bson updatedvalue = new Document("username", new_username);
            Bson updateoperation = new Document("$set", updatedvalue);
            collection.updateOne(founduser,updateoperation);
            Document foundpass1 = (Document) collection.find(new Document("password", hashed_password)).first();
            Bson updatedvalue1 = new Document("password", hashed_new_password);
            Bson updateoperation1 = new Document("$set", updatedvalue1);
            collection.updateOne(foundpass1,updateoperation1);
            System.out.println("User Updated Successfully!");

       }
    }

    //_____________________HASHING__________________________

 // Method to hash the password with the given salt
 private static String hashPassword(String enteredpassword, String storedSalt) {
     try {
          byte[] salt = Base64.getDecoder().decode(storedSalt);
         MessageDigest md = MessageDigest.getInstance("SHA-256");
         md.update(salt);
         byte[] hashedBytes = md.digest(enteredpassword.getBytes(StandardCharsets.UTF_8));

         // Convert the byte array to a hexadecimal string
         return bytesToHex(hashedBytes);
     } catch (NoSuchAlgorithmException e) {
         e.printStackTrace();
         return null;
     }
 }
 private static String bytesToHex(byte[] bytes) {
     StringBuilder hexStringBuilder = new StringBuilder(2 * bytes.length);
     for (byte b : bytes) {
         hexStringBuilder.append(String.format("%02x", b & 0xff));
     }
     return hexStringBuilder.toString();
 }


 public static String find(String uname)
 {
 String uri = "mongodb+srv://admin:admin@testcluster.yvs3ank.mongodb.net/";
 MongoClientURI clientURI = new MongoClientURI(uri);
 MongoClient mongoClient = new MongoClient(clientURI);

 MongoDatabase mongoDatabase = mongoClient.getDatabase("users");
 MongoCollection collection = mongoDatabase.getCollection("auth");
 
 java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
 Bson projectionFields = Projections.fields(
             Projections.include("salt"),
             Projections.excludeId());
     MongoCursor<Document> cursor = collection.find(eq("username",uname)).projection(projectionFields).iterator();
     try {
             String name = cursor.next().toString();
             int startIndex = name.lastIndexOf("salt") + 5;
             int endIndex = name.lastIndexOf("==");
             String result = name.substring(startIndex, endIndex);
             return result;
         }
     finally {
         cursor.close();
     }
 }
}