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
class ClinicaMedicaApplicationTests {

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
	void testScenario1_GetUserNotFound() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Resource ID not found."));
	}

	@Test
	void testScenario2_CreateUser() throws Exception {
		Person user = createPerson("Pedro", "1991-04-04", "masculino", "Maria");
		mockMvc.perform(MockMvcRequestBuilders.post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").value("Pedro"));
	}

	@Test
	void testScenario3_GetUserAfterCreation() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Pedro"));
	}

	@Test
	void testScenario4_UpdateUser() throws Exception {
		Person user = uptadePerson(1L, "Pedro Henrique Oliveira", "1991-04-04", "masculino", "Maria da Glória");
		mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("Pedro Henrique Oliveira"));
	}

	@Test
	void testScenario5_GetUserAfterUpdate() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Pedro Henrique Oliveira"));
	}

	@Test
	void testScenario6_DeleteUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/users/1"))
				.andExpect(status().isNoContent());
	}

	@Test
	void testScenario7_GetUserAfterDeletion() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Resource ID not found."));
	}

	@Test
	void testScenario8_DeleteNonexistentUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/users/1"))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(content().string("This person does not exists."));
	}

	@Test
	void testScenario9_UpdateNonexistentUser() throws Exception {
		Person user = uptadePerson(1L, "Pedro Henrique Oliveira", "1991-04-04", "masculino", "Maria da Glória");
		mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(content().string("This person does not exists."));
	}
}
