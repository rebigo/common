package pl.rebigo.libs.common.Services;

import org.apache.commons.io.FilenameUtils;
import pl.rebigo.libs.common.Exceptions.CommonException;

import java.io.*;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 16.09.2017
 * Time: 12:04
 * Project name: alle
 *
 * @author Karol Golec <karol.rebigo@gmail.com>
 */
public class FileService {

    /**
     * Get file name without extension
     *
     * @param filePath file path
     * @return file name without extension
     */
    public static String getFileNameWithoutExtension(String filePath) {
        File file = new File(filePath);
        String fileName = file.getName();
        return FilenameUtils.removeExtension(fileName);
    }

    /**
     * Get content
     * @param path path
     * @return content or null
     * @throws CommonException CommonException
     */
    public static String getContent(String path) throws CommonException {
        try {
            String content = "";
            File fileDir = new File(path);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF8"));
            String str;
            while ((str = in.readLine()) != null)
                content+=str + System.lineSeparator();
            in.close();
            content = content.substring(0, content.length() - 2);
            return content;
        } catch (Exception e) {
           throw new CommonException(e.getMessage(), e);
        }
    }

    /**
     * Append to file
     * @param line line content
     * @param path path
     * @throws CommonException CommonException
     */
    public static void appendToFile(String line, String path) throws CommonException {
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path, true)))) {
            out.println(line);
        }catch (IOException e) {
            throw new CommonException(e.getMessage(), e);
        }
    }
}

