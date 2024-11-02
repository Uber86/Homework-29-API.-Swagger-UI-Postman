package ru.hogwarts.school.homework29.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.homework29.model.Student;
import ru.hogwarts.school.homework29.service.StudentService;

import java.util.Collection;
import java.util.Collections;

@RequestMapping(path = "/student")
@RestController
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        Student createStudent = service.create(student);
        return ResponseEntity.ok(createStudent);
    }

    @GetMapping ("{id}")
    public ResponseEntity<Student> get(@PathVariable Long id) {
        Student getStudent = service.get(id);
        if (getStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(getStudent);
    }

    @PutMapping
    public ResponseEntity<Student> update(@RequestBody Student student) {
        Student updateStudent = service.update(student);
        if (updateStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @DeleteMapping ("{id}")
    public ResponseEntity<Student> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> findStudents(@RequestParam int minAge, @RequestParam int maxAge) {
        return ResponseEntity.ok(service.findBetween(minAge , maxAge));



    }



}
