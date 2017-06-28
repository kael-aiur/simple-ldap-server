package com.kael.ldap.model;

/**
 * @author kael.
 */
public class BindOpCredentials {
    protected String baseDn;
    protected String username;
    protected String password;

    public BindOpCredentials(String baseDn, String username, String password) {
        this.baseDn = baseDn;
        this.username = username;
        this.password = password;
    }

    public String getBaseDn() {
        return baseDn;
    }

    public void setBaseDn(String baseDn) {
        this.baseDn = baseDn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
