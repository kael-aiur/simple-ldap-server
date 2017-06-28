package com.kael.ldap.handler;

import com.kael.ldap.LdapConfig;
import com.kael.ldap.UserStore;
import com.kael.ldap.model.BindOpCredentials;
import com.kael.ldap.model.BindOpUserPrincipal;
import leap.core.annotation.Bean;
import leap.core.annotation.Inject;
import leap.core.security.UserPrincipal;
import leap.orm.dao.Dao;
import org.apache.directory.api.ldap.model.message.BindRequest;
import org.apache.directory.api.ldap.model.message.LdapResult;
import org.apache.directory.api.ldap.model.message.ResultCodeEnum;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.api.util.Strings;
import org.apache.mina.core.session.IoSession;

import java.util.Objects;

/**
 * @author kael.
 */
@Bean
class BindRequestHandler implements MHandler<BindRequest> {
    
    public static final String AUTHC_UP_ATTR_NAME = "authenticated$user";
    
    protected @Inject LdapConfig config;
    protected @Inject UserStore store;
    
    @Override
    public void handleMessage(IoSession session, BindRequest request) throws Exception {
        
        if(!isAuthenticated(session,request)){
            BindOpCredentials credentials = new BindOpCredentials(config.getRootDn(),
                    request.getName(),Strings.utf8ToString(request.getCredentials()));
            authenticateAdmin(session,request,credentials);
        }else {
            if(!checkDn(request)){
                invalidCredentials(session,request,"root dn error");
                return;
            }
            BindOpCredentials credentials = parseCredentials(session,request);
            if(null == credentials){
                invalidCredentials(session,request,"both username and password is required.");
                return;
            }
            authenticateUser(session,request,credentials);
        }
    }
    
    protected boolean checkDn(BindRequest message){
        String dn = message.getDn().getName();
        
        return Strings.isNotEmpty(dn) && Strings.equals(dn,config.getRootDn());
    }
    
    protected BindOpCredentials parseCredentials(IoSession session, BindRequest request){
        String dn = request.getDn().getName();
        String username = request.getDn().getRdn().getValue();
        String password = Strings.utf8ToString(request.getCredentials());
        
        if(Strings.isEmpty(dn)||Strings.isEmpty(username)||Strings.isEmpty(password)){
            return null;
        }
        
        return new BindOpCredentials(dn,username,password);
    }
    
    protected void invalidCredentials(IoSession session, BindRequest request, String message){
        LdapResult result = request.getResultResponse().getLdapResult();
        result.setResultCode(ResultCodeEnum.INVALID_CREDENTIALS);
        result.setDiagnosticMessage(message);
        session.write(request.getResultResponse());
    }
    
    protected void bindSuccess(IoSession session, BindRequest request, String message){
        LdapResult result = request.getResultResponse().getLdapResult();
        result.setResultCode(ResultCodeEnum.SUCCESS);
        result.setDiagnosticMessage(message);
        session.write(request.getResultResponse());
    }
    
    protected boolean isAuthenticated(IoSession session, BindRequest request){
        return null != session.getAttribute(AUTHC_UP_ATTR_NAME);
    }
    
    protected void authenticateAdmin(IoSession session, BindRequest request,BindOpCredentials credentials){
        if(Strings.equals(credentials.getUsername(),config.getAdminUsername())
                && Strings.equals(credentials.getPassword(),config.getAdminPassword())){
            bindSuccess(session,request,"ok");
        }else {
            invalidCredentials(session,request,"error");
        }
    }

    protected void authenticateUser(IoSession session, BindRequest request,BindOpCredentials credentials){
        BindOpUserPrincipal up = store.loadByLoginName(credentials.getUsername(),null);
        if(null == up){
            invalidCredentials(session,request,"user not found");
            return;
        }else {
            Object password = up.getProperty("password");
            if(!Objects.equals(password,credentials.getPassword())){
                invalidCredentials(session,request,"user not found");
                return;
            }else {
                bindSuccess(session,request,"ok");
                return;
            }
        }
    }
    
    @Override
    public Type getProcessType() {
        return Type.RECEIVE;
    }
    
}
