import org.dhbw.swe.graph.Graph;
import org.dhbw.swe.graph.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.toIntExact;
import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {

    Graph graph;

    @BeforeEach
    public void beforeEachTest() {

        graph = Graph.INSTANCE;

    }

    @Test
    public void startNodeTest(){

        List<Node> startNodes = graph.four.stream()
                .filter(x -> x.getType().toString().contains("INIT"))
                .collect(Collectors.toList());

        assertEquals(4, startNodes.stream().map(x -> x.getType()).distinct().count());

    }

    @Test
    public void endNodeTest1(){

        List<Node> endNodes = graph.four.stream()
                .filter(x -> x.getType().toString().contains("END"))
                .collect(Collectors.toList());

        assertEquals(4, endNodes.stream().filter(x -> x.hasSpecialEdge()).count());

    }

    @Test
    public void endNodeTest2(){

        int edgeCount = graph.four.stream()
                .map(x -> x.getEdges().stream().count())
                .mapToInt(x -> toIntExact(x))
                .sum();

        assertEquals(56, edgeCount);

    }

    @Test
    public void graphInstanceTest(){

        assertEquals(graph, Graph.INSTANCE);

    }

}
