package ru.hogwarts.school.homework29.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.homework29.model.Faculty;
import ru.hogwarts.school.homework29.model.Student;
import ru.hogwarts.school.homework29.service.FacultyService;

import java.util.Collection;
import java.util.Collections;

@RequestMapping(path = "/faculty")
@RestController
public class FacultyController {

    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Faculty> create(@RequestBody Faculty faculty) {
        Faculty createFaculty = service.create(faculty);
        return ResponseEntity.ok(createFaculty);
    }

    @GetMapping ("{id}")
    public ResponseEntity<Faculty> get(@PathVariable Long id) {
        Faculty getFaculty = service.get(id);
        if (getFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(getFaculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> update(@RequestBody Faculty faculty) {
        Faculty updateFaculty = service.update(faculty);
        if (updateFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(updateFaculty);

    }

    @DeleteMapping ("{id}")
    public ResponseEntity<Faculty> delate(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> findFaculties(@RequestParam(required = false) String name, @RequestParam(required = false) String color) {
        return ResponseEntity.ok(service.findByNameOrColor(name, color));
    }
}
