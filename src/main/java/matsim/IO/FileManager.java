package matsim.IO;

import java.io.File;

public class FileManager {
    public static void DeleteFile(String filePath ){
        File file=new File(filePath);
        if (file.exists()){file.delete();}
    }

    public static void DeleteFiles(String[] filePaths){
        for (int i = 0; i < filePaths.length; i++) {
            DeleteFile(filePaths[i]);
        }
    }
}
