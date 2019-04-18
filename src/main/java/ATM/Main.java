package ATM;

import ATM.Account.AccountFactory;
import ATM.Account.AccountManager;
import ATM.FinanceProduct.ProductManager;
import ATM.FinanceProduct.ProductsFactory;
import ATM.GUI.GraphicalInputReader;
import ATM.GUI.LoginDialog;
import ATM.GUI.MessageDisplay;
import ATM.Request.RequestManager;
import ATM.Session.Session;
import ATM.User.Role.Privilege.PrivilegeFactory;
import ATM.User.Role.RoleFactory;
import ATM.User.User;
import ATM.User.UserFactory;
import ATM.User.UserManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * The main program for the ATM.
 */
public class Main {

    private final static Frame frame = new JFrame();
    private final static GraphicalInputReader inputReader = new GraphicalInputReader(frame);
    private final static MessageDisplay messageDisplay = new MessageDisplay(frame);
    private final static String saveFileName = "externalFiles/BankData.ser";
    private static Bank bank;
    private static UserFactory userFactory;

    /**
     * Run the ATM program.
     */
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        loadBank();
        initializeFactories();
        initializeDefaultUser();

        User user;
        Session session;
        LoginDialog loginDialog;
        LoginManager loginManager = new LoginManager(bank.getUserManager());
        boolean running = true;
        while (running) {
            loginDialog = new LoginDialog(frame, loginManager);
            loginDialog.setVisible(true);
            if (loginDialog.isSucceeded()) {
                user = bank.getUserManager().getUser(loginDialog.getUsername());
                session = new Session(user, inputReader, messageDisplay);
                running = session.run();
            }
        }
        bank.getBankTimeManager().updateDate();
        if (bank.getBankTimeManager().isStartOfMonth()) {
            bank.getAccountManager().updateAll();
            bank.getProductManager().updateAll();
        }
        saveBank();
        frame.dispose();
    }

    /**
     * Deserialize the Bank save file, or generate a default bank if it doesn't exist.
     *
     * @throws ClassNotFoundException if a required class cannot be found in the code.
     */
    private static void loadBank() throws ClassNotFoundException {
        ObjectSerializer<Bank> serializer = new ObjectSerializer<Bank>();
        try {
            bank = serializer.readObject(saveFileName);
        } catch (IOException | ClassCastException e) {
            String message = ("The bank could not be loaded for the following reason:\n" +
                    e + "\n\nA default bank will be initialized. Please set the date on your first login.");
            messageDisplay.showErrorMessage(message, "Default bank");

            ATM atm = new ATM();
            AccountManager accountManager = new AccountManager();
            ProductManager productManager = new ProductManager();
            UserManager userManager = new UserManager();
            RequestManager requestManager = new RequestManager(accountManager, productManager);
            BankTimeManager timeManager = new BankTimeManager();
            bank = new Bank(atm, accountManager, productManager, userManager, requestManager, timeManager);
        }
    }

    /**
     * Initialize the factories required to instantiate bank objects.
     */
    private static void initializeFactories() {
        AccountFactory accountFactory = new AccountFactory(bank.getBankTimeManager());
        ProductsFactory productsFactory = new ProductsFactory(bank.getBankTimeManager(), bank.getAccountManager());
        PrivilegeFactory privilegeFactory = new PrivilegeFactory(accountFactory, productsFactory);
        RoleFactory roleFactory = new RoleFactory(privilegeFactory);
        userFactory = new UserFactory(bank, roleFactory);
        privilegeFactory.setUserFactory(userFactory);
    }

    /**
     * Initialize the default admin user if they do not exist.
     */
    private static void initializeDefaultUser() {
        if (!bank.getUserManager().containsUser("admin")) {
            User defaultUser = userFactory.getUser("Bank Manager", "admin", "admin");
            bank.getUserManager().addUser(defaultUser);
        }
    }

    /**
     * Serialize the Bank
     *
     * @throws IOException if an I/O exception occurs.
     */
    private static void saveBank() throws IOException {
        ObjectSerializer<Bank> serializer = new ObjectSerializer<Bank>();
        serializer.writeObject(bank, saveFileName);
    }
}
