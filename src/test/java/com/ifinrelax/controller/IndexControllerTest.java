package com.ifinrelax.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author Timur Berezhnoi
 */
public class IndexControllerTest {

    private IndexController indexController;

    @Before
    public void setUp() {
        indexController = new IndexController();
    }

    @Test
    public void shouldReturnIndexHtml() throws Exception {
        MockMvc mockMvc = standaloneSetup(indexController).build();
        mockMvc.perform(get("/")).andExpect(view().name("index"));
    }
}