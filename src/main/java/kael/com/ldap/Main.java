package kael.com.ldap;

import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Properties;

/**
 * @author kael.
 */
public class Main {
    enum Type{
        SEARCH,BIND
    }
    
    public static void main(String[] args) throws LdapException, CursorException, NamingException {
        Type t = Type.SEARCH;
        switch (t){
            case BIND:
                testBind();
                break;
            case SEARCH:
                testSearch();
                break;
            default:
                throw new UnsupportedOperationException(t.name());
        }
    }
    
    protected static void testBind(){
        final Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.PROVIDER_URL, "ldap://[::1]:10399");
        env.put(Context.SECURITY_PRINCIPAL, "admin");
        env.put(Context.SECURITY_CREDENTIALS, "pass");
        int exitCode = 2;
        try {
            final LdapContext ctx = new InitialLdapContext(env, null);
            System.out.println("User is authenticated");
            exitCode = 0;
            ctx.close();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        System.exit(exitCode);
    }
    
    protected static void testSearch() throws NamingException {
        final String ldapUrl = "ldap://[::1]:10399";
        final Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapUrl);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
        env.put(Context.SECURITY_CREDENTIALS, "secret");
        final LdapContext ctx = new InitialLdapContext(env, null);
        // ctx.setRequestControls(null);
        final SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        NamingEnumeration<?> namingEnum = ctx.search("dc=jboss,dc=org", "(uid=*)", searchControls);
        while (namingEnum.hasMore()) {
            SearchResult sr = (SearchResult) namingEnum.next();
            Attributes attrs = sr.getAttributes();
            System.out.println(attrs.get("cn"));
        }
        namingEnum.close();
        ctx.close();
    }
}
