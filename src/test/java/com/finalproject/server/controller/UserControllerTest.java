package com.finalproject.server.controller;

import com.finalproject.server.entity.ERole;
import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.payload.request.*;
import com.finalproject.server.payload.response.UserResponse;
import com.finalproject.server.repository.UserRepository;
import com.finalproject.server.service.RoleOperations;
import com.finalproject.server.service.TokenOperations;
import com.finalproject.server.service.UserOperations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.only;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {
    private MockMvc mvc;

    private UserOperations userOperations;
    private UserRepository userRepository;
    private TokenOperations tokenOperations;
    private RoleOperations roleOperations;

    @BeforeEach
    void setUp() {
        userOperations = mock(UserOperations.class);
        userRepository = mock(UserRepository.class);
        tokenOperations = mock(TokenOperations.class);
        roleOperations = mock(RoleOperations.class);
        mvc = MockMvcBuilders
                .standaloneSetup(new UserController(userOperations, userRepository, tokenOperations, roleOperations))
                .build();
    }

    @Test
    void testRegister() throws Exception {
        var request = new SignupRequest();
        var date = OffsetDateTime.now();
        request.setEmail("ivan@com.ua");
        request.setPassword("password");
        request.setUsername("ivan");

        var response = new UserResponse();
        response.setEmail("ivan@com.ua");
        response.setCreatedAt(date);
        response.setUsername("ivan");
        response.setId(69);
        response.setRoles(Set.of(ERole.ROLE_USER));

        when(userOperations.create(request)).thenReturn(response);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"ivan@com.ua\",\"username\":\"ivan\", \"password\":\"password\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userOperations, atLeastOnce()).create(request);
    }

    @Test
    void testGetUserByUsername() throws Exception {

        var response = new UserResponse();
        response.setRoles(Collections.singleton(ERole.ROLE_USER));
        response.setId(57);
        response.setEmail("alina@ua.com");
        response.setUsername("alina");
        response.setCreatedAt(null);


        when(userOperations.findByUsername(null)).thenReturn(Optional.of(response));

        mvc.perform(get("/users/me"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userOperations, atLeastOnce()).findByUsername(null);
    }

    @Test
    void testUpdateUsername() throws Exception {
        var request = new UpdateUserLoginRequest();
        request.setUsername("alina120");
        var response = new UserResponse();
        response.setRoles(Collections.singleton(ERole.ROLE_USER));
        response.setId(57);
        response.setEmail("alina@ua.com");
        response.setUsername("alina120");
        response.setCreatedAt(null);

        mvc.perform(patch("/users/me/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"alina120\"}"))
                .andExpect(status().isNoContent());

        verify(userOperations, atLeastOnce()).updateUsername(null, request);
    }

//    @Test
//    void testSendChangeEmailToken() throws Exception {
//        GetTokenForUpdateEmailRequest request = new GetTokenForUpdateEmailRequest();
//        request.setEmail("alina@ua.com");
//
//        String token = tokenOperations.add(null, request);
//        ResponseEntity<String> response = ResponseEntity.ok(token);
//
//
//        mvc.perform(post("/users/me/email")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"email\":\"alina@ua.com\"}"))
//                .andExpect(status().isOk());
//
//        verify(tokenOperations, only()).add(null, request);
//    }

    @Test
    void testUpdateEmail() throws Exception {
        var request = new ChangeEmailRequest();
        request.setEmail("alina222@com.ua");
        request.setToken("1234");

        mvc.perform(post("/users/me/email/change")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"alina222@com.ua\",\"token\":\"1234\"}"))
                .andExpect(status().isOk());

        verify(userOperations, atLeastOnce()).updateEmail(null, request);
    }

    @Test
    void testUpdatePassword() throws Exception {
        var request = new UpdateUserPasswordRequest();
        request.setOldPassword("1234");
        request.setPassword("12345");

        var response = new UserResponse();
        response.setRoles(Collections.singleton(ERole.ROLE_USER));
        response.setId(57);
        response.setEmail("alina@ua.com");
        response.setUsername("alina120");
        response.setCreatedAt(null);

        when(userOperations.updatePassword("alina" ,request)).thenReturn(response);

        mvc.perform(patch("/users/me/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"oldPassword\":\"1234\",\"password\":\"12345\"}"))
                .andExpect(status().isOk());

        verify(userOperations, atLeastOnce()).updatePassword(null, request);
    }

    @Test
    void testDeleteMessage() throws Exception {
        mvc.perform(delete("/users/me" ))
                .andExpect(status().isNoContent());

        verify(userOperations, atLeastOnce()).deleteByUsername(null);
        verifyNoMoreInteractions(userOperations);
    }
}
