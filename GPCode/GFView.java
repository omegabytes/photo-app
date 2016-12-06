package GPCode;

import com.google.gson.Gson;

import java.awt.*;
import java.awt.event.*;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.*;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.swing.JFrame;
import javax.swing.BoxLayout;

public class GFView extends JFrame implements ActionListener {


    GFController controller = new GFController();

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

    public GFView() {
        initComponents();
    }

    private void initComponents() {

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
        this.setSize(GFView.frameWidth, GFView.frameHeight);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            try {
                controller.searchButtonPressed(searchTagField.getText());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        else if (e.getSource() == testButton) {
            controller.testButtonPressed();
        }
        else if (e.getSource() == loadButton) {
            controller.loadButtonPressed();
        }
        else if (e.getSource() == deleteButton) {
            controller.deleteButtonPressed();
        }
        else if (e.getSource() == saveButton) {
            controller.saveButtonPressed();
        }
        else if (e.getSource() == exitButton) {
            controller.exitButtonPressed();
        }
    }
}