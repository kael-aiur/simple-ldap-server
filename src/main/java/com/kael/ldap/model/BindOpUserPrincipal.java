package com.kael.ldap.model;

import leap.core.security.UserPrincipal;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kael.
 */
public class BindOpUserPrincipal implements UserPrincipal {
    
    protected String name;
    protected String loginName;
    protected String id;

    protected String dn;

    protected Map<String, Object> ext = new HashMap<>();
    
    public BindOpUserPrincipal(String id, String loginName, String name, String dn) {
        this.name = name;
        this.loginName = loginName;
        this.id = id;
        this.dn = dn;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLoginName() {
        return loginName;
    }

    @Override
    public Object getId() {
        return id;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public void addAll(Map<String, Object> map){
        this.ext.putAll(map);
    }
    
    public Object getProperty(String key){
        return ext.get(key);
    }
}
