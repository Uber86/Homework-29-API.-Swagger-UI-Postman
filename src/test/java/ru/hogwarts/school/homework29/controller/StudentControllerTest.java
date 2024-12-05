package ru.hogwarts.school.homework29.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.hogwarts.school.homework29.model.Student;
import ru.hogwarts.school.homework29.service.StudentService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;



    @Autowired
    private ObjectMapper objectMapper;



    @MockBean
    private StudentService studentService;

    @Test

    void shouldGetStudent() throws Exception {
        Long studentId = 1L;
        Student student = new Student("Ivan", 20);

        when(studentService.get(studentId)).thenReturn(student);
        ResultActions perform = mockMvc.perform(get("/student/{id}", studentId));

        perform
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());

    }

    @Test
    void shouldCreateStudent() throws Exception {
        Long studentId = 1L;
        Student student = new Student("Ivan", 20);
        Student savedStudent = new Student("Ivan", 20);

        savedStudent.setId(studentId);
        when(studentService.create(student)).thenReturn(savedStudent);
        ResultActions perform = mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));
        perform
                .andExpect(jsonPath("$.id").value(savedStudent.getId()))
                .andExpect(jsonPath("$.name").value(savedStudent.getName()))
                .andExpect(jsonPath("$.age").value(savedStudent.getAge()))
                .andDo(print());
    }

    /*@Test
    void shouldUpdateStudent() throws Exception {
        Long studentId = 1L;
        Student student = new Student("Ivan", 21);
        Student student1 = new Student("Ivan", 20);

        when(studentService.create(any(Student.class))).thenReturn(student);
        when(studentService.update(student)).thenReturn(student1);
        ResultActions perform = mockMvc.perform(put("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

        perform
                .andExpect(jsonPath("$id").value(student1.getId()))
                .andExpect(jsonPath("$.name").value(student1.getName()))
                .andExpect(jsonPath("$.age").value(student1.getAge()))
                .andDo(print());
    }

     */

    @Test
    void shouldUpdate() throws Exception {
        Long id = 1L;
        Student student = new Student("Ivan", 20);
        Student student1 = new Student("Test", 21);

        student.setId(id);
        student1.setId(id);

        when(studentService.create(any(Student.class))).thenReturn(student);
        when(studentService.update(any(Student.class))).thenReturn(student1);


        ResultActions perform = mockMvc.perform(put("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student1)));
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.age").value(21))
                .andDo(print());
    }




    @Test
    void testGetMinMax() throws Exception {
        List <Student> list = List.of(
        new Student("Test1", 10),
        new Student("Test2", 13),
        new Student("Test3", 17));
        int min = 10;
        int max = 13;



        when(studentService.findBetween(min, max)).thenReturn(list);

        ResultActions perform = mockMvc.perform(get("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(list)));
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(print());

    }
    /*@Test
    public void givenGreetURIWithQueryParameter_whenMockMVC_thenResponseOK() throws Exception {
        this.mockMvc.perform(get("/student")
                        .param("name", "John Doe")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message").value("Hello World John Doe!!!"));
    }

     */



}