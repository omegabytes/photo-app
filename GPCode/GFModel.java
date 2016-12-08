package GPCode;

import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by alex on 12/5/16.
 */
public class GFModel {
    public String apiKey;
    public String api  = "https://api.flickr.com/services/rest/?method=flickr.photos.search";
    public String request;
    public ArrayList<String> urlList = new ArrayList<>(); // temp url list for testButtonPressed
    public ArrayList<Image> imageList = new ArrayList<>();
    public ArrayList<String> savedImagesURL = new ArrayList<>(); //List of saved images URL
    public int maxResults = 10;


    // optional search fields
    //request += "&tags=hydrocephalic";
    //String userId = "88935360@N05";
    //request += "&user_id=" + userId;
    public GFModel() throws IOException {
        try {
            FileReader fileReader = new FileReader(new File(".config"));
            BufferedReader br = new BufferedReader(fileReader);
            apiKey = br.readLine();
            request = api + "&per_page=16"
                    + "&format=json&nojsoncallback=1&extras=geo"
                    + "&api_key="
                    + apiKey;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Image getImage(int i) {
        Image image = imageList.get(i);
        return image;
    }


    public void handleSearch(String searchTerm) throws IOException {

        String oldRequest = request;

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


            for (int i = 0; i< maxResults; i++) {
                    System.out.println("Photo " + i +":");
                    int farm = responseObject.photos.photo[i].farm;
                    String server = responseObject.photos.photo[i].server;
                    String id = responseObject.photos.photo[i].id;
                    String secret = responseObject.photos.photo[i].secret;
                    String photoUrl = "http://farm"+farm+".static.flickr.com/"
                            +server+"/"+id+"_"+secret+".jpg";
                    System.out.println(photoUrl);
                    urlList.add(0,photoUrl);
            }
    }
}
