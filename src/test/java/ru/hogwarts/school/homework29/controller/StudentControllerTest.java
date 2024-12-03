package ru.hogwarts.school.homework29.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.hogwarts.school.homework29.model.Student;
import ru.hogwarts.school.homework29.repositories.StudentRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    private final static Random RANDOM = new Random();

    @LocalServerPort
    private int port;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    Student student;
    String url;

    @BeforeEach

    public void clearDB() {

        studentRepository.deleteAll();

        student = new Student(1,"Test", 10);
        url = "http://localhost:" + port + "/student";



    }

    @Test
    void contextLoads() throws Exception {
        assertThat(studentRepository).isNotNull();

    }

    @Test
    void testGet() throws Exception {
        assertThat(this.restTemplate.getForObject(url, String.class))
                .isNotNull();
    }

    @Test
    void testPost() throws Exception {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Antonio");
        student1.setAge(15);

        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student1, Student.class))
                .isNotNull();
    }

    @Test
    void testGetAge() throws Exception {
        student = studentRepository.save(student);
        ResponseEntity<Student> studentResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + student.getId(),
                Student.class

        );
        assertNotNull(studentResponseEntity);

        assertEquals(studentResponseEntity.getStatusCode(),

                HttpStatusCode.valueOf(200));
    }

    @Test
    public void shouldReturnAndUpdateCustomer() {

        Student studentPost = new Student(4, "Artur", 30);
        Student studentResponseEntity = restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                studentPost,
                Student.class);

        studentResponseEntity.setName("Artur Morgan");
        ResponseEntity<Student> response = restTemplate.exchange(
                "http://localhost:" + port + "/student" ,
                HttpMethod.PUT,
                new HttpEntity<>(studentResponseEntity),
                Student.class
                );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Student actual = response.getBody();
        assertEquals(studentResponseEntity.getName(), actual.getName());
    }



    @Test
    void shouldPutStudent() {

        Student studentNew = new Student(5, "Test", 16);
        Student response = restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                studentNew,
                Student.class);

        response.setName("Test1");

        ResponseEntity<Student> response1 = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(response),
                Student.class
        );

        Student upden = response1.getBody();
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response1.getBody()).isNotNull();
        assertThat(upden.getName()).isEqualTo("Test1");

    }


    @Test
    public void delete() {
        student = studentRepository.save(student);


        ResponseEntity<Student> resp = restTemplate.exchange("http://localhost:" + port + "/student/" + student.getId(), HttpMethod.DELETE, new HttpEntity<>(""), Student.class);
        assertEquals(HttpStatus.OK, resp.getStatusCode());

    }



    @Test
    void testFindAge() {
        int minAge = 10;
        int maxAge = 23;
        HashMap<String, Object> map = new HashMap<>();
        map.put("minAge", minAge);
        map.put("maxAge", maxAge);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpEntity entity = new HttpEntity(headers);

        int expectedStudentsCount = 5;
        List<Student> expected = Stream.generate(() -> {
                    Student student = new Student();
                    student.setAge(RANDOM.nextInt(minAge, maxAge));
                    return student;
                })
                .limit(expectedStudentsCount)
                .map(studentRepository::save)
                .toList();
        Stream.generate(() -> {
                    Student student = new Student();
                    student.setAge(RANDOM.nextInt(maxAge + 1, Integer.MAX_VALUE));
                    return student;
                })
                .limit(10)
                .forEach(studentRepository::save);

        ResponseEntity<List<Student>> resp = restTemplate.exchange(url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Student>>(){}, map);

        assertEquals(expected, resp.getBody());

    }

}