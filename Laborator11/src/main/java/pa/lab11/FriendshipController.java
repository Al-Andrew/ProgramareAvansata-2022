package pa.lab11;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/friendships")
public class FriendshipController {

    @GetMapping("/all")
    public List<Friendship> getAllFriendships() {
        return SocialNetwork.the().getAllFriendships();
    }

    @GetMapping("/get")
    public Friendship getFriendship(@RequestParam String frid) {
        return SocialNetwork.the().getFriendship(UUID.fromString(frid));
    }

    @GetMapping("/user")
    public List<Friendship> getFriendshipsForUser(@RequestParam String uid) {
        return SocialNetwork.the().getFriendshipsForUser(UUID.fromString(uid));
    }

    @PostMapping("/create")
    public void createFriendship(@RequestParam String id1, @RequestParam String id2) {
        SocialNetwork.the().addFriendship(UUID.fromString(id1), UUID.fromString(id2));
    }

    @PutMapping("/delete")
    public ResponseEntity<String> deleteFriendship(@RequestParam String frid) {
        return SocialNetwork.the().deleteFriendship(UUID.fromString(frid))?
                new ResponseEntity<>("Friendship ended successfully", HttpStatus.OK):
                new ResponseEntity<>("Friendship does not exist", HttpStatus.NOT_FOUND);
    }
}
