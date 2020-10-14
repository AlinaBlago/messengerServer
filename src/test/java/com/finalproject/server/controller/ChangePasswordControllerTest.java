package com.finalproject.server.controller;

import com.finalproject.server.payload.request.ChangePasswordRequest;
import com.finalproject.server.payload.request.UserRequest;
import com.finalproject.server.service.TokenOperations;
import com.finalproject.server.service.UserOperations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChangePasswordControllerTest {
    private MockMvc mvc;

    private UserOperations userOperations;
    private TokenOperations tokenOperations;

    @BeforeEach
    void setUp() {
        userOperations = mock(UserOperations.class);
        tokenOperations = mock(TokenOperations.class);
        mvc = MockMvcBuilders
                .standaloneSetup(new ChangePasswordController(userOperations, tokenOperations))
                .build();
    }

    @Test
    void testGetChangePasswordToken() throws Exception {
        var request = new UserRequest();
        request.setUsername("alina");

        mvc.perform(post("/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"alina\"}"))
                .andExpect(status().isOk());

        verify(tokenOperations, atLeastOnce()).add(request);
    }

    @Test
    void testChangePassword() throws Exception {
        var request = new ChangePasswordRequest();
        request.setToken("123");
        request.setPassword("12345");
        request.setUsername("alina");


        mvc.perform(post("/password/change")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"alina\",\"token\":\"123\", \"password\":\"12345\"}"))
                .andExpect(status().isOk());

        verify(userOperations, atLeastOnce()).updateForgottenPassword(request);
    }
}
