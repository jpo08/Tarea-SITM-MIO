import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        String linesFile = "lines.csv";
        String stopsFile = "stops.csv";
        String lineStopsFile = "linestops.csv";

        GraphBuilder builder = new GraphBuilder();

        builder.loadStops(stopsFile);
        builder.loadRoutes(linesFile);
        builder.loadLineStops(lineStopsFile);

        builder.buildGraphs();
    }
}
