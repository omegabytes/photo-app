package GPCode;

import com.google.gson.Gson;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;



/**
 * Created by alex on 12/5/16.
 */
public class GFModel {
    public String userID;
    public String api  = "https://api.flickr.com/services/rest/?method=flickr.photos.search";
    public String request = api + "&per_page=16"
                                + "&format=json&nojsoncallback=1&extras=geo"
                                + "&api_key="
                                + "e739c91c42e2153f2e769cb2f9bdcd1d";

    // optional search fields

    //request += "&tags=hydrocephalic";

    //String userId = "88935360@N05";
    //request += "&user_id=" + userId;

    public void handleSearch(String searchTerm) throws IOException {

        GFController.createURLFile(searchTerm);

        if (searchTerm.length() != 0) {
            request += "&tags="+ searchTerm;
        }

        System.out.println("Sending http GET request:");
        System.out.println(request);

        // open http connection
        URL obj = new URL(request);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // send GET request
        con.setRequestMethod("GET");

        // get response
        int responseCode = con.getResponseCode();

        System.out.println("Response Code : " + responseCode);

        // read and construct response String
        BufferedReader in = new BufferedReader(new InputStreamReader
                (con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response);

        Gson gson = new Gson();
        String s = response.toString();

        Response responseObject = gson.fromJson(s, Response.class);
        System.out.println("# photos = " + responseObject.photos.photo.length);
        System.out.println("Photo 0:");
        int farm = responseObject.photos.photo[0].farm;
        String server = responseObject.photos.photo[0].server;
        String id = responseObject.photos.photo[0].id;
        String secret = responseObject.photos.photo[0].secret;
        String photoUrl = "http://farm"+farm+".static.flickr.com/"
                +server+"/"+id+"_"+secret+".jpg";
        System.out.println(photoUrl);
    }
}
