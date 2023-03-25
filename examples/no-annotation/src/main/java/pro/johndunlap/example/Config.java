package pro.johndunlap.example;

public class Config {
    private String fullName;
    private Integer age;

    public Config() {
    }

    public String getFullName() {
        return fullName;
    }

    public Config setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public Config setAge(Integer age) {
        this.age = age;
        return this;
    }
}
