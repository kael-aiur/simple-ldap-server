package com.kael.ldap.handler;

import com.kael.ldap.LdapConfig;
import leap.core.annotation.Bean;
import leap.core.annotation.Inject;
import leap.lang.Strings;
import org.apache.directory.api.ldap.model.entry.DefaultEntry;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.filter.ExprNode;
import org.apache.directory.api.ldap.model.filter.PresenceNode;
import org.apache.directory.api.ldap.model.message.LdapResult;
import org.apache.directory.api.ldap.model.message.Response;
import org.apache.directory.api.ldap.model.message.ResultCodeEnum;
import org.apache.directory.api.ldap.model.message.SearchRequest;
import org.apache.directory.api.ldap.model.message.SearchResultEntry;
import org.apache.directory.api.ldap.model.message.SearchResultEntryImpl;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.api.ldap.model.schema.AttributeType;
import org.apache.mina.core.session.IoSession;

import java.util.List;

/**
 * @author kael.
 */
@Bean
public class SearchRequestHandler implements MHandler<SearchRequest> {
    
    protected @Inject LdapConfig config;
    
    @Override
    public void handleMessage(IoSession session, SearchRequest request) throws Exception {
        if(!checkDnIsRoot(session,request)){
            emptyEntry(session,request);
            return;
        }
        List<String> attr = request.getAttributes();
        ExprNode node = request.getFilter();
        boolean typeOnly = request.getTypesOnly();
        
        if(node instanceof PresenceNode){
            AttributeType at = ((PresenceNode) node).getAttributeType();
            System.out.println(at);
            
        }
        
        
        LdapResult result = request.getResultResponse().getLdapResult();
        result.setResultCode(ResultCodeEnum.SUCCESS);
        
        //session.write(generateEntryResp(message,generateEntry("uid=admin,dc=bingo,dc=net")));
        session.write(request.getResultResponse());
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
    
    protected boolean checkDnIsRoot(IoSession session, SearchRequest request){
        Dn dn = request.getBase();
        if(null == dn || !Strings.equals(dn.getName(),config.getRootDn())){
            return false;
        }else{
            return true;
        }
    }
    
    
    protected void emptyEntry(IoSession session, SearchRequest request){
        LdapResult result = request.getResultResponse().getLdapResult();
        result.setResultCode(ResultCodeEnum.SUCCESS);
        session.write(request.getResultResponse());
    }
    
    @Override
    public Type getProcessType() {
        return Type.RECEIVE;
    }

}
