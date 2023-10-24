import java.io.*;

public class Sample_Deser {
    
    public static void main(String[] args) throws IOException, ClassNotFoundException{

        Sample_Ser_Class d1 = null;

        try{
            FileInputStream fileIn = new FileInputStream("E:\\College\\Sem_7\\LY_Project\\SampleOP.sec");

            ObjectInputStream inStream = new ObjectInputStream(fileIn);

            d1 = (Sample_Ser_Class) inStream.readObject();

            inStream.close();
            fileIn.close();
        }
        finally{
            System.out.println(d1.receiver_id);
            System.out.println(d1.sender_id);
            System.out.println(d1.file_timeout);
        }


    }

}
 