package GPCode;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

/**
 * Created by alex on 12/5/16.
 */
public class GFModel {
    //todo refactor code into a MVC if you have time
    public String userID;

    public String searchTerm = "SFSUCS413F16Test";

    public String api  = "https://api.flickr.com/services/rest/?method=flickr.photos.search";

    public String request = api + "&per_page=16"
                                + "&format=json&nojsoncallback=1&extras=geo"
                                + "&api_key="
                                + "e739c91c42e2153f2e769cb2f9bdcd1d";
    // optional search fields

    //request += "&tags=hydrocephalic";

    //String userId = "88935360@N05";
    //request += "&user_id=" + userId;

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

}
