package Test;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import javax.sound.midi.Patch;

public class Sample_BytetoFile {
    public static void main(String[] args) throws IOException{
    
        //file to bytes
        Path file_path = Paths.get("C:\\Users\\ppk\\Documents\\Encapsulation\\cat.jpg");
    
        byte[] file_arr = Files.readAllBytes(file_path);
    
        //bytes to file
        try{
            Path write_Path = Paths.get("C:\\Users\\ppk\\Documents\\Encapsulation\\written_pdf.jpg");
            Files.write(write_Path, file_arr);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
