/*
 * This class initializes frame that allows users to add items to the tables.
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
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class ItemAddFrame extends JFrame implements ActionListener {

    //Declaring components, constants, and objects.
    private JMenuBar menuBar;
    private JPanel addPanel;
    private JLabel frameTitle, itemLabel, quantityLabel, costLabel, lowLabel;
    private JTextField itemField, currentField, costField, lowField;
    private JButton submit, back;
    private JRadioButton userPrice, averagePrice;
    private ButtonGroup group;
    private JLabel image;
    private Font TITLE_FONT = new Font("Calibri", Font.BOLD, 50);
    private Font MAIN_FONT = new Font("Calibri", Font.BOLD, 35);
    private InventoryMethods inventoryMethods;
    private final Color BLACK = Color.decode("#50514F");
    private final Color DARK_BLUE = Color.decode("#247BA0");
    private final Color WHITE = Color.decode("#FFFFFF");
    private final Color RED = Color.decode("#F25F5C");
    private final Color LIGHT_BLUE = Color.decode("#70C1B3");

    /**
     *
     */
    public ItemAddFrame() {
        super("Add an Item");

        //Initializing components
        this.group = new ButtonGroup();
        this.averagePrice = new JRadioButton("Use Averaged Price");
        this.userPrice = new JRadioButton("Use User-Defined Price");
        this.back = new JButton("Return");
        this.submit = new JButton("Submit");
        this.lowField = new JTextField(30);
        this.costField = new JTextField(30);
        this.currentField = new JTextField(30);
        this.itemField = new JTextField(30);
        this.lowLabel = new JLabel("Predicted Low Quantity:");
        this.costLabel = new JLabel("Approximate Cost:");
        this.quantityLabel = new JLabel("Current Quantity:");
        this.itemLabel = new JLabel("Item:");
        this.frameTitle = new JLabel(" Add an Item");
        this.addPanel = new JPanel(new GridBagLayout());
        this.menuBar = new JMenuBar();
        this.image = new JLabel();

        //Setting frame bounds.
        this.setBounds(200, 200, 1200, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        //Setting fonts.
        this.frameTitle.setFont(TITLE_FONT);
        this.itemLabel.setFont(MAIN_FONT);
        this.quantityLabel.setFont(MAIN_FONT);
        this.costLabel.setFont(MAIN_FONT);
        this.lowLabel.setFont(MAIN_FONT);
        this.itemField.setFont(MAIN_FONT);
        this.currentField.setFont(MAIN_FONT);
        this.costField.setFont(MAIN_FONT);
        this.lowField.setFont(MAIN_FONT);
        this.back.setFont(MAIN_FONT);
        this.submit.setFont(MAIN_FONT);
        this.userPrice.setFont(MAIN_FONT);
        this.averagePrice.setFont(MAIN_FONT);

        //Setting component size and styling.
        this.submit.setPreferredSize(new Dimension(200, 50));
        this.back.setPreferredSize(new Dimension(20, 40));
        this.back.setContentAreaFilled(false);
        this.back.setBorderPainted(false);
        this.image.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("tooth.png")).getImage().getScaledInstance(350, 350, Image.SCALE_DEFAULT)));

        this.menuBar.setBackground(DARK_BLUE);
        this.addPanel.setBackground(LIGHT_BLUE);
        this.back.setForeground(WHITE);
        this.submit.setBackground(BLACK);
        this.submit.setForeground(WHITE);
        this.itemLabel.setForeground(BLACK);
        this.quantityLabel.setForeground(BLACK);
        this.costLabel.setForeground(BLACK);
        this.lowLabel.setForeground(BLACK);
        this.itemLabel.setForeground(BLACK);
        this.userPrice.setBackground(LIGHT_BLUE);
        this.averagePrice.setBackground(LIGHT_BLUE);
        this.userPrice.setBackground(LIGHT_BLUE);
        this.averagePrice.setBackground(LIGHT_BLUE);

        //Setting tooltips.
        this.itemField.setToolTipText("Enter the name of the Item.");
        this.currentField.setToolTipText("Enter the current quantity of the Item");
        this.costField.setToolTipText("Enter the cost per item for the item.");
        this.lowField.setToolTipText("Approximate a low quantity for the item.");
        
        //Setting action commands and action listeners, and adding to the group.
        this.averagePrice.setActionCommand("Average Price");
        this.userPrice.setActionCommand("User Price");

        this.back.addActionListener(this);
        this.submit.addActionListener(this);

        this.userPrice.setSelected(true);
        this.group.add(averagePrice);
        this.group.add(userPrice);

        //Adding components to frame.
        addConstraints(addPanel, frameTitle, 0, 0, 3, 1, GridBagConstraints.LINE_START);
        addConstraints(addPanel, image, 0, 1, 2, 5, GridBagConstraints.CENTER);
        addConstraints(addPanel, itemLabel, 2, 1, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, itemField, 3, 1, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, quantityLabel, 2, 2, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, currentField, 3, 2, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, costLabel, 2, 3, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, costField, 3, 3, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, lowLabel, 2, 4, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, lowField, 3, 4, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, userPrice, 2, 5, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, averagePrice, 3, 5, 1, 1, GridBagConstraints.CENTER);
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

        //Button dependent actions.
        if (commandSource == back) {
            Frame[] frames = Frame.getFrames();
            for (int i = 0; i < frames.length; i++) {
                frames[i].dispose();
            }
            HomeFrame home = new HomeFrame();
        }

        if (commandSource == submit) {
            boolean success = true;
            try {
                //Get entered values.
                String item = itemField.getText();
                int quantity = Integer.parseInt(currentField.getText());
                double unitCost = Math.round(Double.parseDouble(costField.getText()) * 100d) / 100d;
                int userLow = Integer.parseInt(lowField.getText());
                String selected = group.getSelection().getActionCommand();

                if (item.trim().isEmpty()) {
                    throw new EmptyException();
                }

//                if(costField.getText().length() > 4){
//                    throw new LengthException();
//                }
                if (unitCost < 0 || quantity < 0 || userLow < 0) {
                    throw new RangeException();
                }

                //Executing add query.
                inventoryMethods = new InventoryMethods();
                success = inventoryMethods.addItem(item, quantity, unitCost, userLow, selected);

                //Success-dependent frame call.
                if (success == true) {
                    Frame[] frames = Frame.getFrames();
                    for (int i = 0; i < frames.length; i++) {
                        frames[i].dispose();
                    }
                    ItemAddFrame frame = new ItemAddFrame();
                    WarningFrame warning = new WarningFrame("Entry Successful!", "Message");
                } else {
                    WarningFrame warning = new WarningFrame("Entry Failed. Please ensure that your entry values are correct.", "Error");
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

    //Method add components to frame.
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
