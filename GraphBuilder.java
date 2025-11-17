import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class GraphBuilder {

    Map<Integer, Stop> stops = new HashMap<>();
    Map<Integer, Route> routes = new HashMap<>();
    List<LineStop> lineStops = new ArrayList<>();


    // Cargar stops.csv
    public void loadStops(String file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        br.readLine();
        String line;

        while ((line = br.readLine()) != null) {
            String[] parts = line.replace("\"", "").split(",");

            int stopId = Integer.parseInt(parts[0]);
            String name = parts[2];

            stops.put(stopId, new Stop(stopId, name));
        }
        br.close();
    }

    // Cargar lines.csv
    public void loadRoutes(String file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        br.readLine();
        String line;

        while ((line = br.readLine()) != null) {
            String[] parts = line.replace("\"", "").split(",");

            int lineId = Integer.parseInt(parts[0]);
            String shortName = parts[2];

            routes.put(lineId, new Route(lineId, shortName));
        }
        br.close();
    }

    // Cargar linestops.csv
    public void loadLineStops(String file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        br.readLine();
        String line;

        while ((line = br.readLine()) != null) {
            String[] p = line.replace("\"", "").split(",");

            int seq = Integer.parseInt(p[1]);
            int orientation = Integer.parseInt(p[2]);
            int lineId = Integer.parseInt(p[3]);
            int stopId = Integer.parseInt(p[4]);

            lineStops.add(new LineStop(lineId, stopId, seq, orientation));
        }
        br.close();
    }

    // Construir y mostrar grafos
    public void buildGraphs() {

        // agrupar por ruta
        Map<Integer, List<LineStop>> grouped =
                lineStops.stream().collect(Collectors.groupingBy(ls -> ls.lineId));

        for (Integer lineId : grouped.keySet()) {

            Route route = routes.get(lineId);
            System.out.println("\n======================================");
            System.out.println("Ruta " + route.shortName + " (ID=" + lineId + ")");
            System.out.println("======================================");

            List<LineStop> list = grouped.get(lineId);

            // IDA (orientation=0)
            printDirection(list, 0);

            // REGRESO (orientation=1)
            printDirection(list, 1);
        }
    }

    private void printDirection(List<LineStop> list, int orientation) {

        List<LineStop> seq = list.stream()
                .filter(ls -> ls.orientation == orientation)
                .sorted(Comparator.comparingInt(ls -> ls.sequence))
                .collect(Collectors.toList());

        String title = orientation == 0 ? "IDA" : "REGRESO";

        System.out.println("\n  --- " + title + " ---");

        for (int i = 0; i < seq.size() - 1; i++) {
            Stop a = stops.get(seq.get(i).stopId);
            Stop b = stops.get(seq.get(i + 1).stopId);

            System.out.println("     " + a.stopId + " -> " + b.stopId);
        }
    }
}
