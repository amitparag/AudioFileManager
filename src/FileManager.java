import sun.awt.image.ImageWatched;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileManager {



    public static void splitUpload(File file) throws IOException {
        int number = 1;   // To name the chunks. e.g bach.mp3.001, bach.mp3.002 ......

        int sizeOfChunks = 65000;   //Size of each split part

        List<File> listOfFiles = new LinkedList<>();   //TO store the resulting chunks in a linkedList


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
                    listOfFiles.add(newFile);

                    Thread thread = new Thread() {
                        @Override
                        public void run()

                        {
                            retrieve(listOfFiles);
                        }


                    };
                    thread.start();
                }


            }
        }





































































    }




