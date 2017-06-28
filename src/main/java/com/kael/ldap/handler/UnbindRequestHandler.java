package com.kael.ldap.handler;

import leap.core.annotation.Bean;
import org.apache.directory.api.ldap.model.message.UnbindRequest;
import org.apache.mina.core.session.IoSession;

/**
 * @author kael.
 */
@Bean
public class UnbindRequestHandler implements MHandler<UnbindRequest> {
    @Override
    public void handleMessage(IoSession session, UnbindRequest message) throws Exception {
        System.out.println(this.getClass().getName());
        System.out.println("session:"+session);
        System.out.println(message);
        System.out.println(this.getClass().getName());
        session.closeNow();
    }

    @Override
    public Type getProcessType() {
        return Type.RECEIVE;
    }

}
