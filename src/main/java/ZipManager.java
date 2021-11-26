import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipManager<T> {

    public final zipAction<T> zipAction;

    public ZipManager(zipAction<T> zipAction) {
        this.zipAction = zipAction;
    }

    public T unzipFile(File rootFile, T result) {
        return unzipping(result, rootFile);
    }

    private T unzipping(T res, File rootFile) {
        ZipInputStream zipInputStream = null;
        try {
            if (rootFile != null && rootFile.exists()) {

                if (rootFile.isDirectory()) {
                    File[] childFiles = rootFile.listFiles();
                    if (childFiles != null) {
                        for (File file : childFiles) {
                            res = unzipping(res, file);
                        }
                    }
                } else {
                    if (isArchive(rootFile)) {
                        zipInputStream = new ZipInputStream(new FileInputStream(rootFile));
                        ZipEntry zipEntry = zipInputStream.getNextEntry();
                        while (zipEntry != null) {

                            String zipName = zipEntry.getName();
                            String fileName = getFileName(zipName);
                            String fileExt = getExtension(zipName, ".tmp");
                            File tempFile = File.createTempFile(fileName, fileExt);

                            byte[] buffer = new byte[1024];
                            FileOutputStream fos = new FileOutputStream(tempFile);
                            int len;
                            while ((len = zipInputStream.read(buffer)) > 0) {
                                fos.write(buffer, 0, len);
                            }
                            fos.close();

                            if (tempFile.isDirectory()) {
                                res = unzipping(res, tempFile);
                            } else {
                                if (isArchive(tempFile)) {
                                    res = unzipping(res, tempFile);
                                } else {
                                    res = (T) zipAction.action(tempFile, res);
                                }
                            }
                            tempFile.delete();
                            zipEntry = zipInputStream.getNextEntry();
                        }
                    } else {
                        res = (T) zipAction.action(rootFile, res);
                    }
                }
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (zipInputStream != null) {
                    zipInputStream.closeEntry();
                    zipInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    private String getFileName(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            return fileName.substring(0, index + 1);
        }
        return fileName;
    }

    private String getExtension(String fileName, String ifAbsent) {
        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            return fileName.substring(index + 1);
        }
        return ifAbsent;
    }

    /**
     * check file is arhcive (RAR, ZIP)
     * @param f
     * @return
     */
    private boolean isArchive(File f) {
        int fileSignature = 0;
        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            fileSignature = raf.readInt();
        } catch (IOException e) {
        }
        return fileSignature == 0x504B0304 || fileSignature == 0x504B0506 || fileSignature == 0x504B0708;
    }

}

interface zipAction<T> {

    public T action(File file, T result);
}
