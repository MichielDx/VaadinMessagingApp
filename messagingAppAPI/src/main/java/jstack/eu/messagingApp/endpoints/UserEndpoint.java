package jstack.eu.messagingApp.endpoints;

import jstack.eu.messagingApp.models.DTO.UserDTO;
import jstack.eu.messagingApp.models.User;
import jstack.eu.messagingApp.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserEndpoint {
    private Service service;

    @Autowired
    public UserEndpoint(Service service) {
        this.service = service;
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = "application/json")
    public User getUserByName(@PathVariable(value = "username") String username) {
        return service.getUserByName(username);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    public boolean login(@RequestBody UserDTO userDTO) {
        return service.login(userDTO.getUsername(), userDTO.getPassword());
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json")
    public boolean createUser(@RequestBody UserDTO userDTO) {
        return service.createUser(userDTO.getUsername(), userDTO.getPassword());
    }
}
