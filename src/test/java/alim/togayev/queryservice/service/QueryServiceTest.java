package alim.togayev.queryservice.service;

import alim.togayev.queryservice.entities.Query;
import alim.togayev.queryservice.entities.Passenger;
import alim.togayev.queryservice.repo.QueryRepo;
import alim.togayev.queryservice.repo.PassengerRepo;
import alim.togayev.queryservice.util.PassengerUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QueryServiceTest {
    @Mock
    private QueryRepo queryRepo;
    @Mock
    private JdbcTemplate jdbc;
    @Mock
    private PassengerRepo passengerRepo;

    @InjectMocks
    private QueryService queryService;
    @InjectMocks
    private PassengerUtil passengerUtil;

    @Test
    void addQuery_savesExactText() throws Exception {
        String sql = "SELECT 1";
        Query saved = new Query(sql);

        when(queryRepo.save(any(Query.class))).thenAnswer(inv -> inv.getArgument(0));
        queryService.addQuery(sql);
        verify(queryRepo).save(argThat(q -> sql.equals(q.getQueryText())));
    }

    @Test
    void executeQuery_test_whenNotSelect() {
        when(queryRepo.findById(1L)).thenReturn(Optional.of(new Query("UPDATE users set a=1")));
        assertThatThrownBy(() -> queryService.executeQuery(1L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("ONLY READ-ONLY QUERIES ARE ALLOWED");
    }

    @Test
    void executeQuery_test_whenIdNotFound() {
        when(queryRepo.findById(999L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> queryService.executeQuery(999L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("404");
    }

    @Test
    void uploadFile_parsesCsv_andSavesPassengers() throws Exception{
        String csv = String.join("\n",
                "PassengerId,Survived,Pclass,Name,Sex,Age,SibSp,Parch,Ticket,Fare,Cabin,Embarked",
                "1,1,1,John,male,34,0,0,A,100.0,,S",
                "2,0,3,Jane,female,,0,0,B,7.25,,C"
        );
        MockMultipartFile file = new MockMultipartFile("file","titanic.csv","text/csv", csv.getBytes(StandardCharsets.UTF_8));

        String res = passengerUtil.uploadFile(file);

        assertThat(res).contains("file successfully uploaded");
        verify(passengerRepo, atLeastOnce()).saveAll(Mockito.<Iterable<Passenger>>any());
    }


  
}