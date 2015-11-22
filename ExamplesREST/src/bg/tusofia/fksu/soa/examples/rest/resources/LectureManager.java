package bg.tusofia.fksu.soa.examples.rest.resources;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.WebApplicationException;

public class LectureManager {

	private static Map<Integer, Lecture> lectures;
	private static int counter = 0;

	static {
		lectures = Collections.synchronizedMap(new HashMap<Integer, Lecture>());
		lectures.put(++counter, new Lecture(Integer.toString(counter), "Introduction", 30, new Date()));
		lectures.put(++counter, new Lecture(Integer.toString(counter), "Web Services", 210, new Date()));
		lectures.put(++counter, new Lecture(Integer.toString(counter), "BPEL and SCA", 210, new Date()));
	};

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
			lecture.setID(Integer.toString(tempCounter));
			return lectures.put(tempCounter, lecture);
		}
	}

	static void removeInstance(String id) {
		lectures.remove(Integer.valueOf(id));
	}

}
