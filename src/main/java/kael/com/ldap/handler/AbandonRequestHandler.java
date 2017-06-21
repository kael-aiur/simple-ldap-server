package kael.com.ldap.handler;

import org.apache.directory.api.ldap.model.message.AbandonRequest;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.demux.MessageHandler;

/**
 * @author kael.
 */
public class AbandonRequestHandler implements MessageHandler<AbandonRequest> {
    @Override
    public void handleMessage(IoSession session, AbandonRequest message) throws Exception {
        System.out.println(this.getClass().getName());
        System.out.println("session:"+session);
        System.out.println(message);
        System.out.println(this.getClass().getName());
    }
}
