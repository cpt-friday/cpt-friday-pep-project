package Service;

import DAO.MessageDAO;

public class MessageService {
    MessageDAO msgDAO;
    public MessageService(){
        msgDAO = new MessageDAO();
    }
}
