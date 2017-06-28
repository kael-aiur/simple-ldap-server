package com.kael.ldap;

import leap.core.AppContext;

/**
 * @author kael.
 */
public class Server {

    private static AppContext app;
    
    public static void main(String[] args) throws Exception {
        app = AppContext.initStandalone();
        LdapServer server = app.getBeanFactory().getBean(LdapServer.class);
        server.start();
    }

}

