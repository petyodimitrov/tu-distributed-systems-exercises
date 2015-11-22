package bg.tusofia.fksu.soa.examples.rest.resources;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.google.gson.Gson;

@Path("/lectures")
public class DistributesSystems {

	@GET
	@Produces("application/json")
	public String getJson() {
		return new Gson().toJson(LectureManager.getInstances());
	}

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response postJson(String content) {
		Lecture lecture = new Gson().fromJson(content, Lecture.class);
		LectureManager.addInstance(lecture);

		URI uri = UriBuilder.fromPath(lecture.getID()).build(lecture);
		return Response.created(uri).build();
	}

	@Path("{id}")
	public Lecture getLecture(@PathParam("id") String id) {
		return LectureManager.getInstance(id);
	}

}