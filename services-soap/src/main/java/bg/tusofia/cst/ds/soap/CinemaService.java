package bg.tusofia.cst.ds.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface CinemaService {

    @WebMethod
    String getMovieInfo(@WebParam(name = "title") String title, @WebParam(name = "year") int year) throws Exception;

}
