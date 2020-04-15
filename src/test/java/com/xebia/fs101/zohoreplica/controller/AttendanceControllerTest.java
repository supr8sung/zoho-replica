package com.xebia.fs101.zohoreplica.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xebia.fs101.zohoreplica.api.request.UserRequest;
import com.xebia.fs101.zohoreplica.entity.Attendance;
import com.xebia.fs101.zohoreplica.entity.User;
import com.xebia.fs101.zohoreplica.model.Clients;
import com.xebia.fs101.zohoreplica.repository.AttendanceRepository;
import com.xebia.fs101.zohoreplica.repository.UserRepository;
import com.xebia.fs101.zohoreplica.service.AttendanceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalTime;

import static com.xebia.fs101.zohoreplica.model.Clients.XEBIA;
import static com.xebia.fs101.zohoreplica.security.ZohoApplicationRole.ADMIN;
import static com.xebia.fs101.zohoreplica.security.ZohoApplicationRole.EMPLOYEE;
import static java.time.temporal.ChronoUnit.MINUTES;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AttendanceControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AttendanceService attendanceService;

    private User user;


    @BeforeEach
    public void setUp() {
        UserRequest userRequest = new UserRequest.Builder()
                .withUsername("supr8sung")
                .withFullname("Supreet Singh")
                .withEmail("supreetsingh@xebi.com")
                .withPassword("12345")
                .withMobile("9643496936")

                .wihtBirthday("1/11/2019")
                .build();

        user = userRepository.save(userRequest.toUser(passwordEncoder, XEBIA));
    }

    @AfterEach
    public void tearDown() {
        attendanceRepository.deleteAll();
        userRepository.deleteAll();
        ;
    }

    @Test
    void user_should_be_able_to_checkin() throws Exception {
        this.mockMvc.perform(post("/zoho/user/checkin").accept(MediaType.APPLICATION_JSON)
                .with(user("supr8sung").password("12345").roles("USER"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("S"))
                .andExpect(jsonPath("$.data").isNotEmpty());

    }

    @Test
    void user_should_be_able_to_checkout() throws Exception {

        Attendance attendance = attendanceService.checkin(user);

        this.mockMvc.perform(post("/zoho/user/checkout/{id}",attendance.getId())
                .with(user("supr8sung").password("12345").roles("USER"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("S"));

    }



    @Test
    void should_get_daily_working_hours() throws Exception {

        Attendance attendance = attendanceService.checkin(user);
        attendance.setCheckout(LocalTime.now().plus(8, MINUTES));
        attendanceRepository.save(attendance);

        this.mockMvc.perform(get("/zoho/user/dailyhours", "supr8sung")
                .with(user("supr8sung").password("12345").roles("USER")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("00:08"));

    }


}