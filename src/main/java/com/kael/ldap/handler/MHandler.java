package com.kael.ldap.handler;

import org.apache.mina.handler.demux.MessageHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author kael.
 */
public interface MHandler<E> extends MessageHandler<E> {
    enum Type{
        RECEIVE,
        SENT
    }
    
    default Class<?> genericType(){
        java.lang.reflect.Type t = ((ParameterizedType)this.getClass().getGenericInterfaces()[0])
                .getActualTypeArguments()[0];
        return (Class<?>)t;
    }
    
    Type getProcessType();
    
}
