package com.kael.ldap.handler;

import leap.core.annotation.Bean;
import org.apache.directory.api.ldap.model.message.SearchResultDone;
import org.apache.mina.core.session.IoSession;

/**
 * @author kael.
 */
@Bean
public class SearchResultDoneResponseHandler implements MHandler<SearchResultDone> {
    @Override
    public void handleMessage(IoSession session, SearchResultDone message) throws Exception {
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
