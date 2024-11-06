package ru.hogwarts.school.homework29.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.homework29.model.Faculty;
import ru.hogwarts.school.homework29.repositories.FacultyRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private TestRestTemplate restTemplate;


    Faculty faculty;



    @BeforeEach

    public void clearDB() {

        facultyRepository.deleteAll();

        faculty = new Faculty(1,"Griffindor", "Green");

    }




    @Test
    void shouldCreateFaculty() {
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/faculty",
                faculty,
                Faculty.class
        );

        assertNotNull(facultyResponseEntity);

        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Faculty actual = facultyResponseEntity.getBody();

        assertNotNull(actual.getId());

        assertEquals(faculty.getName(), actual.getName());

        assertThat(actual.getColor())

                .isNotEmpty()

                .isEqualTo(faculty.getColor());

    }

    @Test

    void shouldGetFaculty() {

        faculty = facultyRepository.save(faculty);



        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.getForEntity(

                "http://localhost:" + port + "/faculty/"+faculty.getId(),

                Faculty.class

        );



        assertNotNull(facultyResponseEntity);

        assertEquals(facultyResponseEntity.getStatusCode(),

                HttpStatusCode.valueOf(200));

        Faculty actual = facultyResponseEntity.getBody();

        assertEquals(faculty.getName(),actual.getName());

        assertEquals(faculty.getId(),actual.getId());

        assertEquals(faculty.getColor(),actual.getColor());

    }


    @Test
    void shouldPutFaculty() {

        faculty = facultyRepository.save(faculty);

        Faculty updated = new Faculty(1,"Yellow", "Green");

        ResponseEntity<Faculty> response = restTemplate.exchange(
                String.format("http://localhost:" + port + "/faculty"),
                HttpMethod.PUT,
                new HttpEntity<>(updated),
                Faculty.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void delete() {
        faculty = facultyRepository.save(faculty);


        ResponseEntity<Faculty> resp = restTemplate.exchange("http://localhost:" + port + "/faculty/"+ faculty.getId(), HttpMethod.DELETE, new HttpEntity<>(""), Faculty.class);
        assertEquals(HttpStatus.OK, resp.getStatusCode());


    }



}
