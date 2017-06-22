package kael.com.ldap.handler;

import org.apache.directory.api.ldap.model.constants.SchemaConstants;
import org.apache.directory.api.ldap.model.entry.DefaultEntry;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.exception.LdapInvalidDnException;
import org.apache.directory.api.ldap.model.message.LdapResult;
import org.apache.directory.api.ldap.model.message.Response;
import org.apache.directory.api.ldap.model.message.ResultCodeEnum;
import org.apache.directory.api.ldap.model.message.SearchRequest;
import org.apache.directory.api.ldap.model.message.SearchResultEntry;
import org.apache.directory.api.ldap.model.message.SearchResultEntryImpl;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.mina.core.session.IoSession;

/**
 * @author kael.
 */
public class SearchRequestHandler implements MHandler<SearchRequest> {
    @Override
    public void handleMessage(IoSession session, SearchRequest message) throws Exception {
        System.out.println(this.getClass().getName());
        System.out.println("session:"+session);
        System.out.println(message);
        System.out.println(this.getClass().getName());
        LdapResult result = message.getResultResponse().getLdapResult();
        result.setResultCode(ResultCodeEnum.SUCCESS);
        session.write(generateEntryResp(message,generateEntry("uid=admin,dc=bingo,dc=net")));
        session.write(message.getResultResponse());
    }

    protected Entry generateEntry(String dn) throws LdapException {
        DefaultEntry entry = new DefaultEntry(dn);
        
        entry.add("cn","123");
        
        return entry;
    }
    
    protected Response generateEntryResp(SearchRequest req, Entry entry){
        SearchResultEntry respEntry;
        respEntry = new SearchResultEntryImpl( req.getMessageId() );
        respEntry.setEntry( entry );
        respEntry.setObjectName( entry.getDn() );
        return respEntry;
    }
    
    @Override
    public Type getProcessType() {
        return Type.RECEIVE;
    }

}
