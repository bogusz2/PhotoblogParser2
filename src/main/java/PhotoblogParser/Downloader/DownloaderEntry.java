package PhotoblogParser.Downloader;

import java.io.IOException;

public interface DownloaderEntry {
  String saveEntry(String URL, String eNumber) throws IOException, InterruptedException;
}
