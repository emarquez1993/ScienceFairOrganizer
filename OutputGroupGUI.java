// Eric Marquez and Nick Keele
// COSC 3420.001
// Project #5
// Due date: 4/25/2018
/*
 * This class provides the GUI for the OutputGroup class.
 */
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;

public class OutputGroupGUI extends JPanel implements ActionListener {

    private JButton browse1Button;
    private JButton browse2Button;
    private JButton generateButton;
    private JButton checkSourcesButton;
    private JTextArea input;
    private JFileChooser fc1;
    private JFileChooser fc2;
    private JFileChooser fc3;
    private File projectFile, judgeFile, assignmentFile;

    /*
     * Default constructor that sets the layout, button, and
     * text field for the GUI.
     */
    public OutputGroupGUI() {
        super(new BorderLayout());

        input = new JTextArea(5, 35);
        input.setMargin(new Insets(5, 5, 5, 5));
        input.setEditable(false);
        JScrollPane inputScrollPane = new JScrollPane(input);

        fc1 = new JFileChooser();
        fc2 = new JFileChooser();
        fc3 = new JFileChooser();

        browse1Button = new JButton("Browse for project file");
        browse1Button.addActionListener(this);

        browse2Button = new JButton("Browse for judges file");
        browse2Button.addActionListener(this);

        generateButton = new JButton("Generate assignment file");
        generateButton.addActionListener(this);

        checkSourcesButton = new JButton("Check sources before processing");
        checkSourcesButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(browse1Button);
        buttonPanel.add(browse2Button);
        buttonPanel.add(checkSourcesButton);
        buttonPanel.add(generateButton);


        add(buttonPanel, BorderLayout.PAGE_START);
        add(inputScrollPane, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        
    	//Sets the projects file.
    	if (e.getSource() == browse1Button) {
            int inputFile = fc1.showOpenDialog(OutputGroupGUI.this);

            if (inputFile == JFileChooser.APPROVE_OPTION) {
                projectFile = fc1.getSelectedFile();
                input.append("Project file selected.\n");
            }
            else {
                input.append("Project file selection cancelled.\n");
            }
            input.setCaretPosition(input.getDocument().getLength());
        }

    	//Sets the judges file.
        if (e.getSource() == browse2Button) {
            int inputFile2 = fc2.showOpenDialog(OutputGroupGUI.this);

            if(inputFile2 == JFileChooser.APPROVE_OPTION) {
                judgeFile = fc2.getSelectedFile();
                input.append("Judges file selected.\n");
            }
            else{
                input.append("Judge file selection cancelled.\n");
            }
        }

        //Writes to the users specified file.
        if (e.getSource() == generateButton){
            int outputFile = fc3.showOpenDialog(OutputGroupGUI.this);

            if(outputFile == JFileChooser.APPROVE_OPTION){
                assignmentFile = fc3.getSelectedFile();
                input.append("Output file selected.\n");
                OutputGroup.projectsFile(projectFile.getName());
                OutputGroup.judgesFile(judgeFile.getName());
                OutputGroup.assignJudges();
                OutputGroup.writeToFile(assignmentFile.getName());
                input.append("Successfully wrote to output file. \n");
            }
            else{
                input.append("Output file selection cancelled.\n");
            }
        }

        //Checks to see if both the judge and project files are present.
        if (e.getSource() == checkSourcesButton){
            if(projectFile != null && judgeFile != null){
                input.append("Both files present and accounted for, ready for processing.\n");
            }
            else if(projectFile == null && judgeFile == null){
                input.append("Reselect both files.\n");
            }

            else if(projectFile == null){
                input.append("Reselect project file.\n");
            }

            else if(judgeFile == null){
                input.append("Reselect judge file.\n");
            }
        }
    }

    private static void Window() {
        JFrame frame = new JFrame("Judge Assignments");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new OutputGroupGUI());

        frame.pack();
        frame.setVisible(true);
    }

    /*
     * Main method to initialize the GUI.
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Window();
            }
        });

    }
}