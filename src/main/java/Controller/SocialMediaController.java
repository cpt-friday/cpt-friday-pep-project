package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accServ;
    MessageService msgServ;
    public SocialMediaController(){
        accServ = new AccountService();
        msgServ = new MessageService(); 
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("register", this::registerHandler);
        app.post("login", this::loginHandler);
        app.post("messages", this::postMessageHandler);
        app.get("messages", this::getAllMessagesHandler);
        app.get("messages/{message_id}", this::getMessageByIDHandler);
        app.delete("messages/{message_id}", this::deleteMessageByIDHandler);
        app.patch("messages/{message_id}", this::updateMessageByIDHandler);
        app.get("accounts/{account_id}/messages", this::getMessagesFromAccountHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(ctx.body(), Account.class);
        Account added = accServ.addAccount(acc);
        if(added == null) ctx.status(400);
        else ctx.json(mapper.writeValueAsString(added));
    }

    private void loginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(ctx.body(), Account.class);
        Account logged = accServ.login(acc);
        if(logged == null) ctx.status(401);
        else ctx.json(mapper.writeValueAsString(logged));
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(ctx.body(), Message.class);
        if(!accServ.accountExists(msg.getPosted_by())) ctx.status(400);
        Message posted = msgServ.postMessage(msg);
        if(posted == null) ctx.status(400);
        else ctx.json(mapper.writeValueAsString(posted));
    }

    private void getAllMessagesHandler(Context ctx){
        ctx.json(msgServ.getAllMessages());
    }

    private void getMessageByIDHandler(Context ctx){
        int messageID = Integer.parseInt(ctx.pathParam("message_id"));
        Message found = msgServ.getMessageByID(messageID);
        if(found == null) ctx.status(200);
        else ctx.json(found);
    }

    private void deleteMessageByIDHandler(Context ctx){
        int messageID = Integer.parseInt(ctx.pathParam("message_id"));
        Message archived = msgServ.deleteMessage(messageID);
        if(archived == null) ctx.status(200);
        else ctx.json(archived);

    }

    private void updateMessageByIDHandler(Context ctx) throws JsonProcessingException{
        int messageID = Integer.parseInt(ctx.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message update = mapper.readValue(ctx.body(), Message.class);
        Message finished = msgServ.updateMessage(messageID, update);
        if(finished == null) ctx.status(400);
        else ctx.json(finished);
    }

    private void getMessagesFromAccountHandler(Context ctx){
        int accountID = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.json(msgServ.getMessagesFromAccount(accountID));
    }
}