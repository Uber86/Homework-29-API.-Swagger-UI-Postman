package ru.hogwarts.school.homework29.service;

import ru.hogwarts.school.homework29.model.Faculty;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class FacultyServiceTest {
    FacultyService service = new FacultyService();




    void addTest() {
        var q = new Faculty(1L, "last", "lsat");
        var w = new Faculty(1L, "last", "lsat");
        var e = new Faculty(1L, "last", "lsat");

        service.create(q);
        service.create(w);
        service.create(e);



    }

}