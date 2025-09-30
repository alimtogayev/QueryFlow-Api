package alim.togayev.queryservice.entities;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "passengers")
@Data
@NoArgsConstructor
@Builder
public class Passenger {
    @Id
    @CsvBindByName(column = "PassengerId")
    private Long passengerId;
    @CsvBindByName(column = "Survived")
    private Integer survived;
    @CsvBindByName(column = "Pclass")
    private Integer pclass;
    @CsvBindByName(column = "Name")
    private String name;
    @CsvBindByName(column = "Sex")
    private String sex;
    @CsvBindByName(column = "Age")
    @Column(nullable = true)
    private BigDecimal age;
    @CsvBindByName(column = "SibSp")
    private Integer sibSp;
    @CsvBindByName(column = "Parch")
    private Integer parch;
    @CsvBindByName(column = "Ticket")
    private String ticket;
    @CsvBindByName(column = "Fare")
    private BigDecimal fare;
    @CsvBindByName(column = "Cabin")
    private String cabin;
    @CsvBindByName(column = "Embarked")
    private String embarked;
    public Passenger(Long passengerId, Integer survived, Integer pclass, String name,
                     String sex, BigDecimal age, Integer sibSp, Integer parch, String ticket,
                      BigDecimal fare, String cabin, String embarked) {
        this.passengerId = passengerId;
        this.survived = survived;
        this.pclass = pclass;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.sibSp = sibSp;
        this.parch = parch;
        this.ticket = ticket;
        this.fare = fare;
        this.cabin = cabin;
        this.embarked = embarked;
    }
}

