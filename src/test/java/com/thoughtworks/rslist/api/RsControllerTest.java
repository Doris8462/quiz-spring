package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.api.RsController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.UsersDetail;
import org.springframework.beans.factory.annotation.Autowired;


@SpringBootTest
@AutoConfigureMockMvc
class RsControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void initMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(new RsController()).build();
    }

    @Test
    void shouldGetOneRsEvent() throws Exception {
        mockMvc.perform(get("/rs/list/1"))
                .andExpect(jsonPath("$.eventName", is("第一条事件")))
                .andExpect(jsonPath("$.keyword", is("无分类")))
                .andExpect(jsonPath("$.user.userName",is("A")))
                .andExpect(jsonPath("$.user.age",is(18)))
                .andExpect(jsonPath("$.user.gender",is("male")))
                .andExpect(jsonPath("$.user.email",is("A@qq.com")))
                .andExpect(jsonPath("$.user.phoneNumber",is("1234567890")))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list/2"))
                .andExpect(jsonPath("$.eventName", is("第二条事件")))
                .andExpect(jsonPath("$.keyword", is("无分类")))
                .andExpect(jsonPath("$.user.userName",is("B")))
                .andExpect(jsonPath("$.user.age",is(28)))
                .andExpect(jsonPath("$.user.gender",is("female")))
                .andExpect(jsonPath("$.user.email",is("B@qq.com")))
                .andExpect(jsonPath("$.user.phoneNumber",is("1234567890")))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list/3"))
                .andExpect(jsonPath("$.eventName", is("第三条事件")))
                .andExpect(jsonPath("$.keyword", is("无分类")))
                .andExpect(jsonPath("$.user.age",is(38)))
                .andExpect(jsonPath("$.user.gender",is("male")))
                .andExpect(jsonPath("$.user.email",is("C@qq.com")))
                .andExpect(jsonPath("$.user.phoneNumber",is("1234567890")))
                .andExpect(status().isOk());
    }

    @Test
        void  shouldGetRsEventsFromStartToEnd() throws Exception {
            mockMvc.perform(get("/rs/list/?start=1&end=3"))
                    .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
                    .andExpect(jsonPath("$[0].keyword", is("无分类")))
                    .andExpect(jsonPath("$[0].user.userName",is("A")))
                    .andExpect(jsonPath("$[0].user.age",is(18)))
                    .andExpect(jsonPath("$[0].user.gender",is("male")))
                    .andExpect(jsonPath("$[0].user.email",is("A@qq.com")))
                    .andExpect(jsonPath("$[0].user.phoneNumber",is("1234567890")))
                    .andExpect(jsonPath("$[1].eventName", is("第二条事件")))
                    .andExpect(jsonPath("$[1].keyword", is("无分类")))
                    .andExpect(jsonPath("$[1].user.userName",is("B")))
                    .andExpect(jsonPath("$[1].user.age",is(28)))
                    .andExpect(jsonPath("$[1].user.gender",is("female")))
                    .andExpect(jsonPath("$[1].user.email",is("B@qq.com")))
                    .andExpect(jsonPath("$[1].user.phoneNumber",is("1234567890")))
                    .andExpect(jsonPath("$[2].eventName", is("第三条事件")))
                    .andExpect(jsonPath("$[2].keyword", is("无分类")))
                    .andExpect(jsonPath("$[2].user.age",is(38)))
                    .andExpect(jsonPath("$[2].user.gender",is("male")))
                    .andExpect(jsonPath("$[2].user.email",is("C@qq.com")))
                    .andExpect(jsonPath("$[2].user.phoneNumber",is("1234567890")))
                    .andExpect(status().isOk());
        }

       @Test
        void shouldAddOneRsEvent() throws Exception {
            String requestJson = "{\"eventName\":\"第四条事件\",\"keyword\":\"无分类\"}";
            mockMvc.perform(post("/rs/add")
                    .content(requestJson)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            mockMvc.perform(get("/rs/list"))
                    .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
                    .andExpect(jsonPath("$[0].keyword", is("无分类")))
                    .andExpect(jsonPath("$[1].eventName", is("第二条事件")))
                    .andExpect(jsonPath("$[1].keyword", is("无分类")))
                    .andExpect(jsonPath("$[2].eventName", is("第三条事件")))
                    .andExpect(jsonPath("$[2].keyword", is("无分类")))
                    .andExpect(jsonPath("$[3].eventName", is("第四条事件")))
                    .andExpect(jsonPath("$[3].keyword", is("无分类")))
                    .andExpect(status().isOk());
        }

        @Test
        void shouldUpdateRsEventGivenIndex() throws Exception {
            String requestJsonAll = "{\"eventName\":\"要修改的事件\",\"keyword\":\"要修改的分类\"}";
            mockMvc.perform(post("/rs/update/1")
                    .content(requestJsonAll)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
            mockMvc.perform(get("/rs/list/1"))
                    .andExpect(jsonPath("$.eventName", is("要修改的事件")))
                    .andExpect(jsonPath("$.keyword", is("要修改的分类")))
                    .andExpect(status().isOk());
        }

        @Test
        void shouldDeleteRsEventGivenIndex() throws Exception {
            mockMvc.perform(post("/rs/delete/1"))
                    .andExpect(status().isOk());

            mockMvc.perform(get("/rs/list"))
                    .andExpect(jsonPath("$[0].eventName", is("第二条事件")))
                    .andExpect(jsonPath("$[0].keyword", is("无分类")))
                    .andExpect(status().isOk());
        }
    }