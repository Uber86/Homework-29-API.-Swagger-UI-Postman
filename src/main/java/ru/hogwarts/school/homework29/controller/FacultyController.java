package ru.hogwarts.school.homework29.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.homework29.model.Faculty;
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
        Faculty createStudent = service.create(faculty);
        return ResponseEntity.ok(createStudent);
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
        Faculty delateFuculty = service.delete(id);
        return ResponseEntity.ok(delateFuculty);
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> findFaculties(@RequestParam(required = false) String color) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(service.findByColor(color));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
}
