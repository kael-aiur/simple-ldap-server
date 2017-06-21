package kael.com.ldap;

import kael.com.ldap.handler.MsgHandler;
import org.apache.directory.api.ldap.codec.api.LdapApiService;
import org.apache.directory.api.ldap.codec.api.LdapDecoder;
import org.apache.directory.api.ldap.codec.api.LdapMessageContainer;
import org.apache.directory.api.ldap.codec.osgi.DefaultLdapCodecService;
import org.apache.directory.api.ldap.model.exception.ResponseCarryingMessageException;
import org.apache.directory.api.ldap.model.message.extended.NoticeOfDisconnect;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.demux.DemuxingIoHandler;
import org.apache.mina.handler.demux.ExceptionHandler;

/**
 * @author kael.
 */
public class LdapHandler extends DemuxingIoHandler {
    
    private LdapApiService service;

    public LdapHandler() {
        this.service = new DefaultLdapCodecService();
        for(MsgHandler h : MsgHandler.values()){
            switch (h.getType()){
                case RECEIVE:
                    addReceivedMessageHandler(h.getClzz(),h.getHandler());
                    break;
                case SENT:
                    addSentMessageHandler(h.getClzz(),h.getHandler());
                    break;
                default:
                    break;
            }
        }
        
        
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        session.setAttribute(LdapDecoder.MESSAGE_CONTAINER_ATTR,
                new LdapMessageContainer<>(service));
    }
    
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
        if ( cause.getCause() instanceof ResponseCarryingMessageException)
        {
            ResponseCarryingMessageException rcme = ( ResponseCarryingMessageException ) cause.getCause();

            if ( rcme.getResponse() != null )
            {
                session.write( rcme.getResponse() );
                return;
            }
        }
        session.write( NoticeOfDisconnect.PROTOCOLERROR );
        session.closeOnFlush();
    }
}
