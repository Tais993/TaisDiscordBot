package music.spotify;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SearchSpotify {
    static SpotifyApi spotifyApi;
    static String spotifyApiClientSecret;
    private ClientCredentialsRequest clientCredentialsRequest;

    public String getSongUrl() {
        spotifyApi = new SpotifyApi.Builder()
                .setClientId("5a360fbb9cc74ca6b6d864a4a8857296")
                .setClientSecret(spotifyApiClientSecret)
                .build();

        clientCredentialsRequest = spotifyApi.clientCredentials().build();

        try {
            ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            e.printStackTrace();
        }

//        clientCredentialsSync();

        return searchSong();
    }
    
    public String searchSong() {
        SearchTracksRequest test = spotifyApi.searchTracks("lonely together").limit(1).build();
        
        CompletableFuture<Paging<Track>> test2 = test.executeAsync();
        try {
            Paging<Track> test3 = test2.get();

            System.out.println(test3.getItems()[0].getName());
            return test3.getItems()[0].getName();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setSpotifyApiKey() {
        InputStream isSpotifyApi;
        try {
            isSpotifyApi = new FileInputStream(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\Discord bot\\token\\spotifyapi.key");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        BufferedReader brSpotifyApi = new BufferedReader(new InputStreamReader(isSpotifyApi));
        try {
            spotifyApiClientSecret = brSpotifyApi.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clientCredentialsSync() {
        try {
            ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            e.printStackTrace();
        }
    }
}
