package PhotoblogParser;

import PhotoblogParser.Downloader.DownloaderEntry;
import PhotoblogParser.Downloader.DownloaderToDB;
import PhotoblogParser.Downloader.DownloaderToMemory;

import static PhotoblogParser.Config.*;

class PhotoblogParser {

    private void run() {

        DownloaderEntry downloader = new DownloaderToDB();
        String entryURL = BLOG_URL.concat("/" + USERNAME);
        try {

            for (int i = 0; i < NUMBER_OF_POSTS; i++) {
                LOGGER.info(Integer.toString(i));
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
