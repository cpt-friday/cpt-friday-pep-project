package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accDAO;
    
    public AccountService(){
        accDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accDAO){
        this.accDAO = accDAO;
    }

    public Account addAccount(Account acc){
        accDAO.save(acc);
        return null; //todo: change
    }
    
}  
