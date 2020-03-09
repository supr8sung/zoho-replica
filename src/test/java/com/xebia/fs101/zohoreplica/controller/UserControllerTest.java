package com.xebia.fs101.zohoreplica.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xebia.fs101.zohoreplica.api.request.UserRequest;
import com.xebia.fs101.zohoreplica.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void should_be_able_to_save_a_user() throws Exception {
        UserRequest userRequest = new UserRequest.Builder()
                .withUsername("supr8sung")
                .withFullname("Supreet Singh")
                .withEmail("supreetsingh@xebia.com")
                .withPassword("hola@bitch")
                .withMobile("9643496936")
                .withCompany("XEBIA")
                .build();
        String json = objectMapper.writeValueAsString(userRequest);
        this.mockMvc.perform(post("/signup").accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.username").value("supr8sung"))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.status").value("S"));

    }

    @Test
    public void should_be_able_to_find_an_user_by_username() throws  Exception{
        UserRequest userRequest = new UserRequest.Builder()
                .withUsername("supr8sung")
                .withFullname("Supreet Singh")
                .withEmail("supreetsingh@xebia.com")
                .withPassword("hola@bitch")
                .withMobile("9643496936")
                .withCompany("XEBIA")
                .build();
        userRepository.save(userRequest.toUser());
        this.mockMvc.perform(get("/profiles/{username}","supr8sung"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.fullname").value("Supreet Singh"))
                .andExpect(jsonPath("$.status").value("S"));


    }
}