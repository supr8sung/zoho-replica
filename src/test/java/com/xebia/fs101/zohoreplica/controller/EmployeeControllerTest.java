package com.xebia.fs101.zohoreplica.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xebia.fs101.zohoreplica.api.request.UserRequest;
import com.xebia.fs101.zohoreplica.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")

class EmployeeControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;


    @BeforeEach
    public void setUp(){
        UserRequest userRequest = new UserRequest.Builder()
                .withUsername("supr8sung")
                .withFullname("Supreet Singh")
                .withEmail("supreetsingh@xebia.com")
                .withPassword("hola@bitch")
                .withMobile("9643496936")
                .withCompany("XEBIA")
                .build();
        employeeRepository.save(userRequest.toUser());
    }

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    public void user_should_be_able_to_upload_profile_picture() throws Exception {
        ResultMatcher ok= MockMvcResultMatchers.status().isOk();
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile(
                        "file",
                        "test.jpeg",
                        MediaType.IMAGE_JPEG_VALUE,
                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(multipart("/zoho/profiles/upload/{username}","supr8sung")
                .file(mockMultipartFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("profile picture uploaded"));
    }

    @Test
    void should_be_able_to_save_a_user() throws Exception {
        UserRequest userRequest = new UserRequest.Builder()
                .withUsername("supr9sung")
                .withFullname("Supreet Singh")
                .withEmail("supreetsingh5@xebia.com")
                .withPassword("hola@bitch")
                .withMobile("9643496936")
                .withCompany("XEBIA")
                .build();
        String json = objectMapper.writeValueAsString(userRequest);
        this.mockMvc.perform(post("/zoho/signup").accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.username").value("supr9sung"))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.status").value("S"));

    }

    @Test
    public void should_be_able_to_find_an_user_by_username() throws  Exception{

        this.mockMvc.perform(get("/zoho/profiles/{username}","supr8sung"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.fullname").value("Supreet Singh"))
                .andExpect(jsonPath("$.status").value("S"));


    }
}