package com.user.management.client;

import com.user.management.client.misc.Try;
import com.user.management.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.util.function.Function.identity;

@RestController
@RequestMapping("/rest")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    private Consumer<RuntimeException> onFailure = ex -> {
        logger.debug(ex.getMessage(), ex);
        throw new ClientException(ex);
    };

    @RequestMapping(path = "/user/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public User getUser(@PathVariable("id") Long id) {
        return process(() -> userService.getUserById(id));
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<User> getUsers() {
        return process(() -> userService.getAllUsers());
    }

    @RequestMapping(path = "/createuser", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Long createUser(@RequestBody User user) {
        return process(() -> {
            return userService.addUser(user);
        }).getId();
    }

    @RequestMapping(path = "/deleteuser/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") Long id) {
        process(() -> userService.removeUserById(id));
    }

    @RequestMapping(path = "/updateuser/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        process(() -> userService.updateUser(id, user));
    }

    private <A> A process(Supplier<Try<A, RuntimeException>> f) {
        return f.get().onFinish(identity(), onFailure);
    }

}
