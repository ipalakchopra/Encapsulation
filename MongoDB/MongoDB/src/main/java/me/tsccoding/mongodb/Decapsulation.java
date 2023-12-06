package me.tsccoding.mongodb;
import me.tsccoding.mongodb.Container;
import java.util.*;
import java.io.*;
import java.nio.*;
import java.nio.file.*;
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

public class Decapsulation {
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        
        Container c_decap = new Container();

        try{
            RandomAccessFile file_in = new RandomAccessFile("enca_file.sec","r");

            //_____Name To ID test unputs______

            String s_uname="john";
            String r_uname="user";

            String sen_id = find(s_uname);
            System.out.println(sen_id);
            String rec_id = find(r_uname);
            System.out.println(rec_id);

            //__________________________________

            String u_id = "keiwp89XM8";

            c_decap.sender_id = new byte[10];
            c_decap.receiver_id = new byte[10];
            
            file_in.read(c_decap.sender_id);
            file_in.read(c_decap.receiver_id);
                    
            String s_id = new String(c_decap.sender_id);
            String r_id = new String(c_decap.receiver_id);

            System.out.println(s_id);
            System.out.println(r_id);

            System.out.println(u_id);

            if(u_id.equals(r_id)){
                //System.out.println("hello");
                
                String fl_ext = file_in.readLine();
                c_decap.file_extension = fl_ext.getBytes();
                System.out.println(c_decap.file_extension.length);
                
                int file_sz = ((int)file_in.length()/1024*1024) - 20 - c_decap.file_extension.length;

                System.out.println(file_sz);

                c_decap.byte_file = new byte[file_sz];
                
                file_in.read(c_decap.byte_file);
                
                String filestr = new String(c_decap.byte_file);
                //file_in.read(c_decap.byte_file);

                System.out.println(fl_ext);
                System.out.println(filestr);

                Path decap_path = Paths.get(String.format("decap_file%s",fl_ext));
                Files.write(decap_path, c_decap.byte_file);

            }


            file_in.close();

        }

        finally{
            //Path decap_path = Paths.get(String.format("C:\\Users\\ppk\\Documents\\Encapsulation\\decap_file%s",c_decap.file_extension));
            //Files.write(decap_path, c_decap.byte_file);
        }
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
