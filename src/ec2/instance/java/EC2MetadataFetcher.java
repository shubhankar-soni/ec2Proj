import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EC2MetadataFetcher {

    private static final String METADATA_URL = "http://169.254.169.254/latest/meta-data/";

    public static void main(String[] args) {
        try {
            EC2MetadataFetcher fetcher = new EC2MetadataFetcher();
            String metadataJson = fetcher.fetchMetadataAsJson(METADATA_URL);
            System.out.println(metadataJson);
        } catch (Exception e) {
            System.err.println("Error fetching metadata: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String fetchMetadataAsJson(String url) throws Exception {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            boolean first = true;

            while ((line = reader.readLine()) != null) {
                if (!first) {
                    jsonBuilder.append(",");
                }
                first = false;

                String childUrl = url + line;
                if (line.endsWith("/")) {
                    jsonBuilder.append("\"").append(line.substring(0, line.length() - 1)).append("\":")
                               .append(fetchMetadataAsJson(childUrl));
                } else {
                    jsonBuilder.append("\"").append(line).append("\":\"").append(fetchValue(childUrl)).append("\"");
                }
            }
        }
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    private String fetchValue(String url) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString().replace("\"", "\\\""); 
        }
    }
}

