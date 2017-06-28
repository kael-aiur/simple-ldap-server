package com.kael.ldap;

import com.kael.ldap.handler.MHandler;
import leap.core.BeanFactory;
import leap.core.annotation.Bean;
import leap.core.annotation.Inject;
import leap.core.ioc.PostCreateBean;
import org.apache.directory.api.ldap.codec.api.LdapApiService;
import org.apache.directory.api.ldap.codec.api.LdapDecoder;
import org.apache.directory.api.ldap.codec.api.LdapMessageContainer;
import org.apache.directory.api.ldap.codec.osgi.DefaultLdapCodecService;
import org.apache.directory.api.ldap.model.exception.ResponseCarryingMessageException;
import org.apache.directory.api.ldap.model.message.extended.NoticeOfDisconnect;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.demux.DemuxingIoHandler;

/**
 * @author kael.
 */
@Bean
public class LdapHandler extends DemuxingIoHandler implements PostCreateBean {
    
    protected LdapApiService service;

    protected @Inject MHandler[] handlers;
    
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

    @Override
    public void postCreate(BeanFactory factory) throws Throwable {
        this.service = new DefaultLdapCodecService();
        for(MHandler h : handlers){
            switch (h.getProcessType()){
                case RECEIVE:
                    addReceivedMessageHandler(h.genericType(),h);
                    break;
                case SENT:
                    addSentMessageHandler(h.genericType(),h);
                    break;
                default:
                    break;
            }
        }
    }
}
