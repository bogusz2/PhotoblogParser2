package PhotoblogParser.Downloader;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static PhotoblogParser.Config.LOCAL_PATH;


public class DownloaderToMemory extends Downloader implements DownloaderEntry {

  @Override
  public String saveEntry(String URL, String eNumber) throws IOException {

    Document html = Jsoup.connect(URL).timeout(10000).get();
    String title = getTitle(html, eNumber);
    saveTitle(title, LOCAL_PATH + eNumber);//todo same problem
    String date = getDateOfEntry(html);
    saveDate(date, LOCAL_PATH + eNumber);
    byte[] image = getImgOfEntry(html);
    saveImage(image, LOCAL_PATH + eNumber);
    String HTMLPhotoNote = this.getHTMLPhotoNote(html);
    savePhotoNoteAsHTML(HTMLPhotoNote, LOCAL_PATH + eNumber);
    return getNextPage(html);

  }

  private void saveImage(byte[] image, String path) throws IOException {
    FileUtils.writeByteArrayToFile(new File(path + ".jpg"), image);
  }

  private void saveTitle(String title, String path) throws IOException {// todo: save date separetly
    File dateTitlePost = new File(path + ".txt");
    BufferedWriter bwDateTitlePost = new BufferedWriter(new FileWriter(dateTitlePost));
    bwDateTitlePost.write(title);
    bwDateTitlePost.close();
  }

  private void saveDate(String date, String path) throws IOException {
    File dateTitlePost = new File(path + ".txt");
    BufferedWriter bwDateTitlePost = new BufferedWriter(new FileWriter(dateTitlePost, true));
    bwDateTitlePost.newLine();
    bwDateTitlePost.write(date);
    bwDateTitlePost.close();
  }

  private void savePhotoNoteAsHTML(String HTMLPhotoNote, String path) throws IOException {
    File post = new File(path + ".html");
    BufferedWriter bw = new BufferedWriter(new FileWriter(post));
    bw.write(HTMLPhotoNote);
    bw.close();
  }
}