/*
 * This class initializes a warning, message, or error frame 
 * depending on the call.
 */
package dentalinventory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class WarningFrame extends JFrame implements ActionListener{
    //Components
    private JPanel panel;
    private JLabel title;
    private JTextArea warning;
    private JButton back, home;
    private JMenuBar menuBar;

    private final Color BLACK = Color.decode("#50514F");
    private final Color DARK_BLUE = Color.decode("#247BA0");
    private final Color WHITE = Color.decode("#FFFFFF");
    private final Color RED = Color.decode("#F25F5C");
    private final Color LIGHT_BLUE = Color.decode("#70C1B3");
    private final Font TITLE_FONT = new Font("Calibri", Font.BOLD, 50);
    private final Font MAIN_FONT = new Font("Calibri", Font.BOLD, 35);
    
    public WarningFrame(String messageBody, String messageType){
        super(messageType);
        this.setBounds(200, 200, 800, 300);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        this.panel = new JPanel();
        this.back = new JButton("Back");
        this.home = new JButton("Home");
        this.menuBar = new JMenuBar();
        
        this.panel.setLayout(new BorderLayout());
        this.title = new JLabel(messageType.toUpperCase(), SwingConstants.CENTER);
        this.warning = new JTextArea(messageBody);
        
        this.back.setFont(MAIN_FONT);
        this.home.setFont(MAIN_FONT);
        this.title.setFont(TITLE_FONT);
        this.warning.setFont(MAIN_FONT);
        
        this.back.setContentAreaFilled(false);
        this.home.setContentAreaFilled(false);
        this.back.setBorderPainted(false);
        this.home.setBorderPainted(false);
        this.warning.setLineWrap(true);
        this.warning.setWrapStyleWord(true);
        this.warning.setEditable(false);
        this.warning.setFocusable(false);
        this.warning.setOpaque(false);
        this.warning.setBorder(null);
        
        this.panel.setBackground(LIGHT_BLUE);
        this.menuBar.setBackground(DARK_BLUE);
        this.back.setForeground(WHITE);
        this.home.setForeground(WHITE);
        
        this.back.addActionListener(this);
        this.home.addActionListener(this);
        
        this.panel.add(title, BorderLayout.NORTH);
        this.panel.add(warning, BorderLayout.CENTER);
        this.add(panel, BorderLayout.CENTER);
        this.menuBar.add(back);
        this.menuBar.add(home);
        this.setJMenuBar(menuBar);
        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        Object commandSource = ae.getSource();

        if (commandSource == back) {
            this.dispose();
        }

        if (commandSource == home) {
            Frame[] frames = Frame.getFrames();
            for(int i = 0; i < frames.length; i++){
                frames[i].dispose();
            }
            
            HomeFrame homeAccess = new HomeFrame();
        } 
    }
    
    public static void main(String [] args){
        WarningFrame frame = new WarningFrame("Information", "Message Type");
        
    }
}
