package GPCode;

import com.google.gson.Gson;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GFModel {
    public String apiKey;
    public String api  = "https://api.flickr.com/services/rest/?method=flickr.photos.search";
    public String request;
    public ArrayList<String> urlList = new ArrayList<>();
    public ArrayList<JButton> buttonList = new ArrayList<>();
    public ArrayList<String> selectedImages = new ArrayList<>(); //List of selected images

    public int maxResults = 10;

    static private String fileName = "savedURLs.txt";
    protected File file = new File(fileName);


    public GFModel() throws IOException {
        try {
            FileReader fileReader = new FileReader(new File(".config"));
            BufferedReader br = new BufferedReader(fileReader);
            apiKey = br.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*  Handles search functionality by using Flickr's API.
        https://www.flickr.com/services/api/
        Prints list of searched photos
    */
    public void handleSearch(String searchTerm) throws IOException {
        request = api + "&per_page=16"
                + "&format=json&nojsoncallback=1&extras=geo"
                + "&api_key="
                + apiKey;

        if (searchTerm.length() != 0) {
            searchTerm = searchTerm.replaceAll(" ","%20");
            request += "&tags=" + searchTerm;
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

        for (int i = 0; i< maxResults; i++) {
            System.out.println("Photo " + (i+1) +":");
            int farm = responseObject.photos.photo[i].farm;
            String server = responseObject.photos.photo[i].server;
            String id = responseObject.photos.photo[i].id;
            String secret = responseObject.photos.photo[i].secret;
            String photoUrl = "http://farm"+farm+".static.flickr.com/"
                    +server+"/"+id+"_"+secret+".jpg";
            System.out.println("\t\t" + photoUrl);
            urlList.add(photoUrl);
        }
    }

    /*  Handles the file creation functionality.
        Appends to a file if one exists.
    */
    private void createFile(String url) throws IOException {

        BufferedWriter bufferedWriter = null;
        FileWriter writer = null;


        // creates the file
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            // creates a FileWriter Object
            writer = new FileWriter(file, true);
            bufferedWriter = new BufferedWriter(writer);

            // Writes the content to the file
            bufferedWriter.write(url + '\n');
        }
        catch (IOException e) {
            System.out.print("Something happened but I have no clue as to what.");
            e.printStackTrace();
        }
        finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.out.print("Something happened but I have no clue as to what.");
                e.printStackTrace();
            }
        }
    }

    /*  Handles the saving of images.
        Calls createFile(String s) and
        passes urlListArray to the function.
    */
    public void saveImages() {

        System.out.println("Added to " + fileName + ": ");

        for (String UrlList : urlList) {
            System.out.println("\t\t" + UrlList);

            try {
                createFile(UrlList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /*  Handles the file read functionality.
        Loads the file from a relative path.
        Clears the current ArrayLists.
        Replaces urlList with lines from text.
    */

    public void loadImages() throws IOException {


        BufferedReader fileRead = new BufferedReader(new FileReader(file));

        urlList.clear();
        buttonList.clear();

        String currentLine;

        System.out.println("Loaded from " + fileName + ":");

        try {
            while ((currentLine = fileRead.readLine()) != null) {
                urlList.add(currentLine);
                System.out.println("\t\t" + currentLine);
            }
        }
        catch (IOException e) {
            System.out.print("Something happened but I have no clue as to what.");
            e.printStackTrace();
        }
    }

}
