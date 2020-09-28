package music.youtube;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.URL;

public class Search {

    static String ytApiKey;
    JSONParser parser = new JSONParser();

    public String getVideoUrl(String input) {
        input = input.replaceAll(" ", "%20");

        String fullUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=1&q=" + input + "&key=" + ytApiKey;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(new URL(fullUrl).openConnection().getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String fullReturnValueApi = getStringFromBufferedReader(in);

        String videoId = getVideoIdUsingString(fullReturnValueApi);

        if (videoId.startsWith("Error")) return videoId;

        return "https://www.youtube.com/watch?v=" + videoId;
    }

    public String getVideoIdUsingString(String toJson) {
        JSONObject json;
        try {
            json = (JSONObject) parser.parse(toJson);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Error: parsing String to JSON failed";
        }

        JSONArray jsonArray = (JSONArray) json.get("items");
        JSONObject firstItemArray = (JSONObject) jsonArray.get(0);
        JSONObject id = (JSONObject) firstItemArray.get("id");

        return (String) id.get("videoId");
    }

    public String getStringFromBufferedReader(BufferedReader in) {
        String nextLine;
        StringBuilder fullJsonStringBuilder = new StringBuilder();

        while (true) {
            try {
                if ((nextLine = in.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
                return "Error: building String from Buffered Reader went wrong";
            }
            fullJsonStringBuilder.append(nextLine).append("\n");
        }

        return fullJsonStringBuilder.toString();
    }

    public void setYtApiKey() {
        InputStream isYtApi;
        try {
            isYtApi = new FileInputStream(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\Discord bot\\token\\ytapi.key");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        BufferedReader brYtApi = new BufferedReader(new InputStreamReader(isYtApi));
        try {
            ytApiKey = brYtApi.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}