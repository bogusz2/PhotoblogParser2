package PhotoblogParser;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Data
@Table(name = "post_table")
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
  @GeneratedValue(strategy = GenerationType.AUTO)
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
