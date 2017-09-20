package jstack.eu.messagingApp.services;

import com.google.gson.Gson;
import jstack.eu.messagingApp.models.DTO.UserDTO;
import jstack.eu.messagingApp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@org.springframework.stereotype.Service
public class UserService {
    private RestTemplate restTemplate = new RestTemplate();
    private String BASE_URL = "http://localhost:9003/api/v1/user";

    private Gson gson;

    @Autowired
    public UserService(Gson gson) {
        this.gson = gson;
    }


    public User getUserByName(String value) {
        return restTemplate.getForObject(BASE_URL + "/" + value, User.class);
    }

    public boolean login(String username, String password) {
        return restTemplate.postForEntity(BASE_URL + "/login",
                createEntity(new UserDTO(username, password)), Boolean.class).getBody();
    }

    public boolean createUser(String username, String password) {
        User user = restTemplate.postForEntity(BASE_URL, createEntity(new User(username, password)), User.class).getBody();
        return user != null;
    }

    private HttpEntity<String> createEntity(Object obj) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<String>(gson.toJson(obj), headers);
    }
}
