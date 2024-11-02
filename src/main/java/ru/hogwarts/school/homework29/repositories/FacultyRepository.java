package ru.hogwarts.school.homework29.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.homework29.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Collection<Faculty> findByColor(String color);

    Collection<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color);
}
