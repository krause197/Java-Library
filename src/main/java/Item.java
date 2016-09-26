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
  private int currentrecord;
  private int testInt = 999;

  public static final int MAX_DAYS_LOANED = 3;

  public Item(String title, String author, int itemtype) {
    this.title = title;
    this.author = author;
    this.itemtype = itemtype;
    this.currentrecord = 999;
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
    return ItemType.getName(this.itemtype);
  }

  public int getCurrentRecord(){
      return this.currentrecord;
  }

  public int testReturnInt(){
      return testInt;
  }

  public List<ItemRecord> getRecords() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM itemrecords WHERE itemid=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(ItemRecord.class);
    }
  }

  // public static List<Item> all(int itemType) {
  //   String sql = "SELECT * FROM items WHERE itemtype=:itemtype";
  //   try(Connection con = DB.sql2o.open()) {
  //     return con.createQuery(sql)
  //     .addParameter("itemtype", this.itemtype)
  //     .executeAndFetch(ItemType.class);
  //   }
  // }

  public static List<Item> findAll(int itemTypeId) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM items WHERE itemtype=:itemtype";
        List<Item> items = con.createQuery(sql)
          .addParameter("itemtype", itemTypeId)
          .executeAndFetch(Item.class);
        return items;
      }
    }

    public static Item find(int itemId) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM items WHERE id=:itemid";
        Item item = con.createQuery(sql)
          .addParameter("itemid", itemId)
          .executeAndFetchFirst(Item.class);
        return item;
      }
    }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO items (title, author, itemtype, currentrecord) VALUES (:title, :author, :itemtype, :currentrecord)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("title", this.title)
        .addParameter("author", this.author)
        .addParameter("itemtype", this.itemtype)
        .addParameter("currentrecord", this.currentrecord)
        .executeUpdate()
        .getKey();
    }
  }

  public void setCurrentRecord(int crid){
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE items SET currentrecord = :crid WHERE id = :id";
      con.createQuery(sql)
        .addParameter("crid", crid)
        .addParameter("id", this.id)
        .executeUpdate();
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
