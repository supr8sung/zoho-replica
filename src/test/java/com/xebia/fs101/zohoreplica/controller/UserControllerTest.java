//package com.xebia.fs101.zohoreplica.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.xebia.fs101.zohoreplica.api.request.UserRequest;
//import com.xebia.fs101.zohoreplica.entity.User;
//import com.xebia.fs101.zohoreplica.repository.UserRepository;
//import com.xebia.fs101.zohoreplica.service.UserService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultMatcher;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.web.context.WebApplicationContext;
//
//import javax.transaction.Transactional;
//import java.nio.charset.StandardCharsets;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.LinkedList;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//@Transactional
//class UserControllerTest {
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private UserService userService;
//
//    private User mainUser;
//
//    @BeforeEach
//    public void setUp() {
//        UserRequest userRequest = new UserRequest.Builder()
//                .withUsername("supr8sung")
//                .withFullname("Supreet Singh")
//                .withEmail("supreetsingh@xebia.com")
//                .withPassword("1234")
//                .withMobile("9643496936")
//                .withCompany("XEBIA")
//                .build();
//        mainUser=userRepository.save(userRequest.toUser(passwordEncoder));
//    }
//
//    @AfterEach
//    void tearDown() {
//        userRepository.deleteAll();
//    }
//
//    @Test
//    void should_be_able_to_save_a_user() throws Exception {
//        UserRequest userRequest = new UserRequest.Builder()
//                .withUsername("supr9sung")
//                .withFullname("Supreet Singh")
//                .withEmail("supreetsingh5@xebia.com")
//                .withPassword("hola@bitch")
//                .withMobile("9643496936")
//                .withCompany("XEBIA")
//                .build();
//        String json = objectMapper.writeValueAsString(userRequest);
//        this.mockMvc.perform(post("/zoho/signup").accept(MediaType.APPLICATION_JSON)
//                .content(json)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.data").value("supr9sung"))
//                .andExpect(jsonPath("$.status").value("S"));
//
//    }
//
//    @Test
//    void should_not_save_a_user_if_any_required_filed_is_missing() throws Exception {
//        UserRequest userRequest = new UserRequest.Builder()
//                .withUsername("supr9sung")
//                .withFullname("Supreet Singh")
//                .withEmail("supreetsingh5@xebia.com")
//                .withPassword("hola@bitch")
//                .withCompany("XEBIA")
//                .build();
//        String json = objectMapper.writeValueAsString(userRequest);
//        this.mockMvc.perform(post("/zoho/signup").accept(MediaType.APPLICATION_JSON)
//                .content(json)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isBadRequest());
//
//    }
//
//    @Test
//    public void should_be_able_to_find_an_user_by_username() throws Exception {
//        this.mockMvc.perform(get("/zoho/user/{username}", "supr8sung")
//                .with(user("supr8sung").password("1234").roles("EMPLOYEE")))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.fullname").value("Supreet Singh"))
//                .andExpect(jsonPath("$.status").value("S"));
//
//    }
//
//    @Test
//    public void user_should_be_able_to_upload_profile_picture() throws Exception {
//        ResultMatcher ok = MockMvcResultMatchers.status().isOk();
//        MockMultipartFile mockMultipartFile =
//                new MockMultipartFile(
//                        "file",
//                        "test.jpeg",
//                        MediaType.IMAGE_JPEG_VALUE,
//                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));
//
//        mockMvc.perform(multipart("/zoho/user/upload", "supr8sung")
//                .file(mockMultipartFile)
//                .with(user("supr8sung").password("1234").roles("EMPLOYEE")))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("profile picture uploaded"));
//    }
//
////    @Test
////    void should_be_able_to_follow_an_user() throws Exception {
////        UserRequest userRequest = new UserRequest.Builder()
////                .withUsername("nisha")
////                .withFullname("Nisha Kaur")
////                .withPassword("lier")
////                .withCompany("pari")
////                .withEmail("nisha@gmai.com")
////                .withMobile("7408738100")
////                .build();
////        userRepository.save(userRequest.toUser(passwordEncoder));
////        this.mockMvc.perform(post("/zoho/user/follow").param("target", "supr8sung")
////                .with(user("nisha").password("lier").roles("EMPLOYEE")))
////                .andDo(print())
////                .andExpect(status().isCreated());
////        User nisha = userService.findByName("nisha");
////        assertEquals(1, nisha.getFollowingCount());
////        User supr8sung = userService.findByName("supr8sung");
////        assertEquals(1, supr8sung.getFollowersCount());
////
////    }
////
////    @Test
////    void should_be_able_to_unfollow_an_user() throws Exception {
////
////        UserRequest userRequest = new UserRequest.Builder()
////                .withUsername("nisha")
////                .withFullname("Nisha Kaur")
////                .withPassword("lier")
////                .withCompany("pari")
////                .withEmail("nisha@gmai.com")
////                .withMobile("7408738100")
////                .build();
////        User currentUser = userRepository.save(userRequest.toUser(passwordEncoder));
//////        currentUser.setFollowingCount(1);
//////        currentUser.setFollowing(new LinkedList<>(Arrays.asList(mainUser)));
//////        userRepository.save(currentUser);
//////        mainUser.setFollowersCount(1);
//////        mainUser.setFollowers(new LinkedList<>(Arrays.asList(currentUser)));
//////        userRepository.save(mainUser);
////
////        userService.follow(currentUser,mainUser);
////
//////        save.setFollowingCount(1);
//////        User one = userRepository.getOne(save.getId());
//////        one.setFollowingCount(1);
//////        userRepository.save(one);
////
////
////
////
////        this.mockMvc.perform(post("/zoho/user/unfollow").param("target", "supr8sung")
////                .with(user("nisha").password("lier").roles("EMPLOYEE")))
////                .andDo(print())
////                .andExpect(status().isCreated());
////        User requestesUser = userService.findByName("nisha");
////        assertEquals(0, requestesUser.getFollowingCount());
////        User targetUser = userService.findByName("supr8sung");
////        assertEquals(0, targetUser.getFollowersCount());
////
////    }
//}