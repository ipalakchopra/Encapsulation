package Test;
import java.io.*;

public class Sample_Main {

    public static void Write_File(Sample_Ser_Class s){

        System.out.println(s.sender_id);

        try{
            FileOutputStream file_out = new FileOutputStream("C:\\Users\\ppk\\Documents\\Encapsulation\\SampleOP.sec");
            ObjectOutputStream out = new ObjectOutputStream(file_out);

            out.writeObject(s);
            out.close();
            file_out.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
    
        Sample_Ser_Class s1 = new Sample_Ser_Class();

        File mainFile = new File("/test_file.txt"); 

        FileInputStream mainFile_enc = new FileInputStream("C:\\Users\\ppk\\Documents\\Encapsulation\\test_file.txt");

        s1.main_file = mainFile;

        s1.sender_id = 1;

        s1.receiver_id = 2;
    
        s1.file_timeout = 2;

        s1.file_enc = mainFile_enc;

        Write_File(s1);
    }

}
