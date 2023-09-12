package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accDAO;
    public AccountService(){
        accDAO = new AccountDAO();
    }

    public Account addAccount(Account acc){
        if(acc.getUsername().isBlank() || acc.getPassword().length() < 4 || accDAO.getByUsername(acc.getUsername()) != null) return null;
        else return accDAO.save(acc);
    }

    public boolean accountExists(int id){
        return !(accDAO.getByID(id) == null);
    }

    public Account login(Account acc){
        Account test = accDAO.getByUsername(acc.getUsername());
        if(test == null) return null;
        acc.setAccount_id(test.getAccount_id());
        if(acc.equals(test)) return test;
        else return null;
    }
}