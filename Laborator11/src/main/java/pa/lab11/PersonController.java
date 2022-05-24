package pa.lab11;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.SocketImpl;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    public PersonController() {
        SocialNetwork.the().addUser(new Person(UUID.fromString("82e62ee6-4f2e-4483-935b-bb1140b976cf"), "Elon Tusk"));
        SocialNetwork.the().addUser(new Person(UUID.fromString("c69fd4f7-11dc-40c3-99fe-5db09db899a1"), "Ratt Damon"));
        SocialNetwork.the().addUser(new Person(UUID.fromString("de4029c6-9ab6-4a88-a2bc-dcf6c2501f7f"), "Disney Walterson"));
    }

    @GetMapping("/all")
    public List<Person> getAll() {
        return SocialNetwork.the().getAllUsers();
    }
    @GetMapping("/count")
    public int count() {
        return SocialNetwork.the().getUsersCount();
    }
    @GetMapping("/get")
    public Person getPerson(@RequestParam String id) {
        return SocialNetwork.the().getUserById(UUID.fromString(id));
    }
    @GetMapping("/popular")
    public List<Person> getPopular(@RequestParam Integer n) {
        Vector<Person> ppl;
        ppl = SocialNetwork.the().getAllUsers().stream().collect(Collectors.toCollection(Vector::new));
        ppl.sort(Comparator.comparingInt((Person p) -> SocialNetwork.the().getFriendshipsForUser(p.id).size()));

        return ppl.subList(0, n);
    }
    @PostMapping("/create")
    public UUID createPerson(@RequestParam String name) {
        UUID id = UUID.randomUUID();
        SocialNetwork.the().addUser(new Person(id, name));
        return id;
    }
    @PutMapping("/update")
    public ResponseEntity<String> updatePerson(
            @RequestParam String id, @RequestParam String name) {
        return SocialNetwork.the().updateUser(UUID.fromString(id), new Person(UUID.fromString(id), name))?
                new ResponseEntity<>(
                        "Person updated successfully", HttpStatus.OK):
                new ResponseEntity<>(
                        "Person does not exist", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePerson(@RequestParam String id) {
        return SocialNetwork.the().deleteUser(UUID.fromString(id))?
                new ResponseEntity<>("Person deleted", HttpStatus.OK):
                new ResponseEntity<>("Person does not exist", HttpStatus.NOT_FOUND);
    }

}