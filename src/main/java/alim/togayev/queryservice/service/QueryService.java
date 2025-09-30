package alim.togayev.queryservice.service;

import alim.togayev.queryservice.entities.Passenger;
import alim.togayev.queryservice.entities.Person;
import alim.togayev.queryservice.entities.Query;
import alim.togayev.queryservice.repo.PassengerRepo;
import alim.togayev.queryservice.repo.QueryRepo;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QueryService {
    private final QueryRepo queryRepo;
    private final JdbcTemplate jdbc;
    private final PassengerRepo passengerRepo;

    public QueryService(QueryRepo queryRepo, JdbcTemplate jdbc, PassengerRepo passengerRepo) {
        this.queryRepo = queryRepo;
        this.jdbc = jdbc;
        this.passengerRepo = passengerRepo;
    }


    public long addQuery(String queryText) {
        Query query = new Query();
        query.setQueryText(queryText);
        queryRepo.save(query);
        return query.getId();
    }

    public List<Query> getQueries() {
        return queryRepo.findAll();
    }

    public List<List<Object>> executeQuery(long id) {
        String request = queryRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Query not found: " + id))
                .getQueryText();;
        List<List<Object>> results = new ArrayList<>();
        if (!request.startsWith("SELECT ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ONLY READ-ONLY QUERIES ARE ALLOWED");
        }

        return jdbc.query(request, rs -> {
            List<List<Object>> rows = new ArrayList<>();
            ResultSetMetaData md = rs.getMetaData();
            int cols = md.getColumnCount();
            while (rs.next()) {
                List<Object> row = new ArrayList<>(cols);
                for (int c = 1; c <= cols; c++) {
                    row.add(rs.getObject(c));
                }
                rows.add(row);
            }
            return rows;
        });
    }

    public String uploadFile(MultipartFile file) throws IOException {
        Set<Passenger> passengers = parseCsv(file);
        passengerRepo.saveAll(passengers);
        return "file successfully uploaded";
    }

    private Set<Passenger> parseCsv(MultipartFile file) throws IOException {
        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            return new HashSet<>(new CsvToBeanBuilder<Passenger>(reader)
                    .withType(Passenger.class)                  // <-- маппим сразу в Student
                    .withIgnoreEmptyLine(true)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse());
        }
    }
}
