package DAO;

import java.sql.*;
import java.util.*;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO implements DAO<Account> {

    @Override
    public Account getByID(int id) {
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "select * from account where account_id = ?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, id);
            ResultSet rs = psmt.executeQuery();
            while(rs.next()){
                Account acc = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return acc;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;

    }

    public Account getByUsername(String username){
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "select * from account where username = ?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, username);
            ResultSet rs = psmt.executeQuery();
            while(rs.next()){
                Account acc = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return acc;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public List<Account> getAll() {
        Connection conn = ConnectionUtil.getConnection();
        List<Account> accs = new ArrayList<>();
        try{
            String sql = "select * from account";
            PreparedStatement psmt = conn.prepareStatement(sql);
            ResultSet rs = psmt.executeQuery();
            while(rs.next()){
                accs.add(new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password")));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return accs;
    }

    @Override
    public Account save(Account t) {
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "insert into account (username, password) values (?, ?)";
            PreparedStatement psmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            psmt.setString(1, t.getUsername());
            psmt.setString(2, t.getPassword());
            psmt.executeUpdate();
            ResultSet pkeys = psmt.getGeneratedKeys();
            if(pkeys.next()){
                int accID = pkeys.getInt(1);
                return new Account(accID, t.getUsername(), t.getPassword());
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(int id, Account t) {
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "update account set username=?, password=?, WHERE account_id=?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, t.getUsername());
            psmt.setString(2, t.getPassword());
            psmt.setInt(3, id);
            psmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Account delete(int id) {
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "delete from account where account_id=?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, id);
            ResultSet rs = psmt.executeQuery();
            while(rs.next()){
                return new Account(rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
}
