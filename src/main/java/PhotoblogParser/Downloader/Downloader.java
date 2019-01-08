package PhotoblogParser.Downloader;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import static PhotoblogParser.Config.BROWSER;
import static PhotoblogParser.Config.NO_NAME_ENTRY;

public class Downloader {
  public Downloader() {
    System.setProperty("http.agent", BROWSER);
  }

  protected String getHTMLPhotoNote(Document html) {
    return html.getElementById("photo_note").html();
  }

  protected String getTitle(Document html, String eNumber) {
    String title = html.getElementsByTag("title").get(0).text();
    if (title.equals(NO_NAME_ENTRY)) {
      title = "Wpis " + eNumber;
    }
    return title;
  }

  protected String getDateOfEntry(Document html) {
    return html.getElementsByClass("now_date").get(0).text();
  }

  protected byte[] getImgOfEntry(Document html) throws IOException {
    String URL = html.getElementById("show_pic_viewer").absUrl("src");
    java.net.URL url = new URL(URL);
    BufferedImage originalImage = ImageIO.read(url);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(originalImage, "jpg", baos);
    byte[] img = baos.toByteArray();
    baos.close();
    return img;
  }

  protected String getNextPage(Document html) {
    String lastEntryURL;
    Element previous = html.getElementById("photo_nav");
    lastEntryURL = previous.getElementsByAttribute("href").last().absUrl("href");
    return lastEntryURL;
  }
}
