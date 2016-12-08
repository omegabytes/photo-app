package GPCode;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

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
        model.saveImages();
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
//        enable when delete action is created
//        parseFile();
    }

    public void exitButtonPressed() {
        System.exit(0);
    }

    private Image getImageURL(String urlString) {
        Image img = null;
        try {
            final URL url = new URL(urlString);
            img = ImageIO.read(url);
        } catch (IOException e) {
            System.out.println("Error loading image");
            e.printStackTrace();
            return null;
        }
        return img;
    }

    private void getImageList(ArrayList<String> urlStrings) {
        for (String t : urlStrings) {
            model.imageList.add(0,getImageURL(t));
        }
    }

}
