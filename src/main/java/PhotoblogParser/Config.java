package PhotoblogParser;

import PhotoblogParser.Downloader.DownloaderToMemory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {

    public static final String BROWSER = "Firefox";
    public static final Logger LOGGER = LoggerFactory.getLogger(DownloaderToMemory.class);
    public static final int NUMBER_OF_POSTS = 1120;//todo: liczba iteracji
    public static final String BLOG_URL = "https://www.photoblog.pl";
    public static final String LOCAL_PATH = "C:\\Users\\User\\Projects\\prv\\PhotoblogParser2\\BLOG\\";
    public static final String NO_NAME_ENTRY = "Fotoblog mrrrasniasta w Photoblog.pl";
    public static final String USERNAME = "mrrrasniasta";

}
