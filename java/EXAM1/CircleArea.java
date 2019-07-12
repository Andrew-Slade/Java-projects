import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CircleArea extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private javax.swing.JLabel Area;
    private javax.swing.JLabel RadiusLabel;
    private javax.swing.JFormattedTextField areaField;
    private javax.swing.JButton calculateButton;
    private javax.swing.JButton clearButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField radiusField;
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            CircleArea frame = new CircleArea("Compute Circle Area");
            frame.createAndShowGUI();
        });
    }

    private CircleArea(String title) {
        super(title);
    }

    /**
     * Create the GUI and show it. For thread safety, this method should
     * be invoked from the event-dispatch thread.
     */
    private void createAndShowGUI() {

        initComponents();
        addListeners();

        // Display the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    /**
     * Set the frame's layout, create the UI components, and add them to the layout
     */
    private void initComponents() {
        // TODO code application logic here    

        jPanel1 = new javax.swing.JPanel();
        radiusField = new javax.swing.JTextField();
        clearButton = new javax.swing.JButton("clear");
        calculateButton = new javax.swing.JButton("calculate");
        RadiusLabel = new javax.swing.JLabel();
        Area = new javax.swing.JLabel();
        areaField = new javax.swing.JFormattedTextField(new java.text.DecimalFormat("#.00"));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        radiusField.setText("");

        calculateButton.setText("Calculate");

        clearButton.setText("Clear");

        RadiusLabel.setText("Radius");

        Area.setText("Area");

        areaField.setText("");
        areaField.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RadiusLabel)
                            .addComponent(Area))
                        .addGap(170, 170, 170))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(calculateButton)
                        .addGap(57, 57, 57)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clearButton)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(radiusField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(areaField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(radiusField)
                    .addComponent(RadiusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(areaField)
                    .addComponent(Area, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clearButton)
                    .addComponent(calculateButton))
                .addGap(59, 59, 59))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();

	//END HERE
    }
    /**
     * Add listeners for the "Calculate" and "Clear" buttons
     */
    private void addListeners() {
        // TODO code application logic here
        clearButton.addActionListener(this); //add action listener
        calculateButton.addActionListener(this); //add action listener
    }

    /**
     * Handle ActionEvents from the "Calculate" and "Clear" buttons
     **/
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        // TODO code application logic here
        if(e.getActionCommand() == "Clear"){
          radiusField.setText(null);
          areaField.setText(null);
        }
        else if(e.getActionCommand() == "Calculate"){
          if(radiusField.getText().length() != 0){
             double textVal = Double.parseDouble(radiusField.getText());
             double radius = Math.pow(textVal,2) * Math.PI;
             areaField.setValue(radius);
          }
        }
    }
}

