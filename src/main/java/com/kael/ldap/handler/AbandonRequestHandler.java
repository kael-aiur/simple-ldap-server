package com.kael.ldap.handler;

import leap.core.annotation.Bean;
import leap.lang.logging.Log;
import leap.lang.logging.LogFactory;
import org.apache.directory.api.ldap.model.message.AbandonRequest;
import org.apache.mina.core.session.IoSession;

/**
 * @author kael.
 */
@Bean
public class AbandonRequestHandler implements MHandler<AbandonRequest> {
    private final Log log = LogFactory.get(this.getClass());
    @Override
    public void handleMessage(IoSession session, AbandonRequest message) throws Exception {
        log.debug(this.getClass().getName());
    }

    @Override
    public Type getProcessType() {
        return Type.RECEIVE;
    }
}
