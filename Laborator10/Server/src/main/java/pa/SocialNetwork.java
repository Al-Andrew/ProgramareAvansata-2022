package pa;

import java.util.*;


public class SocialNetwork {

    class FriendShip {
        String friend1, friend2;

        FriendShip(String f1, String f2){
            if(f1 == null || f2 == null)
                return;
            if(f1.equals(f2))
                return;

            this.friend1 = f1;
            this.friend2 = f2;
        }

        public boolean includes(String user) {
            return friend1.equals(user) || friend2.equals(user);
        }

        public String getOtherFriend(String user) {
            if(user.equals(friend1))
                return friend2;
            else if (user.equals(friend2))
                return friend1;
            return null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            FriendShip that = (FriendShip) o;

            return (friend1.equals(that.friend1) && friend2.equals(that.friend2)) || (friend1.equals(that.friend2) && friend2.equals(that.friend1));
        }

        @Override
        public int hashCode() {
            int result = friend1.hashCode();
            result = 31 * result + friend2.hashCode();
            return result;
        }
    }

    class Message {
        String from;
        String message;

        Message(String from, String message) {
            this.from = from;
            this.message = message;
        }
    }

    Set<String> users = new HashSet<>();
    Set<String> loggedUsers = new HashSet<>();
    Set<FriendShip> friendShips = new HashSet<>();
    Map<String, List<Message>> messages = new HashMap<>();


    synchronized public void registerUser(String username) throws Exception {
        if(users.contains(username)) {
            throw new Exception("This user is already registered");
        }
        messages.put(username, new ArrayList<>());
        users.add(username);
    }

    synchronized public void loginUser(String username) throws Exception {
        if(!users.contains(username))
            throw new Exception("This user is not registered");

        loggedUsers.add(username);
    }

    synchronized public void logoutUser(String username) {
        loggedUsers.remove(username);
    }

    synchronized public List<String> getFriends(String user) throws Exception {
        if(!loggedUsers.contains(user)) {
            throw new Exception("This user is not logged in");
        }
        List<String> friends = new ArrayList<>();
        for(var fs : friendShips) {
            String possible = fs.getOtherFriend(user);
            if(possible != null)
                friends.add(possible);
        }
        return friends;
    }

    synchronized public void makeFriends(String from, String to) throws Exception {
        if(!loggedUsers.contains(from)) {
            throw new Exception("This user is not logged in");
        }
        if(!users.contains(to)) {
            throw new Exception("You can't be friends with a person that isn't registered");
        }

        friendShips.add(new FriendShip(from, to));
    }

    synchronized public Set<FriendShip> getFriendShips() {
        return friendShips;
    }

    synchronized public Set<String> getUsers() {
        return users;
    }

    synchronized public List<Message> getMessages(String user) throws Exception {
        if(!loggedUsers.contains(user)) {
            throw new Exception("This user is not logged in");
        }

        var ret = messages.get(user);
        messages.put(user, new ArrayList<>()); //Clear the inbox
        return ret;
    }

    synchronized public void sendMessage(String from, String message) throws Exception {
        if(!loggedUsers.contains(from)) {
            throw new Exception("This user is not logged in");
        }

        var friends = getFriends(from);

        for(var fr : friends) {
            messages.get(fr).add(new Message(from, message));
        }
    }


}
