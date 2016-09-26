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

  public int getItemId(){
    return this.itemid;
  }

  public int getPatronId(){
    return this.patronid;
  }

  public String getPatronName(){
    return Patron.find(this.patronid).getName();
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

  public static List<ItemRecord> findItemRecords(int itemId) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM itemrecords WHERE itemid=:itemid";
      List<ItemRecord> itemrecords = con.createQuery(sql)
        .addParameter("itemid", itemId)
        .executeAndFetch(ItemRecord.class);
      return itemrecords;
    }
  }

  public static List<ItemRecord> findPatronRecords(int patronId) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM itemrecords WHERE patronid=:patronid";
      List<ItemRecord> itemrecords = con.createQuery(sql)
        .addParameter("patronid", patronId)
        .executeAndFetch(ItemRecord.class);
      return itemrecords;
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

      Item.find(this.itemid).setCurrentRecord(this.id);
   }
 }

}
