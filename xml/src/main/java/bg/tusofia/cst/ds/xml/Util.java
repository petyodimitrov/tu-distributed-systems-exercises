package bg.tusofia.cst.ds.xml;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Util {

    private Util() {
    }

    public static InputStream loadFile(String name) throws FileNotFoundException {
        InputStream input = Util.class.getResourceAsStream(name);
        if (input == null) {
            throw new FileNotFoundException(name);
        }
        return input;
    }

}
