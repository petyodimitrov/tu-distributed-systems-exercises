package bg.tusofia.fksu.soa.examples.rest.resources;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;

import com.google.gson.Gson;

public class Lecture {

	private String id;
	private String title;
	private int slideCount;
	private Date createdOn;

	Lecture(String id, String title, int slideCount, Date createdOn) {
		this.id = id;
		this.title = title;
		this.slideCount = slideCount;
		this.createdOn = createdOn;
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	@GET
	@Produces("application/json")
	public String getJson() {
		return new Gson().toJson(this);
	}

	@PUT
	@Consumes("application/json")
	public void putJson(String content) {
		Lecture modifiedLecture = new Gson().fromJson(content, Lecture.class);
		title = modifiedLecture.title;
		slideCount = modifiedLecture.slideCount;
		createdOn = modifiedLecture.createdOn;
	}

	@DELETE
	public void delete() {
		LectureManager.removeInstance(id);
	}
}