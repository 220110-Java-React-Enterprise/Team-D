import services.BlankRepo;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        TestReflection test = new TestReflection("Brandon", true);
        TestReflection blank = new TestReflection();

            BlankRepo repo = new BlankRepo();
          //  repo.create(test);
            repo.read(test, "Brandon");

            blank.setName("Hello");
        repo.read(blank, "Hello");

       // test.setName("Hello");
       // test.setBool(false);
        //System.out.println(test.getNum());
       // repo.update(test);
        //repo.read(test, "Brandon");
        //repo.read(test, "Hello");
       // repo.delete(test, test.getNum());
        }
}
