import java.io.*;

public class Sample_Main {

    public static void main(String[] args) {
    
        Sample_Ser_Class s1 = new Sample_Ser_Class();

        File mainFile = new File("/test_file.txt"); 

        s1.main_file = mainFile;

        s1.sender_id = 1;

        s1.receiver_id = 2;
    
    }

}
