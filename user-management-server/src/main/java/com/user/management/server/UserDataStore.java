package com.user.management.server;

import com.user.management.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserDataStore {
    private Map<Long, User> users;

    public UserDataStore() {
        users = new HashMap<>();

        User user1 = new User("Jonh", "aaa@gamil.com", "Dublin", "Talbot street", "Dublin", "Dublin 1", "Ireland", "123", null);
        User user2 = new User("Barbara", "bbb@gamil.com", "Dublin", "Some street", "Dublin", "Dublin 4", "Ireland", "568", null);
        User user3 = new User("Mark", "ccc@gamil.com", "Dublin", "Another street", "Dublin", "Dublin 7", "Ireland", "999", null);

        users.put(1L, user1);
        users.put(2L, user2);
        users.put(3L, user3);
    }

    public List<User> getUsers() {
        return new ArrayList(users.values());
    }
}
