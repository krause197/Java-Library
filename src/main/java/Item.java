import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import org.sql2o.*;
import java.sql.Timestamp;

public class Item {

  private int id;
  private String title;
  private String author;
  private int itemtype;
  private int currentRecord;

  public static final int MAX_DAYS_LOANED = 3;

  public Item(String title, String author, int itemtype) {
    this.title = title;
    this.author = author;
    this.itemtype = itemtype;
    this.currentRecord = -1;
  }

  public int getId(){
    return this.id;
  }

  public String getTitle(){
    return this.title;
  }

  public String getAuthor(){
    return this.author;
  }

  public String getItemType(){
    return ItemType.getName(this.id);
  }

  public ItemRecord getCurrentRecord(){
    if (currentRecord < 0)
      return null;
    else
      return ItemRecord.find(this.currentRecord);
  }

  public List<ItemRecord> getRecords() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM itemrecords WHERE itemid=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(ItemRecord.class);
    }
  }

  public static Item find(int id) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM items WHERE id=:id";
        Item item = con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetchFirst(Item.class);
        return item;
      }
    }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO items (title, author, itemtype, currentRecord) VALUES (:title, :author, :itemtype, :currentRecord)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("title", this.title)
        .addParameter("author", this.author)
        .addParameter("itemtype", this.itemtype)
        .addParameter("currentRecord", this.currentRecord)
        .executeUpdate()
        .getKey();
    }
  }


  public void deleteItem(){
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM items WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();

      sql = "DELETE FROM itemsrecords WHERE itemid = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
      }
  }

  @Override
  public boolean equals(Object otherItem) {
    if (!(otherItem instanceof Item)) {
      return false;
    } else {
      Item newItem = (Item) otherItem;
      return this.getTitle().equals(newItem.getTitle()) &&
             this.getId() == newItem.getId();
    }
  }

}
