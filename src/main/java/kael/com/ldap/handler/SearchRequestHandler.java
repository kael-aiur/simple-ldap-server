package kael.com.ldap.handler;

import org.apache.directory.api.ldap.model.message.LdapResult;
import org.apache.directory.api.ldap.model.message.ResultCodeEnum;
import org.apache.directory.api.ldap.model.message.SearchRequest;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.demux.MessageHandler;

/**
 * @author kael.
 */
public class SearchRequestHandler implements MessageHandler<SearchRequest> {
    @Override
    public void handleMessage(IoSession session, SearchRequest message) throws Exception {
        System.out.println(this.getClass().getName());
        System.out.println(message);
        System.out.println(this.getClass().getName());
        LdapResult result = message.getResultResponse().getLdapResult();
        result.setResultCode(ResultCodeEnum.SUCCESS);
        session.write(message.getResultResponse());
    }
}
