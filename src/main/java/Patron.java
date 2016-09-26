import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Patron {
  private int id;
  private String name;
  private String email;

  public Patron(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public String getName() {
    return this.name;
  }

  public int getId() {
    return this.id;
  }

  public String getEmail() {
    return this.email;
  }

  public List<ItemRecord> getCheckedOutItems() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM itemrecords WHERE (patronid=:id, datecheckedin=null)";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(ItemRecord.class);
    }
  }

  public static Patron find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM patrons WHERE id=:id";
      Patron patron = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Patron.class);
      return patron;
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO patrons (name, email) VALUES (:name, :email)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("email", this.email)
      .executeUpdate()
      .getKey();
    }
  }



}
