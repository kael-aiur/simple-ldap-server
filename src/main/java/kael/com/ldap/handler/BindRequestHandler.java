package kael.com.ldap.handler;

import org.apache.directory.api.ldap.model.message.BindRequest;
import org.apache.directory.api.ldap.model.message.LdapResult;
import org.apache.directory.api.ldap.model.message.ResultCodeEnum;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.api.util.Strings;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.demux.MessageHandler;

/**
 * @author kael.
 */
class BindRequestHandler implements MessageHandler<BindRequest> {
    @Override
    public void handleMessage(IoSession session, BindRequest message) throws Exception {
        System.out.println(this.getClass().getName());
        System.out.println("session:"+session);
        System.out.println(message);
        System.out.println(this.getClass().getName());
        
        String username = fetchName(message);
        String password = Strings.utf8ToString(message.getCredentials());
        System.out.println("username:"+username);
        System.out.println("password:"+password);
        LdapResult result = message.getResultResponse().getLdapResult();
        result.setResultCode(ResultCodeEnum.SUCCESS);
        //result.setMatchedDn(new Dn("cn=duke,uid=jduke,ou=Users"));
        result.setDiagnosticMessage("ok");
        session.write(message.getResultResponse());
    }
    
    protected String fetchName(BindRequest message){
        Dn dn = message.getDn();
        if(null != dn){
            String name = dn.getRdn().getValue();
            if(null != name){
                return name;
            }
        }
        return message.getName();
    }
    
}
