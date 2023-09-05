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
    public void save(Account t) {
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "insert into account (username, password) values (?, ?)";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, t.getUsername());
            psmt.setString(2, t.getPassword());
            psmt.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Account t, String[] params) {
        // params = {sample_user, sample_pass}
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "update account set username=?, password=?, WHERE account_id=?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, params[0]);
            psmt.setString(2, params[1]);
            psmt.setInt(3, t.getAccount_id());
            psmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Account t) {
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "delete from account where account_id=?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, t.getAccount_id());
            psmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
}
