package me.tsccoding.mongodb;
import me.tsccoding.mongodb.Container;
import java.util.*;
import javax.naming.ldap.Rdn;
import java.io.*;
import java.nio.file.*;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import static com.mongodb.client.model.Filters.*;
import org.bson.Document;
import java.util.logging.Level;
import org.bson.conversions.Bson;
import java.util.*;

import javax.crypto.NoSuchPaddingException;
import javax.naming.ldap.Rdn;

import java.io.*;
import java.nio.file.*;

public class Encapsulation1 {

    public static void Write_File(Container c) throws IOException{
        
        try{
            /*FileOutputStream file_out = new FileOutputStream("E:\\College\\Sem_7\\LY_Project\\Encapsulation\\encap_file2.sec");
            ObjectOutputStream obj_out = new ObjectOutputStream(file_out);

            obj_out.writeObject(c);
            obj_out.close();
            file_out.close();*/

            RandomAccessFile file_out = new RandomAccessFile("encap_filenew.sec","rw");

            
            /*file_out.write(c.file_extension, 12, c.file_extension.length);

            file_out.write(c.byte_file, 15, c.byte_file.length);*/

            file_out.write(c.sender_id);
            file_out.write(c.receiver_id);
            file_out.write(c.file_extension);
            //file_out.write("\n".getBytes());
            //file_out.write(c.secretkey);
            file_out.write("\n".getBytes());
            file_out.write(c.byte_file);
            file_out.write("\n".getBytes());
            file_out.write(c.iv);
            
            
            
            file_out.close();
            

            System.out.println("Encapsulated file successfully created!");
        }

        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws FileNotFoundException,IOException,NoSuchPaddingException{

        Container c = new Container();

        File_AES AESUtil = new File_AES();

        Path file_path = Paths.get("C:\\Users\\Zain\\Documents\\GitHub\\Money-Manager-v1.0.0\\4.Add_Income1.jpg");

        String file_ext = file_path.toString(); 
        
        //_____Name To ID test inputs________

        Scanner input1 = new Scanner(System.in);
        System.out.println("Enter Sender Username : ");
        String s_uname = input1.next();

        Scanner input2 = new Scanner(System.in);
        System.out.println("Enter Reciever Username : ");
        String r_uname = input2.next();

        String s_id = find(s_uname);
        System.out.println(s_id);
        String r_id = find(r_uname);
        System.out.println(r_id);
        
        
        c.file_extension = file_ext.substring(file_ext.indexOf(".")).getBytes();
        
        byte[] byte_file = Files.readAllBytes(file_path);
        
        try {
            String string_file = Base64.getEncoder().encodeToString(byte_file);

            String keyString = File_AES.Key_Gen(r_id, s_id);
            System.out.println(keyString);
            
            //c.secretkey = keyString.getBytes();

            byte[] iv = File_AES.generateIv();
            c.iv = iv;
            string_file = File_AES.EncDriver(keyString, string_file, iv);
            System.out.println(string_file.substring(0,4));
            c.byte_file = string_file.getBytes();
        } 
        catch (Exception e) 
        {

        }

        byte[] send_temp= s_uname.getBytes();
        c.sender_id = ensureSize(send_temp, 10);
        byte[] rec_temp= r_uname.getBytes();
        c.receiver_id = ensureSize(rec_temp, 10);
        
        Write_File(c);
    }
     //__________________________ADDED CODE START_____________________________________

    public static String find(String uname)
        {
        String uri = "mongodb+srv://admin:admin@testcluster.yvs3ank.mongodb.net/";
        MongoClientURI clientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(clientURI);

        MongoDatabase mongoDatabase = mongoClient.getDatabase("users");
        MongoCollection collection = mongoDatabase.getCollection("auth");
        
        java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
        Bson projectionFields = Projections.fields(
                    Projections.include("uniqueid"),
                    Projections.excludeId());
            MongoCursor<Document> cursor = collection.find(eq("username",uname)).projection(projectionFields).iterator();
            try {
                    String name = cursor.next().toString();
                    int startIndex = name.lastIndexOf("=") + 1;
                    int endIndex = name.lastIndexOf("}") - 1;
                    String result = name.substring(startIndex, endIndex);
                    return result;
                }
            finally {
                cursor.close();
            }
        }

        public static byte[] ensureSize(byte[] byteArray, int size) {
        if (byteArray.length >= size) {
            return byteArray; // No need to modify if size is already greater than or equal to 10
        }
        
        byte[] newArray = new byte[size];
        System.arraycopy(byteArray, 0, newArray, 0, byteArray.length);
        
        // Append redundant backslashes until the size becomes 10
        for (int i = byteArray.length; i < size; i++) {
            newArray[i] = '\\';
        }
        
        return newArray;
    }

    //__________________________ADDED CODE END_____________________________________
}

