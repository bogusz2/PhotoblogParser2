package PhotoblogParser;

import PhotoblogParser.Downloader.DownloaderEntry;
import PhotoblogParser.Downloader.DownloaderToMemory;

import static PhotoblogParser.Config.*;

public class PhotoblogParser {



    public static void main(String[] args) {
        DownloaderEntry downloader = new DownloaderToMemory();
        String lastEntryURL = BLOG_URL.concat("/" + USERNAME);
        try {

            for (int i = 0; i < NUMBER_OF_POSTS; i++) {
                LOGGER.debug(Integer.toString(i));
                String eNumber = String.valueOf(NUMBER_OF_POSTS - i);
                lastEntryURL = downloader.saveEntry(lastEntryURL, eNumber);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
