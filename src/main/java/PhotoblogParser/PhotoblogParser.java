package PhotoblogParser;

import PhotoblogParser.Downloader.DownloaderEntry;
import PhotoblogParser.Downloader.DownloaderToDB;
import PhotoblogParser.Downloader.DownloaderToMemory;

import static PhotoblogParser.Config.*;

class PhotoblogParser {

    DownloaderEntry downloader = new DownloaderToDB();
    String userHomeURL = BLOG_URL.concat("/" + USERNAME);


    private void run() {
        try {

            for (int i = 0; i < NUMBER_OF_POSTS; i++) {
                LOGGER.info(Integer.toString(i));
                String eNumber = String.valueOf(NUMBER_OF_POSTS - i);
                this.userHomeURL = downloader.saveEntry(this.userHomeURL, eNumber);
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
