package pa.lab11;

import java.util.*;
import java.util.stream.Collectors;

public class SocialNetwork {
    private Set<Person> users = new HashSet<>();
    private Set<Friendship> friendships = new HashSet<>();
    private static SocialNetwork instance;

    static public SocialNetwork the() {
        if(instance == null) {
            instance = new SocialNetwork();
        }
        return instance;
    }

    public int getUsersCount() {
        return users.size();
    }

    public List<Person> getAllUsers() {
        return users.stream().toList();
    }

    public List<Person> getUsersByName(String name) {
        return this.users.stream().filter((Person p1) -> p1.name.equals(name)).collect(Collectors.toList());
    }

    public Person getUserById(UUID id) {
        return this.users.stream().filter((Person p1) -> p1.id.equals(id)).findFirst().orElse(null);
    }

    public void addUser(Person usr){
        users.add(usr);
    }

    public boolean updateUser(UUID id, Person updated) {
        Person user = getUserById(id);
        if(user == null) return false;
        user.setId(updated.getId());
        user.setName(updated.getName());
        return true;
    }

    public boolean deleteUser(UUID id) {
        Person pr = getUserById(id);
        if(pr == null)
            return false;
        friendships.removeAll(getFriendshipsForUser(id));
        users.remove(pr);
        return true;
    }

    public boolean addFriendship(UUID p1ID, UUID p2ID) {
        Person p1 = getUserById(p1ID);
        if(p1 == null)
            return false;
        Person p2 = getUserById(p2ID);
        if(p2 == null)
            return false;
        friendships.add(new Friendship(p1, p2));
        return true;
    }

    public List<Friendship> getAllFriendships() {
        return friendships.stream().toList();
    }

    public Friendship getFriendship(UUID frID) {
        return friendships.stream().filter((Friendship fr) -> fr.id.equals(frID)).findFirst().orElse(null);
    }

    public List<Friendship> getFriendshipsForUser(UUID id) {
        Person p = getUserById(id);
        if(p == null)
            return null;
        return friendships.stream().filter((Friendship fr) -> fr.friend1 != null && fr.friend2 != null).filter((Friendship fr) -> fr.hasUser(p)).collect(Collectors.toList());
    }

    public boolean updateFriendship(UUID frid, Friendship replacement) {
        Friendship fr = getFriendship(frid);
        if(fr == null)
            return false;

        fr.id = replacement.id;
        fr.friend1 = replacement.friend1;
        fr.friend2 = replacement.friend2;
        return true;
    }

    public boolean deleteFriendship(UUID frid){
        Friendship fr = getFriendship(frid);
        if(frid == null)
            return false;
        friendships.remove(frid);
        return true;
    }
}
