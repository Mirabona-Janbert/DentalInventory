/*
 * This class contains relevant modification methods for the inventory table.
 */
package dentalinventory;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class InventoryMethods {

    //Declaring variables.
    private String item;
    private int quantity;
    private String date_added;
    private double unit_cost;
    private int restocks;
    private int userLow;
    private int totalUsed;
    private String lowType;
    private DatabaseMethods database;

    //Constructors
    public InventoryMethods(String item, int quantity, String date_added, double unit_cost, int restocks, int userLow, int totalUsed, String lowType) {
        this.item = item;
        this.quantity = quantity;
        this.date_added = date_added;
        this.unit_cost = unit_cost;
        this.restocks = restocks;
        this.userLow = userLow;
        this.totalUsed = totalUsed;
        this.lowType = lowType;
    }

    public InventoryMethods() {
        this.item = " ";
        this.quantity = 0;
        this.date_added = " ";
        this.unit_cost = 0;
        this.restocks = 0;
        this.userLow = 0;
        this.totalUsed = 0;
        this.lowType = " ";
    }

    //Mutators and accessors.
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

    public String getDate_added() {
        return date_added;
    }

    public double getUnit_cost() {
        return unit_cost;
    }

    public void setUnit_cost(double unit_cost) {
        this.unit_cost = unit_cost;
    }

    public int getRestocks() {
        return restocks;
    }

    public void setRestocks(int restocks) {
        this.restocks = restocks;
    }

    public int getUserLow() {
        return userLow;
    }

    public void setUserLow(int userLow) {
        this.userLow = userLow;
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

    //Selecting the item column from InventoryStats.
    public String[] itemList() {
        //Initializing/declaring variables and query.
        String itemListString = "SELECT item FROM InventoryStats";
        this.database = new DatabaseMethods("DentalInventory");

        //Executing query.
        String[] column = database.columnSelect(itemListString, "InventoryStats", "item");
        return column;
    }

    //Setting up item adding to the database.
    public boolean addItem(String item, int quantity, double unitCost, int userLow, String lowType) {
        //Getting current date, initializing variables and queries.
        LocalDate date = LocalDate.now(ZoneId.of("America/Chicago"));
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        String formattedDate = date.format(formatter);
        this.restocks = 0;
        this.totalUsed = 0;
        String statsString = "INSERT INTO InventoryStats VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String stockString = "INSERT INTO InventoryStock VALUES (?, 0, 0)";

        //Executing queries by calling database methods.
        this.database = new DatabaseMethods("DentalInventory");
        boolean statsValidity = this.database.statsInsert(statsString, item, quantity, formattedDate,
                unitCost, this.restocks, userLow, this.totalUsed, lowType);
        boolean stockValidity = database.stockInsert(stockString, item);

        //Success dependent return statements.
        if (statsValidity == true && stockValidity == true) {
            return true;
        } else {
            return false;
        }
    }

    //Getting a specified item row from InventoryStats
    public String[] statsRow(String item) {
        //Initializing queries, array, and variables.
        String itemString = "SELECT * FROM InventoryStats WHERE item = '" + item + "'";
        String[] tableHeaders = {"item", "quantity", "date_added", "unit_cost", "restocks", "user_defined_low", "total_used", "low_stock_choice"};
        this.database = new DatabaseMethods("DentalInventory");

        //Executing query.
        String[] rowValues = this.database.rowSelect(itemString, "InventoryStats", tableHeaders);
        return rowValues;
    }

    //Getting row from InventoryStock
    public String[] stockRow(String item) {
        //Initializing query, column headers, and database methods call.
        String itemString = "SELECT * FROM InventoryStock WHERE item = '" + item + "'";
        String[] tableHeaders = {"item", "average_low_stock", "average_high_stock"};
        this.database = new DatabaseMethods("DentalInventory");

        //Executing query.
        String[] rowValues = this.database.rowSelect(itemString, "InventoryStats", tableHeaders);

        return rowValues;
    }

    //Modifying item values in InventoryStock and InventoryStats
    public boolean modifyItem(String originalName, String item, int quantity, double unitCost, int userLow, String lowType, boolean restock) {
        //Getting current values at item row in both Inventory tables.
        String[] statsValues = statsRow(originalName);
        String[] stockValues = stockRow(originalName);
        int restockCount = Integer.parseInt(statsValues[4]);

        //Declaring variables, query, and initializing DatabaseMethods object.
        int newLow;
        int newHigh;
        int finalAverageLow;
        int finalAverageHigh;
        String statsQuery = "UPDATE InventoryStats SET item = ?, "
                + "quantity = ?, unit_cost = ?, restocks = ?, "
                + "user_defined_low = ?, total_used = ?, "
                + "low_stock_choice = ? WHERE item='" + originalName + "'";
        String stockQuery = "UPDATE InventoryStock SET item = ?, average_low_stock=?, average_high_stock=? WHERE item='" + originalName + "'";
        String patientsQuery = "UPDATE Patients SET item_used = ? WHERE item_used = ?";
        this.database = new DatabaseMethods("DentalInventory");
        int oldTotalUsed = Integer.parseInt(statsValues[6]);
        int databaseQuantity = Integer.parseInt(statsValues[1]);

        try {
            //Depending on user choice, determine if action is a restock. 
            if (restock == true) {
                //Determine whether this is a first restock on the item or restocks
                //have occurred before.
                if (restockCount == 0) {
                    //If item has not been restocked, initalizing restockCount to 2.
                    restockCount = 2;
                    newLow = Integer.parseInt(statsValues[1]);
                    newHigh = quantity;

                    //Calculating average low stock by using quantity before restock and 
                    //weighting the average.
                    finalAverageLow = (int) (Integer.parseInt(statsValues[5]) 
                            + newLow) / restockCount;

                    //Calculating average high stock by averaging new quantity with 
                    //old low stock.
                    finalAverageHigh = (int) ((Integer.parseInt(stockValues[2])) 
                            + newHigh) / restockCount;

                    //Updating InventoryStock first and returning a boolean if it successful executes.
                    boolean stockValidity = this.database.stockUpdate(stockQuery, 
                            item, finalAverageLow, finalAverageHigh);

                    //Updating InventoryStats depending upon InventoryStock update success.
                    if (stockValidity == true) {
                        boolean statsValidity = this.database.statsUpdate(statsQuery, 
                                item, quantity, unitCost, restockCount, userLow, 
                                oldTotalUsed, lowType);
                        if (statsValidity == true) {
                            //Updating patient database item names only if InventoryStock and 
                            //InventoryStats succeed.
                            this.database.patientItem(patientsQuery, originalName, item);
                            return true;
                        } else {
                            //Restore previous InventoryStock values.
                            this.database.stockUpdate(stockQuery, stockValues[0], 
                                    Integer.parseInt(stockValues[1]), Integer.parseInt(stockValues[2]));
                            return false;
                        }
                    } else {
                        return false;
                    }

                } else {
                    //Calculating restock count by adding to current restock count.
                    restockCount = Integer.parseInt(statsValues[4]) + 1;

                    //Finding low stock by getting present quantity amount.
                    newLow = Integer.parseInt(statsValues[1]);

                    //High stock equals the new quantity.
                    newHigh = quantity;

                    //Calculating the average low and high stock by reweighting the current average
                    //low and high stocks.
                    finalAverageLow = (int) ((Integer.parseInt(stockValues[1]) 
                            * (restockCount - 1)) + newLow) / restockCount;
                    finalAverageHigh = (int) ((Integer.parseInt(stockValues[2]) 
                            * (restockCount - 1)) + newHigh) / restockCount;

                    //Executing InventoryStock update and returning a boolean dependent on success.
                    boolean stockValidity = this.database.stockUpdate(stockQuery, item, finalAverageLow, finalAverageHigh);

                    //Executing InventoryStats update if InventoryStock succeeds.
                    if (stockValidity == true) {
                        boolean statsValidity = this.database.statsUpdate(statsQuery, item, 
                                quantity, unitCost, restockCount, userLow, oldTotalUsed, lowType);
                        if (statsValidity == true) {
                            //Executing Patients update query if and only if 
                            //InventoryStock and InventoryStats succeed.
                            this.database.patientItem(patientsQuery, originalName, item);
                            return true;
                        } else {
                            //Reset InventoryStock values and return a failure.
                            this.database.stockUpdate(stockQuery, stockValues[0], 
                                    Integer.parseInt(stockValues[1]), Integer.parseInt(stockValues[2]));
                            return true;
                        }
                    } else {
                        return false;
                    }
                }
            } else {
                //Finding new total used depending on whether current quantity is less
                //than database quantity (has changed).
                if (quantity < databaseQuantity) {
                    oldTotalUsed = oldTotalUsed + (databaseQuantity - quantity);
                }

                //Updating InventoryStock and returning boolean depending upon success.
                boolean statsValidity = this.database.statsUpdate(statsQuery, item, 
                        quantity, unitCost, restockCount, userLow, oldTotalUsed, lowType);

                //Updating InventoryStats if InventoryStock succeeds.
                if (statsValidity == true) {
                    boolean stockValidity = this.database.stockUpdate(stockQuery, item, 
                            Integer.parseInt(stockValues[1]), Integer.parseInt(stockValues[2]));
                    if (stockValidity = true) {
                        //Executing Patients update query if and only if 
                        //InventoryStock and InventoryStats succeed.
                        this.database.patientItem(patientsQuery, originalName, item);
                        return true;
                    } else {
                        //Restore InventoryStock row to original values.
                        this.database.statsUpdate(statsQuery, statsValues[0], Integer.parseInt(statsValues[1]), 
                                Double.parseDouble(statsValues[3]), Integer.parseInt(statsValues[4]), 
                                Integer.parseInt(statsValues[5]), Integer.parseInt(statsValues[6]), statsValues[7]);
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }

    }

    //Deleting item from InventoryStock and InventoryStats.
    public boolean deleteItem(String item) {
        //Declaring queries and initializing database object.
        String deleteStats = "DELETE FROM InventoryStats WHERE item = '" + item + "'";
        String deleteStock = "DELETE FROM InventoryStock WHERE item = '" + item + "'";
        String deletePatient = "DELETE FROM Patients WHERE item_used = '" + item + "'";
        this.database = new DatabaseMethods("DentalInventory");

        //Executing deletion statement.
        boolean statsSuccess = this.database.deleteTable(deleteStats);
        boolean stockSuccess = this.database.deleteTable(deleteStock);
        boolean patientSuccess = this.database.deleteTable(deletePatient);

        //Returning boolean depending on the success of the statements.
        if (statsSuccess == true && stockSuccess == true && patientSuccess == true) {
            return true;
        } else {
            return false;
        }

    }

    //View InventoryStock
    public Object[][] stockView() {
        //Declaring and Initialzing variables and array.
        String[] columnNames = {"item", "average_low_stock", "average_high_stock"};
        String tableName = "InventoryStock";

        //Getting data
        this.database = new DatabaseMethods("DentalInventory");
        Object[][] data = this.database.getData(tableName, columnNames);

        return data;
    }

    //View InventoryStats
    public Object[][] statsView() {
        //Declaring and Initialzing variables and array.
        String[] columnNames = {"item", "quantity", "date_added", "unit_cost", "restocks", "user_defined_low", "total_used", "low_stock_choice"};
        String tableName = "InventoryStats";

        //Getting data
        this.database = new DatabaseMethods("DentalInventory");
        Object[][] data = this.database.getData(tableName, columnNames);

        return data;
    }

    //Computes useful statistics relating to a specified item.
    public String[] itemStats(String item) {
        //Getting InventoryStock and InventoryStats rows and declaring array 
        //to send to frame.
        String[] statsTableValues = statsRow(item);
        String[] stockTableValues = stockRow(item);
        String[] stats = new String[7];

        //Perform actions dependent on whether the average low stock, high stock,
        //and restock count are not 0.
        if (Integer.parseInt(statsTableValues[4]) > 0 && !"0".equals(stockTableValues[1]) && !"0".equals(stockTableValues[2])) {
            //Current quantity;
            stats[0] = statsTableValues[1];

            //Calculating total cost to restock.
            Double cost = Double.parseDouble(statsTableValues[3]) * (Double.parseDouble(stockTableValues[2]) - Double.parseDouble(statsTableValues[1]));

            //Setting array value depending on whether the cost is positive or 
            //negative.
            if (cost > 0) {
                stats[1] = Double.toString(cost);
            } else {
                stats[1] = item + " is already over maximum stock.";
            }
            stats[2] = Integer.toString(100 * (Integer.parseInt(statsTableValues[1])) / Integer.parseInt(stockTableValues[2]));

            //Finding current date and properly formatting.
            LocalDate today = LocalDate.now(ZoneId.of("America/Chicago"));
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
            LocalDate dateAdded = LocalDate.parse(statsTableValues[2], formatter);

            //Finding the time difference between the current date and the 
            //original date of the add.
            Period timeDifference = Period.between(dateAdded, today);
            int differenceValue = timeDifference.getDays();

            //Calculating rate of usage.
            int rateValue = Integer.parseInt(statsTableValues[6]) / differenceValue;

            //Adding array depending on whether the rate is 0
            if (rateValue == 0) {
                stats[3] = rateValue + " per day (The item has not been used.)";
            } else {
                stats[3] = Integer.toString(Integer.parseInt(statsTableValues[6]) / differenceValue) + " per day";
            }

            //Getting links to restock item.
            String[] links = linkBuilder(item);
            stats[4] = links[0];
            stats[5] = links[1];
            stats[6] = links[2];
            return stats;
        } else {
            //Setting quantity.
            stats[0] = statsTableValues[1];

            //Informing user of insufficient usage to calculate.
            stats[1] = "Not Available. Restock the item to track this statistic.";
            stats[2] = "Not Available. Restock the item to track this statistic.";

            try {
                //Getting and formatting current date and original date.
                LocalDate today = LocalDate.now(ZoneId.of("America/Chicago"));
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
                LocalDate dateAdded = LocalDate.parse(statsTableValues[2], formatter);

                //Finding time difference between original and current date.
                Period timeDifference = Period.between(dateAdded, today);
                int differenceValue = timeDifference.getDays();

                //Calculating usage rate.
                int rateValue = Integer.parseInt(statsTableValues[6]) / differenceValue;
                if (rateValue == 0) {
                    stats[3] = rateValue + " per day (The item has not been used.)";
                } else {
                    stats[3] = Integer.toString(Integer.parseInt(statsTableValues[6]) / differenceValue) + " per day";
                }

            } catch (NumberFormatException | ArithmeticException e) {
                stats[3] = "Not available. Use the item for a longer period of time.";
            }

            //Adding links.
            String[] links = linkBuilder(item);
            stats[4] = links[0];
            stats[5] = links[1];
            stats[6] = links[2];
            return stats;
        }
    }

    //Creating links to access item purchasing websites.
    public String[] linkBuilder(String item) {
        item = item.replaceAll(" ", "%20");
        String[] links = new String[3];
        links[0] = "https://www.amazon.com/s?k=" + item + "&ref=nb_sb_noss_2";
        links[1] = "https://www.3m.com/3M/en_US/company-us/search/?Ntt=" + item;
        links[2] = "https://www.henryschein.com/us-en/Search.aspx?searchkeyWord=" + item;

        return links;
    }

    @Override
    public String toString() {
        return "InventoryMethods{" + "item=" + this.item + ", quantity=" + this.quantity + ", date_added=" + this.date_added + ", unit_cost=" + this.unit_cost + ", restocks=" + this.restocks + ", userLow=" + this.userLow + ", totalUsed=" + this.totalUsed + ", lowType=" + this.lowType + '}';
    }

}
