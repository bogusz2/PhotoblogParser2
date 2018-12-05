package PhotoblogParser.Downloader;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;



import static PhotoblogParser.Config.*;


public class DownloaderToMemory implements DownloaderEntry {

  public DownloaderToMemory() {
    System.setProperty("http.agent", BROWSER);
  }

  @Override
  public String saveEntry(String URL, String eNumber) throws IOException {

    Document html = Jsoup.connect(URL).timeout(10000).get();
    String title = getTitle(html, eNumber);
    saveTitle(title, LOCAL_PATH + eNumber);//todo same problem
    String date = getDate(html);
    saveDate(date, LOCAL_PATH + eNumber);
    byte[] image = getImg(html);
    saveImage(image, LOCAL_PATH + eNumber);
    String HTMLPhotoNote = getHTMLPhotoNote(html);
    savePhotoNoteAsHTML(HTMLPhotoNote, LOCAL_PATH + eNumber);
//    savePhotoNoteAsHTML(html, LOCAL_PATH + eNumber);

    return getNextPage(html);

  }
  private String getHTMLPhotoNote(Document html){
    return html.getElementById("photo_note").html();

  }

  private void saveImage(byte[] image, String path) throws IOException {
    FileUtils.writeByteArrayToFile(new File(path + ".jpg"), image);
  }


  private String getTitle(Document html, String eNumber) {
    String title = html.getElementsByTag("title").get(0).text();
    if (title.equals(NO_NAME_ENTRY)) {
      title = "Wpis " + eNumber;
    }
    return title;
  }

  private String getDate(Document html) {
    return html.getElementsByTag("title").get(0).text();
  }


  private void saveTitle(String title, String path) throws IOException {// todo: save date separetly
    File dateTitlePost = new File(path + ".txt");
    BufferedWriter bwDateTitlePost = new BufferedWriter(new FileWriter(dateTitlePost));
    bwDateTitlePost.write(title);
    bwDateTitlePost.close();
  }


  private byte[] getImg(Document html) throws IOException {
    String URL = html.getElementById("show_pic_viewer").absUrl("src");
    URL url = new URL(URL);
    BufferedImage originalImage = ImageIO.read(url);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(originalImage, "jpg", baos);
    byte[] img = baos.toByteArray();
    baos.close();
    return img;
  }

  private void saveDate(String date, String path) throws IOException {
    File dateTitlePost = new File(path + ".txt");
    BufferedWriter bwDateTitlePost = new BufferedWriter(new FileWriter(dateTitlePost, true));
    bwDateTitlePost.newLine();
    bwDateTitlePost.write(date);
    bwDateTitlePost.close();
  }


//  private void saveImageAs(Document html, String name) throws IOException {
//    String imgURL = html.getElementById("show_pic_viewer").absUrl("src");
//    URL imURL = new URL(imgURL);
//    InputStream in = imURL.openStream();
//    OutputStream out = new BufferedOutputStream(new FileOutputStream(LOCAL_PATH + name + ".jpg"));
//    for (int b; (b = in.read()) != -1; ) {
//      out.write(b);
//    }
//    out.close();
//    in.close();
//  }

  private void savePhotoNoteAsHTML(String HTMLPhotoNote, String path) throws IOException {
    File post = new File(path + ".html");
    BufferedWriter bw = new BufferedWriter(new FileWriter(post));
    bw.write(HTMLPhotoNote);
    bw.close();
  }

  private String getNextPage(Document html) {
    String lastEntryURL;
    Element previous = html.getElementById("photo_nav");
    lastEntryURL = previous.getElementsByAttribute("href").last().absUrl("href");
    return lastEntryURL;
  }

}