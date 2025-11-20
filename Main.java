import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        Map<Integer, Line> lines = GraphBuilder.loadLines("lines-241.csv");
        Map<Integer, Stop> stops = GraphBuilder.loadStops("stops-241.csv");
        List<LineStop> lineStops = GraphBuilder.loadLineStops("linestops-241.csv");

        Map<String, List<Arc>> arcs = GraphBuilder.buildArcs(lineStops);

        System.out.println("Generando mapa global del SITM-MIO...");

        SystemMapRenderer.drawFullSystem(
            "SITM_MIO_FULL_MAP.jpg",
            arcs,
            stops
        );
    }
}