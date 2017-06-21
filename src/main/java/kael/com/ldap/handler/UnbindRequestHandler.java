package kael.com.ldap.handler;

import org.apache.directory.api.ldap.model.message.UnbindRequest;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.demux.MessageHandler;

/**
 * @author kael.
 */
public class UnbindRequestHandler implements MessageHandler<UnbindRequest> {
    @Override
    public void handleMessage(IoSession session, UnbindRequest message) throws Exception {
        System.out.println(this.getClass().getName());
        System.out.println(message);
        System.out.println(this.getClass().getName());
        session.closeNow();
    }
}
