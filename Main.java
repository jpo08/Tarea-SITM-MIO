import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        Map<Integer, Line> lines = GraphBuilder.loadLines("lines-241.csv");
        Map<Integer, Stop> stops = GraphBuilder.loadStops("stops-241.csv");
        List<LineStop> lineStops = GraphBuilder.loadLineStops("linestops-241.csv");

        Map<String, List<Arc>> arcs = GraphBuilder.buildArcs(lineStops);

                for (String key : arcs.keySet()) {
            String[] parts = key.split("-");
            int lineId = Integer.parseInt(parts[0]);
            int orientation = Integer.parseInt(parts[1]);
            int variant = Integer.parseInt(parts[2]);

            Line ln = lines.get(lineId);

            System.out.println("\n==========================================");
            System.out.println("Ruta: " + ln.shortName + 
                               " | ID: " + lineId +
                               " | Orientación: " + orientation +
                               " | Variante: " + variant);
            System.out.println("------------------------------------------");

            for (Arc a : arcs.get(key)) {
                Stop from = stops.get(a.from);
                Stop to = stops.get(a.to);

                System.out.println(
                    a.from + " (" + from.shortName + ") -> " +
                    a.to + " (" + to.shortName + ")"
                );
            }
        }

        System.out.println("Generando mapa global del SITM-MIO...");

        SystemMapRenderer.drawFullSystem(
            "SITM_MIO_FULL_MAP.jpg",
            arcs,
            stops
        );
    }
}