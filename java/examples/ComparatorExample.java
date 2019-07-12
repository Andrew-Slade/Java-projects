import java.util.ArrayList;
import java.util.Comparator;

public class ComparatorExample {

    private ArrayList<Person> peopleList = new ArrayList<>();

    public static void main(String[] args) {
        ComparatorExample e = new ComparatorExample();
        e.doStuff();
    }

    public void doStuff() {
        /**
         * Add some Person objects to the ArrayList.
         */
        peopleList.add(new Person("John Beltran", 48));
        peopleList.add(new Person("Ann Moreno", 34));
        peopleList.add(new Person("Lawrence Ko", 27));
        peopleList.add(new Person("Martha Leonetti", 52));

        /**
         * Print the unsorted list.
         */
        System.out.println("Unsorted list:\n");
        printList();

        /**
         * Sort and print the list using Comparator objects of the classes defined below.
         */
        peopleList.sort(new PersonAgeComparator());
        System.out.println("\nList sorted in ascending order by age:\n");
        printList();

        peopleList.sort(new PersonNameComparator());
        System.out.println("\nList sorted in ascending order by name:\n");
        printList();

        /**
         * Sort and print the list using a reversed() Comparator object.
         */
        peopleList.sort(new PersonNameComparator().reversed());
        System.out.println("\nList sorted in descending order by name:\n");
        printList();

        /**
         * Sort and print the list using an anonymous Comparator class.
         */
        peopleList.sort(new Comparator<Person>(){
            public int compare(Person p1, Person p2) {
                return (p2.getAge() - p1.getAge());
            }
        });
        System.out.println("\nList sorted in descending order by age:\n");
        printList();

        /**
         * Sort and print the list using a lambda expression.
         */
        peopleList.sort((p1, p2) -> p1.getAge() - p2.getAge());
        System.out.println("\nList sorted in ascending order by age:\n");
        printList();

        /**
         * Sort and print the list using Comparator.comparingInt().
         */
        peopleList.sort(Comparator.comparingInt(Person::getAge).reversed());
        System.out.println("\nList sorted in descending order by age:\n");
        printList();
    }

    public void printList() {
        for (Person p : peopleList) System.out.println(p);
    }
}

class PersonAgeComparator implements Comparator<Person> {

    public int compare(Person p1, Person p2) {
        return (p1.getAge() - p2.getAge());
    }
}

class PersonNameComparator implements Comparator<Person> {

    public int compare(Person p1, Person p2) {
        return p1.getName().compareTo(p2.getName());
    }
}