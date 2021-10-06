/*
 * This class runs on startup and checks if any items are nearing low stock,
 * calls the warning frame to run, and then calls the home frame.
 */
package dentalinventory;

public final class Startup {

    private HomeFrame runner;
    private QuantityWarning warningTest;
    private String stockCheck;

    public static void main(String[] args) {
        Startup start = new Startup();
    }

    public Startup() {
        this.runner = new HomeFrame();
        this.warningTest = new QuantityWarning();
        this.stockCheck = warningTest.quantityChecker();

        WarningFrame warning = new WarningFrame(stockCheck, "Message");
        warning.setBounds(200, 200, 800, 600);
    }

}
