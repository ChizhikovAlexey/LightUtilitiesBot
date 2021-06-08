package chizhikov.utilitiesbot.bot.exceptions;

public class MessageProcessingException extends Exception{
    public MessageProcessingException(){
        super();
    }

    public MessageProcessingException(String reason){
        super(reason);
    }

    public MessageProcessingException(String reason, Exception cause){
        super(reason, cause);
    }
}
