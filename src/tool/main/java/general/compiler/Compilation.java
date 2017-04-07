package general.compiler;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Term;
import compiler.asrc.AbstractSyntaxRuleConverter;
import compiler.graphloader.Importer;
import compiler.solver.Solver;
import compiler.solver.VisElem;
import compiler.svg.SvgDocumentGenerator;
import exceptions.UnknownGraphTypeException;
import graafvis.RuleGenerator;
import org.dom4j.Document;
import org.graphstream.graph.Graph;
import org.xml.sax.SAXException;
import utils.FileUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class Compilation extends Observable{


    private Path scriptFile;
    private Path graphFile;
    private List<Term> abstractSyntaxRules;
    private List<Term> scriptRules;
    private List<VisElem> generatedVisElems;
    private Document generatedSVG;
    private Exception exception;

    public Compilation(Path scriptFile, Path graphFile){
        this.scriptFile = scriptFile;
        this.graphFile = graphFile;
    }

    public void convertGraph() throws IOException, SAXException, UnknownGraphTypeException {
        Graph graph = Importer.graphFromFile(graphFile.toFile());
        abstractSyntaxRules = AbstractSyntaxRuleConverter.convertToRules(graph);
        setChanged();
        notifyObservers(CompilationProgress.GRAPHCONVERTED);
    }

    public void compileGraafVis() throws IOException {
        scriptRules = RuleGenerator.generate(FileUtils.readFromFile(scriptFile.toFile()));
        setChanged();
        notifyObservers(CompilationProgress.GRAAFVISCOMPILED);
    }

    public void solve() throws InvalidTheoryException {
        List<Term> rules = new LinkedList<>();
        rules.addAll(abstractSyntaxRules);
        rules.addAll(scriptRules);
        generatedVisElems = new Solver(rules).solve();
        setChanged();
        notifyObservers(CompilationProgress.SOLVED);
    }

    public void generateSVG() {
        generatedSVG = SvgDocumentGenerator.generate(generatedVisElems);
        setChanged();
        notifyObservers(CompilationProgress.SVGGENERATED);
    }

    //This structure is done because maybe some errors can be resolved by the CompilerRunner and don't need to be passed to the observers.
    //Only when erros can't be solved the observers will be notified about them
    public void setException(Exception exception){
        this.exception = exception;
        setChanged();
        notifyObservers(CompilationProgress.ERROROCCURED);
    }

    public Exception getException(){
        return exception;
    }

    public Document getGeneratedSVG() {
        return generatedSVG;
    }
}