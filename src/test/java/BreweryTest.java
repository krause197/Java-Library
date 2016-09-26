import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class BreweryTest {


  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/brewviews", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteBreweriesQuery = "DELETE FROM breweries *";
      //String deletePatientsQuery = "DELETE FROM patients *";
      con.createQuery(deleteBreweriesQuery).executeUpdate();
      //con.createQuery(deletePatientsQuery).executeUpdate();
    }
  }
  //
  // @Test
  // public void doctor_instantiatesCorrectly_true(){
  //   Doctor doc = new Doctor("Phil Philips", 1);
  //   assertEquals(true, doc instanceof Doctor);
  // }
  //
  // @Test
  // public void getName_doctorInstantiatesWithName_Phil() {
  //   Doctor testDoctor = new Doctor("Phil Philips", 1);
  //   assertEquals("Phil Philips", testDoctor.getName());
  // }
  //
  // @Test
  //  public void getId_doctorsInstantiateWithAnId_1() {
  //    Doctor testDoctor = new Doctor("Phil Philips", 1);
  //    testDoctor.save();
  //    assertTrue(testDoctor.getId() > 0);
  //  }
  //
  // @Test
  // public void all_returnsAllInstancesOfDoctor_true() {
  //   Doctor firstDoctor = new Doctor("Phil Philips", 1);
  //   firstDoctor.save();
  //   Doctor secondDoctor = new Doctor("Oz", 2);
  //   secondDoctor.save();
  //   assertEquals(true, Doctor.all().get(0).equals(firstDoctor));
  //   assertEquals(true, Doctor.all().get(1).equals(secondDoctor));
  // }
  //
  // @Test
  // public void find_returnsDoctorWithSameId_secondDoctor() {
  //   Doctor testDoctor = new Doctor("Oz", 2);
  //   testDoctor.save();
  //   assertEquals(Doctor.find(testDoctor.getId()), testDoctor);
  // }
  //
  // @Test
  // public void getTasks_initiallyReturnsEmptyList_ArrayList() {
  //   Doctor testDoctor = new Doctor("Dre", 420);
  //   testDoctor.save();
  //   assertEquals(0, testDoctor.getPatients().size());
  // }
  //
  // @Test
  // public void equals_returnsTrueIfNamesAretheSame() {
  //   Doctor firstDoctor = new Doctor("Dre", 420);
  //   Doctor secondDoctor = new Doctor("Dre", 420);
  //   assertTrue(firstDoctor.equals(secondDoctor));
  // }

  @Test
  public void save_savesIntoDatabase_true() {
    Brewery myBrewery = new Brewery("10 Barrel", "Portland", "Budweiser Poser");
    myBrewery.save();
    assertTrue(Brewery.all().get(0).equals(myBrewery));
  }

  // @Test
  // public void save_assignsIdToObject() {
  //   Category myCategory = new Category("Household chores");
  //   myCategory.save();
  //   Category savedCategory = Category.all().get(0);
  //   assertEquals(myCategory.getId(), savedCategory.getId());
  // }
  // @Test
  // public void save_savesCategoryIdIntoDB_true() {
  //   Category myCategory = new Category("Household chores");
  //   myCategory.save();
  //   Task myTask = new Task("Mow the lawn", myCategory.getId());
  //   myTask.save();
  //   Task savedTask = Task.find(myTask.getId());
  //   assertEquals(savedTask.getCategoryId(), myCategory.getId());
  // }
  //
  // @Test
  //   public void getTasks_retrievesALlTasksFromDatabase_tasksList() {
  //     Category myCategory = new Category("Household chores");
  //     myCategory.save();
  //     Task firstTask = new Task("Mow the lawn", myCategory.getId());
  //     firstTask.save();
  //     Task secondTask = new Task("Do the dishes", myCategory.getId());
  //     secondTask.save();
  //     Task[] tasks = new Task[] { firstTask, secondTask };
  //     assertTrue(myCategory.getTasks().containsAll(Arrays.asList(tasks)));
  //   }

}
