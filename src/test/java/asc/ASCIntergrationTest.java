package asc;

import graphloader.Importer;
import prolog.TuProlog;
import org.graphstream.graph.Graph;
import org.junit.Test;
import utils.FileUtils;
import utils.Printer;

import static asc.GraphRuleTests.generateGraphProlog;
import static asc.GraphRuleTests.graphTest;

public final class ASCIntergrationTest {
    @Test
    public void intergrationTestDOT() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("library/simple_graphs/graph3.dot"));
        TuProlog prolog = generateGraphProlog(graph);
        graphTest(prolog, graph);
    }

    @Test
    public void intergrationTestGXL() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("library/strange_graphs/gxl/test.gxl"));
        TuProlog prolog = generateGraphProlog(graph);
        graphTest(prolog, graph);
    }

    @Test
    public void intergrationTestGST() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("library/strange_graphs/gxl/tictactoe.gst"));
        TuProlog prolog = generateGraphProlog(graph);
        graphTest(prolog, graph);
    }

    @Test
    public void intergrationTestGPR() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("library/strange_graphs/groovegraphs/tictactoe.gps/move.gpr"));
        TuProlog prolog = generateGraphProlog(graph);
        graphTest(prolog, graph);
    }

    @Test
    public void intergrationTestDGS() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("library/strange_graphs/dgs/graph1.dgs"));
        TuProlog prolog = generateGraphProlog(graph);
        Printer.pprint(graph);
        graphTest(prolog, graph);
    }
}
