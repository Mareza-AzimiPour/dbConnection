import dao.DbHandler;
import dao.PersonDAO;
import entity.Person;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("*******************Welcome to Person information**************************");
        System.out.println("1-save the Person ");
        System.out.println("2-show me All Person information saved");
        System.out.println("3-find the person by unique id");
        System.out.println("4-update the information person");
        System.out.println("5-delete person by unique id");
        System.out.println("6-exit");
        System.out.println("***************************************************************************");

        Scanner input = new Scanner(System.in);
        boolean exit=false;

        DbHandler dbHandler = DbHandler.create("jdbc:h2:tcp://localhost/~/test", "sa", "123", 5);
        PersonDAO personDAO = new PersonDAO(dbHandler);


        while (!exit) {
            System.out.println("\nPlease enter the number from the menu:");
            int number = input.nextInt();
            input.nextLine();
            switch (number) {
                case 1 -> {
                    System.out.println("Enter person's name:");
                    String name = input.nextLine();
                    System.out.println("Enter person's age:");
                    int age = input.nextInt();
                    Person person = new Person(0L, name, age);
                    personDAO.save(person);
                    System.out.println("Person saved successfully with ID: " + person.getId());
                    System.out.println("***************************************************************************");
                }
                case 2 -> {
                    List<Person> allPersons = personDAO.findAll();
                    System.out.println("All Persons:");
                    allPersons.forEach(p ->
                            System.out.println("ID: " + p.getId() + ", Name: " + p.getName() + ", Age: " + p.getAge()));
                    System.out.println("***************************************************************************");
                }
                case 3 -> {
                    System.out.println("Enter person's ID:");
                    long id = input.nextLong();
                    Person person = personDAO.findById(id);
                    if (person != null) {
                        System.out.println("Person found:");
                        System.out.println("ID: " + person.getId() + ", Name: " + person.getName() + ", Age: " + person.getAge());
                        System.out.println("***************************************************************************");
                    } else {
                        System.out.println("No person found with ID: " + id);
                        System.out.println("***************************************************************************");
                    }
                }
                case 4 -> {
                    // Update a person's information
                    System.out.println("Enter the ID of the person to update:");
                    long id = input.nextLong();
                    input.nextLine(); // Consume newline character
                    Person person = personDAO.findById(id);
                    if (person != null) {
                        System.out.println("Enter new name (current: " + person.getName() + "):");
                        String name = input.nextLine();
                        System.out.println("Enter new age (current: " + person.getAge() + "):");
                        int age = input.nextInt();
                        person.setName(name);
                        person.setAge(age);
                        personDAO.update(person);
                        System.out.println("Person updated successfully.");
                        System.out.println("***************************************************************************");
                    } else {
                        System.out.println("No person found with ID: " + id);
                        System.out.println("***************************************************************************");
                    }
                }
                case 5 -> {
                    System.out.println("Enter the ID of the person to delete:");
                    long id = input.nextLong();
                    Person person = personDAO.findById(id);
                    if (person != null) {
                        personDAO.delete(id);
                        System.out.println("Person deleted successfully.");
                        System.out.println("***************************************************************************");
                    } else {
                        System.out.println("No person found with ID: " + id);
                        System.out.println("***************************************************************************");
                    }
                }
                case 6 -> {
                    // Exit
                    exit = true;
                    System.out.println("Exiting the program. Goodbye!");
                    System.out.println("***************************************************************************");
                    input.close();
                }
                default -> System.out.println("Invalid option. Please enter a number between 1 and 6.");
            }
        }
        input.close();
        dbHandler.closeAllConnection();
    }
}