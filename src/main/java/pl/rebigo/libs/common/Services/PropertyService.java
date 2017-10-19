package pl.rebigo.libs.common.Services;

import pl.rebigo.libs.common.Exceptions.CommonException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 14.09.2017
 * Time: 11:48
 * Project name: alle
 *
 * @author Karol Golec <karol.rebigo@gmail.com>
 */
public class PropertyService {

    /**
     * Create property
     * @param filePath file path
     * @return properties
     * @throws CommonException CommonException
     */
    public static Properties createProperty(String filePath) throws CommonException {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
            InputStreamReader inStream = new InputStreamReader(fileInputStream, "UTF8");
            properties.load(inStream);
            inStream.close();
            return properties;
        } catch (IOException e) {
            throw new CommonException(e.getMessage(), e);
        }
    }
}

