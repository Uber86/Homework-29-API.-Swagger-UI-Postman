package ru.hogwarts.school.homework29.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.homework29.model.Faculty;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FacultyServiceTest {
    FacultyService service = new FacultyService();


    @BeforeEach
    void title(){
        var q = new Faculty(1, "last1", "lsat1");
        var w = new Faculty(2, "last2", "lsat2");
        var e = new Faculty(3, "last3", "lsat3");
        service.create(q);
        service.create(w);
        service.create(e);
    }

    @Test
    void createTest() {
        var e = new Faculty(4, "last4", "lsat4");
        service.create(e);
        assertNotNull(e);
    }

    @Test
    void getTest() {
        var e = new Faculty(3, "last3", "lsat3");
        service.create(e);
        assertSame(service.get(4), e);
    }

    @Test
    void NoGetTest() {
        var e = new Faculty(3, "last3", "lsat3");
        service.create(e);
        var a = new Faculty(0, "last3", "lsat3");
        assertNotSame(service.get(4), a);
    }

    @Test
    void geteqTest() {
        var e = new Faculty(2, "last2", "lsat2");
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
        var b = new Faculty(4, "last3", "lsat2");
        var a = service.update(new Faculty(4, "last3", "lsat2"));
        assertNull(service.update(new Faculty(5, "last3", "lsat2")));
        assertNull(a);

    }

    @Test
    void colorTest() {
        var f = "lsat3";
        var a = service.findByColor(f);
        var b = service.get(3);
        assertThat(Objects.equals(a, b));
    }

    @Test
    void updateTest() {

        var b = new Faculty(4, "last3", "lsat2");
        var a = new Faculty(4, "last2", "lsat2");
        service.create(a);
        service.update(b);
        assertEquals(b,service.get(4));
        assertNotEquals(a,service.get(4));
    }


}