package com.xebia.fs101.zohoreplica.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xebia.fs101.zohoreplica.api.request.AttendanceRequest;
import com.xebia.fs101.zohoreplica.api.request.UserRequest;
import com.xebia.fs101.zohoreplica.entity.Employee;
import com.xebia.fs101.zohoreplica.repository.AttendanceRepository;
import com.xebia.fs101.zohoreplica.repository.EmployeeRepository;
import com.xebia.fs101.zohoreplica.service.AttendanceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
class AttendanceControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AttendanceService attendanceService;

    private Employee employee;

    @BeforeEach
    public void setUp() {
        UserRequest userRequest = new UserRequest.Builder()
                .withUsername("supr8sung")
                .withFullname("Supreet Singh")
                .withEmail("supreetsingh@xebi.com")
                .withPassword("holahola")
                .withMobile("9643496936")
                .withCompany("Xebia")
                .build();

        employee = employeeRepository.save(userRequest.toUser());
    }

    @AfterEach
    public void tearDown() {
        attendanceRepository.deleteAll();
        employeeRepository.deleteAll();
        ;
    }

    @Test
    void user_should_be_able_to_checkin() throws Exception {
        AttendanceRequest attendanceRequest = new AttendanceRequest(employee.getUsername());
        String json = objectMapper.writeValueAsString(attendanceRequest);
        this.mockMvc.perform(post("/zoho/checkin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("S"));

    }

    @Test
    void user_should_be_able_to_checkout() throws Exception {
        AttendanceRequest attendanceRequest = new AttendanceRequest(employee.getUsername());
        String json = objectMapper.writeValueAsString(attendanceRequest);
        this.mockMvc.perform(post("/zoho/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("S"));

    }

    @Test
    void should_get_daily_working_hours() throws Exception{
        AttendanceRequest attendanceRequest = new AttendanceRequest(employee.getUsername());
        attendanceRequest.checkin(employee);
        attendanceService.doCheckin(attendanceRequest.checkin(employee));

        attendanceService.doCheckout(employee.getId(), LocalTime.now().plus(8,HOURS).plus(5,MINUTES));
       this.mockMvc.perform(get("/zoho/dailyhours/{username}","supr8sung"))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data").value("08:05"));

    }
}