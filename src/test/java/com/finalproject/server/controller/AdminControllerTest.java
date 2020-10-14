package com.finalproject.server.controller;

import com.finalproject.server.entity.ERole;
import com.finalproject.server.payload.request.SignupRequest;
import com.finalproject.server.payload.request.UserRequest;
import com.finalproject.server.payload.response.UserResponse;
import com.finalproject.server.repository.UserRepository;
import com.finalproject.server.service.UserOperations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.OffsetDateTime;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminControllerTest {
    private MockMvc mvc;

    private UserOperations userOperations;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userOperations = mock(UserOperations.class);
        userRepository = mock(UserRepository.class);
        mvc = MockMvcBuilders
                .standaloneSetup(new AdminController(userOperations, userRepository))
                .build();
    }

    @Test
    void testRegister() throws Exception {
        var request = new SignupRequest();
        var date = OffsetDateTime.now();
        request.setEmail("ivan2@com.ua");
        request.setPassword("password");
        request.setUsername("ivan2");

        var response = new UserResponse();
        response.setEmail("ivan2@com.ua");
        response.setCreatedAt(date);
        response.setUsername("ivan2");
        response.setId(69);
        response.setRoles(Set.of(ERole.ROLE_ADMIN));

        when(userOperations.createAdmin(request)).thenReturn(response);

        mvc.perform(post("/users/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"ivan2@com.ua\",\"username\":\"ivan2\",\"password\":\"password\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userOperations, atLeastOnce()).createAdmin(request);
    }

    @Test
    void testDeleteUser() throws Exception {
        var request = new UserRequest();
        request.setUsername("alina");

        mvc.perform(delete("/users/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"alina\"}"))
                .andExpect(status().isOk());

        verify(userOperations, atLeastOnce()).deleteByUsername(request.getUsername());
    }

    @Test
    void testLockUser() throws Exception {
        var request = new UserRequest();
        request.setUsername("alina");

        mvc.perform(patch("/users/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"alina\"}"))
                .andExpect(status().isOk());

        verify(userOperations, atLeastOnce()).lockByUsername(request);
    }

    @Test
    void testUnlockUser() throws Exception {
        var request = new UserRequest();
        request.setUsername("alina");

        mvc.perform(post("/users/admins/unlock")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"alina\"}"))
                .andExpect(status().isOk());

        verify(userOperations, atLeastOnce()).unLockByUsername(request);
    }

    @Test
    void testFindUser() throws Exception {
        var request = new UserRequest();
        request.setUsername("alina");

        mvc.perform(post("/users/admins/find")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"alina\"}"))
                .andExpect(status().isOk());

        verify(userRepository, atLeastOnce()).findMessengerUsersByUsernameIsStartingWith(request.getUsername());
    }

}
