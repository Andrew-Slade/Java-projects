import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingExample2 extends JFrame implements ActionListener {

    private JButton button = new JButton("Say Hello");
    private JTextField inputTextField = new JTextField();
    private JLabel outputLabel = new JLabel("", SwingConstants.CENTER);

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        EventQueue.invokeLater(() -> {
            SwingExample2 ex = new SwingExample2("Swing Example 2");
            ex.setVisible(true);
        });
    }

    public SwingExample2(String title) {
        super(title);
        createAndShowGUI();
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI() {
        //Create and set up the window.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the layout.
        setLayout(new GridLayout(4, 1, 0, 10));

        // Add components to the layout.
        add(new JLabel("Enter your name", SwingConstants.CENTER));
        add(inputTextField);
        add(button);
        add(outputLabel);

        // Add an ActionListener for handling button events.
        button.addActionListener(this);

        //Display the window.
        setMinimumSize(new Dimension(300, 200));
        pack();
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        String name = inputTextField.getText();
        if (name.equals(""))
            outputLabel.setText("Hello, stranger");
        else
            outputLabel.setText("Hello, " + name);
    }
}
