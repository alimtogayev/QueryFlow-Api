package alim.togayev.queryservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@Table(name = "queries")
@Data
@NoArgsConstructor
public class Query {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String queryText;
    public Query(String queryText) {this.queryText = queryText;}
}
