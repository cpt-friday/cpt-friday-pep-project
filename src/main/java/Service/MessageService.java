package Service;

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
}
