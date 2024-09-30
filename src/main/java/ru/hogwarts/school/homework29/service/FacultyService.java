package ru.hogwarts.school.homework29.service;



import org.springframework.stereotype.Service;
import ru.hogwarts.school.homework29.model.Faculty;
import ru.hogwarts.school.homework29.model.Student;

import java.util.*;

@Service
public class FacultyService {
    private final Map<Long, Faculty> facultys = new HashMap<>();
    private long counter = 0;

    public Faculty create(Faculty faculty) {
        faculty.setId(++counter);;
        facultys.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty get(long id) {
        return facultys.get(id);
    }

    public Faculty update(Faculty faculty) {
        if (!facultys.containsKey(faculty.getId())) {
            return null;
        }
        facultys.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty delete(long id) {
        return facultys.remove(id);
    }

    public Collection<Faculty> findByColor(String color) {
        ArrayList <Faculty> result = new ArrayList<>();
        for (Faculty faculty : facultys.values()) {
            if (Objects.equals(faculty.getColor(), color)) {
                result.add(faculty);
            }
        }
        return result;
    }


}
