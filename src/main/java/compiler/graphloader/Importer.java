package compiler.graphloader;


import org.graphstream.graph.Graph;
import org.xml.sax.SAXException;
import utils.FileUtils;

import java.io.File;
import java.io.IOException;

public final class Importer {

    public static Graph graphFromFile(String path) throws IOException, SAXException {
        return graphFromFile(new File(path));
    }

    public static Graph graphFromFile(File file) throws IOException, SAXException {
        if (GXLReader.acceptsExtension(FileUtils.getExtension(file.getName()))) {
            return GXLReader.read(file);
        }
        if (GraphstreamAcceptedImportReader.acceptsExtension(FileUtils.getExtension(file.getName()))) {
            return GraphstreamAcceptedImportReader.read(file);
        }
        throw new UnsupportedOperationException("Unknown file extension for file: " + file.getName());
    }
}
