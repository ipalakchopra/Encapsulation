package src;

import java.util.*;
import java.io.*;
import java.nio.*;
import java.nio.file.*;
import javax.crypto.NoSuchPaddingException;
public class Decapsulation {
    
    public static void main(String[] args) throws IOException, ClassNotFoundException,NoSuchPaddingException {
        
        Container c_decap = new Container();

        try{
            RandomAccessFile file_in = new RandomAccessFile("encap_filenew.sec","r");
            
            String u_id = "ifkhtCNnIc";

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
                String strkey = file_in.readLine();
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
                System.out.println(encfile);
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


            file_in.close();

        }

        finally{
            //Path decap_path = Paths.get(String.format("C:\\Users\\ppk\\Documents\\Encapsulation\\decap_file%s",c_decap.file_extension));
            //Files.write(decap_path, c_decap.byte_file);
        }
    }

}
