package GPCode;

import com.google.gson.Gson;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.BoxLayout;

public class DemoGUI extends JFrame implements ActionListener {

	JTextField searchTagField = new JTextField("");
	JTextField numResultsStr = new JTextField("10");
	JPanel onePanel;
	JScrollPane oneScrollPanel;
	JButton searchButton = new JButton("Search");
    JButton testButton = new JButton("Test");
    JButton loadButton = new JButton("Load");
    JButton deleteButton = new JButton("Delete");
    JButton saveButton = new JButton("Save");
    JButton exitButton = new JButton("Exit");

	static int frameWidth = 800;
	static int frameHeight = 600;

    GFModel search = new GFModel();

	public DemoGUI() {

        // create bottom panel with buttons, flow layout
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        // create middle panel
        JPanel textFieldSubPanel = new JPanel(new FlowLayout());
        JLabel tl = new JLabel("Enter search tag:");
        JLabel tNum = new JLabel("max search results:");
        searchTagField.setColumns(23);
        numResultsStr.setColumns(2);

        // create and add container panel for bottom and middle subpanels
        JPanel textFieldPanel = new JPanel();
        textFieldPanel.setLayout(new BoxLayout(textFieldPanel, BoxLayout.Y_AXIS));
        textFieldPanel.add(textFieldSubPanel);
        textFieldPanel.add(buttonsPanel);

        // add buttons/labels to middle panel
        textFieldSubPanel.add(tl);
        textFieldSubPanel.add(searchTagField);
        textFieldSubPanel.add(searchButton);
        textFieldSubPanel.add(tNum);
        textFieldSubPanel.add(numResultsStr);

        // add buttons to bottom panel
        buttonsPanel.add(testButton);
        buttonsPanel.add(loadButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(saveButton);
        buttonsPanel.add(exitButton);

        // create top panel
        onePanel = new JPanel();
        onePanel.setLayout(new BoxLayout(onePanel, BoxLayout.Y_AXIS));
        oneScrollPanel = new JScrollPane(onePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        oneScrollPanel.setPreferredSize(new Dimension(frameWidth, frameHeight-100));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(oneScrollPanel);
        add(textFieldPanel);

        // add listeners for when buttons are clicked
        testButton.addActionListener(this);
        exitButton.addActionListener(this);
        loadButton.addActionListener(this);
        deleteButton.addActionListener(this);
        searchButton.addActionListener(this);
        searchTagField.addActionListener(this);

        //todo what is this for and can we delete it?
		// some kind of test sout for an object
		System.out.println("testButton at " +
				testButton.getClass().getName() +
				"@" + Integer.toHexString(hashCode()));
		System.out.println("Components: ");
		Component comp[] = buttonsPanel.getComponents();
		for (int i=0; i<comp.length; i++) {
			System.out.println(comp[i].getClass().getName() +
					"@" + Integer.toHexString(hashCode()));
		}
	}

    /** HELPER FUNCTIONS **/
    public void createFrame() {
        this.setTitle("Swing GUI Demo");
        this.setSize(DemoGUI.frameWidth, DemoGUI.frameHeight);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == searchButton) {
            searchButtonPressed();
        }
		else if (e.getSource() == testButton) {
            testButtonPressed();
		}
        else if (e.getSource() == loadButton) {
            loadButtonPressed();
        }
        else if (e.getSource() == deleteButton) {
            deleteButtonPressed();
        }
        else if (e.getSource() == saveButton) {
            saveButtonPressed();
        }
        else if (e.getSource() == exitButton) {
            System.exit(0);
        }
	}

    private void saveButtonPressed() {
        System.out.println("Save button pressed");
    }

    private void searchButtonPressed() {
        System.out.println("Search button pressed");
        search.setSearchTerm(searchTagField.getText());
        try {
            handleSearch();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void loadButtonPressed() {
        System.out.println("Load button pressed");
    }

    private void deleteButtonPressed() {
        System.out.println("Delete button pressed");
    }

    private void testButtonPressed() {
        System.out.println("Test button pressed");
    }

    public void handleSearch() throws IOException {
        if (search.searchTerm.length() != 0) {
            search.request += "&tags="+ search.searchTerm;
        }

        System.out.println("Sending http GET request:");
        System.out.println(search.request);

        // open http connection
        URL obj = new URL(search.request);
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