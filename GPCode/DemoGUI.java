package GPCode;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.BoxLayout;

public class DemoGUI extends JFrame implements ActionListener {

	JTextField searchTagField = new JTextField("");
	JTextField numResultsStr = new JTextField("10");
	JPanel onePanel;
	JScrollPane oneScrollPanel;
	JButton testButton = new JButton("Test");
	JButton searchButton = new JButton("Search");
	JButton deleteButton = new JButton("Delete");
	JButton loadButton = new JButton("Load");
	JButton exitButton = new JButton("Exit");

	static int frameWidth = 800;
	static int frameHeight = 600;

	public DemoGUI() {

		// create bottom subpanel with buttons, flow layout
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
		buttonsPanel.add(testButton);
		buttonsPanel.add(exitButton);
		// add listener for clicks
		testButton.addActionListener(this);
		exitButton.addActionListener(this);

		buttonsPanel.add(loadButton);
		buttonsPanel.add(deleteButton);

		loadButton.addActionListener(this);
		deleteButton.addActionListener(this);

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


		// create middle subpanel with 2 text fields and button, border layout
		JPanel textFieldSubPanel = new JPanel(new FlowLayout());
		// create and add label to subpanel
		JLabel tl = new JLabel("Enter search tag:");
		textFieldSubPanel.add(tl);

		// set width of left text field
		searchTagField.setColumns(23);
		// add listener for typing in left text field
		searchTagField.addActionListener(this);
		// add left text field to middle subpanel
		textFieldSubPanel.add(searchTagField);
		// add search button to middle subpanel
		textFieldSubPanel.add(searchButton);
		// add listener for searchButton clicks
		searchButton.addActionListener(this);

		// create and add label to middle subpanel, add to middle subpanel
		JLabel tNum = new JLabel("max search results:");
		numResultsStr.setColumns(2);
		textFieldSubPanel.add(tNum);
		textFieldSubPanel.add(numResultsStr);

		// create and add panel to contain bottom and middle subpanels
	/*
	JPanel textFieldPanel = new JPanel(new BorderLayout());
	textFieldPanel.add(buttonsPanel, BorderLayout.SOUTH);
	textFieldPanel.add(textFieldSubPanel, BorderLayout.NORTH);
	*/
		JPanel textFieldPanel = new JPanel();
		textFieldPanel.setLayout(new BoxLayout(textFieldPanel, BoxLayout.Y_AXIS));
		textFieldPanel.add(textFieldSubPanel);
		textFieldPanel.add(buttonsPanel);

		// create top panel
		onePanel = new JPanel();
		onePanel.setLayout(new BoxLayout(onePanel, BoxLayout.Y_AXIS));

		// create scrollable panel for top panel
		oneScrollPanel = new JScrollPane(onePanel,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		oneScrollPanel.setPreferredSize(new Dimension(frameWidth, frameHeight-100));
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		// add scrollable panel to main frame
		add(oneScrollPanel);

		// add panel with buttons and textfields to main frame
		add(textFieldPanel);


	}

	//public static void main(String [] args) throws Exception {
	//	DemoGUI frame = new DemoGUI();
	//	frame.setTitle("Swing GUI Demo");
	//	frame.setSize(frameWidth, frameHeight);
	//	frame.setLocationRelativeTo(null);
	//	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	frame.setVisible(true);
	//}

	public void actionPerformed(ActionEvent e) {
		//todo refactor this into a switch statement
		if (e.getSource() == searchButton) {
			System.out.println("Search");
			//todo create function to handle search
		}
		else if (e.getSource() == testButton) {
			System.out.println("Test");
		}
		else if (e.getSource() == deleteButton) {
			System.out.println("Delete");
			//todo create function to handle image deletion
		}
		else if (e.getSource() == loadButton) {
			System.out.println("Load");
		}
		else if (e.getSource() == exitButton) {
			System.exit(0);
		}
		else if (e.getSource() == searchTagField) {
			System.out.println("searchTagField: " + searchTagField.getText());
		}
	}

}