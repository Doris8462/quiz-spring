package com.thoughtworks.rslist.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;


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
                .andExpect(jsonPath("$.user.phone",is("11234567890")))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list/2"))
                .andExpect(jsonPath("$.eventName", is("第二条事件")))
                .andExpect(jsonPath("$.keyword", is("无分类")))
                .andExpect(jsonPath("$.user.userName",is("B")))
                .andExpect(jsonPath("$.user.age",is(28)))
                .andExpect(jsonPath("$.user.gender",is("female")))
                .andExpect(jsonPath("$.user.email",is("B@qq.com")))
                .andExpect(jsonPath("$.user.phone",is("11234567890")))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list/3"))
                .andExpect(jsonPath("$.eventName", is("第三条事件")))
                .andExpect(jsonPath("$.keyword", is("无分类")))
                .andExpect(jsonPath("$.user.age",is(38)))
                .andExpect(jsonPath("$.user.gender",is("male")))
                .andExpect(jsonPath("$.user.email",is("C@qq.com")))
                .andExpect(jsonPath("$.user.phone",is("11234567890")))
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
                    .andExpect(jsonPath("$[0].user.phone",is("11234567890")))
                    .andExpect(jsonPath("$[1].eventName", is("第二条事件")))
                    .andExpect(jsonPath("$[1].keyword", is("无分类")))
                    .andExpect(jsonPath("$[1].user.userName",is("B")))
                    .andExpect(jsonPath("$[1].user.age",is(28)))
                    .andExpect(jsonPath("$[1].user.gender",is("female")))
                    .andExpect(jsonPath("$[1].user.email",is("B@qq.com")))
                    .andExpect(jsonPath("$[1].user.phone",is("11234567890")))
                    .andExpect(jsonPath("$[2].eventName", is("第三条事件")))
                    .andExpect(jsonPath("$[2].keyword", is("无分类")))
                    .andExpect(jsonPath("$[2].user.age",is(38)))
                    .andExpect(jsonPath("$[2].user.gender",is("male")))
                    .andExpect(jsonPath("$[2].user.email",is("C@qq.com")))
                    .andExpect(jsonPath("$[2].user.phone",is("11234567890")))
                    .andExpect(status().isOk());
        }

     @Test
        void shouldAddOneRsEvent() throws Exception {
           String requestJson=  "{\"eventName\":\"添加一条热搜\"," + " \"keyword\":\"娱乐\"," +"\"user\" :{\"name\":\"xiaowang\",  \"age\":19,\"gender\":\"female\", \"email\":\"a@thoughtworks.com\", \"phone\":\"18888888888\"}}";
            mockMvc.perform(post("/rs/add")
                    .content(requestJson)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());

            mockMvc.perform(get("/rs/list"))
                    .andExpect(jsonPath("$[3].eventName", is("添加一条热搜")))
                    .andExpect(jsonPath("$[3].keyword", is("娱乐")))
                    .andExpect(jsonPath("$[3].user.name",is("xiaowang")))
                    .andExpect(jsonPath("$[3].user.age",is(19)))
                    .andExpect(jsonPath("$[3].user.gender",is("female")))
                    .andExpect(jsonPath("$[3].user.email",is("a@thoughtworks.com")))
                    .andExpect(jsonPath("$[3].user.phone",is("18888888888")))
                    .andExpect(status().isOk());
        }
    @Test
    void shouldAddOneRsEventUserNotExist() throws Exception {
        assertEquals(0, UserController.users.size());

        User user = new User("xiaowang", 19, "female", "a@thoughtworks.com", "18888888888");
        RsEvent rsEvent = new RsEvent("添加一条热搜", "娱乐", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/add")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[3].eventName", is("添加一条热搜")))
                .andExpect(jsonPath("$[3].keyword", is("娱乐")))
                .andExpect(jsonPath("$[3].user.name",is("xiaowang")))
                .andExpect(jsonPath("$[3].user.age",is(19)))
                .andExpect(jsonPath("$[3].user.gender",is("female")))
                .andExpect(jsonPath("$[3].user.email",is("a@thoughtworks.com")))
                .andExpect(jsonPath("$[3].user.phone",is("18888888888")))
                .andExpect(status().isOk());

        assertEquals(1, UserController.users.size());
    }

    @Test
    void shouldAddOneRsEventUserAlreadyExist() throws Exception {
        User user = new User("xiaowang", 19, "female", "a@thoughtworks.com", "18888888888");
        RsEvent rsEvent = new RsEvent("添加一条热搜", "娱乐", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/add")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[3].eventName", is("添加一条热搜")))
                .andExpect(jsonPath("$[3].keyword", is("娱乐")))
                .andExpect(jsonPath("$[3].user.name",is("xiaowang")))
                .andExpect(jsonPath("$[3].user.age",is(19)))
                .andExpect(jsonPath("$[3].user.gender",is("female")))
                .andExpect(jsonPath("$[3].user.email",is("a@thoughtworks.com")))
                .andExpect(jsonPath("$[3].user.phone",is("18888888888")))
                .andExpect(status().isOk());

        assertEquals(1, UserController.users.size());

        RsEvent rsEventRepetUser = new RsEvent("同一用户再次添加热搜", "财经", user);
        String userJsonRepetUser = objectMapper.writeValueAsString(rsEventRepetUser);
        mockMvc.perform(post("/rs/add")
                .content(userJsonRepetUser)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[4].eventName", is("同一用户再次添加热搜")))
                .andExpect(jsonPath("$[4].keyword", is("财经")))
                .andExpect(jsonPath("$[4].user.name",is("xiaowang")))
                .andExpect(jsonPath("$[4].user.age",is(19)))
                .andExpect(jsonPath("$[4].user.gender",is("female")))
                .andExpect(jsonPath("$[4].user.email",is("a@thoughtworks.com")))
                .andExpect(jsonPath("$[4].user.phone",is("18888888888")))
                .andExpect(status().isOk());
        assertEquals(1, UserController.users.size());

    }

        @Test
        void shouldUpdateRsEventGivenIndex() throws Exception {
            String requestJsonAll = "{\"eventName\":\"要修改的事件\",\"keyword\":\"要修改的分类\"}";
            mockMvc.perform(post("/rs/update/1")
                    .content(requestJsonAll)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
            mockMvc.perform(get("/rs/list/1"))
                    .andExpect(jsonPath("$.eventName", is("要修改的事件")))
                    .andExpect(jsonPath("$.keyword", is("要修改的分类")))
                    .andExpect(status().isOk());
        }

        @Test
        void shouldDeleteRsEventGivenIndex() throws Exception {
            mockMvc.perform(post("/rs/delete/1"))
                    .andExpect(status().isCreated());

            mockMvc.perform(get("/rs/list"))
                    .andExpect(jsonPath("$[0].eventName", is("第二条事件")))
                    .andExpect(jsonPath("$[0].keyword", is("无分类")))
                    .andExpect(status().isOk());
        }
    @Test
    void nameShouldNotNull()throws Exception {
        User user = new User("xiaowang", 19, "female", "a@thoughtworks.com", "18888888888");
        RsEvent rsEvent = new RsEvent(null, "娱乐", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/add").content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    }