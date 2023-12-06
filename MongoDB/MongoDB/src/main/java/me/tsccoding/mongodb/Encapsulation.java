package me.tsccoding.mongodb;
import me.tsccoding.mongodb.Container;
import java.util.*;
import javax.naming.ldap.Rdn;
import java.io.*;
import java.nio.file.*;
import java.util.Random;
import java.util.Scanner;
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


public class Encapsulation {

    public static void Write_File(Container c) throws IOException{
        
        try{
            /*FileOutputStream file_out = new FileOutputStream("E:\\College\\Sem_7\\LY_Project\\Encapsulation\\encap_file2.sec");
            ObjectOutputStream obj_out = new ObjectOutputStream(file_out);

            obj_out.writeObject(c);
            obj_out.close();
            file_out.close();*/

            RandomAccessFile file_out = new RandomAccessFile("enca_file.sec","rw");

            
            /*file_out.write(c.file_extension, 12, c.file_extension.length);

            file_out.write(c.byte_file, 15, c.byte_file.length);*/

            file_out.write(c.sender_id);
            file_out.write(c.receiver_id);
            file_out.write(c.file_extension);
            file_out.write("\n".getBytes());
            file_out.write(c.byte_file);
             
            file_out.close();
            

            System.out.println("Encapsulated file successfully created!");
        }

        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws FileNotFoundException,IOException{

        Container c = new Container();

        Path file_path = Paths.get("C:\\Users\\Zain\\Documents\\GitHub\\Encapsulation\\test_pdf.pdf");

        String file_ext = file_path.toString(); 
        
        //_____Name To ID test inputs________

        String s_uname="john";
        String r_uname="user";

        String s_id = find(s_uname);
        System.out.println(s_id);
        String r_id = find(r_uname);
        System.out.println(r_id);

        //_________________________________

        c.file_extension = file_ext.substring(file_ext.indexOf(".")).getBytes();
        
        c.byte_file = Files.readAllBytes(file_path);
    
        c.sender_id = s_id.getBytes();
        
        c.receiver_id = r_id.getBytes();
        
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
    //__________________________ADDED CODE END_____________________________________
}
