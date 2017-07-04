/*
 The Size of r.mp3 in the InputDir is 2.42 MB.
 40 files of 64K are created in the UploadDir with the size of the last part being 11KB
 The chunks are names r.mp3.001 , r.mp3.002 ... r.mp3.040

  */

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import aws.example.s3.XferMgrProgress;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
public class FileManager {

    private static ArrayList<File> files = new ArrayList<File>();  //To store the chunks in an ArrayList

    public static void splitUpload(File file) throws IOException {
        int number = 1;   // To name the chunks. e.g bach.mp3.001, bach.mp3.002 ......

        int sizeOfChunks = 65000;   //Size of each split part




        byte[] buffer = new byte[sizeOfChunks]; // Create buffer to process 64K at a time

        String name = file.getName();     // Get the name of the file

        try (
                FileInputStream a = new FileInputStream(file);
                BufferedInputStream b = new BufferedInputStream(a);

        ) {
            int bytes = 0;
            while ((bytes = b.read(buffer)) > 0) {
                String nameOfParts = String.format("%s.%03d", name, number++);   //Name of each of the parts

                File newFile = new File("Z:\\tmp\\UploadDir", nameOfParts);  //Store the split files in upload directory

                try (FileOutputStream out = new FileOutputStream(newFile)) {
                    out.write(buffer, 0, bytes);

                   files.add(newFile);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }catch (IOException e1){e1.printStackTrace();}


            }
        }catch ( RuntimeException e2)
        {e2.printStackTrace();}


    }



    public static void uploadFileList( String bucket_name, String key_prefix)
    {


        TransferManager xfer_mgr = new TransferManager();
        try {
            MultipleFileUpload xfer = xfer_mgr.uploadFileList(bucket_name,
                    key_prefix, new File("."), files);
            // loop with Transfer.isDone()
            XferMgrProgress.showTransferProgress(xfer);
            // or block with Transfer.waitForCompletion()
            XferMgrProgress.waitForCompletion(xfer);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        xfer_mgr.shutdownNow();
    }

















































public static void main(String [] args) throws IOException {
    splitUpload(new File("Z:\\tmp\\InputDir\\r.mp3"));

}







}
