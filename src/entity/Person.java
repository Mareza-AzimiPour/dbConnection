package entity;

import java.util.Objects;

public class Person {

    private Long id;
    private String name;
    private int age;

    public Person(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String age) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Person person)) {
            return false;
        }
        return Objects.equals(this.id, person.id)
                && Objects.equals(this.name, person.name)
                && Objects.equals(this.age, person.age);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id, name, age);
    }

}
