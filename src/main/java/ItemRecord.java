import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class ItemRecord {

  private int id;
  private int patronid;
  private int itemid;
  private int timesRenewed;
  private Timestamp datecheckedout;
  private Timestamp datecheckedin;

  public ItemRecord(int patronid, int itemid) {
    this.patronid = patronid;
    this.itemid = itemid;
    this.timesRenewed = 0;
  }

  public static ItemRecord find(int id) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM itemrecords WHERE id=:id";
        ItemRecord itemrecord = con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetchFirst(ItemRecord.class);
        return itemrecord;
      }
    }

  public void save() {
   try(Connection con = DB.sql2o.open()) {
     String sql = "INSERT INTO itemrecords (patronid, itemid, datecheckedout, timesRenewed) VALUES (:patronid, :itemid, now(), :timesRenewed)";
     this.id = (int) con.createQuery(sql, true)
       .addParameter("patronid", this.patronid)
       .addParameter("itemid", this.itemid)
       .addParameter("timesRenewed", this.timesRenewed)
       .executeUpdate()
       .getKey();
   }
 }

}
