package alim.togayev.queryservice.util;

import alim.togayev.queryservice.entities.Passenger;
import alim.togayev.queryservice.repo.PassengerRepo;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

@Component
public class PassengerUtil {
    private final PassengerRepo passengerRepo;

    public PassengerUtil(PassengerRepo passengerRepo) {
        this.passengerRepo = passengerRepo;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        Set<Passenger> passengers = parseCsv(file);
        passengerRepo.saveAll(passengers);
        return "file successfully uploaded";
    }

    private Set<Passenger> parseCsv(MultipartFile file) throws IOException {
        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            return new HashSet<>(new CsvToBeanBuilder<Passenger>(reader)
                    .withType(Passenger.class)
                    .withIgnoreEmptyLine(true)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse());
        }
    }
}
