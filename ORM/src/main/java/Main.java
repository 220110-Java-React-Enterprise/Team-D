import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        TestReflection test = new TestReflection("Brandon", true);

            BlankRepo repo = new BlankRepo();

            repo.create(test);
            repo.read(test);

            //testing read function by creating a new object and getting its name
            TestReflection test2 = (TestReflection) repo.read(test);
        System.out.println(test2.getNum());
        System.out.println(test.getNum());
        test.setName("Hello");
        test.setBool(false);
        System.out.println(test.getNum());
        repo.update(test);
        repo.delete(test, test.getNum());
        }
}
