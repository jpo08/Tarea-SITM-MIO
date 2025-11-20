import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import java.io.File;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;


public class SystemMapRenderer {

    public static void drawFullSystem(
            String outputFile,
            Map<String, List<Arc>> arcsByRoute,
            Map<Integer, Stop> stops
    ) throws Exception {

        int width = 2000;
        int height = 1400;

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
        double scale = Math.min(scaleX, scaleY) * 0.92;

        Map<Integer, Color> routeColors = assignRouteColors(arcsByRoute);

        for (String key : arcsByRoute.keySet()) {

            int lineId = Integer.parseInt(key.split("-")[0]);
            g.setColor(routeColors.get(lineId));

            g.setStroke(new BasicStroke(2));

            for (Arc arc : arcsByRoute.get(key)) {
                Stop a = stops.get(arc.from);
                Stop b = stops.get(arc.to);
                if (a == null || b == null) continue;

                int x1 = (int) ((a.lon - minLon) * scale + 20);
                int y1 = (int) ((maxLat - a.lat) * scale + 20);

                int x2 = (int) ((b.lon - minLon) * scale + 20);
                int y2 = (int) ((maxLat - b.lat) * scale + 20);

                g.drawLine(x1, y1, x2, y2);
            }
        }

        g.setColor(Color.BLACK);
        int r = 4;

        for (Stop s : stops.values()) {
            int x = (int) ((s.lon - minLon) * scale + 20);
            int y = (int) ((maxLat - s.lat) * scale + 20);
            g.fillOval(x - r/2, y - r/2, r, r);
        }

        g.dispose();
        ImageIO.write(img, "jpg", new File(outputFile));
        System.out.println("Imagen general guardada en " + outputFile);
    }

    private static Map<Integer, Color> assignRouteColors(Map<String, List<Arc>> arcsByRoute) {
        Map<Integer, Color> colors = new HashMap<>();
        Random rand = new Random(12345);

        for (String key : arcsByRoute.keySet()) {
            int lineId = Integer.parseInt(key.split("-")[0]);
            if (!colors.containsKey(lineId)) {
                colors.put(lineId, new Color(
                        50 + rand.nextInt(180),
                        50 + rand.nextInt(180),
                        50 + rand.nextInt(180)
                ));
            }
        }
        return colors;
    }
}