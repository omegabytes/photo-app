package GPCode;

import com.google.gson.Gson;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by alex on 12/5/16.
 */
public class GFModel {
    public String api  = "https://api.flickr.com/services/rest/?method=flickr.photos.search";
    public String request = api + "&per_page=16"
                                + "&format=json&nojsoncallback=1&extras=geo"
                                + "&api_key="
                                + "e739c91c42e2153f2e769cb2f9bdcd1d";
    public ArrayList<String> urlList = new ArrayList<>(); // temp url list for testButtonPressed
    public  ArrayList<Image> imageList = new ArrayList<>();
    public Image testURL;

    // optional search fields

    //request += "&tags=hydrocephalic";

    //String userId = "88935360@N05";
    //request += "&user_id=" + userId;

    public void handleSearch(String searchTerm) throws IOException {

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
        for (int i = 0; i< responseObject.photos.photo.length; i++) {
            int farm = responseObject.photos.photo[i].farm;
            String server = responseObject.photos.photo[i].server;
            String id = responseObject.photos.photo[i].id;
            String secret = responseObject.photos.photo[i].secret;
            String photoUrl = "http://farm"+farm+".static.flickr.com/"
                    +server+"/"+id+"_"+secret+".jpg";
            System.out.println(photoUrl);
            urlList.add(photoUrl);
        }
    }
}
