package dao;

import entity.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO implements CrudInterface<Person> {

    private final DbHandler dbHandler;

    public PersonDAO(DbHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @Override
    public Person save(Person person) throws Exception {
        String query = "INSERT INTO persons (name, age) VALUES (?, ?)";
        try (Connection connection = dbHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());
            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                person.setId(keys.getLong(1)); // Assuming `id` is auto-generated.
            }
            return person;
        }
    }

    @Override
    public List<Person> findAll() throws Exception {
        List<Person> persons = new ArrayList<>();
        String query = "SELECT * FROM persons";
        try (Connection connection = dbHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                persons.add(new Person(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("age")
                ));
            }
        }
        return persons;
    }

    @Override
    public Person update(Person person) throws Exception {
        String query = "UPDATE persons SET name = ?, age = ? WHERE id = ?";
        try (Connection connection = dbHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());
            statement.setLong(3, person.getId());
            statement.executeUpdate();
            return person;
        }
    }

    @Override
    public Person findById(Long id) throws Exception {
        String query = "SELECT * FROM persons WHERE id = ?";
        try (Connection connection = dbHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Person(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("age")
                );
            }
        }
        return null;
    }

    @Override
    public void delete(Long id) throws Exception {
        String query = "DELETE FROM persons WHERE id = ?";
        try (Connection connection = dbHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
}
