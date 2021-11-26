import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnaliticManager {



    public static void main(String[] args) throws Exception {
//		FileManager fm = new FileManager();
//		List<File> files = fm.getAllFileOnDirectory(new File("C:\\Temp\\log"), null);
//
//
//		ZipManager<HashMap<String, Integer>> zipUtils = new ZipManager<HashMap<String, Integer>>(new zipAction<HashMap<String, Integer>>() {
//			@Override
//			public HashMap<String, Integer> action(File file, HashMap<String, Integer> result) {
//				System.out.println("action : "+file.getName()+" : ");
//				System.out.println((file.length()/(1024*1024)+" Mb "));
//				return result;
//			}
//		});
//
//
//		for(File f : files) {
//			System.out.println();
//			System.out.println(f.getCanonicalPath());
//			zipUtils.unzipFile(f, null);
//		}

        //00:00:11.594|I| : ALFA MQ CONSUMER UNKNOWN TYPES MSG:{"value":{"dt":
        File file = new File("C:\\Temp\\log\\reg\\0.log");
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null; ) {
                System.out.println(line);
            }
        }
//		String patternStr = ".";
//		Pattern p = Pattern.compile("");
//
//		Matcher m = new Matcher

    }
}
