public class App {
    public static void main(String[] args) throws Exception {
        Person person = new Person("Vlad", 27);

        Repository.write(person);
        Person human = (Person) Repository.load(person.getClass().getName());
        System.out.println(human);
    }
}
