package pa.lab11.client;

import com.github.javafaker.Faker;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pa.lab11.Person;

import java.util.*;

@Service
class RestService {

    private final RestTemplate restTemplate;
    private final String base = "http://localhost:1444";

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getPersonsPlainJSON() {
        String url = base + "/api/persons/all";
        return this.restTemplate.getForObject(url, String.class);
    }
    public List<Person> getAllPersons() {
        String url = base + "/api/persons/all";
        return Arrays.stream(this.restTemplate.getForObject(url, Person[].class)).toList();
    }
    public void createPerson(String name) {
        String url = base + "/api/persons/create?name=";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, Object> map = new HashMap<>();
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        url += name;
        restTemplate.postForEntity(url, entity, Void.class);
    }
    public void createFriendship(Person p1, Person p2) {
        String url = base + "/api/friendships/create?id1=";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, Object> map = new HashMap<>();
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        url += p1.getId().toString();
        url += "&id2=" + p2.getId().toString();
        restTemplate.postForEntity(url, entity, Void.class);
    }

}

public class Main {

    public static void main(String[] args) {
        RestService rs = new RestService(new RestTemplateBuilder());

        Faker fk = new Faker();
        int userCount = 40;
        int friendsMin = 5;
        int friendsMax = 20;

        for(int i = 0; i < userCount ; ++i) {
            String name = fk.name().fullName();
            rs.createPerson(name);
        }

        List<Person> users = rs.getAllPersons();
        Random rng = new Random();
        for(var user : users) {
            int friendsCount = rng.nextInt(friendsMin, friendsMax);
            for(int i = 0; i < friendsCount; ++i) {
                Person friend = users.get(rng.nextInt(0, users.size() - 1));
                rs.createFriendship(user, friend);
            }
        }

    }
}
