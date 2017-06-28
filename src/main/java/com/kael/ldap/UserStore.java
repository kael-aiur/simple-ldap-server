package com.kael.ldap;

import com.kael.ldap.model.BindOpUserPrincipal;
import leap.core.annotation.Bean;
import leap.core.annotation.Inject;
import leap.core.value.Record;
import leap.lang.New;
import leap.orm.annotation.SqlKey;
import leap.orm.dao.DaoCommand;

import java.util.Map;

/**
 * @author kael.
 */
@Bean
public class UserStore {
    
    public static final String LOAD_BY_LOGIN_NAME_KEY = "security.findUserDetailsByLoginName";
    public static final String LOAD_BY_ID_KEY = "security.findUserDetailsById";
    @SqlKey(LOAD_BY_LOGIN_NAME_KEY)
    protected DaoCommand loadByLoginName;
    @SqlKey(LOAD_BY_ID_KEY)
    protected DaoCommand loadById;
    protected @Inject LdapConfig config;
    
    public BindOpUserPrincipal loadByLoginName(String loginName, Map<String, Object> params){
        if(null == params){
            params = New.hashMap();
        }
        Record record = loadByLoginName.createQuery()
                .param("loginName",loginName)
                .params(params).singleOrNull();
        return parse(record);
    }
    
    public BindOpUserPrincipal loadById(String id, Map<String, Object> params){
        if(null == params){
            params = New.hashMap();
        }
        Record record = loadById.createQuery()
                .param("userId",id)
                .params(params).singleOrNull();
        return parse(record);
    }
    
    protected BindOpUserPrincipal parse(Record record){
        if(null == record){
            return null;
        }
        String id = record.getString("id");
        String loginId = record.getString("loginId");
        String name = record.getString("name");
        BindOpUserPrincipal up = new BindOpUserPrincipal(id,loginId,name,config.getRootDn());
        up.addAll(record);
        return up;
    }
    
}
