package ru.hogwarts.school.homework29.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.homework29.model.Student;
import ru.hogwarts.school.homework29.repositories.StudentRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    Student student;

    @BeforeEach

    public void clearDB() {

        studentRepository.deleteAll();

        student = new Student("Test",10);

    }

    @Test
    void contextLoads() throws Exception {
        assertThat(studentRepository).isNotNull();

    }

    @Test
    void testGet() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotNull();
    }

    @Test
    void testPost() throws Exception {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Antonio");
        student1.setAge(15);

        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student1, String.class))
                .isNotNull();
    }

    @Test
    void testGetAge() throws Exception {
        assertThat(this.restTemplate.getForEntity("http://localhost:" + port + "/student" + student.getId(), String.class))
                .isNotNull();
    }


    /*@Test
    public void shouldReturn200ResponseAndUpdateCustomer() {

        Student studentPost = new Student( "Artur", 30);
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", studentPost, Student.class));
        Student studentPut = new Student( "Artur Morgan", 41);

        ResponseEntity<Student> response = this.restTemplate.exchange(
                String.format("http://localhost:" + port + "/student/"+studentPost.getId()),
                HttpMethod.PUT,
                new HttpEntity<>(studentPut),
                Student.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
     */
    @Test
    void shouldPutFaculty() {

        student = studentRepository.save(student);

        Student updated = new Student("Yellow", 11);

        ResponseEntity<Student> response = restTemplate.exchange(
                String.format("http://localhost:" + port + "/faculty"),
                HttpMethod.PUT,
                new HttpEntity<>(updated),
                Student.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void delete() {
        student = studentRepository.save(student);


        ResponseEntity<Student> resp = restTemplate.exchange("http://localhost:" + port + "/faculty/" + student.getId(), HttpMethod.DELETE, new HttpEntity<>(""), Student.class);
        assertEquals(HttpStatus.OK, resp.getStatusCode());

    }
   /* @Test
    public void delete() {

        Student student2 = new Student();
        student2.setName("Anticor1");
        student2.setAge(13);

        String url = "http://localhost:" + port + "/student";
        ResponseEntity<Student> resp = restTemplate.exchange(url+student2.getId(), HttpMethod.DELETE, new HttpEntity<>(""), Student.class);
        assertEquals(HttpStatus.NO_CONTENT, resp.getStatusCode());
    }

     @Test
    public void givenPerson_whenGetPerson_thenStatus200() {
        long id = createTestPerson("Joe").getId();
        Person person = restTemplate.getForObject("/persons/{id}", Person.class, id);
        assertThat(person.getName(), is("Joe"));
    }

     */
}