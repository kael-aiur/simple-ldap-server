package com.kael.ldap.handler;

import leap.core.annotation.Bean;
import leap.lang.logging.Log;
import leap.lang.logging.LogFactory;
import org.apache.directory.api.ldap.model.message.BindResponse;
import org.apache.mina.core.session.IoSession;

/**
 * @author kael.
 */
@Bean
public class BindResponseHandler implements MHandler<BindResponse> {
    private final Log log = LogFactory.get(this.getClass());
    @Override
    public void handleMessage(IoSession session, BindResponse message) throws Exception {
        log.debug(this.getClass().getName());
    }

    @Override
    public Type getProcessType() {
        return Type.SENT;
    }
    
}
