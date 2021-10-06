/*
 * This class initializes the item specific statistic frame.
 */
package dentalinventory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class ItemInfoFrame extends JFrame implements ActionListener {
    //Components, constants, variables, and objects.
    private JMenuBar menuBar;
    private JPanel addPanel;
    private JLabel frameTitle, quantityLabel, costLabel, percentLabel, rateLabel, linkLabel;
    private JLabel quantity, restockCost, percentRemaining, usageRate;
    private JButton amazonLink, henryScheinLink, threemLink;
    private JButton back;
    private JLabel image;
    private final Font TITLE_FONT = new Font("Calibri", Font.BOLD, 50);
    private final Font MAIN_FONT = new Font("Calibri", Font.BOLD, 35);
    private final Color BLACK = Color.decode("#50514F");
    private final Color DARK_BLUE = Color.decode("#247BA0");
    private final Color WHITE = Color.decode("#FFFFFF");
    private final Color RED = Color.decode("#F25F5C");
    private final Color LIGHT_BLUE = Color.decode("#70C1B3");
    
    private String[] data;
    private InventoryMethods inventoryMethods;

    public ItemInfoFrame(String item) {
        super(item + " Information");

        //Initializing components.
        inventoryMethods = new InventoryMethods();
        data = inventoryMethods.itemStats(item);
        frameTitle = new JLabel(item + " Information");
        back = new JButton("Return");
        linkLabel = new JLabel("Links to Purchase:");
        menuBar = new JMenuBar();
        addPanel = new JPanel(new GridBagLayout());
        quantityLabel = new JLabel("Current Quantity:");
        costLabel = new JLabel("Total Restock Cost:");
        percentLabel = new JLabel("Percent Remaining:");
        rateLabel = new JLabel("Rate of Usage:");
        quantity = new JLabel(data[0]);
        restockCost = new JLabel(data[1]);
        percentRemaining = new JLabel(data[2]);
        usageRate = new JLabel(data[3]);
        amazonLink = new JButton("Amazon Link");
        henryScheinLink = new JButton("Henry Schein Link");
        threemLink = new JButton("3M Link");
        this.image = new JLabel();

        //Setting link action commands.
        amazonLink.setActionCommand(data[4]);
        henryScheinLink.setActionCommand(data[6]);
        threemLink.setActionCommand(data[5]);

        //Setting window.
        this.setBounds(200, 200, 1200, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        //Setting fonts
        frameTitle.setFont(TITLE_FONT);
        quantityLabel.setFont(MAIN_FONT);
        costLabel.setFont(MAIN_FONT);
        linkLabel.setFont(MAIN_FONT);
        quantityLabel.setFont(MAIN_FONT);
        costLabel.setFont(MAIN_FONT);
        percentLabel.setFont(MAIN_FONT);
        rateLabel.setFont(MAIN_FONT);
        quantity.setFont(MAIN_FONT);
        restockCost.setFont(MAIN_FONT);
        percentRemaining.setFont(MAIN_FONT);
        usageRate.setFont(MAIN_FONT);
        amazonLink.setFont(MAIN_FONT);
        henryScheinLink.setFont(MAIN_FONT);
        threemLink.setFont(MAIN_FONT);
        back.setFont(MAIN_FONT);
        
        //Setting component sizes and styling.
        back.setPreferredSize(new Dimension(20, 40));
        back.setContentAreaFilled(false);
        back.setBorderPainted(false);
        amazonLink.setBackground(BLACK);
        henryScheinLink.setBackground(BLACK);
        threemLink.setBackground(BLACK);
        amazonLink.setForeground(WHITE);
        henryScheinLink.setForeground(WHITE);
        threemLink.setForeground(WHITE);
        menuBar.setBackground(DARK_BLUE);
        addPanel.setBackground(LIGHT_BLUE);
        back.setForeground(WHITE);
        image.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("drill.png")).getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT)));

        
        //Setting action listeners.
        back.addActionListener(this);
        amazonLink.addActionListener(this);
        henryScheinLink.addActionListener(this);
        threemLink.addActionListener(this);

        //Adding components to frame.
        addConstraints(addPanel, frameTitle, 0, 0, 3, 1, GridBagConstraints.LINE_START);
        addConstraints(addPanel, image, 0, 1, 2, 5, GridBagConstraints.CENTER);
        addConstraints(addPanel, quantityLabel, 2, 1, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, quantity, 3, 1, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, costLabel, 2, 2, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, restockCost, 3, 2, 2, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, percentLabel, 2, 3, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, percentRemaining, 3, 3, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, rateLabel, 2, 4, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, usageRate, 3, 4, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, linkLabel, 2, 5, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, amazonLink, 3, 5, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, henryScheinLink, 3, 6, 1, 1, GridBagConstraints.CENTER);
        addConstraints(addPanel, threemLink, 3, 7, 1, 1, GridBagConstraints.CENTER);

        menuBar.add(back);
        this.add(addPanel, BorderLayout.CENTER);
        this.setJMenuBar(menuBar);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object commandSource = ae.getSource();
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;

        //Button-dependent actions.
        if (commandSource == back) {
            Frame[] frames = Frame.getFrames();
            for (int i = 0; i < frames.length; i++) {
                frames[i].dispose();
            }
            HomeFrame home = new HomeFrame();
        } else if (commandSource == amazonLink) {
            //Getting link.
            String link = amazonLink.getActionCommand().toString();
            
            //Calling browser-opening method for amazon link.
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(new URL(link).toURI());
                } catch (IOException | URISyntaxException e) {
                    WarningFrame warning = new WarningFrame("Unable to access webpage.", "ERROR");
                }
            }
        } else if (commandSource == henryScheinLink) {
            //Getting link.
            String link = henryScheinLink.getActionCommand().toString();
            
            //Calling browser-opening method for Henry Schein link.
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(new URL(link).toURI());
                } catch (IOException | URISyntaxException e) {
                    WarningFrame warning = new WarningFrame("Unable to access webpage.", "ERROR");
                }
            }
        } else if (commandSource == threemLink) {
            //Getting link.
            String link = threemLink.getActionCommand().toString();
            
            //Calling browser-opening method for 3M link.
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(new URL(link).toURI());
                } catch (IOException | URISyntaxException e) {
                    WarningFrame warning = new WarningFrame("Unable to access webpage.", "ERROR");
                }
            }
        }
    }

    //Adding components to frame.
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
