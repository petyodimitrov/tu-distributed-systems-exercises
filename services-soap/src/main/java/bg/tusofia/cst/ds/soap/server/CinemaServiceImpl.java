package bg.tusofia.cst.ds.soap.server;

import bg.tusofia.cst.ds.soap.CinemaService;

import javax.jws.WebService;
import javax.xml.ws.soap.Addressing;

@WebService(endpointInterface = "bg.tusofia.cst.ds.soap.CinemaService")
@Addressing(/*required = true*/) // uncomment to make WS-Addressing required
public class CinemaServiceImpl implements CinemaService {

    public String getMovieInfo(String title, int year) throws Exception {
        return "The length of movie '" + title + "' (" + year + ") is "
                + CinemaUtil.getInstance().getMovieLength(title);
    }

}
