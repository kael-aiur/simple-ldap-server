package kael.com.ldap.handler;

import org.apache.directory.api.ldap.model.message.BindResponse;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.demux.MessageHandler;

/**
 * @author kael.
 */
public class BindResponseHandler implements MessageHandler<BindResponse> {
    @Override
    public void handleMessage(IoSession session, BindResponse message) throws Exception {
        System.out.println(this.getClass().getName());
        System.out.println(message);
        System.out.println(this.getClass().getName());
    }
}
