package bg.tusofia.fksu.soa.examples.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface ICinema {

	@WebMethod
	String getMovieInfo(@WebParam(name = "title") String title, @WebParam(name = "year") int year) throws Exception;

}
