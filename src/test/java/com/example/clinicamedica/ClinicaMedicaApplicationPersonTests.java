package com.example.clinicamedica;

import com.example.clinicamedica.domain.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ClinicaMedicaApplicationPersonTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private Person createPerson(String name, String birthDate, String gender, String motherName) {
		Person person = new Person();
		person.setName(name);
		person.setBirthDate(LocalDate.parse(birthDate));
		person.setGender(gender);
		person.setMotherName(motherName);
		return person;
	}

	private Person uptadePerson(Long id, String name, String birthDate, String gender, String motherName) {
		Person person = new Person();
		person.setId(id);
		person.setName(name);
		person.setBirthDate(LocalDate.parse(birthDate));
		person.setGender(gender);
		person.setMotherName(motherName);
		return person;
	}

	@Test
	void testScenario1_GetPersonNotFound() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/person/1"))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Resource ID not found."));
	}

	@Test
	void testScenario2_CreatePerson() throws Exception {
		Person person = createPerson("Pedro", "1991-04-04", "masculino", "Maria");
		mockMvc.perform(MockMvcRequestBuilders.post("/person")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(person)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").value("Pedro"));
	}

	@Test
	void testScenario3_UpdateNonexistentPerson() throws Exception {
		Person person = uptadePerson(1L, "Pedro Henrique Oliveira", "1991-04-04", "masculino", "Maria da Glória");
		mockMvc.perform(MockMvcRequestBuilders.put("/person")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(person)))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(content().string("This person does not exists."));
	}

	@Test
	void testScenario4_DeleteNonexistentPerson() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/person/1"))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(content().string("This person does not exists."));
	}

	@Test
	void testScenario5_CompleteRequestsTest() throws Exception {
		// Test Scenario 1: Get User Not Found
		mockMvc.perform(MockMvcRequestBuilders.get("/person/1"))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Resource ID not found."));

		// Test Scenario 2: Create User
		Person person = createPerson("Pedro", "1991-04-04", "masculino", "Maria");
		mockMvc.perform(MockMvcRequestBuilders.post("/person")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(person)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").value("Pedro"));

		// Test Scenario 3: Get User After Creation
		mockMvc.perform(MockMvcRequestBuilders.get("/person/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Pedro"));

		// Test Scenario 4: Update User
		Person user = uptadePerson(1L, "Pedro Henrique Oliveira", "1991-04-04", "masculino", "Maria da Glória");
		mockMvc.perform(MockMvcRequestBuilders.put("/person")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("Pedro Henrique Oliveira"));

		// Test Scenario 5: Get User After Update
		mockMvc.perform(MockMvcRequestBuilders.get("/person/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Pedro Henrique Oliveira"));

		// Test Scenario 6: Delete User
		mockMvc.perform(MockMvcRequestBuilders.delete("/person/1"))
				.andExpect(status().isNoContent());

		// Test Scenario 7: Get User After Deletion
		mockMvc.perform(MockMvcRequestBuilders.get("/person/1"))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Resource ID not found."));

		// Test Scenario 8: Delete Nonexistent User
		mockMvc.perform(MockMvcRequestBuilders.delete("/person/1"))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(content().string("This person does not exists."));

		// Test Scenario 9: Update Nonexistent User
		Person nonExistentUser = uptadePerson(1L, "Pedro Henrique Oliveira", "1991-04-04", "masculino", "Maria da Glória");
		mockMvc.perform(MockMvcRequestBuilders.put("/person")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(nonExistentUser)))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(content().string("This person does not exists."));
	}
}
