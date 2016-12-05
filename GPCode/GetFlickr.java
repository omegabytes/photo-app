//
// File: GetFlickr.java
// 
// Instructions:
// 1) replace ****YOUR API KEY HERE**** with your flickr API key
// 2) compile and run
// 3) returns response string with flickr images with tag SFSUCS413F16Test
// 4) parse into JSON object with gson

package GPCode;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import com.google.gson.*;
import com.google.gson.Gson;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.*;

public class GetFlickr {
	GetFlickr() {
	}

	public static void main(String [] args) throws Exception {

		DemoGUI frame = new DemoGUI();
		frame.createFrame();

		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
			public X509Certificate[] getAcceptedIssuers(){return null;}
			public void checkClientTrusted(X509Certificate[] certs, String authType){}
			public void checkServerTrusted(X509Certificate[] certs, String authType){}
		}};

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			;
		}

		//// tag to search for
		//String searchTerm = "SFSUCS413F16Test";
		//String api  = "https://api.flickr.com/services/rest/?method=flickr.photos.search";
		//// number of results per page
		//String request = api + "&per_page=16";
		//request += "&format=json&nojsoncallback=1&extras=geo";
		//request += "&api_key=" + "e739c91c42e2153f2e769cb2f9bdcd1d";

		// optional search fields
		//String userId = "88935360@N05";
		//request += "&user_id=" + userId;
		//request += "&tags=hydrocephalic";

		// handle search

		//if (search.searchTerm.length() != 0) {
		//	search.request += "&tags="+ search.searchTerm;
		//}
        //
		//System.out.println("Sending http GET request:");
		//System.out.println(search.request);
        //
		//// open http connection
		//URL obj = new URL(search.request);
		//HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        //
		//// send GET request
		//con.setRequestMethod("GET");
        //
		//// get response
		//int responseCode = con.getResponseCode();
        //
		//System.out.println("Response Code : " + responseCode);
        //
		//// read and construct response String
		//BufferedReader in = new BufferedReader(new InputStreamReader
		//		(con.getInputStream()));
		//String inputLine;
		//StringBuffer response = new StringBuffer();
        //
		//while ((inputLine = in.readLine()) != null) {
		//	response.append(inputLine);
		//}
		//in.close();
        //
		//System.out.println(response);
        //
		//Gson gson = new Gson();
		//String s = response.toString();
        //
		//Response responseObject = gson.fromJson(s, Response.class);
		//System.out.println("# photos = " + responseObject.photos.photo.length);
		//System.out.println("Photo 0:");
		//int farm = responseObject.photos.photo[0].farm;
		//String server = responseObject.photos.photo[0].server;
		//String id = responseObject.photos.photo[0].id;
		//String secret = responseObject.photos.photo[0].secret;
		//String photoUrl = "http://farm"+farm+".static.flickr.com/"
		//		+server+"/"+id+"_"+secret+".jpg";
		//System.out.println(photoUrl);

	}
}
