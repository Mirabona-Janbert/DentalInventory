/*
 * This class initializes the patient adding frame.
 */
package dentalinventory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PatientAddFrame extends JFrame implements ActionListener {

    //Declaring components and variables.
    private JMenuBar menuBar;
    private JPanel addPanel;
    private JLabel frameTitle, firstNameLabel, lastNameLabel, itemLabel, quantityLabel, commentsLabel;
    private JTextField lastName, firstName, quantity;
    private JTextArea comments;
    private JComboBox itemUsed;
    private JButton submit, back;
    private JLabel image = new JLabel();
    private final Font TITLE_FONT = new Font("Calibri", Font.BOLD, 50);
    private final Font MAIN_FONT = new Font("Calibri", Font.BOLD, 40);
    private final Color BLACK = Color.decode("#50514F");
    private final Color DARK_BLUE = Color.decode("#247BA0");
    private final Color WHITE = Color.decode("#FFFFFF");
    private final Color RED = Color.decode("#F25F5C");
    private final Color LIGHT_BLUE = Color.decode("#70C1B3");

    private DatabaseMethods databaseMethods;
    private InventoryMethods inventoryMethods;
    private PatientMethods patientMethods;

    public PatientAddFrame() {
        super("Add a Patient Record");

        //Initializing components.
        this.back = new JButton("Return");
        this.submit = new JButton("Submit");
        this.inventoryMethods = new InventoryMethods();
        this.itemUsed = new JComboBox(inventoryMethods.itemList());
        this.comments = new JTextArea(3, 20);
        this.quantity = new JTextField(5);
        this.lastName = new JTextField(20);
        this.firstName = new JTextField(20);
        this.commentsLabel = new JLabel("Comments:");
        this.quantityLabel = new JLabel("Quantity:");
        this.itemLabel = new JLabel("Item Used:");
        this.firstNameLabel = new JLabel("First Name:");
        this.lastNameLabel = new JLabel("Last Name:");
        this.menuBar = new JMenuBar();
        this.addPanel = new JPanel(new GridBagLayout());
        this.frameTitle = new JLabel("Add a Patient \nRecord");
        this.setBounds(200, 200, 1200, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        //Setting fonts
        this.frameTitle.setFont(TITLE_FONT);
        this.itemLabel.setFont(MAIN_FONT);
        this.quantityLabel.setFont(MAIN_FONT);
        this.commentsLabel.setFont(MAIN_FONT);
        this.firstName.setFont(MAIN_FONT);
        this.firstNameLabel.setFont(MAIN_FONT);
        this.lastName.setFont(MAIN_FONT);
        this.lastNameLabel.setFont(MAIN_FONT);
        this.quantity.setFont(MAIN_FONT);
        this.comments.setFont(MAIN_FONT);
        this.submit.setFont(MAIN_FONT);
        this.back.setFont(MAIN_FONT);
        this.itemUsed.setFont(MAIN_FONT);

        //Formatting component dimesions and styling.
        this.comments.setLineWrap(true);
        this.submit.setPreferredSize(new Dimension(200, 50));
        this.back.setPreferredSize(new Dimension(20, 40));
        this.back.setContentAreaFilled(false);
        this.back.setBorderPainted(false);
        this.image.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("tooth.png")).getImage().getScaledInstance(350, 350, Image.SCALE_DEFAULT)));

        //Setting tooltips
        this.lastName.setToolTipText("Enter the last name of the Patient.");
        this.firstName.setToolTipText("Enter the first name of the Patient.");
        this.quantity.setToolTipText("Enter amount of item used on the patient.");
        this.comments.setToolTipText("Enter some comments regarding the procedure done on the patient.");
        this.itemUsed.setToolTipText("Enter the name of the item used on the patient.");
        
        this.submit.setBackground(BLACK);
        this.submit.setForeground(WHITE);
        this.addPanel.setBackground(LIGHT_BLUE);
        this.menuBar.setBackground(DARK_BLUE);
        this.itemLabel.setForeground(BLACK);
        this.quantityLabel.setForeground(BLACK);
        this.commentsLabel.setForeground(BLACK);
        this.firstNameLabel.setForeground(BLACK);
        this.lastNameLabel.setForeground(BLACK);
        this.back.setForeground(WHITE);

        //Adding actionListeners
        this.back.addActionListener(this);
        this.submit.addActionListener(this);

        //Adding components to frame.
        addConstraints(addPanel, frameTitle, 0, 0, 3, 1, GridBagConstraints.LINE_START);
        addConstraints(addPanel, image, 0, 1, 2, 5, GridBagConstraints.CENTER);
        addConstraints(addPanel, firstNameLabel, 2, 1, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, firstName, 3, 1, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, lastNameLabel, 2, 2, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, lastName, 3, 2, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, itemLabel, 2, 3, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, itemUsed, 3, 3, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, quantityLabel, 2, 4, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, quantity, 3, 4, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, commentsLabel, 2, 5, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, comments, 3, 5, 1, 3, GridBagConstraints.CENTER);
        addConstraints(addPanel, submit, 2, 6, 1, 1, GridBagConstraints.CENTER);

        this.menuBar.add(back);
        this.add(addPanel, BorderLayout.CENTER);
        this.setJMenuBar(menuBar);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object commandSource = ae.getSource();
        String dbQuery = "SELECT quantity FROM InventoryStats WHERE item=?";

        //Button dependent actions.
        if (commandSource == back) {
            Frame[] frames = Frame.getFrames();
            for (int i = 0; i < frames.length; i++) {
                frames[i].dispose();
            }
            HomeFrame home = new HomeFrame();
        } else if (commandSource == submit) {
            boolean success = true;
            try {
                //Getting text field values.
                String item = itemUsed.getSelectedItem().toString();
                String firstNameValue = firstName.getText();
                String lastNameValue = lastName.getText();
                String quantityUsed = quantity.getText();
                String doctorComment = comments.getText();

                if(item.trim().isEmpty() || firstNameValue.trim().isEmpty() || lastNameValue.trim().isEmpty() || quantityUsed.trim().isEmpty()){
                    throw new EmptyException();
                }
                
                if (Integer.parseInt(quantityUsed) < 0) {
                    throw new RangeException();
                }
                this.patientMethods = new PatientMethods();

                this.databaseMethods = new DatabaseMethods("DentalInventory");
                int currentQuantity = Integer.parseInt(databaseMethods.valueSelect(dbQuery, item, "quantity"));

                if (Integer.parseInt(quantityUsed) > currentQuantity) {
                    throw new RangeException();
                }

                success = patientMethods.addPatient(item, firstNameValue, lastNameValue, quantityUsed, doctorComment);

                //Boolean dependent frame actions.
                if (success == true) {
                    Frame[] frames = Frame.getFrames();
                    for (int i = 0; i < frames.length; i++) {
                        frames[i].dispose();
                    }                    PatientAddFrame addFrame = new PatientAddFrame();
                    WarningFrame warning = new WarningFrame("Patient entered successfully.", "Message");
                } else {
                    WarningFrame warning = new WarningFrame("Patient entry unsuccessful. Please retry.", "Error");
                }
            } catch (NumberFormatException e) {
                WarningFrame warning = new WarningFrame("The values entered were not all appropriate data types.", "Error!");
            } catch (RangeException re) {
                WarningFrame warning = new WarningFrame("The values were out of the range of possible entry values.", "Error");
            } catch (EmptyException ex){
                WarningFrame warning = new WarningFrame("Some entries are empty. Make sure to enter values.", "Error");
            }
        }
    }

    //GridBag method to add components frame.
    private void addConstraints(JPanel panel, JComponent component, int x, int y, int width, int height, int align) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = width;
        c.gridheight = height;
        c.weightx = 100;
        c.weighty = 75.0;
        c.insets = new Insets(0, 0, 10, 10);
        c.anchor = align;
        c.fill = GridBagConstraints.BOTH;
        panel.add(component, c);
    }
}
