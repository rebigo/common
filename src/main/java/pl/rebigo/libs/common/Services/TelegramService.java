package pl.rebigo.libs.common.Services;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import pl.itgolo.plugin.helper.Thread.SleepHelper;
import pl.rebigo.libs.common.Exceptions.CommonException;
import pl.rebigo.libs.common.Libraries.MultipartUtility;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 14.09.2017
 * Time: 10:35
 * Project name: alle
 *
 * @author Karol Golec <karol.rebigo@gmail.com>
 */
@Slf4j
@Data
public class TelegramService {

    /* @var API key */
    private String apiKey;

    /* @var main group ID */
    private String mainGroupID;

    /**
     * Constructor
     *
     * @param apiKey      API key
     * @param mainGroupID main group ID
     */
    public TelegramService(String apiKey, String mainGroupID) {
        this.apiKey = apiKey;
        this.mainGroupID = mainGroupID;
    }

    /**
     * Send photos to group Telegram.org
     *
     * @param caption caption
     * @throws CommonException CommonException
     */
    public void sendPhotos(String caption, List<String> pathPhotos) throws CommonException {
        sendPhotosToGroup(caption, pathPhotos, this.mainGroupID);
    }

    /**
     * Send message to group Telegram.org
     *
     * @param message message
     * @throws CommonException CommonException
     */
    public void sendMessage(String message) throws CommonException {
        sendMessageToGroup(message, this.mainGroupID);
    }

    /**
     * Send photos to group Telegram.org
     *
     * @param caption caption
     * @param idGroup id group or channel
     * @throws CommonException CommonException
     */
    public void sendPhotosToGroup(String caption, List<String> pathPhotos, String idGroup) throws CommonException {
        synchronized (this) {
            log.debug("Sending photos to group Telegram ID: " + idGroup);
            SleepHelper.sleep(1000);

            try {
                String urlSendMessage = String.format("https://api.telegram.org/bot%1$s/sendPhoto", this.apiKey);
                for (String pathPhoto : pathPhotos) {
                    String body = sendImageByPostRequest(urlSendMessage, pathPhoto, caption, idGroup);
                    if (!body.contains("\"ok\":true"))
                        throw new CommonException("Not send message to Telegram.org for id group: " + idGroup);
                }
            } catch (Exception e) {
                throw new CommonException(e.getMessage(), e);
            }
        }
    }

    /**
     * Send message to group Telegram.org
     *
     * @param message message to telegram
     * @param idGroup id group or channel
     * @throws CommonException CommonException
     */
    public void sendMessageToGroup(String message, String idGroup) throws CommonException {
        synchronized (this) {
            log.debug("Sending message to group Telegram ID: " + idGroup);
            SleepHelper.sleep(1000);
            try {
                message = URLEncoder.encode(message, "UTF-8");
                String urlSendMessage = String.format("https://api.telegram.org/bot%1$s/sendMessage?chat_id=%2$s&text=%3$s", this.apiKey, idGroup, message);
                String body = sendMessageByGetRequest(urlSendMessage);
                if (!body.contains("\"ok\":true"))
                    throw new CommonException("Not send message to Telegram.org for id group: " + idGroup);
            } catch (Exception e) {
                throw new CommonException(e.getMessage(), e);
            }
        }
    }

    /**
     * Send image by post request
     *
     * @param requestURL request url
     * @return body of response
     * @throws CommonException CommonException
     */
    private String sendImageByPostRequest(String requestURL, String pathPhoto, String caption, String idGroup) throws CommonException {
        String result = "";
        String charset = "UTF-8";
        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);
            multipart.addHeaderField("User-Agent", "CodeJava");
            multipart.addFormField("chat_id", idGroup);
            multipart.addFormField("caption", caption);
            File uploadFile1 = new File(pathPhoto);
            multipart.addFilePart("photo", uploadFile1);
            List<String> response = multipart.finish();
            for (String line : response) {
                result += line;
            }
        } catch (IOException e) {
            throw new CommonException(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Send message by GET request
     *
     * @param url url
     * @return body of response
     * @throws CommonException CommonException
     */
    private String sendMessageByGetRequest(String url) throws CommonException {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6");
            con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String output;
            StringBuffer response = new StringBuffer();
            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            final String result = response.toString();
            in.close();
            con.disconnect();
            return result;
        } catch (Exception e) {
            throw new CommonException(e.getMessage(), e);
        }
    }

    /**
     * Build group ID from URL
     *
     * @param urlTelegram url with encode Telegram ID example: https://web.telegram.org/#/im?p=c1091752480_8709327486137743880
     * @return Telegram ID
     * @throws CommonException CommonException
     */
    public static String buildGroupIdFromUrl(String urlTelegram) throws CommonException {
        String result;
        Matcher matcher = Pattern.compile("im\\?p=c(.*)_").matcher(urlTelegram);
        while (matcher.find()) {
            result = matcher.group(1);
            if (result.length() == 10) {
                return "-100" + result;
            }
        }
        throw new CommonException("Can not build group ID for Telegram.org from URL.");
    }
}