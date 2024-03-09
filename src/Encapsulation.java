
package src;
import src.Container;

import java.util.*;

import javax.crypto.NoSuchPaddingException;
import javax.naming.ldap.Rdn;

import java.io.*;
import java.nio.file.*;

public class Encapsulation {

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
            file_out.write("\n".getBytes());
            file_out.write(c.secretkey);
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

        Path file_path = Paths.get("C:\Users\Zain\Documents\GitHub\Encapsulation\NRI Letter.pdf");

        String file_ext = file_path.toString(); 
        
        String s_id = "DL0KG6WZwu";
        String r_id = "ifkhtCNnIc";
        
        c.file_extension = file_ext.substring(file_ext.indexOf(".")).getBytes();
        
        byte[] byte_file = Files.readAllBytes(file_path);
        
        try {
            String string_file = Base64.getEncoder().encodeToString(byte_file);

            String keyString = File_AES.Key_Gen(r_id, s_id);
            System.out.println(keyString);
            
            c.secretkey = keyString.getBytes();

            byte[] iv = File_AES.generateIv();
            c.iv = iv;
            string_file = File_AES.EncDriver(keyString, string_file, iv);
            System.out.println(string_file.substring(0,4));
            c.byte_file = string_file.getBytes();
        } catch (Exception e) {
            
        }
        c.sender_id = s_id.getBytes();
        
        c.receiver_id = r_id.getBytes();
        
        Write_File(c);
    }
}
