/*
 * This class initializes the item modifying frame. 
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
import java.awt.event.ItemListener;
import java.text.NumberFormat;
import javax.swing.BorderFactory;
import javax.swing.Box;
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
import javax.swing.JTextField;

public class ItemModifyFrame extends JFrame implements ActionListener, ItemListener {

    //Declaring components, variables, and constants.
    private JMenuBar menuBar;
    private JPanel addPanel;
    private JLabel frameTitle, itemComboLabel, itemLabel, quantityLabel, costLabel, lowLabel;
    private JComboBox itemCombo;
    private JTextField itemField, quantityField, lowField;
    private JTextField costField;
    private NumberFormat costFormat;
    private JRadioButton userPrice, averagePrice;
    private JRadioButton yesRestock, noRestock;
    private ButtonGroup priceGroup;
    private ButtonGroup restocksGroup;
    private Box restockBox, priceBox;
    private JButton submit, back;
    private JLabel image;
    private final Color BLACK = Color.decode("#50514F");
    private final Color DARK_BLUE = Color.decode("#247BA0");
    private final Color WHITE = Color.decode("#FFFFFF");
    private final Color RED = Color.decode("#F25F5C");
    private final Color LIGHT_BLUE = Color.decode("#70C1B3");
    private final Font TITLE_FONT = new Font("Calibri", Font.BOLD, 50);
    private final Font MAIN_FONT = new Font("Calibri", Font.BOLD, 35);
    private final Font MINOR_FONT = new Font("Calibri", Font.BOLD, 30);
    private InventoryMethods inventoryMethods;
    private QuantityWarning warningTest;
    private String stockCheck;
    private String[] firstItemValues;

    /**
     *
     */
    public ItemModifyFrame() {
        super("Modify an Item");
        //Setting frame.
        this.setBounds(200, 200, 1200, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        //Initializing components
        this.image = new JLabel();
        this.menuBar = new JMenuBar();
        this.addPanel = new JPanel(new GridBagLayout());
        this.frameTitle = new JLabel(" Modify an Item");
        this.itemComboLabel = new JLabel("Item Name:");
        this.itemLabel = new JLabel("New Item Name:");
        this.quantityLabel = new JLabel("Current Quantity:");
        this.costLabel = new JLabel("Approximate Cost:");
        this.lowLabel = new JLabel("Predicted Low Quantity:");
        this.itemField = new JTextField(30);
        this.quantityField = new JTextField(30);
        this.costField = new JTextField(30);
        this.costFormat = NumberFormat.getCurrencyInstance();
        this.lowField = new JTextField(30);
        this.userPrice = new JRadioButton("Use User-Defined Price");
        this.averagePrice = new JRadioButton("Use Averaged Price");
        this.yesRestock = new JRadioButton("This is a restock.");
        this.noRestock = new JRadioButton("This is NOT a restock.");
        this.priceGroup = new ButtonGroup();
        this.restocksGroup = new ButtonGroup();
        this.submit = new JButton("Submit");
        this.back = new JButton("Return");

        //Add items to item combo box.
        this.inventoryMethods = new InventoryMethods();
        this.itemCombo = new JComboBox(inventoryMethods.itemList());

        String firstItem = itemCombo.getSelectedItem().toString();
        this.firstItemValues = inventoryMethods.statsRow(firstItem);

        //Setting action commands and texts depending upon row values.
        this.averagePrice.setActionCommand("Average Price");
        this.userPrice.setActionCommand("User Price");
        this.yesRestock.setActionCommand("Yes");
        this.noRestock.setActionCommand("No");
        this.itemField.setText(firstItemValues[0]);
        this.quantityField.setText(firstItemValues[1]);
        this.costField.setText(firstItemValues[3]);
        this.lowField.setText(firstItemValues[5]);

        //Adding buttons to group.
        this.priceGroup.add(averagePrice);
        this.priceGroup.add(userPrice);

        //Setting selection dependent on initially selected radio button.
        if ("Average Price".equals(firstItemValues[7])) {
            this.priceGroup.clearSelection();
            this.averagePrice.setSelected(true);
        } else if ("User Price".equals(firstItemValues[7])) {
            this.priceGroup.clearSelection();
            this.userPrice.setSelected(true);
        }

        //Setting fonts.
        this.frameTitle.setFont(TITLE_FONT);
        this.itemComboLabel.setFont(MAIN_FONT);
        this.itemLabel.setFont(MAIN_FONT);
        this.quantityLabel.setFont(MAIN_FONT);
        this.costLabel.setFont(MAIN_FONT);
        this.lowLabel.setFont(MAIN_FONT);
        this.itemCombo.setFont(MAIN_FONT);
        this.itemField.setFont(MAIN_FONT);
        this.quantityField.setFont(MAIN_FONT);
        this.costField.setFont(MAIN_FONT);
        this.lowField.setFont(MAIN_FONT);
        this.userPrice.setFont(MAIN_FONT);
        this.averagePrice.setFont(MAIN_FONT);
        this.yesRestock.setFont(MAIN_FONT);
        this.noRestock.setFont(MAIN_FONT);
        this.submit.setFont(MAIN_FONT);
        this.back.setFont(MAIN_FONT);

        //Setting component properties
        this.submit.setPreferredSize(new Dimension(200, 50));
        this.back.setPreferredSize(new Dimension(20, 40));
        this.back.setContentAreaFilled(false);
        this.back.setBorderPainted(false);
        this.noRestock.setSelected(true);
        this.image.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("drill.png")).getImage().getScaledInstance(350, 350, Image.SCALE_DEFAULT)));

        //Setting tooltips
        this.itemField.setToolTipText("Enter the name of the Item.");
        this.quantityField.setToolTipText("Enter the current quantity of the Item");
        this.costField.setToolTipText("Enter the cost per item for the item.");
        this.lowField.setToolTipText("Approximate a low quantity for the item.");

        this.menuBar.setBackground(DARK_BLUE);
        this.addPanel.setBackground(LIGHT_BLUE);
        this.back.setForeground(WHITE);
        this.submit.setBackground(BLACK);
        this.submit.setForeground(WHITE);
        this.itemComboLabel.setForeground(BLACK);
        this.itemLabel.setForeground(BLACK);
        this.quantityLabel.setForeground(BLACK);
        this.costLabel.setForeground(BLACK);
        this.lowLabel.setForeground(BLACK);
        this.itemLabel.setForeground(BLACK);
        this.userPrice.setBackground(LIGHT_BLUE);
        this.averagePrice.setBackground(LIGHT_BLUE);
        this.yesRestock.setBackground(LIGHT_BLUE);
        this.noRestock.setBackground(LIGHT_BLUE);

        //Adding action listeners.
        this.back.addActionListener(this);
        this.submit.addActionListener(this);

        //Creating box for radio buttons, adding buttons and setting border.
        this.priceBox = Box.createVerticalBox();
        this.restockBox = Box.createVerticalBox();

        this.priceBox.add(averagePrice);
        this.priceBox.add(userPrice);
        this.priceBox.setBorder(BorderFactory.createTitledBorder("Price Calculation Type"));
        ((javax.swing.border.TitledBorder) this.priceBox.getBorder()).setTitleFont(MINOR_FONT);

        this.restockBox.add(yesRestock);
        this.restockBox.add(noRestock);
        this.restockBox.setBorder(BorderFactory.createTitledBorder("Is this a restock?"));
        ((javax.swing.border.TitledBorder) this.restockBox.getBorder()).setTitleFont(MINOR_FONT);
        this.restocksGroup.add(yesRestock);
        this.restocksGroup.add(noRestock);
            
        //Setting components depending on item.
        this.itemCombo.addItemListener((ItemEvent ie) -> {
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                //Getting selected item.
                String item = itemCombo.getSelectedItem().toString();
                this.inventoryMethods = new InventoryMethods();

                //Getting row from data.
                String[] itemValues = inventoryMethods.statsRow(item);

                //Setting text.
                this.itemField.setText(itemValues[0]);
                this.quantityField.setText(itemValues[1]);
                this.costField.setText(itemValues[3]);
                this.lowField.setText(itemValues[5]);

                //Setting radio button.
                if ("Average Price".equals(itemValues[7])) {
                    this.priceGroup.clearSelection();
                    this.averagePrice.setSelected(true);
                } else if ("User Price".equals(itemValues[7])) {
                    this.priceGroup.clearSelection();
                    this.userPrice.setSelected(true);
                }

                revalidate();
                repaint();
            }
        });
        //Adding components to frame.
        addConstraints(addPanel, frameTitle, 0, 0, 3, 1, GridBagConstraints.LINE_START);
        addConstraints(addPanel, image, 0, 1, 2, 7, GridBagConstraints.CENTER);
        addConstraints(addPanel, itemComboLabel, 2, 1, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, itemCombo, 3, 1, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, itemLabel, 2, 2, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, itemField, 3, 2, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, quantityLabel, 2, 3, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, quantityField, 3, 3, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, costLabel, 2, 4, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, costField, 3, 4, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, lowLabel, 2, 5, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, lowField, 3, 5, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, priceBox, 2, 6, 1, 2, GridBagConstraints.CENTER);
        addConstraints(addPanel, restockBox, 3, 6, 1, 2, GridBagConstraints.CENTER);
        addConstraints(addPanel, submit, 2, 8, 1, 1, GridBagConstraints.CENTER);

        this.menuBar.add(back);
        this.add(addPanel, BorderLayout.CENTER);
        this.setJMenuBar(menuBar);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object commandSource = ae.getSource();

        //Button dependent actions.
        if (commandSource == back) {
            Frame[] frames = Frame.getFrames();
            for (int i = 0; i < frames.length; i++) {
                frames[i].dispose();
            }
            HomeFrame home = new HomeFrame();
        }

        if (commandSource == submit) {
            boolean restock;
            boolean success;
            try {
                //Getting entered values.
                String originalName = itemCombo.getSelectedItem().toString();
                String item = itemField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                double unitCost = Math.round(Double.parseDouble(costField.getText()) * 100d) / 100d;
                int userLow = Integer.parseInt(lowField.getText());
                String selected = priceGroup.getSelection().getActionCommand();
                String restockValue = restocksGroup.getSelection().getActionCommand();
                restock = "Yes".equals(restockValue);

                if (unitCost < 0 || quantity < 0 || userLow < 0) {
                    throw new RangeException();
                }

                if (item.trim().isEmpty()) {
                    throw new EmptyException();
                }

                //Updating row.
                this.inventoryMethods = new InventoryMethods();
                success = inventoryMethods.modifyItem(originalName, item, quantity, unitCost, userLow, selected, restock);

                //Boolean dependent warning frame activation.
                if (success == true) {
                    this.warningTest = new QuantityWarning();
                    this.stockCheck = warningTest.quantityChecker();
                    Frame[] frames = Frame.getFrames();
                    for (Frame frame : frames) {
                        frame.dispose();
                    }
                    ItemModifyFrame modifyFrame = new ItemModifyFrame();
                    WarningFrame warning = new WarningFrame("Entry was successful, " + stockCheck, "Message");
                    warning.setBounds(200, 200, 800, 600);
                } else {
                    WarningFrame warning = new WarningFrame("The entry failed. Please recheck your entered values.", "Error!");
                    this.itemField.setText(firstItemValues[0]);
                    this.quantityField.setText(firstItemValues[1]);
                    this.costField.setText(firstItemValues[3]);
                    this.lowField.setText(firstItemValues[5]);

                    //Setting selection dependent on initially selected radio button.
                    if ("Average Price".equals(firstItemValues[7])) {
                        this.priceGroup.clearSelection();
                        this.averagePrice.setSelected(true);
                    } else if ("User Price".equals(firstItemValues[7])) {
                        this.priceGroup.clearSelection();
                        this.userPrice.setSelected(true);
                    }
                }
            } catch (NumberFormatException e) {
                WarningFrame warning = new WarningFrame("The values entered were not all appropriate data types. Make sure quantity and user-defined low are positive integers.", "Error");
            } catch (RangeException re) {
                WarningFrame warning = new WarningFrame("The values were out of the range of possible entry values.", "Error");
            } catch (EmptyException ex) {
                WarningFrame warning = new WarningFrame("Some entries are empty. Make sure to enter values.", "Error");
            }
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

    @Override
    public void itemStateChanged(ItemEvent ie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
