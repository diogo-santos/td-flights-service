package com.example.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasKey;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class FlightControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getFlightsTest() throws Exception {
		mockMvc.perform(get("/flights/IST/DUB/2014-01-01/2014-01-02/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.availability").isArray())
				.andExpect(jsonPath("$.availability[0].flight", hasKey("operator")))
				.andExpect(jsonPath("$.availability[0].flight", hasKey("flightNumber")))
				.andExpect(jsonPath("$.availability[0].flight", hasKey("departsFrom")))
				.andExpect(jsonPath("$.availability[0].flight", hasKey("arrivesAt")))
				.andExpect(jsonPath("$.availability[0].flight", hasKey("departsOn")))
				.andExpect(jsonPath("$.availability[0].flight", hasKey("arrivesOn")))
				.andExpect(jsonPath("$.availability[0].flight", hasKey("flightTime")))
				.andExpect(jsonPath("$.availability[0].flight", hasKey("farePrices")));
	}
}