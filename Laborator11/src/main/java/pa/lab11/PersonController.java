package pa.lab11;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    private List<Person> persons = new ArrayList<>();

    public PersonController() {
        persons.add(new Person(UUID.randomUUID(), "Elon Tusk"));
        persons.add(new Person(UUID.randomUUID(), "Ratt Damon"));
    }

    @GetMapping("/all")
    public List<Person> getAll() {
        return persons;
    }
    @GetMapping("/count")
    public int count() {
        return persons.size();
    }
    @GetMapping("/get")
    public Person getPerson(@RequestParam String id) {
        return persons.stream()
                .filter(p -> p.getId().equals(UUID.fromString(id))).findFirst().orElse(null);
    }
    @PostMapping("/create")
    public UUID createPerson(@RequestParam String name) {
        UUID id = UUID.randomUUID();
        persons.add(new Person(id, name));
        return id;
    }
    @PutMapping("/update")
    public ResponseEntity<String> updatePerson(
            @RequestParam String id, @RequestParam String name) {
        Person product = getPerson(id);
        if (product == null) {
            return new ResponseEntity<>(
                    "Person not found", HttpStatus.NOT_FOUND); //or GONE
        }
        product.setName(name);
        return new ResponseEntity<>(
                "Person updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePerson(@RequestParam String id) {
        Person product = getPerson(id);
        if (product == null) {
            return new ResponseEntity<>(
                    "Product not found", HttpStatus.GONE);
        }
        persons.remove(product);
        return new ResponseEntity<>("Person removed", HttpStatus.OK);
    }

}