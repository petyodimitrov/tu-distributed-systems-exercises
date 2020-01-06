package bg.tusofia.cst.ds.soap.server;

import java.util.HashMap;
import java.util.Map;

class CinemaUtil {

    private Map<String, Integer> movieLengths;

    private static volatile CinemaUtil instance;

    static CinemaUtil getInstance() {
        if (instance == null) {
            synchronized (CinemaUtil.class) {
                if (instance == null) {
                    instance = new CinemaUtil();
                }
            }
        }
        return instance;
    }

    private CinemaUtil() {
        movieLengths = new HashMap<>();
        movieLengths.put("Avengers", 144);
        // top 3
        movieLengths.put("The Shawshank Redemption", 142);
        movieLengths.put("The Godfather", 175);
        movieLengths.put("The Godfather: Part II", 200);
    }

    int getMovieLength(String title) throws Exception {
        Integer length = movieLengths.get(title);
        if (length != null) {
            return length.intValue();
        } else {
            throw new Exception("opa");
        }
    }

}
