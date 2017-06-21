package kael.com.ldap.handler;

import org.apache.directory.api.ldap.model.message.ExtendedResponse;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.demux.MessageHandler;

/**
 * @author kael.
 */
public class ExtendedResponseHandler implements MessageHandler<ExtendedResponse> {
    @Override
    public void handleMessage(IoSession session, ExtendedResponse message) throws Exception {
        System.out.println(this.getClass().getName());
        System.out.println(message);
        System.out.println(this.getClass().getName());
    }
}
