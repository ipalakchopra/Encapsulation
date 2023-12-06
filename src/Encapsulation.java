
package src;
import src.Container;

import java.util.*;

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

            RandomAccessFile file_out = new RandomAccessFile("enca_file.sec","rw");

            
            /*file_out.write(c.file_extension, 12, c.file_extension.length);

            file_out.write(c.byte_file, 15, c.byte_file.length);*/

            file_out.write(c.sender_id);
            file_out.write(c.receiver_id);
            file_out.write(c.file_extension);
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

        Path file_path = Paths.get("E:\\College\\Sem_7\\LY_Project\\Encapsulation\\datatype.xlsx");

        String file_ext = file_path.toString(); 
        
        String s_id = "111111";
        String r_id = "222222";

        c.file_extension = file_ext.substring(file_ext.indexOf(".")).getBytes();
        
        c.byte_file = Files.readAllBytes(file_path);
    
        c.sender_id = s_id.getBytes();
        
        c.receiver_id = r_id.getBytes();
        
        Write_File(c);
    }
}
