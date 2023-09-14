package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    MessageDAO msgDAO;
    public MessageService(){
        msgDAO = new MessageDAO();
    }

    public Message postMessage(Message msg){
        if(msg.message_text.isBlank() || msg.message_text.length() >= 255) return null;
        else return msgDAO.save(msg);
    }

    public List<Message> getAllMessages(){
        return msgDAO.getAll();
    }

    public Message getMessageByID(int id){
        return msgDAO.getByID(id);
    }

    public Message deleteMessage(int id){
        return msgDAO.delete(id);
    }

    public Message updateMessage(int id, Message msg){
        Message base = getMessageByID(id);
        if(base == null || msg.getMessage_text().isBlank() || msg.getMessage_text().length() > 255) return null;
        msgDAO.update(id, base);
        return getMessageByID(id);
    }

    public List<Message> getMessagesFromAccount(int accID){
        return msgDAO.getByAccID(accID);
    }
}
