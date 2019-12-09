package bg.tusofia.cst.ds.rest;

import bg.tusofia.cst.ds.rest.model.Lecture;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Collection;

@Path("/lectures")
public class LectureService {

    @GET
    @Produces("application/json")
    public Collection<Lecture> listLectures() {
        return LectureManager.getInstances();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response createLecture(Lecture lecture) {
        LectureManager.addInstance(lecture);

        URI uri = UriBuilder.fromPath(lecture.getId()).build(lecture);
        return Response.created(uri).build();
    }

    @Path("{id}")
    @GET
    @Produces("application/json")
    public Lecture getLecture(@PathParam("id") String id) {
        return LectureManager.getInstance(id);
    }

    @Path("{id}")
    @PUT
    @Consumes("application/json")
    public void update(@PathParam("id") String id, Lecture modifiedLecture) {
        Lecture lecture = LectureManager.getInstance(id);
        lecture.setTitle(modifiedLecture.getTitle());
        lecture.setSlideCount(modifiedLecture.getSlideCount());
        lecture.setCreatedOn(modifiedLecture.getCreatedOn());
    }

    @Path("{id}")
    @DELETE
    public void delete(@PathParam("id") String id) {
        LectureManager.removeInstance(id);
    }

}