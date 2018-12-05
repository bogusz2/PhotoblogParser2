package PhotoblogParser;

import PhotoblogParser.Downloader.DownloaderEntry;
import PhotoblogParser.Downloader.DownloaderToMemory;

import static PhotoblogParser.Config.*;

public class PhotoblogParser {

    public void run() {

        DownloaderEntry downloader = new DownloaderToMemory();
        String entryURL = BLOG_URL.concat("/" + USERNAME);
        try {

            for (int i = 0; i < NUMBER_OF_POSTS; i++) {
                LOGGER.debug(Integer.toString(i));
                String eNumber = String.valueOf(NUMBER_OF_POSTS - i);
                entryURL = downloader.saveEntry(entryURL, eNumber);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        PhotoblogParser parser = new PhotoblogParser();
        parser.run();

    }

}
