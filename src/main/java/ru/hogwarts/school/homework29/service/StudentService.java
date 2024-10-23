package ru.hogwarts.school.homework29.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.homework29.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {

    private final Map<Long, Student> students = new HashMap<>();
    private long counter = 0;

    public Student create(Student student) {
        student.setId(++counter);
        students.put(counter, student);
        return student;
    }

    public Student get(long id) {
        return students.get(id);
    }

    public Student update(Student student) {
        if (!students.containsKey(student.getId())) {
            return null;
        }
        students.put(student.getId(), student);
        return student;
    }

    public Student delete(long id) {
        return students.remove(id);
    }

    public Collection <Student> findByAge(int age) {
        ArrayList <Student> result = new ArrayList<>();
        for (Student student : students.values()) {
            if (student.getAge() == age) {
                result.add(student);
            }
        }
        return result;
    }


}
