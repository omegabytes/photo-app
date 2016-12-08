package GPCode;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alex on 12/5/16.
 */
public class GFController {
    public GFController() throws IOException {
    }

    public static void main(String [] args) throws Exception {

        GFView frame = new GFView();
        frame.createFrame();

        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            ;
        }
    }

    GFModel model = new GFModel();

    public void saveButtonPressed() {
        System.out.println("Save button pressed");
        //todo: handle save
        //test for file creation
        try {
            createURLFile("https://returnofsavedURL.here");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchButtonPressed(String text) throws IOException {
        System.out.println("Search button pressed");
        model.handleSearch(text);
        getImageList(model.urlList);
    }

    public void loadButtonPressed() {
        System.out.println("Load button pressed");
        //todo: handle load
    }

    public void deleteButtonPressed() {
        System.out.println("Delete button pressed");
        //todo: handle delete
    }

    public void exitButtonPressed() {
        System.exit(0);
    }

    // Created by Evan Terry to create file for saved URLs
    //todo : images at selected index saved url appends to file
    private void createURLFile (String url) throws IOException {

        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        BufferedWriter bufferedWriter = null;
        FileWriter writer = null;

        File file = new File("Saved Image URLs" + timestamp +".text");

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


    private void savedImages(String savedImages) {
        for (int i = 0; i < model.savedImagesURL.size(); i++) {
            try {
                createURLFile(model.savedImagesURL.get(i));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Image getImageURL(String urlString) {
        Image img = null;
        try {
            final URL url = new URL(urlString);
            img = ImageIO.read(url);
            createURLFile(urlString);
        } catch (IOException e) {
            System.out.println("Error loading image");
            e.printStackTrace();
            return null;
        }
        return img;
    }

    private void getImageList(ArrayList<String> urlStrings) {
        for (String t : urlStrings) {
            model.imageList.add(getImageURL(t));
        }
    }

}
