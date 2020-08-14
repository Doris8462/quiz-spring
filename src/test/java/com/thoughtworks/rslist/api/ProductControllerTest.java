package com.thoughtworks.rslist.api;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setup(){
        ProductController.products.clear();
    }
@Test
    void shouldRegisterUser() throws Exception {
        Product product=new Product("可乐",5,2,"瓶");
        ObjectMapper objectMapper=new ObjectMapper();
        String productJson=objectMapper.writeValueAsString(product);
        mockMvc.perform(post("/product").content(productJson).contentType
                (MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        assertEquals(1, ProductController.products.size());
    }
}


