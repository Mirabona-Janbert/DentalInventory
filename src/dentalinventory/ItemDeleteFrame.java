/*
 * This class initializes the frame to delete an item.
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

public class ItemDeleteFrame extends JFrame implements ActionListener {
    //Components, constants, and objects.
    private JMenuBar menuBar;
    private JPanel addPanel;
    private JLabel frameTitle, itemComboLabel, alert;
    private JComboBox itemCombo;
    private JButton submit, back;
    private Font TITLE_FONT = new Font("Calibri", Font.BOLD, 50);
    private Font MAIN_FONT = new Font("Calibri", Font.BOLD, 35);
    private JLabel image;
    private final Color BLACK = Color.decode("#50514F");
    private final Color DARK_BLUE = Color.decode("#247BA0");
    private final Color WHITE = Color.decode("#FFFFFF");
    private final Color RED = Color.decode("#F25F5C");
    private final Color LIGHT_BLUE = Color.decode("#70C1B3");
    private InventoryMethods inventoryMethods;

    public ItemDeleteFrame() {
        super("Delete an Item");
        //Setting window.
        this.setBounds(200, 200, 1200, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        //Initializing components.
        this.menuBar = new JMenuBar();
        this.addPanel = new JPanel(new GridBagLayout());
        this.frameTitle = new JLabel(" Delete an Item");
        this.itemComboLabel = new JLabel("Item Name:");
        this.submit = new JButton("Submit");
        this.back = new JButton("Return");
        this.alert = new JLabel("<html>WARNING: Deleting will remove all patient<br> records having used this item.</html>");
        this.image = new JLabel();

        //Initializing combo box.
        this.inventoryMethods = new InventoryMethods();
        this.itemCombo = new JComboBox(inventoryMethods.itemList());
        
        //Set fonts
        this.frameTitle.setFont(TITLE_FONT);
        this.itemComboLabel.setFont(MAIN_FONT);
        this.itemCombo.setFont(MAIN_FONT);
        this.submit.setFont(MAIN_FONT);
        this.back.setFont(MAIN_FONT);
        this.alert.setFont(MAIN_FONT);
        
        //Setting component sizes and properties.
        this.submit.setPreferredSize(new Dimension(200, 50));
        this.back.setPreferredSize(new Dimension(20, 40));
        this.itemCombo.setPreferredSize(new Dimension(400, 60));
        this.back.setContentAreaFilled(false);
        this.back.setBorderPainted(false);
        this.image.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("hygiene.png")).getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT)));

        this.menuBar.setBackground(DARK_BLUE);
        this.addPanel.setBackground(LIGHT_BLUE);
        this.back.setForeground(WHITE);
        this.submit.setBackground(BLACK);
        this.submit.setForeground(WHITE);
        this.itemComboLabel.setForeground(BLACK);
        
        //Adding action listeners.
        this.back.addActionListener(this);
        this.submit.addActionListener(this);

        //Adding components to frame.
        addConstraints(addPanel, frameTitle, 0, 0, 3, 1, GridBagConstraints.LINE_START);
        addConstraints(addPanel, image, 0, 1, 2, 5, GridBagConstraints.CENTER);
        addConstraints(addPanel, itemComboLabel, 2, 1, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, itemCombo, 3, 1, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, alert, 2, 2, 2, 1, GridBagConstraints.CENTER);
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
            //Executing query.
            this.inventoryMethods = new InventoryMethods();
            boolean validity = inventoryMethods.deleteItem(itemCombo.getSelectedItem().toString());
            //Success dependent frame call.
            if (validity == true) {
                Frame[] frames = Frame.getFrames();
                for (int i = 0; i < frames.length; i++) {
                    frames[i].dispose();
                }                ItemDeleteFrame deleteFrame = new ItemDeleteFrame();
                WarningFrame warning = new WarningFrame("Delete Successful!", "Message");
            } else {
                WarningFrame warning = new WarningFrame("Delete Failed.", "Error");
            }
        }
    }

    //Add components to frame.
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
