package com.thoughtworks.rslist.api;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setup(){
        UserController.users.clear();
    }
@Test
    void shouldRegisterUser() throws Exception {
        User user=new User("Alibaba",18,"male","a@b.com","11234567890");
        ObjectMapper objectMapper=new ObjectMapper();
        String userJson=objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType
                (MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        assertEquals(1,UserController.users.size());
    }

    @Test
    void nameShouldNotLongerThan8()throws Exception {
        User user=new User("Alibaba12",18,"male","a@b.com","11234567890");
        ObjectMapper objectMapper=new ObjectMapper();
        String userJson=objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType
                (MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
    @Test
    void nameShouldNotNull()throws Exception {
        User user=new User(null,18,"male","a@b.com","11234567890");
        ObjectMapper objectMapper=new ObjectMapper();
        String userJson=objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType
                (MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
    @Test
    void genderShouldNotNull()throws Exception {
        User user=new User("Alibaba",18,null,"a@b.com","11234567890");
        ObjectMapper objectMapper=new ObjectMapper();
        String userJson=objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType
                (MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
}


