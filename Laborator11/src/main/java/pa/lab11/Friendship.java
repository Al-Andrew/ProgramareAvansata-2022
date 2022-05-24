package pa.lab11;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.UUID;

public class Friendship {
    @JsonSerialize
    Person friend1, friend2;
    @JsonSerialize
    UUID id;

    Friendship(Person f1, Person f2){
        if(f1 == null || f2 == null)
            return;
        if(f1.equals(f2))
            return;

        this.friend1 = f1;
        this.friend2 = f2;
        this.id = UUID.randomUUID();
    }

    public boolean hasUser(Person user) {
        return friend1.equals(user) || friend2.equals(user);
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "friend1=" + friend1 +
                ", friend2=" + friend2 +
                ", id=" + id +
                '}';
    }
}
