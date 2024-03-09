package me.tsccoding.mongodb;
import java.util.*;
import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import javax.naming.ldap.Rdn;
import java.io.*;
import java.nio.*;
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

import javax.crypto.NoSuchPaddingException;
public class Decapsulation1 {
    
    public static void main(String[] args) throws IOException, ClassNotFoundException,NoSuchPaddingException, InvalidKeySpecException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        
        Container c_decap = new Container();

        try{
            RandomAccessFile file_in = new RandomAccessFile("encap_filenew.sec","r");
            
            Scanner input1 = new Scanner(System.in);
            System.out.println("Enter Username : ");
            String uname = input1.next();

            String u_id= find(uname);

            c_decap.sender_id = new byte[10];
            c_decap.receiver_id = new byte[10];
            
            file_in.read(c_decap.sender_id);
            file_in.read(c_decap.receiver_id);

            byte[] s_uname_temp = removeBackslashes(c_decap.sender_id);
            byte[] r_uname_temp = removeBackslashes(c_decap.receiver_id);
            String s_uname = new String(s_uname_temp);
            String r_uname= new String(r_uname_temp);

            String s_id=find(s_uname);
            String r_id=find(r_uname);

            //System.out.println(s_id);
            //System.out.println(r_id);

           // System.out.println(u_id);

            if(u_id.equals(r_id)){
                //System.out.println("hello");
                
                String fl_ext = file_in.readLine();
                //String strkey = file_in.readLine();
                String strkey = File_AES.Key_Gen(r_id, s_id);
                //String iv = file_in.readLine();
                System.out.println(strkey);
                //System.out.println(iv);
                c_decap.secretkey = strkey.getBytes();
                c_decap.file_extension = fl_ext.getBytes();
                System.out.println(new String(c_decap.file_extension));
                //System.out.println(new String(c_decap.secretkey));
                
                

                /*int file_sz = ((int)file_in.length()/1024*1024) - 20 - c_decap.file_extension.length - c_decap.iv.length - c_decap.secretkey.length;

                System.out.println(file_sz);

                c_decap.byte_file = new byte[file_sz];
                
                file_in.read(c_decap.byte_file);
                
                String encfile = new String(c_decap.byte_file);*/
                String encfile = file_in.readLine();
                c_decap.iv = new byte[16];

                file_in.read(c_decap.iv);
                //System.out.println(encfile);
                String decfile;
                try {
                    System.out.println("hello");
                    decfile = File_AES.DecDriver(strkey, encfile, c_decap.iv);
                    //System.out.println(decfile);
                    byte[] dec = Base64.getDecoder().decode(decfile);
                    Path decap_path = Paths.get(String.format("decap_filenew%s",fl_ext));
                    Files.write(decap_path, dec);
                } catch (Exception e) {
                }
                

                //System.out.println(striv);
                //System.out.println(strkey);
                
                /*String filestr = new String(c_decap.byte_file);
                //file_in.read(c_decap.byte_file);

                System.out.println(fl_ext);
                System.out.println(filestr);*/

               

            }
            else{
                System.out.println("Authentication Failed! ID not Authoized");
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

        public static byte[] removeBackslashes(byte[] byteArray) {
            int newSize = byteArray.length;
            // Count number of redundant backslashes
            for (int i = byteArray.length - 1; i >= 0; i--) {
                if (byteArray[i] != '\\') {
                    break;
                }
                newSize--;
            }
            // Create a new array without redundant backslashes
            byte[] newArray = Arrays.copyOf(byteArray, newSize);
            return newArray;
        }

//__________________________ADDED CODE END_____________________________________
}
