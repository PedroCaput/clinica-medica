package com.example.clinicamedica.domain.model.modelMapper;

import com.example.clinicamedica.domain.model.Patient;
import com.example.clinicamedica.domain.model.dto.PatientDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Period;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Mapeamento de Patient para PatientDto
        modelMapper.createTypeMap(Patient.class, PatientDto.class)
                .addMapping(src -> src.getPerson().getName(), PatientDto::setName)
                .addMapping(src -> src.getPerson().getBirthDate(), PatientDto::setBirthDate)
                .addMapping(src -> src.getPerson().getGender(), PatientDto::setGender)
                .addMapping(src -> src.getPerson().getId(), PatientDto::setId)
                .addMapping(src -> src.getHealthPlan(), PatientDto::setHealthPlanName)
                .addMapping(src -> calculateAgeFromBirthDate(src.getPerson().getBirthDate()), PatientDto::setAge);

        return modelMapper;
    }

    private int calculateAgeFromBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            return 0; // Tratar quando a data de nascimento é nula
        }
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }
}
