package com.user.management.client;

import com.user.management.client.misc.Try;
import com.user.management.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.user.management.client.misc.Try.*;
import static java.util.Optional.ofNullable;

@Component
public class UserService {

    private UserDao userDao;
    private RestTemplate restTemplate;
    private String serverUrl;
    private DateTimeFormatter dateFormat;

    public UserService(UserDao userDao, RestTemplateBuilder restTemplateBuilder, @Value("${user.server.url}") String serverUrl) {
        this.userDao = userDao;
        this.restTemplate = restTemplateBuilder.build();
        this.serverUrl = serverUrl;
        dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    }

    @Transactional(readOnly = true)
    public Try<Unit, RuntimeException> importUsers() {
        return execute(() -> {
            ResponseEntity<List<User>> usersResponse = restTemplate
                    .exchange(serverUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                    });
            List<User> users = usersResponse.getBody();

            String retrievalTime = dateFormat.format(LocalDateTime.now());
            users.stream().forEach(user -> {
                user.setRetrievalTime(retrievalTime);
            });

            userDao.save(users);

            return success(unit());
        }, ex -> failure(new RuntimeException("Error importing users from server", ex)));

    }

    @Transactional(readOnly = true)
    public Try<List<User>, RuntimeException> getAllUsers() {
        return execute(() -> {
            List<User> users = new ArrayList<>();
            userDao.findAll().forEach(users::add);

            return success(users);
        }, ex -> failure(new RuntimeException("Error getting all users", ex)));
    }

    @Transactional(readOnly = true)
    public Try<User, RuntimeException> getUserById(Long id) {
        return execute(() -> {
            return ofNullable(userDao.findOne(id))
                    .<Try<User, RuntimeException>>map(Try::success)
                    .orElse(failure(new RuntimeException("No user with ID 'id" + id + "'")));
        }, ex -> failure(new RuntimeException("Error getting user with ID '" + "'", ex)));
    }

    @Transactional
    public Try<User, RuntimeException> addUser(User user) {
        return execute(() -> {
            return success(userDao.save(user));
        }, ex -> failure(new RuntimeException("Error adding user: '" + user + "'", ex)));
    }

    @Transactional
    public Try<Unit, RuntimeException> removeUserById(Long id) {
        return execute(() -> {
            userDao.delete(id);
            return success(unit());
        }, ex -> failure(new RuntimeException("Error deleting user with ID '" + id + "'", ex)));
    }

    @Transactional
    public Try<User, RuntimeException> updateUser(Long id, User user) {
        return execute(() -> {
            if (userDao.exists(id)) {
                User oldUser = userDao.findOne(id);
                user.setId(id);
                user.setRetrievalTime(oldUser.getRetrievalTime());

                return success(userDao.merge(user));
            } else {
                return failure(new RuntimeException("Cannot update, no user with ID '" + id + "'"));
            }
        }, ex -> failure(new RuntimeException("Error updating user with ID '" + id + "'", ex)));
    }

}
