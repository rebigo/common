package pl.rebigo.libs.common.Services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import pl.itgolo.plugin.helper.Thread.SleepHelper;
import pl.rebigo.libs.common.Exceptions.CommonException;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 15.09.2017
 * Time: 15:33
 * Project name: alle
 *
 * @author Karol Golec <karol.rebigo@gmail.com>
 */
@Slf4j
public class XmlSplitService {

    /* @var split by tag */
    private String splitByTag;

    /* @var share number items */
    private Integer shareNumberItems;

    /* @var count nodes */
    private Integer countNodes = 0;

    /* @var count share */
    private Integer countShare = 0;

    /* @var directional out */
    private String dirOut;

    /* @var tag name */
    private String tagName = "";

    /* @var in tag */
    private Boolean inTag = false;

    /* @var can read xml node */
    private Boolean canReadXmlNode = false;

    /* @var xml node string */
    private String xmlNodeString = "";

    /* @var xml nodes string */
    private String xmlNodesString = "";

    /* @var temp file XML */
    private String tempFileXml = "";

    /**
     * Constructor
     *
     * @param splitByTag       split by tag
     * @param shareNumberItems share number items
     * @param dirOut           directory out
     */
    public XmlSplitService(String splitByTag, Integer shareNumberItems, String dirOut) {
        this.splitByTag = splitByTag;
        this.shareNumberItems = shareNumberItems;
        this.dirOut = dirOut;
    }

    /**
     * Split file
     * @param xmlFile xmlFile
     * @throws CommonException CommonException
     */
    public void splitFile(String xmlFile) throws CommonException {
        if (!this.tempFileXml.equals(xmlFile))
            this.countShare = 0;
        this.tempFileXml = xmlFile;
        try {
            FileUtils.forceMkdir(new File(dirOut));
            log.debug("Split file");
            splitFileCall(xmlFile);
        } catch (Exception e) {
            throw new CommonException(e.getMessage(), e);
        }
    }

    /**
     * Split file XML
     * @param xmlFile xml file
     * @throws IOException IOException
     */
    private void splitFileCall(String xmlFile) throws IOException {
        FileInputStream fis = new FileInputStream(new File(xmlFile));
        InputStreamReader isr = new InputStreamReader(fis, "UTF8");
        Reader in = new BufferedReader(isr);
        int ch;
        while ((ch = in.read()) > -1) {
            String character = String.valueOf((char) ch);
            if (character.equals(">")) {
                this.inTag = false;
            }
            if (this.inTag) {
                this.tagName += character;
            }
            if (character.equals("<")) {
                this.tagName = "";
                this.inTag = true;
            }
            if (tagName.equals(this.splitByTag) && !this.inTag) {
                this.xmlNodeString = "";
                this.canReadXmlNode = true;
            }
            if (tagName.equals("/" + this.splitByTag) && !this.inTag) {
                this.canReadXmlNode = false;
                this.xmlNodesString += "<" + tagName.replace("/","") + this.xmlNodeString + ">" + System.lineSeparator();
                this.countNodes++;
            }
            if (this.canReadXmlNode) {
                this.xmlNodeString += character;
            }
            if (this.countNodes >= this.shareNumberItems){
                this.countShare++;
                savePartXmlFile(xmlFile);
                this.countNodes = 0;
                this.xmlNodesString = "";
            }
        }
        if (!this.xmlNodesString.equals("")){
            this.countShare++;
            savePartXmlFile(xmlFile);
        }
        in.close();
        isr.close();
        fis.close();
        SleepHelper.sleep(30000);
        log.debug("Release memory 30 seconds");
    }

    /**
     * Save part XML file
     * @param xmlFile xml file
     * @throws IOException IOException
     */
    private void savePartXmlFile(String xmlFile) throws IOException {

        String fileName = FilenameUtils.removeExtension(new File(xmlFile).getName());
        String numberPart = String.format("%05d", this.countShare);
        String fileNameOut = String.format("%1$s_part_%2$s.xml", fileName, numberPart);
        File fileOut = new File(this.dirOut + "/" + fileNameOut);
        log.debug("Save part XML file: " + fileOut.getAbsolutePath());
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileOut), StandardCharsets.UTF_8);
        writer.write(this.xmlNodesString);
        writer.close();
    }
}
