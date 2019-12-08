package bg.tusofia.cst.ds.xml;

import bg.tusofia.cst.ds.xml.others.JAXBMarshalExample;
import bg.tusofia.cst.ds.xml.others.XPathExample;
import bg.tusofia.cst.ds.xml.others.XSLTExample;
import bg.tusofia.cst.ds.xml.parsers.DOMReadExample;
import bg.tusofia.cst.ds.xml.parsers.DOMWriteExample;
import bg.tusofia.cst.ds.xml.parsers.SAXReadExample;
import bg.tusofia.cst.ds.xml.parsers.StAXReadCursorExample;
import bg.tusofia.cst.ds.xml.parsers.StAXReadEventExample;
import bg.tusofia.cst.ds.xml.parsers.StAXWriteExample;
import bg.tusofia.cst.ds.xml.validators.XMLValidationExample2;
import bg.tusofia.cst.ds.xml.validators.XMLValidationExample3;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {

    private static final Map<String, Class> commands = new LinkedHashMap<>();

    static {
        // parsers
        commands.put("dom_read", DOMReadExample.class);
        commands.put("dom_write", DOMWriteExample.class);
        commands.put("sax_read", SAXReadExample.class);
        commands.put("stax_read_cursor", StAXReadCursorExample.class);
        commands.put("stax_read_event", StAXReadEventExample.class);
        commands.put("stax_write", StAXWriteExample.class);

        // validations
        commands.put("validate_dom", XMLValidationExample2.class);
        commands.put("validate_schema", XMLValidationExample3.class);

        // other
        commands.put("xpath", XPathExample.class);
        commands.put("xslt", XSLTExample.class);
        commands.put("jaxb", JAXBMarshalExample.class);
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Missing command. Supported commands: " + commands.keySet().toString());
        }

        Class<?> clazz = commands.get(args[0]);
        if (clazz != null) {
            runCommand(clazz);
        } else {
            // run all commands
            System.out.println("Running all examples");
            commands.values().forEach(Main::runCommand);
        }
    }

    private static void runCommand(Class<?> clazz) {
        System.out.println("Running example " + clazz);
        try {
            Method mainMethod = clazz.getMethod("main", String[].class);
            mainMethod.invoke(null, (Object) null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println();
        System.out.println();
    }
}
