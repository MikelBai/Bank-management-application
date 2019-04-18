package ATM.User.Role.Privilege;

import ATM.Account.AccountFactory;
import ATM.Bank;
import ATM.FinanceProduct.ProductsFactory;
import ATM.User.Role.Privilege.CustomerPrivilege.AddAuthorizedUserPrivilege;
import ATM.User.Role.Privilege.CustomerPrivilege.RequestPrivilege.RequestAccountPrivilege;
import ATM.User.Role.Privilege.CustomerPrivilege.RequestPrivilege.RequestProductPrivilege;
import ATM.User.Role.Privilege.CustomerPrivilege.RequestPrivilege.RequestRevertPrivilege;
import ATM.User.Role.Privilege.CustomerPrivilege.SetForeignCurrencyPrivilege;
import ATM.User.Role.Privilege.CustomerPrivilege.TransactionPrivilege.*;
import ATM.User.Role.Privilege.CustomerPrivilege.ViewAccountPrivilege.ViewAccountInformationPrivilege;
import ATM.User.Role.Privilege.CustomerPrivilege.ViewAccountPrivilege.ViewAccountSummaryPrivilege;
import ATM.User.Role.Privilege.CustomerPrivilege.ViewAccountPrivilege.ViewProductInfoPrivilege;
import ATM.User.Role.Privilege.CustomerPrivilege.ViewExchangeRatesPrivilege;
import ATM.User.Role.Privilege.EmployeePrivilege.ATMMaintenancePrivilege.RestockATMPrivilege;
import ATM.User.Role.Privilege.EmployeePrivilege.ATMMaintenancePrivilege.ViewATMAlertPrivilege;
import ATM.User.Role.Privilege.EmployeePrivilege.ApproveRequestPrivilege.ApproveAccountCreationRequestPrivilege;
import ATM.User.Role.Privilege.EmployeePrivilege.ApproveRequestPrivilege.ApproveProductCreationRequestPrivilege;
import ATM.User.Role.Privilege.EmployeePrivilege.ApproveRequestPrivilege.ApproveRevertRequestPrivilege;
import ATM.User.Role.Privilege.EmployeePrivilege.CreateUserPrivilege;
import ATM.User.Role.Privilege.EmployeePrivilege.SetTimePrivilege;
import ATM.User.UserFactory;

import java.io.Serializable;

/**
 * A class used to instantiate privileges.
 */
public class PrivilegeFactory implements Serializable {

    private final AccountFactory accountFactory;
    private final ProductsFactory productsFactory;
    private UserFactory userFactory;

    /**
     * Initialize this PrivilegeFactory with GUI input/output objects and account/user factories.
     *
     * @param accountFactory A factory to generate new accounts, for account creation privileges.
     */
    public PrivilegeFactory(AccountFactory accountFactory, ProductsFactory productsFactory) {
        this.productsFactory = productsFactory;
        this.accountFactory = accountFactory;
    }

    public void setUserFactory(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    /**
     * Initialize a new privilege.
     *
     * @param privilegeType The new privilege type.
     * @param username      The name of the user exercising the privilege.
     * @param bank          The bank that the privilege operates on.
     * @return the new privilege, or null if an invalid privilegeType was given.
     */
    public Privilege getPrivilege(String privilegeType, String username, Bank bank) {
        Privilege privilege = null;
        switch (privilegeType) {
            case "Change Password":
                privilege = new ChangePasswordPrivilege(username, bank);
                break;
            case "Request Account":
                privilege = new RequestAccountPrivilege(username, bank, accountFactory);
                break;
            case "Request Revert":
                privilege = new RequestRevertPrivilege(username, bank);
                break;
            case "Request Product":
                privilege = new RequestProductPrivilege(username, bank, productsFactory);
                break;
            case "Deposit":
                privilege = new DepositPrivilege(username, bank);
                break;
            case "Transfer Between Accounts":
                privilege = new TransferBetweenAccountPrivilege(username, bank);
                break;
            case "Transfer to User":
                privilege = new TransferToUserPrivilege(username, bank);
                break;
            case "Pay Bill":
                privilege = new PayBillPrivilege(username, bank);
                break;
            case "Withdraw":
                privilege = new WithdrawPrivilege(username, bank);
                break;
            case "View Account Information":
                privilege = new ViewAccountInformationPrivilege(username, bank);
                break;
            case "View Account Summary":
                privilege = new ViewAccountSummaryPrivilege(username, bank);
                break;
            case "View Products info":
                privilege = new ViewProductInfoPrivilege(username, bank);
                break;
            case "Approve Account Creation":
                privilege = new ApproveAccountCreationRequestPrivilege(username, bank);
                break;
            case "Approve New Product":
                privilege = new ApproveProductCreationRequestPrivilege(username, bank);
                break;
            case "Approve Revert":
                privilege = new ApproveRevertRequestPrivilege(username, bank);
                break;
            case "Restock ATM":
                privilege = new RestockATMPrivilege(username, bank);
                break;
            case "View ATM Alerts":
                privilege = new ViewATMAlertPrivilege(username, bank);
                break;
            case "Create User":
                privilege = new CreateUserPrivilege(username, bank, userFactory, accountFactory);
                break;
            case "Set Time":
                privilege = new SetTimePrivilege(username, bank);
                break;
            case "Set Currency Type":
                privilege = new SetForeignCurrencyPrivilege(username, bank);
                break;
            case "Add Authorized User":
                privilege = new AddAuthorizedUserPrivilege(username, bank);
                break;
            case "View Exchange Rates":
                privilege = new ViewExchangeRatesPrivilege(username, bank);
                break;
        }
        return privilege;
    }
}
