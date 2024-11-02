package ru.hogwarts.school.homework29.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.homework29.model.Student;
import ru.hogwarts.school.homework29.repositories.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student) {
        return studentRepository.save(student);

    }

    public Student get(long id) {
        return studentRepository.findById(id).get();
    }

    public Student update(Student student) {
        return studentRepository.save(student);
    }

    public void delete(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> findAge(int age) {
        return studentRepository.findByAge(age);
    }


    public Collection<Student> findBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }



}
