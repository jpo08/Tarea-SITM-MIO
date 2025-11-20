import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.List;
import java.util.Map;

public class GraphRenderer {

    public static void drawGraph(
            String outputFile,
            List<Arc> arcs,
            Map<Integer, Stop> stops,
            int width,
            int height
    ) throws Exception {

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double minLat = Double.MAX_VALUE, maxLat = -Double.MAX_VALUE;
        double minLon = Double.MAX_VALUE, maxLon = -Double.MAX_VALUE;

        for (Stop s : stops.values()) {
            if (s.lat < minLat) minLat = s.lat;
            if (s.lat > maxLat) maxLat = s.lat;
            if (s.lon < minLon) minLon = s.lon;
            if (s.lon > maxLon) maxLon = s.lon;
        }

        double scaleX = width / (maxLon - minLon);
        double scaleY = height / (maxLat - minLat);
        double scale = Math.min(scaleX, scaleY) * 0.9;

        g.setStroke(new BasicStroke(2));
        g.setColor(Color.BLUE);

        for (Arc arc : arcs) {
            Stop a = stops.get(arc.from);
            Stop b = stops.get(arc.to);

            int x1 = (int) ((a.lon - minLon) * scale + 20);
            int y1 = (int) ((maxLat - a.lat) * scale + 20);

            int x2 = (int) ((b.lon - minLon) * scale + 20);
            int y2 = (int) ((maxLat - b.lat) * scale + 20);

            g.drawLine(x1, y1, x2, y2);
        }

        g.setColor(Color.RED);
        int r = 6;

        for (Stop s : stops.values()) {
            int x = (int) ((s.lon - minLon) * scale + 20);
            int y = (int) ((maxLat - s.lat) * scale + 20);
            g.fillOval(x - r / 2, y - r / 2, r, r);
        }

        g.dispose();
        ImageIO.write(img, "jpg", new File(outputFile));
        System.out.println("Imagen guardada en: " + outputFile);
    }
}