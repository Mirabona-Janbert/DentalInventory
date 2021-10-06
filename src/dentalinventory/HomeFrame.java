/*
 * This class initializes the home frame, which allows the user to access all the patient and inventory classes.
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
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class HomeFrame extends JFrame implements ActionListener {

    //Declaring variables and constants.
    private JPanel homePanel;
    private JMenuBar menuBar;
    private JMenu patient, inventory, help;
    private JMenuItem addPatient, modifyPatient, deletePatient, viewPatient;
    private JMenuItem addInventory, modifyInventory, deleteInventory, viewInventory, quantityCheck;
    private JMenuItem patientHelp, itemHelp;
    private JButton exit;
    private JLabel title;
    private JTextArea subheader;
    private JLabel image;
    private final Color BLACK = Color.decode("#50514F");
    private final Color DARK_BLUE = Color.decode("#247BA0");
    private final Color WHITE = Color.decode("#FFFFFF");
    private final Color RED = Color.decode("#F25F5C");
    private final Color LIGHT_BLUE = Color.decode("#70C1B3");
    private final Font TITLE_FONT = new Font("Calibri", Font.BOLD, 40);
    private final Font MAIN_FONT = new Font("Calibri", Font.BOLD, 30);
    private final Font MINOR_FONT = new Font("Calibri", Font.BOLD, 30);
    private QuantityWarning warningTest;
    private String stockCheck;

    public HomeFrame() {
        super("Home");

        //Initializing variables 
        this.exit = new JButton("Exit");
        this.itemHelp = new JMenuItem("Item Help");
        this.patientHelp = new JMenuItem("Patient Help");
        this.viewInventory = new JMenuItem("View");
        this.deleteInventory = new JMenuItem("Delete");
        this.modifyInventory = new JMenuItem("Modify");
        this.addInventory = new JMenuItem("Add");
        this.quantityCheck = new JMenuItem("Check Quantity");
        this.viewPatient = new JMenuItem("View");
        this.deletePatient = new JMenuItem("Delete");
        this.modifyPatient = new JMenuItem("Modify");
        this.addPatient = new JMenuItem("Add");
        this.help = new JMenu("Help");
        this.inventory = new JMenu("Inventory");
        this.patient = new JMenu("Patient");
        this.menuBar = new JMenuBar();
        this.homePanel = new JPanel();
        this.title = new JLabel("DentalInventory version 1.1");
        this.subheader = new JTextArea("Navigate the menus above to access your desired page. Click on a help menu for more information on how to use the program.");
        this.image = new JLabel();
        this.homePanel = new JPanel(new GridBagLayout());

        //Setting up Frame
        this.setPreferredSize(new Dimension(800, 400));
        this.setBounds(200, 200, 400, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        //Adding components to Menu 
        this.patient.add(viewPatient);
        this.patient.add(addPatient);
        this.patient.add(modifyPatient);
        this.patient.add(deletePatient);
        this.inventory.add(viewInventory);
        this.inventory.add(addInventory);
        this.inventory.add(modifyInventory);
        this.inventory.add(deleteInventory);
        this.inventory.add(quantityCheck);
        this.help.add(patientHelp);
        this.help.add(itemHelp);

        //Set font
        this.patient.setFont(MAIN_FONT);
        this.inventory.setFont(MAIN_FONT);
        this.help.setFont(MAIN_FONT);
        this.addPatient.setFont(MAIN_FONT);
        this.modifyPatient.setFont(MAIN_FONT);
        this.deletePatient.setFont(MAIN_FONT);
        this.viewPatient.setFont(MAIN_FONT);
        this.addInventory.setFont(MAIN_FONT);
        this.modifyInventory.setFont(MAIN_FONT);
        this.deleteInventory.setFont(MAIN_FONT);
        this.viewInventory.setFont(MAIN_FONT);
        this.quantityCheck.setFont(MAIN_FONT);
        this.patientHelp.setFont(MAIN_FONT);
        this.itemHelp.setFont(MAIN_FONT);
        this.title.setFont(TITLE_FONT);
        this.subheader.setFont(MAIN_FONT);
        this.exit.setFont(MAIN_FONT);

        //Styling and formatting components.
        this.subheader.setLineWrap(true);
        this.subheader.setWrapStyleWord(true);
        this.subheader.setEditable(false);
        this.subheader.setFocusable(false);
        this.subheader.setOpaque(false);
        this.subheader.setBorder(null);
        this.exit.setContentAreaFilled(false);
        this.exit.setBorderPainted(false);
        this.image.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("report.png")).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));

        this.homePanel.setBackground(LIGHT_BLUE);
        this.menuBar.setBackground(DARK_BLUE);
        this.addPatient.setBackground(DARK_BLUE);
        this.modifyPatient.setBackground(DARK_BLUE);
        this.deletePatient.setBackground(DARK_BLUE);
        this.viewPatient.setBackground(DARK_BLUE);
        this.addInventory.setBackground(DARK_BLUE);
        this.modifyInventory.setBackground(DARK_BLUE);
        this.deleteInventory.setBackground(DARK_BLUE);
        this.viewInventory.setBackground(DARK_BLUE);
        this.quantityCheck.setBackground(DARK_BLUE);
        this.patientHelp.setBackground(DARK_BLUE);
        this.itemHelp.setBackground(DARK_BLUE);
        this.patient.setForeground(WHITE);
        this.inventory.setForeground(WHITE);
        this.help.setForeground(WHITE);
        this.addPatient.setForeground(WHITE);
        this.modifyPatient.setForeground(WHITE);
        this.deletePatient.setForeground(WHITE);
        this.viewPatient.setForeground(WHITE);
        this.addInventory.setForeground(WHITE);
        this.modifyInventory.setForeground(WHITE);
        this.deleteInventory.setForeground(WHITE);
        this.viewInventory.setForeground(WHITE);
        this.quantityCheck.setForeground(WHITE);
        this.patientHelp.setForeground(WHITE);
        this.itemHelp.setForeground(WHITE);
        this.exit.setForeground(WHITE);

        //Adding action listeners.
        this.addPatient.addActionListener(this);
        this.modifyPatient.addActionListener(this);
        this.deletePatient.addActionListener(this);
        this.viewPatient.addActionListener(this);
        this.quantityCheck.addActionListener(this);
        this.addInventory.addActionListener(this);
        this.modifyInventory.addActionListener(this);
        this.deleteInventory.addActionListener(this);
        this.viewInventory.addActionListener(this);
        this.patientHelp.addActionListener(this);
        this.itemHelp.addActionListener(this);
        this.exit.addActionListener(this);

        //Adding menus to menubar and adding components to frame.
        addConstraints(homePanel, title, 0, 0, 3, 1, GridBagConstraints.CENTER);
        addConstraints(homePanel, subheader, 0, 1, 2, 2, GridBagConstraints.CENTER);
        addConstraints(homePanel, image, 2, 0, 1, 4, GridBagConstraints.CENTER);

        this.menuBar.add(inventory);
        this.menuBar.add(patient);
        this.menuBar.add(help);
        this.menuBar.add(exit);

        this.setJMenuBar(menuBar);
        this.add(homePanel, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object commandSource = ae.getSource();

        //Button dependent actions.
        if (commandSource == addPatient) {
            Frame[] frames = Frame.getFrames();
            for (int i = 0; i < frames.length; i++) {
                frames[i].dispose();
            }
            PatientAddFrame patientAdd = new PatientAddFrame();

        } else if (commandSource == modifyPatient) {
            Frame[] frames = Frame.getFrames();
            for (int i = 0; i < frames.length; i++) {
                frames[i].dispose();
            }
            PatientModifyFrame patientModify = new PatientModifyFrame();

        } else if (commandSource == quantityCheck) {
            warningTest = new QuantityWarning();
            stockCheck = warningTest.quantityChecker();

            WarningFrame warning = new WarningFrame(stockCheck, "Message");
            warning.setBounds(200, 200, 800, 600);

        } else if (commandSource == deletePatient) {
            Frame[] frames = Frame.getFrames();
            for (int i = 0; i < frames.length; i++) {
                frames[i].dispose();
            }
            PatientDeleteFrame patientDelete = new PatientDeleteFrame();
        } else if (commandSource == viewPatient) {
            Frame[] frames = Frame.getFrames();
            for (int i = 0; i < frames.length; i++) {
                frames[i].dispose();
            }
            PatientView patientView = new PatientView();

        } else if (commandSource == viewInventory) {
            Frame[] frames = Frame.getFrames();
            for (int i = 0; i < frames.length; i++) {
                frames[i].dispose();
            }
            ItemView view = new ItemView();

        } else if (commandSource == addInventory) {
            Frame[] frames = Frame.getFrames();
            for (int i = 0; i < frames.length; i++) {
                frames[i].dispose();
            }
            ItemAddFrame itemAdd = new ItemAddFrame();

        } else if (commandSource == modifyInventory) {
            Frame[] frames = Frame.getFrames();
            for (int i = 0; i < frames.length; i++) {
                frames[i].dispose();
            }
            ItemModifyFrame itemModify = new ItemModifyFrame();

        } else if (commandSource == deleteInventory) {
            Frame[] frames = Frame.getFrames();
            for (int i = 0; i < frames.length; i++) {
                frames[i].dispose();
            }
            ItemDeleteFrame itemAdd = new ItemDeleteFrame();

        } else if (commandSource == patientHelp) {
            Frame[] frames = Frame.getFrames();
            for (int i = 0; i < frames.length; i++) {
                frames[i].dispose();
            }
            PatientHelpFrame patientHelp = new PatientHelpFrame();

        } else if (commandSource == itemHelp) {
            Frame[] frames = Frame.getFrames();
            for (int i = 0; i < frames.length; i++) {
                frames[i].dispose();
            }
            ItemHelpFrame itemHelp = new ItemHelpFrame();

        } else if (commandSource == exit) {
            System.exit(0);
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
