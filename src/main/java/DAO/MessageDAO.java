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
                Message mss = new Message(id, rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return mss;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
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
            e.printStackTrace();
        }
        return msgs;
    }

    @Override
    public void save(Message t) {
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "insert into message (posted_by, message_text, time_posted_epoch) values (?, ?, ?)";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, t.getPosted_by());
            psmt.setString(2, t.getMessage_text());
            psmt.setLong(3, t.getTime_posted_epoch());
            psmt.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Message t, String[] params) {
        // params = {sample_poster, sample_text, sample_epoch}
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "update message set posted_by=?, message_text=?, time_posted_epoch=? when message_id=?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, Integer.parseInt(params[0]));
            psmt.setString(2, params[1]);
            psmt.setLong(3, Long.parseLong(params[2]));
            psmt.setInt(4, t.getMessage_id());
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Message t) {
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "delete from message where message_id=?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, t.getMessage_id());
            psmt.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    
}
