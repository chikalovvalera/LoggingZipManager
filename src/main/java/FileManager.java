import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public FileFilter findByType(String type){
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String name = pathname.getName();
                int charDot = name.lastIndexOf(".");
                return (charDot > 0 && name.substring(charDot+1).equalsIgnoreCase(type));
            }
        };
        return fileFilter;
    }

    public FileFilter findByNameSubstring(String substring){
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String name = pathname.getName();
                return (name.contains(substring));
            }
        };
        return fileFilter;
    }

    public FileFilter findByNameSubstringIgnoreCase(String substring){
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String name = pathname.getName().toLowerCase();
                return (name.contains(substring.toLowerCase()));
            }
        };
        return fileFilter;
    }

    private final static FileFilter acceptAll(){
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        };
        return fileFilter;
    }
    public List<File> getAllFileOnDirectory(String dir, FileFilter fileFilter){
        File rootDir = new File(dir);

        if (rootDir != null && rootDir.exists()){
            if (rootDir.isDirectory()){
                return getAllFiles(new ArrayList<File>(), rootDir, fileFilter == null ? acceptAll() : fileFilter);
            }
        }
        return null;
    }

    private List<File> getAllFiles(List<File> files, File file, final FileFilter fileFilter){
        if (file != null && file.exists()){
            if (file.isDirectory()){
                File[] childFiles = file.listFiles();
                if (childFiles != null){
                    for (File childFile : childFiles){
                        if (childFile.isDirectory()){
                            files = getAllFiles(files, childFile, fileFilter);
                        } else {
                            if (fileFilter != null) {
                                if (fileFilter.accept(childFile)){
                                    files.add(childFile);
                                }
                            } else {
                                files.add(childFile);
                            }
                        }
                    }
                }
            } else {
                files.add(file);
            }
        }
        return files;
    }
}
