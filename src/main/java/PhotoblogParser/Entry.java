package PhotoblogParser;

import com.sun.istack.internal.NotNull;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;


@Entity
@Data
public class Entry {

  @Lob
  @NotNull
  private String title;

  @Lob
  @NotNull
  private String text;

  @NotNull
  private String date;

  @Id
  private long id;

  @Lob
  private byte[] img;

  @Transient
  private String pathPostImage;

  public Entry(String title, String text, String date) {
    this.title = title;
    this.text = text;
    this.date = date;
  }

  public Entry() {
  }

}
