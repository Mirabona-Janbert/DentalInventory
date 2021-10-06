/*
 * This class was used to install the database and tables.
 */
package dentalinventory;

import java.util.Date;

/**
 *
 * @author Sourav
 */
public class DbInstall{
    public static void main(String [] args){
        //Declaring queries and object.
        DatabaseMethods runner = new DatabaseMethods("DentalInventory");
        String tableString = "CREATE TABLE Patients (time_stamp varchar(255) NOT NULL, "
                + "last_name varchar(255) NOT NULL, first_name varchar(255) NOT NULL, "
                + "item_used varChar(255), Quantity int, comments LONG VARCHAR, "
                + "PRIMARY KEY(time_stamp))";
        String tableString2 = "CREATE TABLE InventoryStats (item varchar(255), "
                + "quantity int, date_added varchar(255), unit_cost double, "
                + "restocks int, user_defined_low int, total_used int, "
                + "low_stock_choice varchar(255), PRIMARY KEY (item))";
        String tableString4 = "CREATE TABLE InventoryStock (item varchar(255), "
                + "average_low_stock int, average_high_stock int, PRIMARY KEY (item))";
        String tableString3 = "DROP TABLE InventoryStats";
        //runner.createTable(tableString2, "DentalInventory");
//        System.out.println(new Date());

        //Running query.
        runner.insertTable("INSERT INTO InventoryStock VALUES('Toothbrush', 0, 0)");
    }
}
//Patients
//InventoryStats
//InventoryStock
//DentalInventory