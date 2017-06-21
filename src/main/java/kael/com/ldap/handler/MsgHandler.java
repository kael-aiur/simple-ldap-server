package kael.com.ldap.handler;

import org.apache.directory.api.ldap.model.message.AbandonRequest;
import org.apache.directory.api.ldap.model.message.BindRequest;
import org.apache.directory.api.ldap.model.message.BindResponse;
import org.apache.directory.api.ldap.model.message.ExtendedResponse;
import org.apache.directory.api.ldap.model.message.SearchRequest;
import org.apache.directory.api.ldap.model.message.SearchResultDone;
import org.apache.directory.api.ldap.model.message.UnbindRequest;
import org.apache.mina.handler.demux.MessageHandler;

/**
 * @author kael.
 */
public enum MsgHandler {
    // receive
    BIND_REQ(Type.RECEIVE, BindRequest.class, new BindRequestHandler()),
    UNBIND_REQ(Type.RECEIVE, UnbindRequest.class, new UnbindRequestHandler()),
    ABANDON_ABLE_REQ(Type.RECEIVE, AbandonRequest.class, new AbandonRequestHandler()),
    SEARCH_REQ(Type.RECEIVE, SearchRequest.class, new SearchRequestHandler()),
    // sent
    BIND_RESP(Type.SENT, BindResponse.class, new BindResponseHandler()),
    EXT_RESP(Type.SENT, ExtendedResponse.class, new ExtendedResponseHandler()),
    SEARCH_DONE_RESP(Type.SENT, SearchResultDone.class, new SearchResultDoneResponseHandler());
    
    protected final MessageHandler handler;
    protected final Class<?> clzz;
    protected final Type type;
    
    MsgHandler(Type type, Class<?> clzz ,MessageHandler handler) {
        this.handler = handler;
        this.clzz = clzz;
        this.type = type;
    }

    public MessageHandler getHandler(){
        return handler;
    }
    
    public Class<?> getClzz(){
        return clzz;
    }
    
    public Type getType(){
        return type;
    }
    
    public enum Type{
        RECEIVE,
        SENT
    }
    
}
