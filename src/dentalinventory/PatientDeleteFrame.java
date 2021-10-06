/*
 * This class initializes the frame to allow patient to delete a patient entry.
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
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class PatientDeleteFrame extends JFrame implements ActionListener {
    
    //Declaring components, constants, and variables.
    private JMenuBar menuBar;
    private JPanel addPanel;
    private JLabel frameTitle, lastNameLabel, firstNameLabel, dateLabel, itemLabel, inventoryLabel;
    private JComboBox date, lastName, firstName, itemUsed;
    private JButton submit, back, dateSearch, lastSearch, firstSearch;
    private JRadioButton yesButton, noButton;
    private ButtonGroup group;
    private JLabel image;
    private final Color BLACK = Color.decode("#50514F");
    private final Color DARK_BLUE = Color.decode("#247BA0");
    private final Color WHITE = Color.decode("#FFFFFF");
    private final Color RED = Color.decode("#F25F5C");
    private final Color LIGHT_BLUE = Color.decode("#70C1B3");
    private final Font TITLE_FONT = new Font("Calibri", Font.BOLD, 50);
    private final Font MAIN_FONT = new Font("Calibri", Font.BOLD, 40);
    private InventoryMethods inventoryMethods;
    private PatientMethods patientMethods;
    private String[] arrayValues;
    private String dateValue = null, lastNameValue = null, firstNameValue = null, itemUsedValue = null;
    
    public PatientDeleteFrame() {
        super("Delete a Patient Record");
        
        //Initializing components and variables.
        this.image = new JLabel();
        this.inventoryMethods = new InventoryMethods();
        this.patientMethods = new PatientMethods();
        this.back = new JButton("Return");
        this.submit = new JButton("Submit");
        this.itemUsed = new JComboBox();
        this.lastName = new JComboBox();
        this.firstName = new JComboBox();
        this.dateSearch = new JButton("Search for Date");
        this.lastSearch = new JButton("Search for Last Name");
        this.firstSearch = new JButton("Search for First Name");
        this.yesButton = new JRadioButton("Yes");
        this.noButton = new JRadioButton("No");
        this.group = new ButtonGroup();
        this.date = new JComboBox(patientMethods.modifiedTimestamp());
        this.itemLabel = new JLabel("Item Used:");
        this.dateLabel = new JLabel("Date:");
        this.lastNameLabel = new JLabel("Last Name:");
        this.firstNameLabel = new JLabel("First Name:");
        this.inventoryLabel = new JLabel("Restore inventory table?: ");
        this.frameTitle = new JLabel("Delete a Patient \nRecord");
        this.addPanel = new JPanel(new GridBagLayout());
        this.menuBar = new JMenuBar();
        
        //Initializing frame.
        this.setBounds(200, 200, 1200, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        //Setting fonts.
        this.frameTitle.setFont(TITLE_FONT);
        this.dateLabel.setFont(MAIN_FONT);
        this.lastNameLabel.setFont(MAIN_FONT);
        this.firstNameLabel.setFont(MAIN_FONT);
        this.itemLabel.setFont(MAIN_FONT);
        this.lastName.setFont(MAIN_FONT);
        this.firstName.setFont(MAIN_FONT);
        this.submit.setFont(MAIN_FONT);
        this.back.setFont(MAIN_FONT);
        this.itemUsed.setFont(MAIN_FONT);
        this.date.setFont(MAIN_FONT);
        this.dateSearch.setFont(MAIN_FONT);
        this.lastSearch.setFont(MAIN_FONT);
        this.firstSearch.setFont(MAIN_FONT);
        this.yesButton.setFont(MAIN_FONT);
        this.noButton.setFont(MAIN_FONT);
        this.inventoryLabel.setFont(MAIN_FONT);
        
        //Modifying component sizes and styling.
        this.submit.setPreferredSize(new Dimension(200, 50));
        this.back.setPreferredSize(new Dimension(20, 40));
        this.back.setContentAreaFilled(false);
        this.back.setBorderPainted(false);
        this.date.setPreferredSize(new Dimension(400, 60));
        this.lastName.setPreferredSize(new Dimension(400, 60));
        this.firstName.setPreferredSize(new Dimension(400, 60));
        this.itemUsed.setPreferredSize(new Dimension(400, 60));
        this.image.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("hygiene.png")).getImage().getScaledInstance(350, 350, Image.SCALE_DEFAULT)));

        this.menuBar.setBackground(DARK_BLUE);
        this.addPanel.setBackground(LIGHT_BLUE);
        this.back.setForeground(WHITE);
        this.submit.setBackground(BLACK);
        this.dateSearch.setBackground(BLACK);
        this.lastSearch.setBackground(BLACK);
        this.firstSearch.setBackground(BLACK);
        this.yesButton.setBackground(LIGHT_BLUE);
        this.noButton.setBackground(LIGHT_BLUE);
        this.submit.setForeground(WHITE);
        this.dateSearch.setForeground(WHITE);
        this.lastSearch.setForeground(WHITE);
        this.firstSearch.setForeground(WHITE);
        this.lastNameLabel.setForeground(BLACK);
        this.firstNameLabel.setForeground(BLACK);
        this.dateLabel.setForeground(BLACK);
        this.itemLabel.setForeground(BLACK);
        this.inventoryLabel.setForeground(BLACK);
        
        //Adding radio buttons to group and modifying action commands.
        this.group.add(yesButton);
        this.group.add(noButton);
        
        this.yesButton.setActionCommand("Yes");
        this.noButton.setActionCommand("No");
        
        //Setting states of combo boxes and buttons.
        this.itemUsed.setEnabled(false);
        this.firstName.setEnabled(false);
        this.lastName.setEnabled(true);
        this.submit.setEnabled(false);
        this.lastSearch.setEnabled(true);
        this.firstSearch.setEnabled(false);
        this.yesButton.setSelected(true);
        
        //Adding action listeners to buttons.
        this.back.addActionListener(this);
        this.submit.addActionListener(this);
        this.dateSearch.addActionListener(this);
        this.lastSearch.addActionListener(this);
        this.firstSearch.addActionListener(this);
        
        //Adding components to frame.
        addConstraints(addPanel, frameTitle, 0, 0, 3, 1, GridBagConstraints.LINE_START);
        addConstraints(addPanel, image, 0, 1, 2, 5, GridBagConstraints.CENTER);
        addConstraints(addPanel, dateLabel, 2, 1, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, date, 4, 1, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, dateSearch, 6, 1, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, lastNameLabel, 2, 2, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, lastName, 4, 2, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, lastSearch, 6, 2, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, firstNameLabel, 2, 3, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, firstName, 4, 3, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, firstSearch, 6, 3, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, itemLabel, 2, 4, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, itemUsed, 4, 4, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, inventoryLabel, 2, 5, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, yesButton, 4, 5, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, noButton, 5, 5, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, submit, 2, 7, 1, 1, GridBagConstraints.CENTER);
        
        this.menuBar.add(back);
        this.add(addPanel, BorderLayout.CENTER);
        this.add(menuBar, BorderLayout.NORTH);
        this.pack();
        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        Object commandSource = ae.getSource();
        this.arrayValues = null;
        
        //Action-dependent if statements.
        if (commandSource == back) {
            Frame[] frames = Frame.getFrames();
            for (int i = 0; i < frames.length; i++) {
                frames[i].dispose();
            }
            HomeFrame home = new HomeFrame();
        } else if (commandSource == submit) {
            //Getting string from combo box.
            this.itemUsedValue = itemUsed.getSelectedItem().toString();
            this.patientMethods = new PatientMethods();
            String inventory = group.getSelection().getActionCommand();
            boolean inventoryEdit;
            inventoryEdit = inventory.equals("Yes");
            
            //Deleting the entry and printing success/failure frame as a message.
            boolean validity = patientMethods.patientDelete(dateValue, lastNameValue, firstNameValue, itemUsedValue, inventoryEdit);
            
            if (validity == true) {
                Frame[] frames = Frame.getFrames();
                for (int i = 0; i < frames.length; i++) {
                    frames[i].dispose();
                }
                PatientDeleteFrame deleteFrame = new PatientDeleteFrame();
                WarningFrame warning = new WarningFrame("Deletion successful.", "Message");
            } else {
                WarningFrame warning = new WarningFrame("Deletion failed. Try again", "Error");
            }
        } else if (commandSource == dateSearch) {
            //Getting string from combo box.
            this.dateValue = date.getSelectedItem().toString();
            this.patientMethods = new PatientMethods();
            
            //Removing all combo box values, adding new values, and setting state of components.
            this.lastName.removeAllItems();
            this.firstName.removeAllItems();
            this.itemUsed.removeAllItems();
            
            this.arrayValues = patientMethods.patientColumn(dateValue);
            for (String arrayValue : arrayValues) {
                lastName.addItem(arrayValue);
            }
            this.lastName.setEnabled(true);
            this.firstName.setEnabled(false);
            this.itemUsed.setEnabled(false);
            this.submit.setEnabled(false);
            this.lastSearch.setEnabled(true);
            this.firstSearch.setEnabled(false);
            this.lastName.setEditable(false);
            this.firstName.setEditable(false);
            this.revalidate();
            this.repaint();
        } else if (commandSource == lastSearch) {
            //Getting string from combo box.
            this.lastNameValue = lastName.getSelectedItem().toString();
            this.patientMethods = new PatientMethods();
            
            //Removing values from combo boxes, adding values to a combo box, 
            //And setting component states.
            this.firstName.removeAllItems();
            this.itemUsed.removeAllItems();
            this.arrayValues = patientMethods.patientColumn(dateValue, lastNameValue);
            for (String arrayValue : arrayValues) {
                this.firstName.addItem(arrayValue);
            }
            this.firstName.setEnabled(true);
            this.itemUsed.setEnabled(false);
            this.submit.setEnabled(false);
            this.lastSearch.setEnabled(true);
            this.firstSearch.setEnabled(true);
            this.lastName.setEditable(false);
            this.firstName.setEditable(false);
            this.revalidate();
            this.repaint();
        } else if (commandSource == firstSearch) {
            //Getting string from combo box.
            this.firstNameValue = firstName.getSelectedItem().toString();
            this.patientMethods = new PatientMethods();
            
            //Removing items from relevant combo box, adding values to a combo box
            //And setting component states.
            this.itemUsed.removeAllItems();
            this.arrayValues = patientMethods.patientColumn(dateValue, lastNameValue, firstNameValue);
            for (String arrayValue : arrayValues) {
                this.itemUsed.addItem(arrayValue);
            }
            this.itemUsed.setEnabled(true);
            this.submit.setEnabled(true);
            this.lastSearch.setEnabled(true);
            this.firstSearch.setEnabled(true);
            this.lastName.setEditable(false);
            this.firstName.setEditable(false);
            this.revalidate();
            this.repaint();
        }
    }
    
    //Method to add components to frame.
    public void addConstraints(JPanel panel, JComponent component, int x, int y, int width, int height, int align) {
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
