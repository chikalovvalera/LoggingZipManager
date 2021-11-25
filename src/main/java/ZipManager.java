import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class ZipManager {

   public List<File> unzipFile(File file) {
       return getUnzipFile(new ArrayList<File>(), file);
   }

   private List<File> getUnzipFile(List<File> files, File file){
       if (file != null && file.exists()){
           if(isArchive(file)){

           }
       }
   }

   private boolean isArchive(File file){
       try {
           ZipFile zipFile = new ZipFile(file);
           return true;
       } catch (ZipException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
       return false;
   }

}
