package PhotoblogParser.Downloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static PhotoblogParser.Config.*;


public class DownloaderToMemory implements DownloaderEntry {

    public DownloaderToMemory() {
        System.setProperty("http.agent", BROWSER);
    }


    public String saveEntry(String URL, String eNumber) throws IOException, InterruptedException {

            TimeUnit.SECONDS.sleep(1);//todo harcode
            Document html = Jsoup.connect(URL).get();

            saveTitleAndDate(html, eNumber);
            saveImageAs(html, eNumber);
            savePhotoNoteAsHTML(html, eNumber);

            return getNextPage(html);

    }



    private void saveTitleAndDate(Document html, String eNumber) throws IOException {
        String title = getTitle(html, eNumber);
        String date = html.getElementsByClass("now_date").get(0).html(); // todo: get only date
        File dateTitlePost = new File(LOCAL_PATH + eNumber + ".txt");
        BufferedWriter bwDateTitlePost = new BufferedWriter(new FileWriter(dateTitlePost));
        bwDateTitlePost.write(date);
        bwDateTitlePost.newLine();
        bwDateTitlePost.write(title);
        bwDateTitlePost.close();
    }

    private String getTitle(Document html, String eNumber) {
        String title = html.getElementsByTag("title").get(0).text();
        if (NO_NAME_ENTRY.equals(title)) {
            title = "Wpis " + eNumber;
        }
        return title;
    }

    private void saveImageAs(Document html, String name) throws IOException {
        String imgURL = html.getElementById("show_pic_viewer").absUrl("src");
        URL imURL = new URL(imgURL);
        InputStream in = imURL.openStream();
        OutputStream out = new BufferedOutputStream(new FileOutputStream(LOCAL_PATH + name + ".jpg"));
        for (int b; (b = in.read()) != -1; ) {
            out.write(b);
        }
        out.close();
        in.close();
    }

    private void savePhotoNoteAsHTML(Document html, String name) throws IOException {
        String noteHTML = html.getElementById("photo_note").html();
        File post = new File(LOCAL_PATH + name + ".html");
        BufferedWriter bw = new BufferedWriter(new FileWriter(post));
        bw.write(noteHTML);
        bw.close();
    }

    private String getNextPage(Document html) {
        String lastEntryURL;
        Element previous = html.getElementById("photo_nav");
        lastEntryURL = previous.getElementsByAttribute("href").last().absUrl("href");
        return lastEntryURL;
    }

}