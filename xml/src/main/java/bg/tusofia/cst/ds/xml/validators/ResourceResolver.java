package bg.tusofia.cst.ds.xml.validators;

import com.sun.org.apache.xerces.internal.dom.DOMInputImpl;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

import java.io.InputStream;
import java.util.Objects;

/**
 * Internal class to allow loading the reference to the XSD.
 */
public class ResourceResolver implements LSResourceResolver {

    private final String basePath;

    public ResourceResolver() {
        this.basePath = null;
    }

    @Override
    public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
        // note: in this sample, the XSD's are expected to be in the root of the classpath
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(buildPath(systemId));
        Objects.requireNonNull(resourceAsStream, String.format("Could not find the specified xsd file: %s", systemId));
        return new DOMInputImpl(publicId, systemId, baseURI, resourceAsStream, "UTF-8");
    }

    private String buildPath(String systemId) {
        return basePath == null ? systemId : String.format("%s/%s", basePath, systemId);
    }
}