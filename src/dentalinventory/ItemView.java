/*
 * This class initializes frame that allows user to view tables.
 */
package dentalinventory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class ItemView extends JFrame implements ActionListener {

    //Components, constants, and objects.
    private final Color BLACK = Color.decode("#50514F");
    private final Color DARK_BLUE = Color.decode("#247BA0");
    private final Color WHITE = Color.decode("#FFFFFF");
    private final Color RED = Color.decode("#F25F5C");
    private final Color LIGHT_BLUE = Color.decode("#70C1B3");

    private final Font TITLE_FONT = new Font("Calibri", Font.BOLD, 40);
    private final Font MAIN_FONT = new Font("Calibri", Font.BOLD, 30);

    private Object[][] data;
    private JScrollPane scrollPane;
    private JTable displayTable;
    private JTableHeader header;
    private TableColumn column;
    private final String[] STATS_HEADER = {"Item", "Quantity", "Date Added", "Unit Cost", "Restocks", "User-Defined Low", "Total Used", "Low Stock Preference"};
    private final String[] STOCK_HEADER = {"Item", "Average Low Stock", "Average High Stock"};

    private JMenuBar menuBar;
    private JButton back;
    private JLabel title;
    private JRadioButton statsButton;
    private JRadioButton stockButton;
    private ButtonGroup group;
    private JPanel panel;

    private InventoryMethods inventoryMethods;

    public ItemView() {
        super("View the Item Tables");
        //Initializing window.
        this.setBounds(200, 200, 1200, 700);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        //Setting data array.
        this.inventoryMethods = new InventoryMethods();
        this.data = inventoryMethods.statsView();

        //Initializing table.
        this.displayTable = new JTable(data, STATS_HEADER) {
            public boolean editCellAt(int row, int column, EventObject e) {
                return false;
            }
        };
        this.scrollPane = new JScrollPane(displayTable);
        this.menuBar = new JMenuBar();
        this.back = new JButton("Return");
        this.title = new JLabel(" Current Inventory Statistics");
        this.statsButton = new JRadioButton("Statistics Table");
        this.stockButton = new JRadioButton("Stock Table");
        this.panel = new JPanel();
        this.group = new ButtonGroup();

        this.displayTable.setFillsViewportHeight(true);

        //Adding mouse listener to allow for double-click to open item stats.
        this.displayTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    String item = (String) displayTable.getValueAt(displayTable.getSelectedRow(), 0);
                    ItemInfoFrame infoFrame = new ItemInfoFrame(item);
                    dispose();
                }

            }
        });

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
        this.column.setPreferredWidth(200);
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
        this.column = displayTable.getColumnModel().getColumn(6);
        this.column.setPreferredWidth(220);
        this.column = displayTable.getColumnModel().getColumn(7);
        this.column.setPreferredWidth(500);
        this.displayTable.setRowHeight(40);

        //Styling components
        this.menuBar.setBackground(DARK_BLUE);
        this.back.setForeground(WHITE);
        this.setBackground(DARK_BLUE);
        this.panel.setBackground(LIGHT_BLUE);
        this.title.setBackground(LIGHT_BLUE);
        this.statsButton.setBackground(LIGHT_BLUE);
        this.stockButton.setBackground(LIGHT_BLUE);
        //Setting button fonts.
        this.statsButton.setFont(MAIN_FONT);
        this.stockButton.setFont(MAIN_FONT);
        this.title.setFont(TITLE_FONT);

        //Adding listeners
        statsButton.addActionListener(this);
        stockButton.addActionListener(this);
        statsButton.setSelected(true);

        //Setting component properties.
        this.back.setFont(MAIN_FONT);
        this.back.setPreferredSize(new Dimension(20, 40));
        this.back.setContentAreaFilled(false);
        this.back.setBorderPainted(false);
        this.back.addActionListener(this);

        //Adding to frame.
        this.group.add(statsButton);
        this.group.add(stockButton);
        this.panel.add(statsButton);
        this.panel.add(stockButton);

        this.menuBar.add(back);
        this.setJMenuBar(menuBar);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(title, BorderLayout.NORTH);
        this.add(panel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object commandSource = ae.getSource();

        //Button-dependent actions.
        if (commandSource == back) {
            Frame[] frames = Frame.getFrames();
            for (int i = 0; i < frames.length; i++) {
                frames[i].dispose();
            }
            HomeFrame home = new HomeFrame();
        }

        this.inventoryMethods = new InventoryMethods();
        if (commandSource == statsButton) {
            //Setting data to InventoryStats table.
            this.data = inventoryMethods.statsView();
            this.displayTable = new JTable(data, STATS_HEADER) {
                public boolean editCellAt(int row, int column, EventObject e) {
                    return false;
                }
            };
            this.scrollPane = new JScrollPane(displayTable);
            this.displayTable.setFillsViewportHeight(true);
            this.title = new JLabel("Current Inventory Statistics");
            this.title.setFont(TITLE_FONT);

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
            this.column.setPreferredWidth(180);
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
            this.column = displayTable.getColumnModel().getColumn(6);
            this.column.setPreferredWidth(220);
            this.column = displayTable.getColumnModel().getColumn(7);
            this.column.setPreferredWidth(500);
            this.displayTable.setRowHeight(40);

            //Setting button state.
            this.statsButton.setSelected(true);
            this.stockButton.setSelected(false);

            //Adding components.
            this.getContentPane().removeAll();
            this.menuBar.add(back);
            this.setJMenuBar(menuBar);
            this.group.add(statsButton);
            this.group.add(stockButton);
            this.panel.add(statsButton);
            this.panel.add(stockButton);
            this.add(scrollPane, BorderLayout.CENTER);
            this.add(title, BorderLayout.NORTH);
            this.add(panel, BorderLayout.SOUTH);
            //Readding mouse listener.
            this.displayTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    if (me.getClickCount() == 2) {
                        String item = (String) displayTable.getValueAt(displayTable.getSelectedRow(), 0);
                        ItemInfoFrame infoFrame = new ItemInfoFrame(item);
                        dispose();
                    }

                }
            });
            this.revalidate();
            this.repaint();
        } else if (commandSource == stockButton) {
            //Setting data to InventoryStock
            this.data = inventoryMethods.stockView();
            this.displayTable = new JTable(data, STOCK_HEADER) { //{
                public boolean editCellAt(int row, int column, EventObject e) {
                    return false;
                }
            };
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

            this.displayTable.setRowHeight(40);

            this.title = new JLabel("Current Inventory Stock");
            this.title.setFont(TITLE_FONT);
            this.statsButton.setSelected(false);
            this.stockButton.setSelected(true);

            //Adding components
            this.getContentPane().removeAll();
            this.menuBar.add(back);
            this.setJMenuBar(menuBar);
            this.group.add(statsButton);
            this.group.add(stockButton);
            this.panel.add(statsButton);
            this.panel.add(stockButton);
            this.add(scrollPane, BorderLayout.CENTER);
            this.add(title, BorderLayout.NORTH);
            this.add(panel, BorderLayout.SOUTH);

            //Readding mouse listener.
            this.displayTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    if (me.getClickCount() == 2) {
                        String item = (String) displayTable.getValueAt(displayTable.getSelectedRow(), 0);
                        ItemInfoFrame infoFrame = new ItemInfoFrame(item);
                        dispose();
                    }

                }
            });
            this.revalidate();
            this.repaint();

        }
    }

}
