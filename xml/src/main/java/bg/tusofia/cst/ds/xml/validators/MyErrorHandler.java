package bg.tusofia.cst.ds.xml.validators;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

class MyErrorHandler implements ErrorHandler {

    @Override
    public void warning(SAXParseException e) {
        System.out.println("warning: " + e.getMessage());
    }

    @Override
    public void error(SAXParseException e) {
        System.out.println("error: " + e.getMessage());
    }

    @Override
    public void fatalError(SAXParseException e) {
        System.out.println("fatalError: " + e.getMessage());
    }
}
