package com.kael.ldap.sql;

import leap.core.annotation.Bean;
import leap.lang.Strings;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kael.
 */
@Bean
public class FieldMappingStrategy {
    
    private Map<String, String> ldapToSql = new HashMap<>();
    private Map<String, String> sqlToLdap = new HashMap<>();
    
    {
        setLdapToSql("uid","login_id");
        setLdapToSql("cn","name");
        //setLdapToSql("sn","id");
        //setLdapToSql("displayName","name");
        setLdapToSql("mail","email");
        setLdapToSql("objectclass","*");
        setLdapToSql("sAMAccountName","login_id");
        
        setSqlToLdap("loginId","uid");
    }
    
    public void setLdapToSql(String ldap, String sql){
        ldapToSql.put(ldap,sql);
    }
    public void setSqlToLdap(String sql, String ldap){
        sqlToLdap.put(sql,ldap);
    }
    public String getSqlField(String ldap){
        String field = ldapToSql.get(ldap);
        if(null == field){
            return ldap;
        }
        return field;
    }
    public String getLdapField(String sql){
        String field = sqlToLdap.get(sql);
        if(null == field){
            for(Map.Entry<String, String> entry : ldapToSql.entrySet()){
                if(Strings.equals(entry.getValue(),sql)){
                    return entry.getKey();
                }
            }
        }
        if(null == field){
            return sql;
        }
        return field;
    }
    
}
