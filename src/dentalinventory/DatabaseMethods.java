/*
 * This class contains methods relevant to database modification and selection.
 */
package dentalinventory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseMethods {
    //Declaring variables.
    private Connection conn = null;
    private String dbName;
    private Object[][] data;

    //Initializing variables depending on entered arguments.
    public DatabaseMethods() {
        this.dbName = "";
        this.data = null;
        this.conn = null;
    }

    public DatabaseMethods(String dbName) {
        this.dbName = dbName;
        this.data = null;
        connectDB();
    }

    //Mutators and accessors.
    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    //Connecting to the Database.
    public void connectDB() {
        String connectionURL = "jdbc:derby:" + dbName;

        this.conn = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            this.conn = DriverManager.getConnection(connectionURL);
        } catch (ClassNotFoundException | SQLException e) {
        }

    }

    //Creating a new database.
    public void createDB(String newDbName) {
        String newConnectionUrl = "jdbc:derby:" + newDbName + ";create=true";

        Connection newConn;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            newConn = DriverManager.getConnection(newConnectionUrl);
            newConn.close();
        } catch (ClassNotFoundException | SQLException e) {
        }
    }

    //Creating a table within the database.
    public void createTable(String cmdString, String dbName) {

        setDbName(dbName);
        connectDB();
        try {
            //Command execution.
            Statement s = conn.createStatement();
            s.execute(cmdString);
            conn.close();
        } catch (SQLException e) {
        }
    }

    //Close the database connection.
    public void closeDB() {
        try {
            conn.close();
        } catch (SQLException e) {
        }
    }

    //Reading the table.
    public Object[][] readTable(String dbQuery, String tableName, int tableColumns, String[] tableHeaders) {
        int tableRows = rowCounter(tableName);
        Object[][] dbDetails = new Object[tableRows][tableColumns];
        ResultSet rs;
        dbDetails[0][0] = "error";
        int rowNumber;

        try {
            Statement s = conn.createStatement();
            rs = s.executeQuery(dbQuery);
            rowNumber = 0;
            while (rs.next()) {
                for (int columnNo = 0; columnNo < tableColumns; columnNo++) {
                    dbDetails[rowNumber][columnNo] = rs.getString(tableHeaders[columnNo]);
                }
                rowNumber++;

            }
        } catch (SQLException e) {
            return null;
        }

        return dbDetails;
    }

    //Getting data to display from database.
    public Object[][] getData(String tableName, String[] tableHeaders) {
        //Variables and queries.
        int columnCount = tableHeaders.length;
        ResultSet rs = null;
        Statement s = null;
        String dbQuery = "SELECT * FROM " + tableName;
        ArrayList<ArrayList<String>> dataList = new ArrayList<>();

        try {
            s = conn.createStatement();
            rs = s.executeQuery(dbQuery);
            //Converting the result set to an arraylist.
            while (rs.next()) {
                ArrayList<String> row = new ArrayList<>();
                for (int i = 0; i < columnCount; i++) {
                    row.add(rs.getString(tableHeaders[i]));
                }

                dataList.add(row);
            }

            data = new Object[dataList.size()][columnCount];
            //Converting arraylist to array since size is now known.
            for (int i = 0; i < dataList.size(); i++) {
                ArrayList<String> row; //= new ArrayList<>()
                row = dataList.get(i);
                for (int j = 0; j < columnCount; j++) {
                    this.data[i][j] = row.get(j);
                }
            }

        } catch (SQLException e) {
            return null;
        }
        return data;
    }

    //Find number of rows in the table.
    public int rowCounter(String tableName) {
        //Declaring variables.
        ResultSet rs = null;
        int rowSize = 0;
        try {
            //Getting row count as a variable from the table.
            Statement s = conn.createStatement();
            rs = s.executeQuery("SELECT COUNT(*) AS rowCounter FROM " + tableName);
            while (rs.next()) {
                rowSize = rs.getInt("rowCounter");
            }
            rs.close();
        } catch (SQLException e) {
            return 0;
        }

        return rowSize;
    }

    //General insert statement.
    public boolean insertTable(String dbQuery) {
        try {
            PreparedStatement s = conn.prepareStatement(dbQuery);
            s.execute(dbQuery);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    //Inserting into inventory stats.
    public boolean statsInsert(String dbQuery, String item, int quantity, String date, double cost, int restocks, int low, int totalUsed, String lowType) {
        try {
            //Creating prepared statement and setting relevant values using parameters.
            PreparedStatement statement = conn.prepareStatement(dbQuery);
            statement.setString(1, item);
            statement.setInt(2, quantity);
            statement.setString(3, date);
            statement.setDouble(4, cost);
            statement.setInt(5, restocks);
            statement.setInt(6, low);
            statement.setInt(7, totalUsed);
            statement.setString(8, lowType);
            
            //Updating.
            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    //Inserting into the patients table.
    public boolean patientsInsert(String dbQuery, String timeStamp, String lastName, String firstName, String itemUsed, int quantity, String comments) {
        try {
            //Creating prepared statement and setting relevant values using parameters.
            PreparedStatement statement = conn.prepareStatement(dbQuery);
            statement.setString(1, timeStamp);
            statement.setString(2, lastName);
            statement.setString(3, firstName);
            statement.setString(4, itemUsed);
            statement.setInt(5, quantity);
            statement.setString(6, comments);
            
            //Executing insert statement.
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    //Inserting rows into InventoryStock
    public boolean stockInsert(String dbQuery, String item) {
        try {
            //Preparing query to execute.
            PreparedStatement statement = conn.prepareStatement(dbQuery);
            statement.setString(1, item);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    //General deletion statement.
    public boolean deleteTable(String query) {
        boolean success;
        try {
            PreparedStatement s = conn.prepareStatement(query);
            success = s.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    //General update statement.
    public void updateTable(String dbQuery) {

        try {
            Statement s = conn.createStatement();
            s.execute(dbQuery);
        } catch (SQLException e) {
        }
    }
    
    //Changing item value in patient table if it is modified.
    public boolean patientItem(String dbQuery, String item, String newItem) {
        
        try {
            //Preparing and executing statement.
            PreparedStatement statement = conn.prepareStatement(dbQuery);
            statement.setString(1, newItem);
            statement.setString(2, item);
            statement.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    //Updating patients table return boolean depending on success of execution.
    public boolean patientsUpdate(String dbQuery, String lastName, String firstName, int quantity, String comments) {

        try {
            //Preparing statement, setting values, and executing update.
            PreparedStatement statement = conn.prepareStatement(dbQuery);
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.setInt(3, quantity);
            statement.setString(4, comments);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    //Updating quantity and totalUsed in InventoryStock depending upon patient modify or delete.
    public boolean updateQuantity(String dbQuery, int quantity, int totalUsed) {

        try {
            PreparedStatement s = conn.prepareStatement(dbQuery);
            s.setInt(1, quantity);
            s.setInt(2, totalUsed);
            s.executeUpdate();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    //Updating the InventoryStats table.
    public boolean statsUpdate(String dbQuery, String item, int quantity, double unitCost, int restock, int userLow, int totalUsed, String lowType) {
        //Test code.
//        dbQuery = "UPDATE InventoryStats SET item = ?, "
//                + "quantity = ?, unit_cost = ?, restocks = ?, "
//                + "user_defined_low = ?, total_used = ?, "
//                + "low_stock_choice = ? WHERE item=?";
//        String[] header = {"item", "quantity", "date_added", "unit_cost", "restocks", "user_defined_low", "total_used","low_stock_choice"}; 
//        
//        
//        Object[][] data = getData("InventoryStats", header);
//
//        System.out.println("Database items:");
//        for (int i = 0; i < data.length; i++) {
//            for (int j = 0; j < data[i].length; j++) {
//                System.out.println(data[i][j]);
//            }
//        }
//        System.out.println("Item is: " + item);
        try {
            //Preparing and executing statement.
            PreparedStatement s = conn.prepareStatement(dbQuery);
            s.setString(1, item);
            s.setInt(2, quantity);
            s.setDouble(3, unitCost);
            s.setInt(4, restock);
            s.setInt(5, userLow);
            s.setInt(6, totalUsed);
            s.setString(7, lowType);
            s.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
//        System.out.println("SUCcess" + success);
//        System.out.println("Database items:");
//        for (int i = 0; i < data.length; i++) {
//            for (int j = 0; j < data[i].length; j++) {
//                System.out.println(data[i][j]);
//            }
//        }
    }

    //Update InventoryStock table.
    public boolean stockUpdate(String dbQuery, String item, int averageLow, int averageHigh) {
        try {
            //Preparing statement.
            PreparedStatement s = conn.prepareStatement(dbQuery);
            s.setString(1, item);
            s.setInt(2, averageLow);
            s.setInt(3, averageHigh);

            //Executing statement and returning success depending on execution.
            s.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    //Selecting a specified column from a table.
    public String[] columnSelect(String dbQuery, String tableName, String tableHeader) {
        //Declaring and initializing variables, Strings, and ArrayList
        String[] columnValues = null;
        String[] finalValues;
        ArrayList<String> list = new ArrayList<>();
        int count;
        try {
            //Executing query.
            Statement s = conn.createStatement();
            ResultSet column = s.executeQuery(dbQuery);

            //Looping through the result set to add values to the arrayList.
            while (column.next()) {
                list.add(column.getString(tableHeader));
            }
            columnValues = new String[list.size()];
            count = 0;

            //Adding the arraylist to an array.
            while (count < list.size()) {
                columnValues[count] = list.get(count);
                count++;
            }

            count = 0;
            int i;

            //Shortening array and removing duplicate values.
            for (i = 0; i < columnValues.length; i++) {
                if (columnValues[i] != null) {
                    count++;
                }
            }

            i = 0;

            //Adding edited array to new array to return.
            finalValues = new String[count];
            while (i < count) {
                finalValues[i] = columnValues[i];
                i++;
            }

            return finalValues;
        } catch (SQLException e) {
            return columnValues;
        }

    }

    //Selecting a specified row.
    public String[] rowSelect(String dbQuery, String tableName, String[] tableHeaders) {
        //Declaring array.
        String[] rowValues = new String[tableHeaders.length];
        try {
            //Executing query.
            Statement s = conn.createStatement();
            ResultSet row = s.executeQuery(dbQuery);

            //Adding the result set to an array.
            while (row.next()) {
                for (int count = 0; count < tableHeaders.length; count++) {
                    rowValues[count] = row.getString(count + 1);
                }
            }
        } catch (SQLException e) {
            return null;
        }

        return rowValues;
    }

    //Selecting a specific value
    public String valueSelect(String dbQuery, String item, String header){
        ResultSet rs;
        String value = "";
        try {
            PreparedStatement s = conn.prepareStatement(dbQuery);
            s.setString(1, item);
            rs = s.executeQuery();
            
            while(rs.next()){
                value = rs.getString(header);
            }
            
        } catch (SQLException e) {
            return null;
        }
        return value;
    }
    
    //Statement tester.
    public void runStatement(String query) {
        try {
            Statement s = conn.createStatement();
            s.execute(query);
        } catch (SQLException e) {
        }
    }

    //Main method for testing.
    public static void main(String[] args) {
        DatabaseMethods obj = new DatabaseMethods("DentalInventory");
        //boolean statsValidity = obj.statsUpdate("", "Dental Pick", 120, 1.00, 1, 20, 500, "averagePrice");
        //System.out.println(statsValidity);
        //String query = "RENAME COLUMN Patients.Quantity TO quantity";
        //obj.runStatement(query);

        String query = "SELECT quantity FROM InventoryStats WHERE item LIKE 'Soft%'";
        String[] values = obj.columnSelect(query, "InventoryStats", "quantity");
    }

    //ToString
    @Override
    public String toString() {
        return "DatabaseMethods{" + "dbName=" + dbName + '}';
    }
    
}
