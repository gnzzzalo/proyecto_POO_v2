package upm.data.models;

import java.util.Objects;

public class User {
    private static final int MAXIMUM_AGE = 100;
    private static final int MINIMUM_AGE = 14;
    private static final int LONG_MINIMUM_PASSWORD = 3;
    private final String name;
    private final Integer mobile;
    private final Valuation rating;
    private Integer age;
    private String password;
    private Integer id;

    public User(String name, Integer age, Integer mobile, String password) {
        this.name = name;
        setAge(age);
        this.mobile = mobile;
        setPassword(password);
        this.rating = new Valuation();
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        if (age < MINIMUM_AGE || age > MAXIMUM_AGE) {
            throw new InvalidAttributeException("La edad es incorrecta, tiene que estar entre 14 y 100 años: " + age);
        }
        this.age = age;
    }

    public Integer getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length() < LONG_MINIMUM_PASSWORD) {
            throw new InvalidAttributeException("La contraseña debe tener un mínimo de 3 caracteres: " + password);
        }
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Valuation getRating() {
        return rating;
    }


    public double getAverageRating() {
        return this.rating.getAverageRating();
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", mobile=" + mobile +
                ", password='" + password + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
