package GPCode;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.*;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

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
            System.out.print("Something happened but I have no clue as to what.");
            e.printStackTrace();
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
    }

    public void loadButtonPressed(JPanel thisPanel) throws IOException {
        System.out.println("Load button pressed");
        thisPanel.removeAll();
        model.loadImages();
    }

    public void deleteButtonPressed(JPanel thisPanel) {
        System.out.println("Delete button pressed");
        //todo: this only deletes one at a time, why?
        for (int i =0; i<model.buttonList.size(); i++) {
            if (model.buttonList.get(i).isBorderPainted()) {
                System.out.println("Photo " + i + " has been deleted.");
                System.out.println("\t\t" + model.buttonList.get(i).getName());
                model.buttonList.remove(i);
                thisPanel.remove(i);
                model.urlList.remove(i);
            }
        }


        thisPanel.revalidate();
        thisPanel.repaint();
    }

    public void exitButtonPressed() {
        System.exit(0);
    }
}
