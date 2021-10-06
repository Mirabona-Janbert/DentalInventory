/*
 * This class runs on startup to determine the proximity of items to low stock.
 */
package dentalinventory;

import java.util.ArrayList;

public class QuantityWarning {

    //Variable declarations.
    private String[] item;
    private InventoryMethods inventory;
    private DatabaseMethods database;
    private int quantity;
    private int lowStock;
    private String lowPreference;
    private ArrayList<String> lowStockItems;
    private ArrayList<String> belowStockItems;
    private String belowStock;
    private String lowStockString;
    private String outputString;

    //Call dependent constructors.
    public QuantityWarning(String[] item, InventoryMethods inventory, DatabaseMethods database, int quantity, int lowStock, String lowPreference, ArrayList<String> lowStockItems, ArrayList<String> belowStockItems, String belowStock, String lowStockString) {
        this.item = item;
        this.inventory = inventory;
        this.database = database;
        this.quantity = quantity;
        this.lowStock = lowStock;
        this.lowPreference = lowPreference;
        this.lowStockItems = lowStockItems;
        this.belowStockItems = belowStockItems;
        this.belowStock = belowStock;
        this.lowStockString = lowStockString;

    }

    public QuantityWarning() {
        this.quantity = 0;
        this.lowStock = 0;
        this.lowPreference = " ";
        this.belowStock = "";
        this.lowStockString = "";
    }

    //Mutators and Accessors
    public String[] getItem() {
        return item;
    }

    public void setItem(String[] item) {
        this.item = item;
    }

    public InventoryMethods getInventory() {
        return inventory;
    }

    public void setInventory(InventoryMethods inventory) {
        this.inventory = inventory;
    }

    public DatabaseMethods getDatabase() {
        return database;
    }

    public void setDatabase(DatabaseMethods database) {
        this.database = database;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getLowStock() {
        return lowStock;
    }

    public void setLowStock(int lowStock) {
        this.lowStock = lowStock;
    }

    public String getLowPreference() {
        return lowPreference;
    }

    public void setLowPreference(String lowPreference) {
        this.lowPreference = lowPreference;
    }

    public ArrayList<String> getLowStockItems() {
        return lowStockItems;
    }

    public void setLowStockItems(ArrayList<String> lowStockItems) {
        this.lowStockItems = lowStockItems;
    }

    public ArrayList<String> getBelowStockItems() {
        return belowStockItems;
    }

    public void setBelowStockItems(ArrayList<String> belowStockItems) {
        this.belowStockItems = belowStockItems;
    }

    public String getBelowStock() {
        return belowStock;
    }

    public void setBelowStock(String belowStock) {
        this.belowStock = belowStock;
    }

    public String getLowStockString() {
        return lowStockString;
    }

    public void setLowStockString(String lowStockString) {
        this.lowStockString = lowStockString;
    }

    public String getOutputString() {
        return outputString;
    }

    public void setOutputString(String outputString) {
        this.outputString = outputString;
    }

    //This method determines whether items are below or near low stock.
    public String quantityChecker() {
        //Declaring and initializing variables and objects.
        String quantityQuery = "SELECT quantity FROM InventoryStats WHERE item = ?";
        String preferenceQuery = "SELECT low_stock_choice FROM InventoryStats WHERE item=?";
        String lowQuery;
        this.inventory = new InventoryMethods();
        this.database = new DatabaseMethods("DentalInventory");
        this.item = inventory.itemList();
        this.lowStockItems = new ArrayList<>();
        this.belowStockItems = new ArrayList<>();
        this.belowStock = "";
        this.lowStockString = "";

        try {
            //While there are items in the array.
            for (int i = 0; i < item.length; i++) {
                this.lowPreference = database.valueSelect(preferenceQuery, item[i], "low_stock_choice");
                //Check user's preference for price type.
                if (lowPreference.equals("Average Price")) {
                    lowQuery = "SELECT average_low_stock FROM InventoryStock WHERE item = ?";
                    this.lowStock = Integer.parseInt(database.valueSelect(lowQuery, item[i], "average_low_stock"));
                    this.quantity = Integer.parseInt(database.valueSelect(quantityQuery, item[i], "quantity"));

                    //Add understocked items to a specific array.
                    if (quantity < lowStock) {
                        this.belowStockItems.add(item[i]);
                    } //Add items close to low stock to another specified array.
                    else {
                        if (quantity < (1.1 * lowStock)) {
                            lowStockItems.add(item[i]);

                        }
                    }
                } else {
                    lowQuery = "SELECT user_defined_low FROM InventoryStats WHERE item = ?";
                    this.lowStock = Integer.parseInt(database.valueSelect(lowQuery, item[i], "user_defined_low"));
                    this.quantity = Integer.parseInt(database.valueSelect(quantityQuery, item[i], "quantity"));

                    
                    //Add understocked items to a specific array.
                    if (quantity < lowStock) {
                        this.belowStockItems.add(item[i]);

                    } //Add items close to low stock to another specified array.
                    else {
                        
                        if (quantity < (1.1 * lowStock)) {
                            this.lowStockItems.add(item[i]);

                        }
                    }
                }
            }

            //Adding below stock items to a string.
            if (!belowStockItems.isEmpty()) {
                for (int i = 0; i < belowStockItems.size(); i++) {
                    this.belowStock = belowStock + belowStockItems.get(i) + ", ";
                }
                this.belowStock = belowStock.substring(0, belowStock.length() - 2);
            }

            //Adding near low stock items to a string.
            if (!lowStockItems.isEmpty()) {
                for (int i = 0; i < lowStockItems.size(); i++) {
                    this.lowStockString = lowStockString + lowStockItems.get(i) + ", ";
                }
                this.lowStockString = lowStockString.substring(0, lowStockString.length() - 2);
            }

            //Setting up return string depending upon whether items are below stock, low stock, both, or neither.
            if ("".equals(belowStock) && "".equals(lowStockString)) {
                this.outputString = "All items are at safe stock levels. No items need to be restocked.";
            } else {
                if ("".equals(belowStock) && !"".equals(lowStockString)) {
                    this.outputString = "Items near Low Stock: " + lowStockString;
                } else {
                    if (!"".equals(belowStock) && "".equals(lowStockString)) {
                        this.outputString = "Items below Low Stock: " + belowStock;
                    } else {
                        this.outputString = "Items near Low Stock: " + lowStockString + ".\n\nItems below Low Stock: " + belowStock;
                    }
                }
            }
        } catch (NumberFormatException e) {
            return "Error checking values.";
        }
        return outputString;
    }

    @Override
    public String toString() {
        return "QuantityWarning{" + "outputString=" + outputString + '}';
    }

}
