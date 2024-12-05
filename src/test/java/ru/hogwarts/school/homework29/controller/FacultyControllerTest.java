package ru.hogwarts.school.homework29.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.hogwarts.school.homework29.model.Faculty;
import ru.hogwarts.school.homework29.service.FacultyService;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;



    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private FacultyService facultyService;

    @Test
    void shouldGetFaculty() throws Exception {
        Long id = 1L;
        Faculty faculty = new Faculty("Griff", "Blue");

        when(facultyService.get(id)).thenReturn(faculty);
        ResultActions perform = mockMvc.perform(get("/faculty/{id}", id));

        perform
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());

    }

    @Test
    void shouldCreateFaculty() throws Exception {
        Long id = 1L;
        Faculty faculty = new Faculty("Griff", "Blue");
        Faculty facultyDouble = new Faculty("Griff", "Blue");

        facultyDouble.setId(id);
        when(facultyService.create(faculty)).thenReturn(facultyDouble);
        ResultActions perform = mockMvc.perform(post("/faculty")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(faculty)));
        perform
                .andExpect(jsonPath("$.id").value(facultyDouble.getId()))
                .andExpect(jsonPath("$.name").value(facultyDouble.getName()))
                .andExpect(jsonPath("$.color").value(facultyDouble.getColor()))
                .andDo(print());
    }


    @Test
    void shouldUpdate() throws Exception {
        Long id = 1L;
        Faculty faculty = new Faculty("Griff", "Blue");
        Faculty facultyDouble = new Faculty("Griff", "Red");

        faculty.setId(id);
        facultyDouble.setId(id);

        when(facultyService.create(any(Faculty.class))).thenReturn(faculty);
        when(facultyService.update(any(Faculty.class))).thenReturn(facultyDouble);


        ResultActions perform = mockMvc.perform(put("/faculty")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(facultyService)));
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyDouble.getId()))
                .andExpect(jsonPath("$.name").value(facultyDouble.getName()))
                .andExpect(jsonPath("$.color").value(facultyDouble.getColor()))
                .andDo(print());
    }




    @Test
    void testGetFind() throws Exception {
        Collection<Faculty> collection = asList(
                new Faculty("Griff", "Blue"),
                new Faculty("Slezer", "Blue"),
                new Faculty("Puff", "Pink"));

        String color = "Blue";
        String name = "Puff";



        when(facultyService.findByNameOrColor(any(String.class),any(String.class))).thenReturn(collection);

        ResultActions perform = mockMvc.perform(get("/faculty")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(name)));
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andDo(print());

    }







}