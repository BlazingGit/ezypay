package com.ezypay.controller.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.*;

import com.ezypay.config.WebApplication;
import com.ezypay.model.SubscriptionModel;
import com.ezypay.model.SubscriptionType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@AutoConfigureMockMvc
public class SubscriptionControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
    @Test 
    public void testAddSubscription1() throws Exception {
    	mockMvc.perform( MockMvcRequestBuilders
	      .post("/subscription/add")
	      .content(asJsonString(new SubscriptionModel("Wei Yang", SubscriptionType.WEEKLY, "Monday", "06/02/2018", "27/02/2018", new BigDecimal("100"))))
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.totalAmount").value(400))
	      .andExpect(MockMvcResultMatchers.jsonPath("$.invoiceDates", hasItem("06/02/2018")))
	      .andExpect(MockMvcResultMatchers.jsonPath("$.invoiceDates", hasItem("13/02/2018")))
	      .andExpect(MockMvcResultMatchers.jsonPath("$.invoiceDates", hasItem("20/02/2018")))
	      .andExpect(MockMvcResultMatchers.jsonPath("$.invoiceDates", hasItem("27/02/2018")));
    }
    
    @Test 
    public void testAddSubscription2() throws Exception {
    	mockMvc.perform( MockMvcRequestBuilders
	      .post("/subscription/add")
	      .content(asJsonString(new SubscriptionModel("Wei Yang", SubscriptionType.MONTHLY, "Monday", "02/03/2020", "05/04/2020", new BigDecimal("100.50"))))
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.totalAmount").value(201.00))
	      .andExpect(MockMvcResultMatchers.jsonPath("$.invoiceDates", hasItem("02/03/2020")))
	      .andExpect(MockMvcResultMatchers.jsonPath("$.invoiceDates", hasItem("02/04/2020")));
    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
