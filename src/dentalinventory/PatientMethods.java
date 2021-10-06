/*
 * This class contains methods relevant to patient modification and display.
 */
package dentalinventory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class PatientMethods {

    //Declaring constants and variables.
    private String lastName;
    private String firstName;
    private String item;
    private int quantity;
    private String timeStamp;
    private int restocks;
    private int totalUsed;
    private String lowType;
    private DatabaseMethods database;
    private InventoryMethods inventory;
    private String[] columnValues;
    private final SimpleDateFormat DATABASE_FORMAT = new SimpleDateFormat("yyyyMMdd");
    private final SimpleDateFormat DISPLAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    //Constructors to set values dependent on call.
    public PatientMethods() {
        this.lastName = " ";
        this.firstName = " ";
        this.item = " ";
        this.quantity = 0;
        this.timeStamp = " ";
        this.restocks = 0;
        this.totalUsed = 0;
        this.lowType = " ";
        this.columnValues = new String[]{" "};
    }

    public PatientMethods(String lastName, String firstName, String item, int quantity, String timeStamp, int restocks, int totalUsed, String lowType, DatabaseMethods database, InventoryMethods inventory, String[] columnValues) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.item = item;
        this.quantity = quantity;
        this.timeStamp = timeStamp;
        this.restocks = restocks;
        this.totalUsed = totalUsed;
        this.lowType = lowType;
        this.database = database;
        this.inventory = inventory;
        this.columnValues = columnValues;
    }

    //Mutators and accessors.
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getRestocks() {
        return restocks;
    }

    public void setRestocks(int restocks) {
        this.restocks = restocks;
    }

    public int getTotalUsed() {
        return totalUsed;
    }

    public void setTotalUsed(int totalUsed) {
        this.totalUsed = totalUsed;
    }

    public String getLowType() {
        return lowType;
    }

    public void setLowType(String lowType) {
        this.lowType = lowType;
    }

    public DatabaseMethods getDatabase() {
        return database;
    }

    public void setDatabase(DatabaseMethods database) {
        this.database = database;
    }

    public InventoryMethods getInventory() {
        return inventory;
    }

    public void setInventory(InventoryMethods inventory) {
        this.inventory = inventory;
    }

    public String[] getColumnValues() {
        return columnValues;
    }

    public void setColumnValues(String[] columnValues) {
        this.columnValues = columnValues;
    }

    //Method to add patient entry to table.
    public boolean addPatient(String item, String firstNameValue, String lastNameValue, String quantityUsed, String doctorComment) {
        //Declaring database query strings and current date and time value.
        String patientAdd = "INSERT INTO Patients VALUES (?, ?, ?, ?, ?, ?)";
        String quantityModify = "UPDATE InventoryStats SET quantity = ?, total_used = ? WHERE item = '" + item + "'";
        String formattedDate = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

        try {
            //Parsing value for database, initializing relevant classes, and getting data from those classes.
            this.quantity = Integer.parseInt(quantityUsed);
            this.database = new DatabaseMethods("DentalInventory");
            this.inventory = new InventoryMethods();
            String[] itemRow = inventory.statsRow(item);
            int statsQuantity = Integer.parseInt(itemRow[1]) - Integer.parseInt(quantityUsed);
            this.totalUsed = Integer.parseInt(itemRow[6]) + Integer.parseInt(quantityUsed);

            //Adding entries to table dependent on success of first statement and returning success of the process.
            boolean updateValidity = database.updateQuantity(quantityModify, statsQuantity, totalUsed);
            if (updateValidity == true) {
                boolean insertValidity = database.patientsInsert(patientAdd, formattedDate, lastNameValue, firstNameValue, item, quantity, doctorComment);
                if (insertValidity == true) {
                    return true;
                } else {
                    this.database.updateQuantity(quantityModify, Integer.parseInt(itemRow[1]), Integer.parseInt(itemRow[6]));
                    return false;
                }
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //This method gets the last_name column in the patients table depending on the date.
    public String[] patientColumn(String date) {
        try {
            //System.out.println("Date: " + date);
            //Declaring and initializing relevant variables.
            Date convertedDate = (Date) DISPLAY_FORMAT.parse(date);
            date = DATABASE_FORMAT.format(convertedDate);
            String patientString = "SELECT DISTINCT last_name FROM Patients WHERE time_stamp LIKE '" + date + "%'";
            this.database = new DatabaseMethods("DentalInventory");

            //Getting data
            this.columnValues = database.columnSelect(patientString, "InventoryStats", "last_name");
            //System.out.println(columnValues[0]);
            return columnValues;
        } catch (ParseException ex) {
            return null;
        }
    }

    //This method gets first_name column from patients table using date and lastName entered values.
    public String[] patientColumn(String date, String lastName) {
        try {
//            System.out.println();
//            System.out.println("Date: " + date);
//            Declaring and initializing relevant variables.
            Date convertedDate = (Date) DISPLAY_FORMAT.parse(date);
            date = DATABASE_FORMAT.format(convertedDate);
            String patientString = "SELECT DISTINCT first_name FROM Patients WHERE time_stamp LIKE '" + date + "%' AND last_name='" + lastName + "'";
            this.database = new DatabaseMethods("DentalInventory");

            //Getting data.
            this.columnValues = database.columnSelect(patientString, "InventoryStats", "first_name");

            return columnValues;
        } catch (ParseException ex) {
            return null;
        }
    }

    //This method gets the item list using date, last name, and first name.
    public String[] patientColumn(String date, String lastName, String firstName) {
        try {
            //Converting date format, declaring queries and variables.
            Date convertedDate = (Date) DISPLAY_FORMAT.parse(date);
            date = DATABASE_FORMAT.format(convertedDate);
            String patientString = "SELECT DISTINCT item_used FROM Patients WHERE time_stamp LIKE '" + date + "%' AND "
                    + "last_name='" + lastName + "' AND first_name = '" + firstName + "'";
            this.database = new DatabaseMethods("DentalInventory");

            //Getting database column
            this.columnValues = database.columnSelect(patientString, "InventoryStats", "item_used");

            return columnValues;
        } catch (ParseException ex) {
            return null;
        }
    }

    //Getting specified row using date, last name, first name, and item used.
    public String[] patientRow(String date, String lastName, String firstName, String itemUsed) {
        try {
            //Converting date, declaring variables, headers, and query.
            Date convertedDate = (Date) DISPLAY_FORMAT.parse(date);
            date = DATABASE_FORMAT.format(convertedDate);
            String patientString = "SELECT * FROM Patients WHERE time_stamp LIKE '" + date + "%' AND "
                    + "last_name='" + lastName + "' AND first_name ='" + firstName + "' AND item_used='" + itemUsed + "'";
            String[] tableHeaders = {"time_stamp", "last_name", "first_name", "item_used", "Quantity", "comments"};
            this.database = new DatabaseMethods("DentalInventory");

            //Getting specified row
            String[] rowValues = database.rowSelect(patientString, "InventoryStats", tableHeaders);

            return rowValues;
        } catch (ParseException ex) {
            return null;
        }
    }

    //Getting a chosen column from inventory table.
    public String[] inventoryStatsList(String columnName) {

        //Declaring queries and variables.
        String columnListString = "SELECT " + columnName + " FROM InventoryStats";
        this.database = new DatabaseMethods("DentalInventory");

        //Getting column
        this.columnValues = database.columnSelect(columnListString, "InventoryStats", columnName);
        return columnValues;
    }

    //Getting a column from patients table.
    public String[] patientList(String columnName) {
        //Declaring variables and query.
        String columnListString = "SELECT " + columnName + " FROM Patients";
        this.database = new DatabaseMethods("DentalInventory");

        //Getting column from table.
        String[] column = database.columnSelect(columnListString, "InventoryStats", columnName);
        return column;
    }

    //Selecting unique values from patient columns.
    public String[] patientDistinctList(String columnName) {
        //Converting date format, declaring queries and variables.
        String columnListString = "SELECT DISTINCT " + columnName + " FROM Patients";
        this.database = new DatabaseMethods("DentalInventory");

        //Getting column from table.
        String[] column = database.columnSelect(columnListString, "InventoryStats", columnName);
        return column;
    }

    //Modifying given timestamps into appropriate display formats.
    public String[] modifiedTimestamp() {
        //Declaring variables and receiving values.
        String[] timeList = patientList("time_stamp");
        int count = 0;
        Date date;

        //While the counter is less than the array length, shorten and convert 
        //the date into a displayable format.
        try {
            while (count < timeList.length) {

                //Shorten date.
                timeList[count] = timeList[count].substring(0, timeList[count].length() - 7);

                //Convert date formatting.
                date = (Date) DATABASE_FORMAT.parse(timeList[count]);
                timeList[count] = DISPLAY_FORMAT.format(date);
                count++;

            }
        } catch (ParseException ex) {
            return null;
        }
        //Removing duplicates.
        timeList = new HashSet<>(Arrays.asList(timeList)).toArray(new String[0]);

        return timeList;
    }

    //Modifying the patient entry.
    public boolean patientModify(String date, String originalLast, String newLast, String originalFirst, String newFirst, String item, int quantity, String comments) {
        try {
            //Declaring and initializing variables and queries.
            Date convertedDate = (Date) DISPLAY_FORMAT.parse(date);
            String fixedDate = DATABASE_FORMAT.format(convertedDate);
            String patientsQuery = "UPDATE Patients SET last_name=?, first_name=?, quantity=?, comments=? WHERE time_stamp LIKE '" + fixedDate + "%' AND last_name= '" + originalLast + "' AND first_name='" + originalFirst + "' AND item_used='" + item + "'";
            String inventoryQuery = "UPDATE InventoryStats SET quantity=?, total_used=? WHERE item='" + item + "'";

            this.inventory = new InventoryMethods();
            this.database = new DatabaseMethods("DentalInventory");

            String[] statsValues = inventory.statsRow(item);
            String[] patientValues = patientRow(date, originalLast, originalFirst, item);

            int oldQuantity = Integer.parseInt(statsValues[1]);
            int oldTotalUsed = Integer.parseInt(statsValues[6]);
            int patientQuantity = Integer.parseInt(patientValues[4]);
            this.quantity = oldQuantity + patientQuantity - quantity;
            this.totalUsed = oldTotalUsed - patientQuantity + quantity;

            //Modifying the quantity of the item in InventoryStats dependent on the user entry.
            boolean inventorySuccess = database.updateQuantity(inventoryQuery, this.quantity, this.totalUsed);

            //Modifying Patients if inventory entry was successful.
            if (inventorySuccess == true) {
                boolean patientSuccess = database.patientsUpdate(patientsQuery, newLast, newFirst, quantity, comments);
                if (patientSuccess == true) {
                    return true;
                } else {
                    this.database.updateQuantity(inventoryQuery, oldQuantity, oldTotalUsed);
                    return false;
                }
            } else {
                return false;
            }

        } catch (NumberFormatException | ParseException e) {
            e.printStackTrace();
            return false;
        }

    }

    //Deleting a patient entry.
    public boolean patientDelete(String date, String lastName, String firstName, String item, boolean inventoryEdit) {
        try {
            //Declaring and initializing variables and queries.
            this.inventory = new InventoryMethods();
            Date convertedDate = (Date) DISPLAY_FORMAT.parse(date);
            String fixedDate = DATABASE_FORMAT.format(convertedDate);
            String patientDelete = "DELETE FROM Patients WHERE time_stamp LIKE '" + fixedDate + "%' AND last_name='" + lastName + "' AND first_name='" + firstName + "' AND item_used='" + item + "'";
            String inventoryQuery = "UPDATE InventoryStats SET quantity=?, total_used=? WHERE item='" + item + "'";
            String[] statsValues = inventory.statsRow(item);
            String[] patientValues = patientRow(date, lastName, firstName, item);

            int oldQuantity = Integer.parseInt(statsValues[1]);
            int oldTotalUsed = Integer.parseInt(statsValues[6]);
            int patientQuantity = Integer.parseInt(patientValues[4]);

            //Depending on user preference, modifying the inventory stats table.
            if (inventoryEdit == true) {
                this.quantity = oldQuantity + patientQuantity;
                this.totalUsed = oldTotalUsed - patientQuantity;
                this.database = new DatabaseMethods("DentalInventory");

                //Modifying the patient database.
                boolean success = database.deleteTable(patientDelete);

                //Modifying inventory stats dependent on the success of the patient query.
                if (success == true) {
                    boolean validity = database.updateQuantity(inventoryQuery, this.quantity, this.totalUsed);
                    return true;
                } else {
                    return false;
                }
            } else {
                //Deleting patient row.
                this.database = new DatabaseMethods("DentalInventory");
                boolean success = database.deleteTable(patientDelete);

                return success;
            }

        } catch (ParseException ex) {
            return false;
        }
    }

    //Getting the patient table to modify and display.
    public Object[][] patientView() {
        try {
            //Declaring variables, initializing values, and getting table as a two dimensional array.
            this.database = new DatabaseMethods("DentalInventory");
            String[] tableHeaders = {"time_stamp", "last_name", "first_name", "item_used", "Quantity", "comments"};
            Date date;
            Object[][] table = database.getData("Patients", tableHeaders);

            //Iterating through the table to format the date.
            for (int i = 0; i < table.length; i++) {
                //Shortening string.
                String uneditedString = table[i][0].toString();
                String shortenedString = uneditedString.substring(0, uneditedString.length() - 7);
                table[i][0] = shortenedString;

                //formatting string to display.
                date = (Date) DATABASE_FORMAT.parse(table[i][0].toString());
                table[i][0] = DISPLAY_FORMAT.format(date);
            }
            return table;
        } catch (ParseException ex) {
            return null;
        }
    }

    //test method.
    public static void main(String[] args) {
        PatientMethods methods = new PatientMethods();
        String[] list = methods.modifiedTimestamp();
        System.out.println("Length: " + list.length + " first value " + list[0] + " second value " + list[1]);
    }

    @Override
    public String toString() {
        return "PatientMethods{" + "lastName=" + lastName + ", firstName=" + firstName + ", item=" + item + ", quantity=" + quantity + ", timeStamp=" + timeStamp + ", restocks=" + restocks + ", totalUsed=" + totalUsed + ", lowType=" + lowType + '}';
    }

}
