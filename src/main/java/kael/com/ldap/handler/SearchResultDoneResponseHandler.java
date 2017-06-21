package kael.com.ldap.handler;

import org.apache.directory.api.ldap.model.message.SearchResultDone;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.demux.MessageHandler;

/**
 * @author kael.
 */
public class SearchResultDoneResponseHandler implements MessageHandler<SearchResultDone> {
    @Override
    public void handleMessage(IoSession session, SearchResultDone message) throws Exception {
        System.out.println(this.getClass().getName());
        System.out.println("session:"+session);
        System.out.println(message);
        System.out.println(this.getClass().getName());
    }
}
