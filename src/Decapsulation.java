package src;

import java.util.*;
import java.io.*;
import java.nio.*;
import java.nio.file.*;

public class Decapsulation {
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        
        Container c_decap = null;

        try{
            FileInputStream file_in = new FileInputStream("C:\\Users\\ppk\\Documents\\Encapsulation\\encap_file.sec");
            ObjectInputStream obj_in = new ObjectInputStream(file_in);

            c_decap =(Container) obj_in.readObject();
            c_decap.method2();
            file_in.close();
            obj_in.close();
        }

        finally{
            Path decap_path = Paths.get(String.format("C:\\Users\\ppk\\Documents\\Encapsulation\\decap_file%s",c_decap.file_extension));
            Files.write(decap_path, c_decap.byte_file);
        }
    }

}
