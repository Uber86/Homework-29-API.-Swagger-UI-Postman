package ru.hogwarts.school.homework29.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.homework29.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int min, int max);


}

