import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.time.LocalDateTime;
import java.sql.Timestamp;

public class App {

  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      model.put("itemtypes", ItemType.all());
      model.put("patrons", Patron.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/itemtypes/new", (request, response) -> {
      String typeName = request.queryParams("new-item-type-name");
      ItemType newItemType = new ItemType(typeName);
      newItemType.save();
      response.redirect("/");
      return "New Item Type Added";
    });

    post("/items/new", (request, response) -> {
      String itemTitle = request.queryParams("new-item-title");
      String itemAuthor = request.queryParams("new-item-author");
      int itemTypeId = Integer.parseInt(request.queryParams("new-item-type"));
      Item newItem = new Item(itemTitle, itemAuthor, itemTypeId);
      newItem.save();
      response.redirect("/collections/" + ItemType.getName(itemTypeId));
      return "New Item Added";
    });

    post("/items/checkout", (request, response) -> {
      int patronId = Integer.parseInt(request.queryParams("item-checkout-patronid"));
      int itemId = Integer.parseInt(request.queryParams("item-checkout-itemid"));
      ItemRecord newItemRecord = new ItemRecord(patronId, itemId);
      newItemRecord.save();
      response.redirect("/items/" + itemId);
      return "New Item Added";
    });

    get("/collections/:itemtype", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      ItemType itemType = ItemType.find(request.params(":itemtype"));
      List<Item> items = Item.findAll(itemType.getId());
      model.put("items", items);
      model.put("itemtype", itemType);
      model.put("template", "templates/collection.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/items/:itemid", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int itemId = Integer.parseInt(request.params(":itemid"));
      Item item = Item.find(itemId);
      model.put("item", item);
      if(item.getCurrentRecord() == 0)
        model.put("temp", "Yes it is");
      else
        model.put("temp", "No it isn't");
      //Item.find(itemId).setCurrentRecord(-1);
      //model.put("records", ItemRecord.findItemRecords(itemId));
      model.put("patrons", Patron.all());
      model.put("template", "templates/item.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    post("/patrons/new", (request, response) -> {
      String patronName = request.queryParams("new-patron-name");
      String patronEmail = request.queryParams("new-patron-email");
      Patron newPatron = new Patron(patronName, patronEmail);
      newPatron.save();
      response.redirect("/");
      return "New Patron Added";
    });

    get("/patrons/:patronid", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int patronId = Integer.parseInt(request.params(":patronid"));
      Patron patron = Patron.find(patronId);
      model.put("patron", patron);
      model.put("Item", Item.class);
      model.put("records", ItemRecord.findPatronRecords(patronId));
      model.put("template", "templates/patron.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
