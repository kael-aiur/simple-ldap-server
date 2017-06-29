package com.kael.ldap.handler;

import com.kael.ldap.LdapConfig;
import com.kael.ldap.sql.FieldMappingStrategy;
import com.kael.ldap.sql.Sql;
import com.kael.ldap.sql.SqlBuilder;
import com.kael.ldap.sql.SqlNode;
import com.kael.ldap.sql.filter.AndSqlNode;
import com.kael.ldap.sql.filter.EqSqlNode;
import com.kael.ldap.sql.select.TableSqlNode;
import leap.core.annotation.Bean;
import leap.core.annotation.Inject;
import leap.core.value.Record;
import leap.lang.New;
import leap.lang.Strings;
import leap.orm.dao.Dao;
import org.apache.directory.api.ldap.model.entry.DefaultEntry;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.filter.FilterVisitor;
import org.apache.directory.api.ldap.model.message.LdapResult;
import org.apache.directory.api.ldap.model.message.Response;
import org.apache.directory.api.ldap.model.message.ResultCodeEnum;
import org.apache.directory.api.ldap.model.message.SearchRequest;
import org.apache.directory.api.ldap.model.message.SearchResultEntry;
import org.apache.directory.api.ldap.model.message.SearchResultEntryImpl;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.mina.core.session.IoSession;

import java.util.List;
import java.util.Objects;

/**
 * @author kael.
 */
@Bean
public class SearchRequestHandler implements MHandler<SearchRequest> {
    
    protected @Inject LdapConfig config;
    protected @Inject FilterVisitor visitor;
    protected @Inject Dao dao;
    protected @Inject FieldMappingStrategy strategy;
    
    @Override
    public void handleMessage(IoSession session, SearchRequest request) throws Exception {
        if(!checkDnIsRoot(session,request.getBase())){
            Dn base = request.getBase();
            if(null == base || !checkDnIsRoot(session,base.getParent())){
                //emptyEntry(session,request);
                //return;
            }
            String type = base.getRdn().getNormType();
            String uid = base.getRdn().getValue();
            
            SqlBuilder builder = new SqlBuilder(strategy);
            builder.setTable(new TableSqlNode(config.getUserTable()));
            builder.select(New.arrayList("id, login_id, name,email"));
            AndSqlNode n = new AndSqlNode();
            n.setChildren(new SqlNode[]{new EqSqlNode(strategy.getSqlField(type),uid)});
            builder.setWhereNode(n);
            Sql sql = builder.build();
            Record record = dao.createSqlQuery(sql.getSql()).params(sql.getParams()).singleOrNull();
            Entry entry = generateEntry(record);
            session.write(generateEntryResp(request,entry));
            LdapResult result = request.getResultResponse().getLdapResult();
            result.setResultCode(ResultCodeEnum.SUCCESS);
            session.write(request.getResultResponse());
            return;
        }
        
        SqlBuilder builder = new SqlBuilder(strategy);
        builder.select(request.getAttributes());
        SqlNode where = (SqlNode)request.getFilter().accept(visitor);
        builder.setWhereNode(where);
        builder.setTable(new TableSqlNode(config.getUserTable()));
        
        Sql sql = builder.build();
        List<Record> records = dao.createSqlQuery(sql.getSql()).params(sql.getParams()).list();
        
        records.forEach(record -> {
            try {
                Entry entry = generateEntry(record);
                
                Response entryResp = generateEntryResp(request,entry);
                session.write(entryResp);
            } catch (LdapException e) {
                throw new RuntimeException(e);
            }
        });
        LdapResult result = request.getResultResponse().getLdapResult();
        result.setResultCode(ResultCodeEnum.SUCCESS);
        session.write(request.getResultResponse());
    }

    protected Entry generateEntry(Record record) throws LdapException {
        DefaultEntry entry = new DefaultEntry(buildUserDn(Objects.toString(record.get("loginId"))));
        
        entry.add("uid",record.getString("loginId"));
        entry.add("cn",record.getString("name"));
        entry.add("id",record.getString("id"));
        entry.add("email",record.getString("email"));
        entry.add("mail",record.getString("email"));
        entry.add("sAMAccountName",record.getString("loginId"));
        /*
        DefaultEntry entry = new DefaultEntry(generateUserDn(ud,config.getRootDn()));
        entry.add("uid",ud.getLoginName());
        entry.add("cn",ud.getName());
        entry.add("id",ud.getIdAsString());
        entry.add("email",ud.getLoginName()+"@bingosoft.net");
        entry.add("mail",ud.getLoginName()+"@bingosoft.net");
        entry.add("sAMAccountName",ud.getLoginName());
        */
        /*
        record.entrySet().forEach(e -> {
            try {
                String upid = strategy.getLdapField(e.getKey());
                String val = Objects.toString(e.getValue());
                if(null != val && !val.isEmpty()){
                    entry.add(upid, val);
                }
            } catch (LdapException e1) {
                throw new RuntimeException(e1);
            }
        });
        */
        return entry;
    }
    
    protected Response generateEntryResp(SearchRequest req, Entry entry){
        SearchResultEntry respEntry;
        respEntry = new SearchResultEntryImpl( req.getMessageId() );
        respEntry.setEntry( entry );
        respEntry.setObjectName( entry.getDn() );
        return respEntry;
    }
    
    protected boolean checkDnIsRoot(IoSession session, Dn dn){
        if(null == dn || !Strings.equals(dn.getName(),config.getRootDn())){
            return false;
        }else{
            return true;
        }
    }
    
    
    protected void emptyEntry(IoSession session, SearchRequest request){
        LdapResult result = request.getResultResponse().getLdapResult();
        result.setResultCode(ResultCodeEnum.SUCCESS);
        session.write(request.getResultResponse());
    }
    
    protected String buildUserDn(String uid){
        return "uid="+uid+","+config.getRootDn();
    }
    
    @Override
    public Type getProcessType() {
        return Type.RECEIVE;
    }

}
