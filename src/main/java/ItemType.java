import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import org.sql2o.*;

public class ItemType {
  private int id;
  private String name;

  public ItemType (String name){
    this.name = name;
  }

  public int getId(){
    return this.id;
  }

  public String getName(){
    return this.name;
  }

  public static String getName(int id) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM itemtypes WHERE id=:id";
        ItemType type = con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetchFirst(ItemType.class);
        return type.getName();
      }
    }

  public static List<ItemType> all() {
    String sql = "SELECT * FROM itemtypes;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(ItemType.class);
    }
  }

  public static ItemType find(int id) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM itemtypes WHERE id=:id";
        ItemType type = con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetchFirst(ItemType.class);
        return type;
      }
    }

    public static ItemType find(String itemTypeName) {
        try(Connection con = DB.sql2o.open()) {
          String sql = "SELECT * FROM itemtypes WHERE name=:itemtypename";
          ItemType type = con.createQuery(sql)
            .addParameter("itemtypename", itemTypeName)
            .executeAndFetchFirst(ItemType.class);
          return type;
        }
      }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO itemtypes (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

}
