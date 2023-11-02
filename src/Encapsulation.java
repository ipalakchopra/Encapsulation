package src;

import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Encapsulation {

    public static void Write_File(Container c) throws IOException{
        
        try{
            FileOutputStream file_out = new FileOutputStream("C:\\Users\\ppk\\Documents\\Encapsulation\\encap_file.sec");
            ObjectOutputStream obj_out = new ObjectOutputStream(file_out);

            obj_out.writeObject(c);
            obj_out.close();
            file_out.close();

            System.out.println("Encapsulated file successfully created!");
        }

        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws FileNotFoundException,IOException{

        Container c = new Container();

        Path file_path = Paths.get("C:\\Users\\ppk\\Documents\\Encapsulation\\datatype.xlsx");

        String file_ext = file_path.toString(); 
        
        c.file_extension = file_ext.substring(file_ext.indexOf("."));
        
        //System.out.println(c.file_extension);

        c.byte_file = Files.readAllBytes(file_path);
        c.sender_id = 1;
        c.receiver_id = 2;

        Write_File(c);
    }
}
