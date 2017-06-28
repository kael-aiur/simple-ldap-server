package com.kael.ldap;

import leap.core.annotation.Bean;

/**
 * @author kael.
 */
@Bean
public class LdapConfig {
    
    protected String rootDn = "dc=bingosoft,dc=net";
    protected int port=10399;
    protected String adminUsername = "ldapAdmin";
    protected String adminPassword = "ldapPwd";

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getRootDn() {
        return rootDn;
    }

    public void setRootDn(String rootDn) {
        this.rootDn = rootDn;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
