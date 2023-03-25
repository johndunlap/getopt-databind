package pro.johndunlap.example;

import pro.johndunlap.getopt.GetOpt;
import pro.johndunlap.getopt.exception.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException {
        Config config = new GetOpt().bind(Config.class, args);
        System.out.println("Full name: " + config.getFullName());
        System.out.println("     Age : " + config.getAge());
    }
}
