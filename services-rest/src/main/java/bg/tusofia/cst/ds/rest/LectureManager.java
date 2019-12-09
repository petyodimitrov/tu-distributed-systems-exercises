package bg.tusofia.cst.ds.rest;

import bg.tusofia.cst.ds.rest.model.Lecture;
import javax.ws.rs.WebApplicationException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class LectureManager {

    private static Map<Integer, Lecture> lectures;
    private static int counter = 0;

    static {
        lectures = Collections.synchronizedMap(new HashMap<Integer, Lecture>());
        lectures.put(++counter, new Lecture(Integer.toString(counter), "Introduction", 50, new Date()));
        lectures.put(++counter, new Lecture(Integer.toString(counter), "XML", 100, new Date()));
        lectures.put(++counter, new Lecture(Integer.toString(counter), "SOAP", 90, new Date()));
    }

    static Lecture getInstance(String id) {
        try {
            return lectures.get(Integer.valueOf(id));
        } catch (Exception ex) {
            throw new WebApplicationException(400);
        }
    }

    static Collection<Lecture> getInstances() {
        return lectures.values();
    }

    static Lecture addInstance(Lecture lecture) {
        synchronized (Lecture.class) {
            int tempCounter = ++counter;
            lecture.setId(Integer.toString(tempCounter));
            return lectures.put(tempCounter, lecture);
        }
    }

    static void removeInstance(String id) {
        lectures.remove(Integer.valueOf(id));
    }

}
