import java.io.*;
import java.util.*;

public class CSVReader {
    public static List<String[]> read(String path) throws Exception {
        List<String[]> rows = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(path));

        String line;
        boolean first = true;
        while ((line = br.readLine()) != null) {
            if (first) { first = false; continue; }
            rows.add(line.split(","));
        }
        br.close();
        return rows;
    }
}