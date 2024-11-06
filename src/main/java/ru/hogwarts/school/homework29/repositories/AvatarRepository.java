package ru.hogwarts.school.homework29.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.homework29.model.Avatar;

import java.util.Optional;


public interface AvatarRepository extends JpaRepository<Avatar, Long> {

Optional<Avatar> findByStudentId(Long studentId);

}
