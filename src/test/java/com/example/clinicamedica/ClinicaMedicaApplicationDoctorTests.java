package com.example.clinicamedica;

import com.example.clinicamedica.domain.model.Doctor;
import com.example.clinicamedica.domain.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Esta anotação especifica a ordem de execução dos testes
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
	@Order(1) // Especifica a ordem de execução deste teste
	@Transactional
		// Garante que este teste seja executado dentro de uma transação
	void testScenario1_GetDoctorNotFound() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/doctor/1"))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Resource ID not found."));
	}

	@Test
	@Order(2) // Especifica a ordem de execução deste teste
	@Transactional // Garante que este teste seja executado dentro de uma transação
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
	@Order(3) // Especifica a ordem de execução deste teste
	@Transactional // Garante que este teste seja executado dentro de uma transação
	void testScenario3_UpdateNonexistentDoctor() throws Exception {
		Doctor doctor = uptadeDoctor(1L, "pediatra");
		mockMvc.perform(MockMvcRequestBuilders.put("/doctor")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(doctor)))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(content().string("This doctor does not exists."));
	}

	@Test
	@Order(4) // Especifica a ordem de execução deste teste
	@Transactional // Garante que este teste seja executado dentro de uma transação
	void testScenario4_DeleteNonexistentDoctor() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/doctor/1"))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(content().string("This doctor does not exists."));
	}

	@Test
	@Order(5) // Especifica a ordem de execução deste teste
	@Transactional // Garante que este teste seja executado dentro de uma transação
	void testScenario5_CompleteRequestsTest() throws Exception {
		// Test Scenario 1: Get Doctor Not Found
		mockMvc.perform(MockMvcRequestBuilders.get("/doctor/1"))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Resource ID not found."));

		// Test Scenario 2: Create Person and Doctor
		Person person = createPerson("Pedro", "1991-04-04", "masculino", "Maria");
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/person")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(person)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").value("Pedro"));

			// Extrair o ID da entidade Person
		String jsonResponse = resultActions.andReturn().getResponse().getContentAsString();
		Integer personId = JsonPath.read(jsonResponse, "$.id");

			// Converter o ID para o tipo correto (por exemplo, Long)
		Long personIdLong = personId.longValue();

			// Criar o Doctor usando o ID da Person
		Doctor doctor = createDoctor(personIdLong, "ortopedista");
		mockMvc.perform(MockMvcRequestBuilders.post("/doctor")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(doctor)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.specialty").value("ortopedista"));

		// Test Scenario 3: Get Doctor After Creation
		mockMvc.perform(MockMvcRequestBuilders.get("/doctor/{personId}", personIdLong))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.specialty").value("ortopedista"));

		// Test Scenario 4: Update Doctor
		Doctor updateDoctor = uptadeDoctor(personIdLong, "pediatra");
		mockMvc.perform(MockMvcRequestBuilders.put("/doctor")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updateDoctor)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.specialty").value("pediatra"));

		// Test Scenario 5: Get Doctor After Update
		mockMvc.perform(MockMvcRequestBuilders.get("/doctor/{personId}", personIdLong))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.specialty").value("pediatra"));

		// Test Scenario 6: Delete Doctor
		mockMvc.perform(MockMvcRequestBuilders.delete("/doctor/{personId}", personIdLong))
				.andExpect(status().isNoContent());

		// Test Scenario 7: Get Doctor After Deletion
		mockMvc.perform(MockMvcRequestBuilders.get("/doctor/{personId}", personIdLong))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Resource ID not found."));

		// Test Scenario 8: Delete Nonexistent Doctor
		mockMvc.perform(MockMvcRequestBuilders.delete("/doctor/{personId}", personIdLong))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(content().string("This doctor does not exists."));

		// Test Scenario 9: Update Nonexistent User
		Doctor nonExistentUser = uptadeDoctor(personIdLong, "cardiologista");
		mockMvc.perform(MockMvcRequestBuilders.put("/doctor")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(nonExistentUser)))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(content().string("This doctor does not exists."));
	}
}
