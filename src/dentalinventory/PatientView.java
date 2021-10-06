/*
 * This frame allows the user to see the displayed patients table.
 */
package dentalinventory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class PatientView extends JFrame implements ActionListener {

    //Components.
    private final Color BLACK = Color.decode("#50514F");
    private final Color DARK_BLUE = Color.decode("#247BA0");
    private final Color WHITE = Color.decode("#FFFFFF");
    private final Color RED = Color.decode("#F25F5C");
    private final Color LIGHT_BLUE = Color.decode("#70C1B3");
    private final Font TITLE_FONT = new Font("Calibri", Font.BOLD, 40);
    private final Font MAIN_FONT = new Font("Calibri", Font.BOLD, 30);
    private final String[] PATIENTS_HEADER = {"Date", "Last Name", "First Name", "Item Used", "Quantity", "Comments"};

    private Object[][] data;
    private JScrollPane scrollPane;
    private JTable displayTable;
    private JTableHeader header;
    private TableColumn column;
    private JMenuBar menuBar;
    private JButton back;
    private JLabel title;

    private PatientMethods patientMethods;

    public PatientView() {
        super("View the Item Tables");
        this.setBounds(200, 200, 1200, 700);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        //Initializing table.
        this.patientMethods = new PatientMethods();
        this.data = patientMethods.patientView();
        this.displayTable = new JTable(data, PATIENTS_HEADER) {
            public boolean editCellAt(int row, int column, EventObject e) {
                return false;
            }
        };
        this.menuBar = new JMenuBar();
        this.back = new JButton("Return");
        this.title = new JLabel(" Current Patient Records");
        this.scrollPane = new JScrollPane(displayTable);
        this.displayTable.setFillsViewportHeight(true);

        /* Change grid color, background color, font of table.*/
        this.displayTable.setGridColor(Color.WHITE);
        this.displayTable.setBackground(BLACK);
        this.displayTable.setFont(MAIN_FONT);
        this.displayTable.setForeground(Color.WHITE);

        /*Format the header*/
        this.header = displayTable.getTableHeader();
        this.header.setBackground(LIGHT_BLUE);
        this.header.setForeground(BLACK);
        this.header.setBorder(BorderFactory.createLineBorder(RED));
        this.header.setFont(TITLE_FONT);

        /*Format the column size*/
        this.displayTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.column = null;
        this.column = displayTable.getColumnModel().getColumn(0);
        this.column.setPreferredWidth(150);
        this.column = displayTable.getColumnModel().getColumn(1);
        this.column.setPreferredWidth(200);
        this.column = displayTable.getColumnModel().getColumn(2);
        this.column.setPreferredWidth(200);
        this.column = displayTable.getColumnModel().getColumn(3);
        this.column.setPreferredWidth(220);
        this.column = displayTable.getColumnModel().getColumn(4);
        this.column.setPreferredWidth(200);
        this.column = displayTable.getColumnModel().getColumn(5);
        this.column.setPreferredWidth(450);
        this.displayTable.setRowHeight(40);

        //Formatting component dimesions and styling.
        this.title.setFont(TITLE_FONT);
        this.back.setFont(MAIN_FONT);

        this.back.setPreferredSize(new Dimension(20, 40));
        this.back.setContentAreaFilled(false);
        this.back.setBorderPainted(false);
        this.back.addActionListener(this);

        this.menuBar.setBackground(DARK_BLUE);
        this.back.setForeground(WHITE);
        this.setBackground(DARK_BLUE);

        //Adding components to frame.
        this.menuBar.add(back);
        this.setJMenuBar(menuBar);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(title, BorderLayout.NORTH);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object commandSource = ae.getSource();

        if (commandSource == back) {
            Frame[] frames = Frame.getFrames();
            for (int i = 0; i < frames.length; i++) {
                frames[i].dispose();
            }
            HomeFrame home = new HomeFrame();
        }
    }

}
