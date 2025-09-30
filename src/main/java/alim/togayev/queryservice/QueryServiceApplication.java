package alim.togayev.queryservice;

import alim.togayev.queryservice.entities.Person;
import alim.togayev.queryservice.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class QueryServiceApplication {
    @Autowired
    private PersonRepo repo;

    public void insertSamples() {
        Person p1 = new Person();
        p1.setName("Alim");
        p1.setAge(22);
        repo.save(p1);
        Person p2 = new Person();
        p2.setName("Bob");
        p2.setAge(12);
        repo.save(p2);

    }
    public static void main(String[] args) {
        SpringApplication.run(QueryServiceApplication.class, args);
    }

}
