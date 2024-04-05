package com.example.clinicamedica;

import com.example.clinicamedica.domain.model.Doctor;
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
class ClinicaMedicaApplicationDoctorTests {

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

	private Doctor createDoctor(Long id, String specialty) {
		Doctor doctor = new Doctor();
		doctor.setId(id);
		doctor.setSpecialty(specialty);
		return doctor;
	}

	private Doctor uptadeDoctor(Long id, String specialty) {
		Doctor doctor = new Doctor();
		doctor.setId(id);
		doctor.setSpecialty(specialty);
		return doctor;
	}

	@Test
	void testScenario1_GetDoctorNotFound() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/doctor/1"))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Resource ID not found."));
	}

	@Test
	void testScenario2_CreateDoctor() throws Exception {
		Person person = createPerson("Pedro", "1991-04-04", "masculino", "Maria");
		mockMvc.perform(MockMvcRequestBuilders.post("/person")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(person)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").value("Pedro"));

		Doctor doctor = createDoctor(1L, "ortopedista");
		mockMvc.perform(MockMvcRequestBuilders.post("/doctor")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(doctor)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.specialty").value("ortopedista"));
	}

	@Test
	void testScenario3_UpdateNonexistentDoctor() throws Exception {
		Doctor doctor = uptadeDoctor(1L, "pediatra");
		mockMvc.perform(MockMvcRequestBuilders.put("/doctor")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(doctor)))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(content().string("This doctor does not exists."));
	}

	@Test
	void testScenario4_DeleteNonexistentDoctor() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/doctor/1"))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(content().string("This doctor does not exists."));
	}

	@Test
	void testScenario5_CompleteRequestsTest() throws Exception {
		// Test Scenario 1: Get Doctor Not Found
		mockMvc.perform(MockMvcRequestBuilders.get("/doctor/1"))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Resource ID not found."));

		// Test Scenario 2: Create Person and Doctor
		Person person = createPerson("Pedro", "1991-04-04", "masculino", "Maria");
		mockMvc.perform(MockMvcRequestBuilders.post("/person")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(person)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").value("Pedro"));

		Doctor doctor = createDoctor(1L, "ortopedista");
		mockMvc.perform(MockMvcRequestBuilders.post("/doctor")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(doctor)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.specialty").value("ortopedista"));

		// Test Scenario 3: Get Doctor After Creation
		mockMvc.perform(MockMvcRequestBuilders.get("/doctor/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Pedro"));

		// Test Scenario 4: Update User
		Doctor user = uptadeDoctor(1L, "Pedro Henrique Oliveira", "1991-04-04", "masculino", "Maria da Glória");
		mockMvc.perform(MockMvcRequestBuilders.put("/doctor")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("Pedro Henrique Oliveira"));

		// Test Scenario 5: Get User After Update
		mockMvc.perform(MockMvcRequestBuilders.get("/doctor/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Pedro Henrique Oliveira"));

		// Test Scenario 6: Delete User
		mockMvc.perform(MockMvcRequestBuilders.delete("/doctor/1"))
				.andExpect(status().isNoContent());

		// Test Scenario 7: Get User After Deletion
		mockMvc.perform(MockMvcRequestBuilders.get("/doctor/1"))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Resource ID not found."));

		// Test Scenario 8: Delete Nonexistent User
		mockMvc.perform(MockMvcRequestBuilders.delete("/doctor/1"))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(content().string("This doctor does not exists."));

		// Test Scenario 9: Update Nonexistent User
		Doctor nonExistentUser = uptadeDoctor(1L, "Pedro Henrique Oliveira", "1991-04-04", "masculino", "Maria da Glória");
		mockMvc.perform(MockMvcRequestBuilders.put("/doctor")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(nonExistentUser)))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(content().string("This doctor does not exists."));
	}
}
