/*
 * This method initializes the modify patient frame.
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
import java.awt.event.ItemEvent;
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

public class PatientModifyFrame extends JFrame implements ActionListener {

    //Declaring constants, components, variable.
    private JMenuBar menuBar;
    private JPanel addPanel;
    private JLabel frameTitle, lastNameLabel, firstNameLabel, dateLabel, itemLabel, quantityLabel, commentsLabel, inventoryLabel;
    private JTextField quantity;
    private JTextArea comments;
    private JComboBox date, lastName, firstName, itemUsed;
    private JButton submit, back, dateSearch, lastSearch, firstSearch, itemSearch;
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
    private DatabaseMethods databaseMethods;
    private String[] arrayValues;
    private String dateValue = null, lastNameValue = null, firstNameValue = null, itemUsedValue = null;

    public PatientModifyFrame() {
        super("Modify a Patient Record");

        //Declaring constants
        this.image = new JLabel();
        this.back = new JButton("Return");
        this.submit = new JButton("Submit");
        this.inventoryMethods = new InventoryMethods();
        this.patientMethods = new PatientMethods();
        this.lastName = new JComboBox();
        this.firstName = new JComboBox();
        this.itemUsed = new JComboBox();
        this.date = new JComboBox(patientMethods.modifiedTimestamp());
        this.comments = new JTextArea(3, 20);
        this.quantity = new JTextField(5);
        this.commentsLabel = new JLabel("Comments:");
        this.quantityLabel = new JLabel("Quantity:");
        this.itemLabel = new JLabel("Item Used:");
        this.dateLabel = new JLabel("Date:");
        this.firstNameLabel = new JLabel("First Name:");
        this.lastNameLabel = new JLabel("Last Name:");
        this.inventoryLabel = new JLabel("Edit Inventory Quantity?:");
        this.frameTitle = new JLabel("Modify a Patient \nRecord");
        this.addPanel = new JPanel(new GridBagLayout());
        this.menuBar = new JMenuBar();
        this.dateSearch = new JButton("Search for Date");
        this.lastSearch = new JButton("Search for Last Name");
        this.firstSearch = new JButton("Search for First Name");
        this.itemSearch = new JButton("Search for Item");

        //Setting up table.
        this.setBounds(200, 200, 1200, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        //Setting fonts
        this.frameTitle.setFont(TITLE_FONT);
        this.dateLabel.setFont(MAIN_FONT);
        this.firstNameLabel.setFont(MAIN_FONT);
        this.lastNameLabel.setFont(MAIN_FONT);
        this.itemLabel.setFont(MAIN_FONT);
        this.itemUsed.setFont(MAIN_FONT);
        this.quantityLabel.setFont(MAIN_FONT);
        this.commentsLabel.setFont(MAIN_FONT);
        this.lastName.setFont(MAIN_FONT);
        this.firstName.setFont(MAIN_FONT);
        this.itemUsed.setFont(MAIN_FONT);
        this.quantity.setFont(MAIN_FONT);
        this.comments.setFont(MAIN_FONT);
        this.submit.setFont(MAIN_FONT);
        this.back.setFont(MAIN_FONT);
        this.date.setFont(MAIN_FONT);
        this.dateSearch.setFont(MAIN_FONT);
        this.lastSearch.setFont(MAIN_FONT);
        this.firstSearch.setFont(MAIN_FONT);
        this.itemSearch.setFont(MAIN_FONT);
        this.inventoryLabel.setFont(MAIN_FONT);

        //Formatting component sizes and styling.
        this.comments.setLineWrap(true);
        this.submit.setPreferredSize(new Dimension(200, 50));
        this.back.setPreferredSize(new Dimension(20, 40));
        this.back.setContentAreaFilled(false);
        this.back.setBorderPainted(false);
        this.image.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("drill.png")).getImage().getScaledInstance(350, 350, Image.SCALE_DEFAULT)));

        //Setting tooltips
        this.lastName.setToolTipText("Enter the last name of the Patient.");
        this.firstName.setToolTipText("Enter the first name of the Patient.");
        this.quantity.setToolTipText("Enter amount of item used on the patient.");
        this.comments.setToolTipText("Enter some comments regarding the procedure done on the patient.");
        this.itemUsed.setToolTipText("Enter the name of the item used on the patient.");
        this.date.setToolTipText("Select the date the patient was treated.");
        
        this.menuBar.setBackground(DARK_BLUE);
        this.addPanel.setBackground(LIGHT_BLUE);
        this.back.setForeground(WHITE);
        this.submit.setBackground(BLACK);
        this.dateSearch.setBackground(BLACK);
        this.lastSearch.setBackground(BLACK);
        this.firstSearch.setBackground(BLACK);
        this.itemSearch.setBackground(BLACK);
        this.submit.setForeground(WHITE);
        this.dateSearch.setForeground(WHITE);
        this.lastSearch.setForeground(WHITE);
        this.firstSearch.setForeground(WHITE);
        this.itemSearch.setForeground(WHITE);
        this.lastNameLabel.setForeground(BLACK);
        this.firstNameLabel.setForeground(BLACK);
        this.dateLabel.setForeground(BLACK);
        this.itemLabel.setForeground(BLACK);
        this.quantityLabel.setForeground(BLACK);
        this.commentsLabel.setForeground(BLACK);
        this.inventoryLabel.setForeground(BLACK);

        //Setting date combo box.
        this.dateValue = date.getSelectedItem().toString();
        this.patientMethods = new PatientMethods();

        //Setting last name values.
        this.arrayValues = patientMethods.patientColumn(dateValue);
        for (String arrayValue : arrayValues) {
            this.lastName.addItem(arrayValue);
        }

        //Setting enable and editable settings.
        this.itemUsed.setEnabled(false);
        this.firstName.setEnabled(false);
        this.lastName.setEnabled(true);
        this.submit.setEnabled(false);
        this.lastSearch.setEnabled(true);
        this.firstSearch.setEnabled(false);
        this.itemSearch.setEnabled(false);
        this.quantity.setEditable(false);
        this.comments.setEditable(false);

        //Adding action listeners.
        this.back.addActionListener(this);
        this.submit.addActionListener(this);
        this.dateSearch.addActionListener(this);
        this.lastSearch.addActionListener(this);
        this.firstSearch.addActionListener(this);
        this.itemSearch.addActionListener(this);

        //Adding item listeners for combo boxes.
        this.lastName.addItemListener((ItemEvent ie) -> {
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                this.lastNameValue = lastName.getSelectedItem().toString();
            }
        });

        this.firstName.addItemListener((ItemEvent ie) -> {
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                this.firstNameValue = firstName.getSelectedItem().toString();
            }
        });

        //Adding components to frame.
        addConstraints(addPanel, frameTitle, 0, 0, 3, 1, GridBagConstraints.LINE_START);
        addConstraints(addPanel, image, 0, 1, 2, 5, GridBagConstraints.CENTER);
        addConstraints(addPanel, dateLabel, 2, 1, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, date, 3, 1, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, dateSearch, 4, 1, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, lastNameLabel, 2, 2, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, lastName, 3, 2, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, lastSearch, 4, 2, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, firstNameLabel, 2, 3, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, firstName, 3, 3, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, firstSearch, 4, 3, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, itemLabel, 2, 4, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, itemUsed, 3, 4, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, itemSearch, 4, 4, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, quantityLabel, 2, 5, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, quantity, 3, 5, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, commentsLabel, 2, 6, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, comments, 3, 6, 1, 3, GridBagConstraints.CENTER);
        addConstraints(addPanel, submit, 2, 7, 1, 1, GridBagConstraints.CENTER);

        this.menuBar.add(back);
        this.add(addPanel, BorderLayout.CENTER);
        this.setJMenuBar(menuBar);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object commandSource = ae.getSource();
        this.arrayValues = null;
        String dbQuery = "SELECT quantity FROM InventoryStats WHERE item=?";

        //Button dependent actions.
        if (commandSource == back) {
            Frame[] frames = Frame.getFrames();
            for (int i = 0; i < frames.length; i++) {
                frames[i].dispose();
            }
            HomeFrame home = new HomeFrame();
        } else if (commandSource == submit) {
            try {
                //Getting values from combo boxes.
                String selectedDate = date.getSelectedItem().toString();
                String lastNameEntry = (String) lastName.getEditor().getItem();
                String firstNameEntry = (String) firstName.getEditor().getItem();
                String selectedItem = itemUsed.getSelectedItem().toString();
                int quantityEntry = Integer.parseInt(quantity.getText());
                String commentsEntry = comments.getText();
                
                if (lastNameEntry.trim().isEmpty() || firstNameEntry.trim().isEmpty() || selectedItem.trim().isEmpty()) {
                    throw new EmptyException();
                }
                
                if (quantityEntry < 0) {
                    throw new RangeException();
                }
                this.inventoryMethods = new InventoryMethods();
                this.databaseMethods = new DatabaseMethods("DentalInventory");
                int currentQuantity = Integer.parseInt(databaseMethods.valueSelect(dbQuery, selectedItem, "quantity"));

                if (quantityEntry > currentQuantity) {
                    throw new RangeException();
                }

                //Accessing modify method and displaying success frame based upon result.
                this.patientMethods = new PatientMethods();
                boolean success = patientMethods.patientModify(selectedDate, lastNameValue, lastNameEntry, firstNameValue, firstNameEntry, selectedItem, quantityEntry, commentsEntry);
                if (success == true) {
                    Frame[] frames = Frame.getFrames();
                    for (int i = 0; i < frames.length; i++) {
                        frames[i].dispose();
                    }
                    PatientModifyFrame modifyFrame = new PatientModifyFrame();
                    WarningFrame message = new WarningFrame("Entry was successful.", "Message");
                } else {
                    WarningFrame message = new WarningFrame("Entry failed. Recheck your values.", "Error");
                }
            } catch (RangeException e) {
                WarningFrame message = new WarningFrame("Entry failed. Values are out of bounds.", "Error");
            } catch (NumberFormatException e) {
                WarningFrame message = new WarningFrame("Entry failed. Ensure your numbers are apropriate data types.", "Error");   
            } catch (EmptyException ex){
                WarningFrame warning = new WarningFrame("Some entries are empty. Make sure to enter values.", "Error");
            }
        } else if (commandSource == dateSearch) {
            //Get combo box value.
            this.dateValue = date.getSelectedItem().toString();
            this.patientMethods = new PatientMethods();

            //Remove combo box items.
            this.lastName.removeAllItems();
            this.firstName.removeAllItems();
            this.itemUsed.removeAllItems();
            this.quantity.setText("");
            this.comments.setText("");

            //Add combo box items.
            this.arrayValues = patientMethods.patientColumn(dateValue);
            for (String arrayValue : arrayValues) {
                this.lastName.addItem(arrayValue);
            }

            //Enabling/disabling comboboxes and textfields.
            this.lastName.setEnabled(true);
            this.firstName.setEnabled(false);
            this.itemUsed.setEnabled(false);
            this.submit.setEnabled(false);
            this.lastSearch.setEnabled(true);
            this.firstSearch.setEnabled(false);
            this.itemSearch.setEnabled(false);

            this.lastName.setEditable(false);
            this.firstName.setEditable(false);
            this.quantity.setEditable(false);
            this.comments.setEditable(false);
            this.revalidate();
            this.repaint();
        } else if (commandSource == lastSearch) {
            this.lastNameValue = lastName.getSelectedItem().toString();
            this.patientMethods = new PatientMethods();

            this.firstName.removeAllItems();
            this.itemUsed.removeAllItems();
            this.quantity.setText("");
            this.comments.setText("");

            this.arrayValues = patientMethods.patientColumn(dateValue, lastNameValue);
            for (String arrayValue : arrayValues) {
                firstName.addItem(arrayValue);
            }
            this.firstName.setEnabled(true);
            this.itemUsed.setEnabled(false);
            this.submit.setEnabled(false);
            this.lastSearch.setEnabled(true);
            this.firstSearch.setEnabled(true);
            this.itemSearch.setEnabled(false);

            this.lastName.setEditable(false);
            this.firstName.setEditable(false);
            this.quantity.setEditable(false);
            this.comments.setEditable(false);
            this.revalidate();
            this.repaint();
        } else if (commandSource == firstSearch) {
            //Getting combobox value.
            this.firstNameValue = firstName.getSelectedItem().toString();
            this.patientMethods = new PatientMethods();

            //Removing values from combobox and adding new values.
            this.itemUsed.removeAllItems();
            this.quantity.setText("");
            this.comments.setText("");
            this.arrayValues = patientMethods.patientColumn(dateValue, lastNameValue, firstNameValue);
            for (String arrayValue : arrayValues) {
                this.itemUsed.addItem(arrayValue);
            }

            //Enabling/disabling comboboxes and textfields.
            this.itemUsed.setEnabled(true);
            this.submit.setEnabled(false);
            this.lastSearch.setEnabled(true);
            this.firstSearch.setEnabled(true);
            this.itemSearch.setEnabled(true);

            this.lastName.setEditable(false);
            this.firstName.setEditable(false);
            this.quantity.setEditable(false);
            this.comments.setEditable(false);
            this.revalidate();
            this.repaint();
        } else if (commandSource == itemSearch) {
            //Getting combobox value.
            this.itemUsedValue = itemUsed.getSelectedItem().toString();
            this.patientMethods = new PatientMethods();

            this.quantity.setText("");
            this.comments.setText("");
            //Setting textfields values and edtability.
            this.arrayValues = patientMethods.patientRow(dateValue, lastNameValue, firstNameValue, itemUsedValue);

            this.quantity.setText(arrayValues[4]);
            this.comments.setText(arrayValues[5]);
            this.lastName.setEditable(true);
            this.firstName.setEditable(true);
            this.quantity.setEditable(true);
            this.comments.setEditable(true);
            this.itemUsed.setEnabled(true);

            this.submit.setEnabled(true);
            this.lastSearch.setEnabled(true);
            this.firstSearch.setEnabled(true);
            this.itemSearch.setEnabled(true);

            this.revalidate();
            this.repaint();
        }
    }

    //Method to add components frame.
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
