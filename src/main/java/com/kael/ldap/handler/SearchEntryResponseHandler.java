package com.kael.ldap.handler;

import leap.core.annotation.Bean;
import org.apache.directory.api.ldap.model.message.SearchResultEntry;
import org.apache.mina.core.session.IoSession;

/**
 * @author kael.
 */
@Bean
public class SearchEntryResponseHandler implements MHandler<SearchResultEntry> {
    @Override
    public void handleMessage(IoSession session, SearchResultEntry message) throws Exception {
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
