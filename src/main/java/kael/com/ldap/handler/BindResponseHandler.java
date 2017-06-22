package kael.com.ldap.handler;

import org.apache.directory.api.ldap.model.message.BindResponse;
import org.apache.mina.core.session.IoSession;

/**
 * @author kael.
 */
public class BindResponseHandler implements MHandler<BindResponse> {
    @Override
    public void handleMessage(IoSession session, BindResponse message) throws Exception {
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
