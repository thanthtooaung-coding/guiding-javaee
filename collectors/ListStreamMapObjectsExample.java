import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Person {
    String name;
    int age;
    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age;
    }
}

public class ListStreamMapObjectsExample {
  public static void main(String args[]) {

    List<Person> people = Arrays.asList(
        new Person("Bob", 20),
        new Person("Alice", 25),
        new Person("Charlie", 35)
    );  
    
    List<String> names = people.stream() // for each
                            .filter(person -> person.age < 30)
                            .map(person -> person.name) // action
                            .sorted()
                            .collect(Collectors.toList()); // Collectors to List -> change into List
                            
    System.out.println("Names Sorted People: " + names);
    
    
    List<Person> ageSortedPeople = people.stream() // for each
                            .filter(person -> person.age < 30)
                            .sorted((person1, person2) -> Integer.compare(person1.age, person2.age))
                            .collect(Collectors.toList()); // Collectors to List -> change into List
                            
    System.out.println("ageSortedPeople: " + ageSortedPeople);
    
  }
}
