package GPCode;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Component;
import java.io.IOException;
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
            try {
                controller.searchButtonPressed(searchTagField.getText());
                for (Image i : model.imageList) {
                    JButton imageButton = new JButton(new ImageIcon(i));
                    onePanel.add(imageButton);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            onePanel.revalidate();
            onePanel.repaint();
            oneScrollPanel.setViewportView(onePanel);
        }
        else if (e.getSource() == testButton) {
            try {
                onePanel.add(new JButton(new ImageIcon(controller.testButtonPressed())));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            onePanel.revalidate();
            onePanel.repaint();
            oneScrollPanel.setViewportView(onePanel);
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