package com.kael.ldap.handler;

import leap.core.annotation.Bean;
import org.apache.directory.api.ldap.model.message.ExtendedResponse;
import org.apache.mina.core.session.IoSession;

/**
 * @author kael.
 */
@Bean
public class ExtendedResponseHandler implements MHandler<ExtendedResponse> {
    @Override
    public void handleMessage(IoSession session, ExtendedResponse message) throws Exception {
        System.out.println(this.getClass().getName());
        System.out.println("session:"+session);
        System.out.println(message);
        System.out.println(this.getClass().getName());
    }

    @Override
    public Type getProcessType() {
        return Type.SENT;
    }
}
