package ru.hogwarts.school.homework29.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.homework29.model.Faculty;
import ru.hogwarts.school.homework29.model.Student;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    StudentService service = new StudentService();


    @BeforeEach
    void title(){
        var q = new Student(1, "last1", 10);
        var w = new Student(2, "last2", 12);
        var e = new Student(3, "last3", 15);
        service.create(q);
        service.create(w);
        service.create(e);
    }

    @Test
    void createTest() {
        var e = new Student(4, "last4", 16);
        service.create(e);
        assertNotNull(e);
    }

    @Test
    void getTest() {
        var e = new Student(3, "last3", 15);
        service.create(e);
        assertSame(service.get(4), e);
    }

    @Test
    void NoGetTest() {
        var e = new Student(3, "last3", 15);
        service.create(e);
        var a = new Student(3, "last3", 16);
        assertNotSame(service.get(4), a);
    }

    @Test
    void geteqTest() {
        var e = new Student(2, "last2", 12);
        assertEquals(service.get(2) , e );
    }

    @Test
    void getNull() {
        service.delete(2);
        assertNull(service.get(2));
    }

    @Test
    void getNoNull() {
        service.delete(3);
        assertNotNull(service.get(2));
    }


    @Test
    void nullUpdateTest() {
        var b = new Student(4, "last3", 17);
        var a = service.update(new Student(4, "last3", 17));
        assertNull(service.update(new Student(5, "last3", 16)));
        assertNull(a);

    }

    @Test
    void colorTest() {
        var f = 15;
        var a = service.findByAge(f);
        var b = service.get(3);
        assertThat(Objects.equals(a, b));
    }

    @Test
    void updateTest() {

        var b = new Student(4, "last3", 10);
        var a = new Student(4, "last2", 15);
        service.create(a);
        service.update(b);
        assertEquals(b,service.get(4));
        assertNotEquals(a,service.get(4));
    }


}
