package GPCode;

import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.io.*;
//import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alex on 12/5/16.
 */
public class GFModel {
    public String apiKey;
    public String api  = "https://api.flickr.com/services/rest/?method=flickr.photos.search";
    public String request;
    public ArrayList<String> urlList = new ArrayList<>(); // temp url list for testButtonPressed
    public ArrayList<Image> imageList = new ArrayList<>();
    public ArrayList<JButton> buttonList = new ArrayList<>();
    public ArrayList<String> savedImagesURL = new ArrayList<>(); //List of saved images URL
    public int maxResults = 10;

    private File file;
    private String fileName = "savedURLs.txt";

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

    // Created by Evan Terry to create file for saved URLs
    // images at selected index saved url appends to file
    private void createFile(String url) throws IOException {

//        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        BufferedWriter bufferedWriter = null;
        FileWriter writer = null;

//        File file = new File("Saved Image URLs" + timestamp +".text");

        file = new File(fileName);

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

    public void parseFile(String urlToBeDeleted) throws IOException {

        File tempFile = new File("tempFile.txt");

        BufferedReader fileRead = new BufferedReader(new FileReader(file));
        FileWriter tempFileWriter = new FileWriter(tempFile);

        String currentLine;

        try {

            while((currentLine = fileRead.readLine()) !=null) {

                String removedURL = currentLine.trim();

                if (!removedURL.equals(urlToBeDeleted)) {
                    tempFileWriter.write(currentLine + "\n");
                }
            }
        }
        catch (IOException e) {
            System.out.print("Something happened but I have no clue as to what.");
            e.printStackTrace();
        }
        finally {
            tempFileWriter.close();
            fileRead.close();
            tempFile.renameTo(file);
        }

    }

    public void saveImages() {

        for (String UrlList : urlList) {
            try {
                createFile(UrlList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
