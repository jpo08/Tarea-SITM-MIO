import java.util.*;

public class GraphBuilder {

    public static Map<Integer, Line> loadLines(String path) throws Exception {
        Map<Integer, Line> lines = new HashMap<>();
        for (String[] r : CSVReader.read(path)) {
            int id = Integer.parseInt(r[0]);
            String shortName = r[2];
            String desc = r[3];
            lines.put(id, new Line(id, shortName, desc));
        }
        return lines;
    }

    public static Map<Integer, Stop> loadStops(String path) throws Exception {
        Map<Integer, Stop> stops = new HashMap<>();
        for (String[] r : CSVReader.read(path)) {
            int id = Integer.parseInt(r[0]);
            String shortName = r[2];
            double lon = Double.parseDouble(r[6]);
            double lat = Double.parseDouble(r[7]);
            stops.put(id, new Stop(id, shortName, lat, lon));
        }
        return stops;
    }

    public static List<LineStop> loadLineStops(String path) throws Exception {
        List<LineStop> lst = new ArrayList<>();
        for (String[] r : CSVReader.read(path)) {
            int seq = Integer.parseInt(r[1]);
            int orient = Integer.parseInt(r[2]);
            int lineId = Integer.parseInt(r[3]);
            int stopId = Integer.parseInt(r[4]);
            int variant = Integer.parseInt(r[6]);

            lst.add(new LineStop(lineId, stopId, seq, orient, variant));
        }
        return lst;
    }

    public static Map<String, List<Arc>> buildArcs(List<LineStop> list) {

        Map<String, List<Arc>> arcsByRoute = new HashMap<>();
        Map<String, List<LineStop>> grouped = new HashMap<>();

        for (LineStop ls : list) {
            String key = ls.lineId + "-" + ls.orientation + "-" + ls.variant;
            grouped.computeIfAbsent(key, k -> new ArrayList<>()).add(ls);
        }

        for (String key : grouped.keySet()) {
            List<LineStop> stops = grouped.get(key);
            stops.sort(Comparator.comparingInt(s -> s.sequence));

            List<Arc> arcs = new ArrayList<>();
            for (int i = 0; i < stops.size() - 1; i++) {
                arcs.add(new Arc(stops.get(i).stopId, stops.get(i+1).stopId));
            }
            arcsByRoute.put(key, arcs);
        }

        return arcsByRoute;
    }
}