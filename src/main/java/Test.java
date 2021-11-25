import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;

public class Test {

    public static void main(String[] args) throws IOException {
        FileManager fileManager = new FileManager();
        List<File> resultFileList = fileManager.getAllFileOnDirectory("src/main/resources", null);
        for(File f :resultFileList){
            System.out.println(f.getCanonicalFile());
        }

    }
}
