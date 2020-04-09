package com.xebia.fs101.zohoreplica.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xebia.fs101.zohoreplica.api.request.ChangePasswordRequest;
import com.xebia.fs101.zohoreplica.api.request.ForgotPasswordRequest;
import com.xebia.fs101.zohoreplica.api.request.UserRequest;
import com.xebia.fs101.zohoreplica.entity.User;
import com.xebia.fs101.zohoreplica.entity.UserToken;
import com.xebia.fs101.zohoreplica.repository.UserRepository;
import com.xebia.fs101.zohoreplica.repository.UserTokenRepository;
import com.xebia.fs101.zohoreplica.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UserControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private UserTokenRepository userTokenRepository;
    private User mainUser;

    @BeforeEach
    public void setUp() {

        UserRequest userRequest = new UserRequest.Builder()
                .withUsername("supr8sung")
                .withFullname("Supreet Singh")
                .withEmail("supreetsingh@xebia.com")
                .withPassword("1234")
                .withMobile("9643496936")
                .withCompany("XEBIA")
                .wihtBirthday("1/11/2019")
                .build();
        mainUser = userRepository.save(userRequest.toUser(passwordEncoder));
    }

    @AfterEach
    void tearDown() {

        userRepository.deleteAll();
        userTokenRepository.deleteAll();
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
                .wihtBirthday("1/11/2019")
                .build();
        String json = objectMapper.writeValueAsString(userRequest);
        this.mockMvc.perform(post("/zoho/signup").accept(MediaType.APPLICATION_JSON)
                                     .content(json)
                                     .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.status").value("S"));
    }

    @Test
    void user_should_be_able_to_login_and_get_back_status_ok() throws Exception {

        this.mockMvc.perform(get("/")
                                     .with(httpBasic("supr8sung", "1234")))
                .andExpect(status().isOk());
    }

    @Test
    void user_should_not_login_if_wrong_username_or_password_given() throws Exception {

        this.mockMvc.perform(get("/login")
                                     .with(httpBasic("supre", "1234")))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void should_not_save_a_user_if_any_required_filed_is_missing() throws Exception {

        UserRequest userRequest = new UserRequest.Builder()
                .withUsername("supr9sung")
                .withFullname("Supreet Singh")
                .withEmail("supreetsingh5@xebia.com")
                .withPassword("hola@bitch")
                .withCompany("XEBIA")
                .wihtBirthday("1/11/2019")
                .build();
        String json = objectMapper.writeValueAsString(userRequest);
        this.mockMvc.perform(post("/zoho/signup").accept(MediaType.APPLICATION_JSON)
                                     .content(json)
                                     .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    public void should_be_able_to_find_an_user_by_username() throws Exception {

        this.mockMvc.perform(get("/zoho/user/{username}", "supr8sung")
                                     .accept(MediaType.APPLICATION_JSON)
                                     .with(httpBasic("supr8sung", "1234")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.fullname").value("Supreet Singh"))
                .andExpect(jsonPath("$.status").value("S"))
                .andExpect(jsonPath("$.data.followersCount").isNotEmpty());
    }

    @Test
    public void user_should_be_able_to_upload_profile_picture() throws Exception {

        ResultMatcher ok = MockMvcResultMatchers.status().isOk();
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile(
                        "file",
                        "test.jpeg",
                        MediaType.IMAGE_JPEG_VALUE,
                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));
        mockMvc.perform(multipart("/zoho/user/upload", "supr8sung")
                                .file(mockMultipartFile)
                                .with(user("supr8sung").password("1234").roles("EMPLOYEE")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("profile picture uploaded"));
    }

    @Test
    void should_be_able_to_follow_an_user() throws Exception {

        UserRequest userRequest = new UserRequest.Builder()
                .withUsername("nisha")
                .withFullname("Nisha Kaur")
                .withPassword("lier")
                .withCompany("pari")
                .withEmail("nisha@gmail.com")
                .withMobile("7408738100")
                .wihtBirthday("1/11/2019")
                .build();
        userRepository.save(userRequest.toUser(passwordEncoder));
        this.mockMvc.perform(post("/zoho/user/follow").param("target", "supr8sung")
                                     .with(user("nisha").password("lier").roles("EMPLOYEE")))
                .andDo(print())
                .andExpect(status().isCreated());
        User nisha = userService.findByName("nisha");
        assertEquals(1, nisha.getFollowingCount());
        User supr8sung = userService.findByName("supr8sung");
        assertEquals(1, supr8sung.getFollowersCount());
    }

    @Test
    void should_be_able_to_unfollow_an_user() throws Exception {

        UserRequest userRequest = new UserRequest.Builder()
                .withUsername("nisha")
                .withFullname("Nisha Kaur")
                .withPassword("lier")
                .withCompany("pari")
                .withEmail("nisha@gmail.com")
                .withMobile("7408738100")
                .wihtBirthday("1/11/2019")
                .build();
        User savedNisha = userRepository.save(userRequest.toUser(passwordEncoder));
        savedNisha.setFollowingCount(1);
        Set<String> followingList = new HashSet<>();
        followingList.add("supr8sung");
        savedNisha.setFollowing(followingList);
        userRepository.save(savedNisha);
        mainUser.setFollowersCount(1);
        Set<String> followerList = new HashSet<>();
        followerList.add("nisha");
        mainUser.setFollowers(followerList);
        userRepository.save(mainUser);
        this.mockMvc.perform(post("/zoho/user/unfollow").param("target", "supr8sung")
                                     .with(user("nisha").password("lier").roles("EMPLOYEE")))
                .andDo(print())
                .andExpect(status().isCreated());
        User requestesUser = userService.findByName("nisha");
        assertEquals(0, requestesUser.getFollowingCount());
        User targetUser = userService.findByName("supr8sung");
        assertEquals(0, targetUser.getFollowersCount());
    }

    @Test
    void logged_in_user_can_change_password_if_he_knows_current_password() throws Exception {

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("1234", "54321");
        String json = objectMapper.writeValueAsString(changePasswordRequest);
        this.mockMvc.perform(post("/zoho/user/change-password")
                                     .accept(MediaType.APPLICATION_JSON)
                                     .content(json)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .with(httpBasic("supr8sung", "1234")))
                .andDo(print())
                .andExpect(status().isCreated());
        this.mockMvc.perform(get("/zoho/user/{username}", "supr8sung")
                                     .accept(MediaType.APPLICATION_JSON)
                                     .with(httpBasic("supr8sung", "54321")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void should_not_change_password_if_current_password_is_wrong() throws Exception {

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("123", "54321");
        String json = objectMapper.writeValueAsString(changePasswordRequest);
        this.mockMvc.perform(post("/zoho/user/change-password")
                                     .accept(MediaType.APPLICATION_JSON)
                                     .content(json)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .with(httpBasic("supr8sung", "1234")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("B"))
                .andExpect(jsonPath("$.message").value("Password not matched"));
    }

    @Test
    void validate_a_username_if_it_is_already_taken_or_not() throws Exception {

        this.mockMvc.perform(get("/zoho/valid").param("username", "supr8sung"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(false));
    }

    @Test
    void should_change_password_after_providing_otp_sent_to_mail() throws Exception {

        UserToken userToken = new UserToken("supr8sung", "0987");
        UserToken savedUserToken = userTokenRepository.save(userToken);
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest(
                savedUserToken.getId(),
                "0987", "123456");
        String json = objectMapper.writeValueAsString(forgotPasswordRequest);
        this.mockMvc.perform(
                post("/zoho/user/recover-password").accept(MediaType.APPLICATION_JSON).content(
                        json).contentType(MediaType.APPLICATION_JSON).with(
                        httpBasic("supr8sung", "1234")))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("S"));
        this.mockMvc.perform(get("/login").with(httpBasic("supr8sung", "123456")))
                .andDo(print())
                .andExpect(status().isOk());
    }
}