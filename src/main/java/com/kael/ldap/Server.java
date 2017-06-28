package com.kael.ldap;

/**
 * @author kael.
 */
public class Server {

    public static void main(String[] args) throws Exception {
        LdapServer server = new LdapServer();
        server.start();
    }

}

