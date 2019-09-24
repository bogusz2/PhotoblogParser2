package PhotoblogParser.Downloader;

import PhotoblogParser.Entry;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

import static PhotoblogParser.Config.TIME_TO_LOAD_PAGE_MS;

public class DownloaderToDB extends Downloader implements DownloaderEntry {

    @Override
    public String saveEntry(String URL, String eNumber) throws IOException {


        Document html = Jsoup.connect(URL).timeout(TIME_TO_LOAD_PAGE_MS).get();

        String title = getTitle(html, eNumber);
        String HTMLPhotoNote = this.getHTMLPhotoNote(html);
        String date = getDateOfEntry(html);

        Entry entry = new Entry(title, HTMLPhotoNote, date);

        byte[] image = getImgOfEntry(html);
        entry.setImg(image);

        saveInDB(entry);

        return getNextPage(html);
    }

    private void saveInDB(Entry entry) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.blog");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(entry);
        entityManager.getTransaction().commit();
        entityManagerFactory.close();


    }
}
