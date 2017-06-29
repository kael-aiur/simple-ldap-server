package com.kael.ldap.handler;

import leap.core.annotation.Bean;
import leap.lang.logging.Log;
import leap.lang.logging.LogFactory;
import org.apache.directory.api.ldap.model.message.UnbindRequest;
import org.apache.mina.core.session.IoSession;

/**
 * @author kael.
 */
@Bean
public class UnbindRequestHandler implements MHandler<UnbindRequest> {

    private final Log log = LogFactory.get(this.getClass());
    
    @Override
    public void handleMessage(IoSession session, UnbindRequest message) throws Exception {
        log.debug(this.getClass().getName());
        session.closeNow();
    }

    @Override
    public Type getProcessType() {
        return Type.RECEIVE;
    }

}
