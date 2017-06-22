package kael.com.ldap.handler;

import org.apache.mina.handler.demux.MessageHandler;

/**
 * @author kael.
 */
public enum MsgHandler {
    // receive
    BIND_REQ(new BindRequestHandler()),
    UNBIND_REQ(new UnbindRequestHandler()),
    ABANDON_ABLE_REQ(new AbandonRequestHandler()),
    SEARCH_REQ(new SearchRequestHandler()),
    // sent
    BIND_RESP(new BindResponseHandler()),
    EXT_RESP(new ExtendedResponseHandler()),
    SEARCH_ENTRY_RESP(new SearchEntryResponseHandler()),
    SEARCH_DONE_RESP(new SearchResultDoneResponseHandler());
    
    protected final MessageHandler handler;
    protected final Class<?> clzz;
    protected final MHandler.Type type;
    
    MsgHandler(MHandler handler) {
        this.handler = handler;
        this.clzz = handler.genericType();
        this.type = handler.getProcessType();
    }

    public MessageHandler getHandler(){
        return handler;
    }
    
    public Class<?> getClzz(){
        return clzz;
    }
    
    public MHandler.Type getType(){
        return type;
    }
}
