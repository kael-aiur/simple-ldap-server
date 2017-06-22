package kael.com.ldap.handler;

import org.apache.mina.handler.demux.MessageHandler;

/**
 * @author kael.
 */
public interface MHandler<E> extends MessageHandler<E> {
    enum Type{
        RECEIVE,
        SENT
    }
    
    Class<?> genericType();
    
    Type getProcessType();
    
}
