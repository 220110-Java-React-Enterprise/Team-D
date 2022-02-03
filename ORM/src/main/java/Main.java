import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        TestReflection test = new TestReflection("Brandon", true);
        TestReflection testNoArgs = new TestReflection();

            BlankRepo repo = new BlankRepo();
            repo.read(test);
        System.out.println(test.getNum());
        TestReflection readTest = (TestReflection) repo.read(testNoArgs,9);
        repo.read(readTest);
        repo.read(test);
        System.out.println(test.getNum());
        System.out.println(test.getName());
            repo.create(test);
            repo.read(test);

            //testing read function by creating a new object and getting its name
            TestReflection test2 = (TestReflection) repo.read(test);
        System.out.println(test2.getNum());
        System.out.println(test2.getName());
        System.out.println(test2.isBool());
        System.out.println(test.getNum());
        test.setName("Hello");
        test.setBool(false);
        System.out.println(test.getNum());
        repo.update(test);
       // repo.delete(test, test.getNum());
        }
}
