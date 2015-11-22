package bg.tusofia.fksu.soa.examples.ws.server;

import javax.jws.WebService;

import bg.tusofia.fksu.soa.examples.ws.ICinema;

@WebService(endpointInterface = "bg.tusofia.fksu.soa.examples.ws.ICinema")
// @javax.xml.ws.soap.Addressing(enabled=true, required=true)
public class Cinema implements ICinema {

	public String getMovieInfo(String title, int year) throws Exception {
		return "The length of movie '" + title + "' (" + year + ") is "
				+ CinemaUtil.getInstance().getMovieLength(title);
	}

}
