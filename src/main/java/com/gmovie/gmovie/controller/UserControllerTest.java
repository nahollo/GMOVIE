// package com.gmovie.gmovie.controller;

// import com.gmovie.gmovie.domain.User;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.web.client.TestRestTemplate;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;

// import static org.assertj.core.api.Assertions.assertThat;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// public class UserControllerTest {

// @Autowired
// private TestRestTemplate restTemplate;

// @Test
// public void testCreateUser() {
// User user = new User();
// user.setName("John Doe");
// user.setEmail("john.doe@example.com");

// ResponseEntity<User> response = restTemplate.postForEntity("/users", user,
// User.class);

// assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
// assertThat(response.getBody().getName()).isEqualTo(user.getName());
// assertThat(response.getBody().getEmail()).isEqualTo(user.getEmail());
// }
// }
