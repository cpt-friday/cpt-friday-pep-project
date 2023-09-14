package DAO;

import java.util.*;
import java.sql.*;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO implements DAO<Message> {

    @Override
    public Message getByID(int id) {
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "select * from message where message_id=?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, id);
            ResultSet rs = psmt.executeQuery();
            while(rs.next()){
                return new Message(id, rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getByAccID(int accID){
        Connection conn = ConnectionUtil.getConnection();
        List<Message> msgs = new ArrayList<>();
        try{
            String sql = "select * from message where posted_by=?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, accID);
            ResultSet rs = psmt.executeQuery();
            while(rs.next()){
                msgs.add(new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch")));
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return msgs;
    }

    @Override
    public List<Message> getAll() {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> msgs = new ArrayList<>();
        try{
            String sql = "select * from message";
            PreparedStatement psmt = conn.prepareStatement(sql);
            ResultSet rs = psmt.executeQuery();
            while(rs.next()){
                msgs.add(new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch")));
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return msgs;
    }

    @Override
    public Message save(Message t) {
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "insert into message (posted_by, message_text, time_posted_epoch) values (?, ?, ?)";
            PreparedStatement psmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            psmt.setInt(1, t.getPosted_by());
            psmt.setString(2, t.getMessage_text());
            psmt.setLong(3, t.getTime_posted_epoch());
            psmt.executeUpdate();
            ResultSet pkeys = psmt.getGeneratedKeys();
            if(pkeys.next()){
                int msgID = pkeys.getInt(1);
                return new Message(msgID, t.getPosted_by(), t.getMessage_text(), t.getTime_posted_epoch());
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    @Override
    public void update(int id, Message t) {
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "update message set posted_by=?, message_text=?, time_posted_epoch=? where message_id=?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, t.getPosted_by());
            psmt.setString(2, t.getMessage_text());
            psmt.setLong(3, t.getTime_posted_epoch());
            psmt.setInt(4, id);
            psmt.executeUpdate();
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "delete from message where message_id=?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, id);
            psmt.executeUpdate();
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
}
