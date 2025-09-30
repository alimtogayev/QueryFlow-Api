package alim.togayev.queryservice.controller;

import alim.togayev.queryservice.entities.Person;
import alim.togayev.queryservice.entities.Query;
import alim.togayev.queryservice.service.QueryService;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class QueryController {
    private final QueryService queryService;

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @PostMapping("/queries")
    public long addQuery(@RequestBody String queryText) {
        return queryService.addQuery(queryText);
    }

    @GetMapping("/queries")
    public List<Query> getAllQueries() {
        return queryService.getQueries();
    }

    @GetMapping("/execute/{id}")
    public List<List<Object>> executeQuery(@PathVariable(name = "id") long id) {
        return queryService.executeQuery(id);
    }

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return queryService.uploadFile(file);
    }
}
