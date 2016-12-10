package GPCode;

import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.BoxLayout;

public class GFView extends JFrame implements ActionListener {


    GFController controller = new GFController();
    private GFModel model = controller.model;

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
    JButton imageButton = new JButton("Image");

    static int frameWidth = 800;
    static int frameHeight = 600;

    public GFView() throws IOException {
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
        oneScrollPanel.getVerticalScrollBar().setUnitIncrement(10);
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
        saveButton.addActionListener(this);

        printTestInfo(buttonsPanel);
    }

    /** HELPER FUNCTIONS **/
    public void createFrame() {
        this.setTitle("Photo App");
        this.setSize(GFView.frameWidth, GFView.frameHeight);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            model.maxResults = Integer.parseInt(numResultsStr.getText());

            //System.out.println("button list before clear: " + model.buttonList);
            onePanel.removeAll();
            model.buttonList.clear();
            //System.out.println("button list after clear: " + model.buttonList);

            createImageButton();
            //System.out.println("button list after createImageButton(): " + model.buttonList);

            for (JButton button : model.buttonList){
                imageButton = button;
                imageButton.addActionListener(this);
                onePanel.add(imageButton);
            }

            onePanel.revalidate();
            onePanel.repaint();
            oneScrollPanel.setViewportView(onePanel);
        }
        else if (e.getSource() == testButton) {
            try{
                String testUrl = "https://v1.std3.ru/73/19/1423452199-731965de88a111efd89bcfeea594c24b.jpeg";
                JButton testButton = new JButton(new ImageIcon(resize(testUrl,200)));
                onePanel.add(testButton);

            } catch (IOException e1) {
                e1.printStackTrace();
            }

            onePanel.revalidate();
            onePanel.repaint();
            oneScrollPanel.setViewportView(onePanel);
        }
        else if (e.getSource() == loadButton) {
            try {
                controller.loadButtonPressed();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        else if (e.getSource() == deleteButton) {
            controller.deleteButtonPressed();
            onePanel.revalidate();
            onePanel.repaint();
        }
        else if (e.getSource() == saveButton) {
            controller.saveButtonPressed();
        }
        else if (e.getSource() == exitButton) {
            controller.exitButtonPressed();
        }

        for (int i=0;i<model.buttonList.size();i++) {
            if (e.getSource() == model.buttonList.get(i)) {
                System.out.println("Photo " + i + " selected.");
            }
        }
    }

    public void createImageButton() {
        try {
            controller.searchButtonPressed(searchTagField.getText());
            for (String url : model.urlList) {
                int i = 0;
                imageButton = new JButton(new ImageIcon(resize(url,200)));
                imageButton.setName(searchTagField.getText() + i++);
                model.buttonList.add(imageButton);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        System.out.println("urlList: " + model.urlList+"\n");
    }

    public BufferedImage resize(String url, int newHeight) throws IOException{
        URL u = new URL(url);
        final BufferedImage image = ImageIO.read(u);
        float width = image.getWidth();
        float height = image.getHeight();
        float aspectRatio = height/width;
        float newWidth = (newHeight/aspectRatio);
        final BufferedImage resized = new BufferedImage((int)newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = resized.createGraphics();
        g.drawImage(image, 0, 0, (int)newWidth, newHeight, null);
        g.dispose();
        return resized;
    }

    // some kind of test sout for an object
    private void printTestInfo(JPanel buttonsPanel) {
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
}