/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dentalinventory;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 *
 * @author Sourav
 */
public class PatientHelpFrame extends JFrame implements ActionListener {

    //Components and Constants
    private JMenuBar menuBar;
    private JPanel panel;
    private JButton back;
    private JLabel title;
    private JTextArea information;
    private JLabel image;
    
    private final Color BLACK = Color.decode("#50514F");
    private final Color DARK_BLUE = Color.decode("#247BA0");
    private final Color WHITE = Color.decode("#FFFFFF");
    private final Color RED = Color.decode("#F25F5C");
    private final Color LIGHT_BLUE = Color.decode("#70C1B3");
    private final Font TITLE_FONT = new Font("Calibri", Font.BOLD, 40);
    private final Font MAIN_FONT = new Font("Calibri", Font.BOLD, 30);
    private final Font MINOR_FONT = new Font("Calibri", Font.BOLD, 30);

    public PatientHelpFrame() {
        super("Help with Patients");
        //Setting up table
        this.setBounds(200, 200, 1200, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        //Initializing components
        this.back = new JButton("Return");
        this.menuBar = new JMenuBar();
        this.panel = new JPanel(new GridBagLayout());
        this.title = new JLabel("About Patient Actions");
        this.information = new JTextArea("Open the add page to add new patient records to the system. "
                + "Open modify to search through and edit these records. Ensure that the quantity used within adding and modifying is an integer value."
                + "Open delete to remove a record."
                + " Choose the option to reset the inventory table if you desire to remove that record's "
                + "impact on the database. Finally, clicking view will allow you to scroll through the patient records.");
        this.image = new JLabel();
        
        //Styling and formatting components
        this.information.setLineWrap(true);
        this.information.setWrapStyleWord(true);
        this.information.setEditable(false);
        this.information.setFocusable(false);
        this.information.setOpaque(false);
        this.information.setBorder(null);
        this.back.setContentAreaFilled(false);
        this.back.setBorderPainted(false);
        this.image.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("tooth.png")).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
        this.panel.setBackground(LIGHT_BLUE);
        this.menuBar.setBackground(DARK_BLUE);
        this.back.setForeground(WHITE);
        this.back.setFont(MAIN_FONT);
        this.title.setFont(TITLE_FONT);
        this.information.setFont(MAIN_FONT);
        
        //Adding Listener
        this.back.addActionListener(this);
        
        //Adding menu to menubar and adding components to frame.
        addConstraints(panel, title, 0, 0, 3, 1, GridBagConstraints.CENTER);
        addConstraints(panel, information, 0, 1, 2, 2, GridBagConstraints.CENTER);
        addConstraints(panel, image, 2, 0, 1, 4, GridBagConstraints.CENTER);
     
        this.menuBar.add(back);
        this.setJMenuBar(menuBar);
        this.add(panel, BorderLayout.CENTER);
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
    
    public static void main(String[] args) {
        PatientHelpFrame frame = new PatientHelpFrame();

    }
}
