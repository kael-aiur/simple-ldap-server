package com.kael.ldap.sql;

import com.kael.ldap.sql.select.SelectFieldSqlNode;
import com.kael.ldap.sql.select.SelectSqlNode;
import leap.lang.New;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kael.
 */
public class SqlBuilder {
    
    protected SqlNode where;
    protected SqlNode select;
    protected SqlNode table;
    
    protected FieldMappingStrategy strategy;

    public SqlBuilder(FieldMappingStrategy strategy) {
        this.strategy = strategy;
    }

    public SqlBuilder select(List<String> field){
        if(null == field || field.size() == 0){
            field = New.arrayList("id, login_id, name,email");
        }
        SqlNode[] ns = field.stream().map(s -> new SelectFieldSqlNode(strategy.getSqlField(s)))
                .toArray(value -> new SqlNode[value]);
        SelectSqlNode n = new SelectSqlNode();
        n.setChildren(ns);
        setSelectNode(n);
        return this;
    }
    
    public SqlBuilder setTable(SqlNode table){
        this.table = table;
        return this;
    }
    
    public SqlBuilder setWhereNode(SqlNode where){
        this.where = where;
        return this;
    }
    public SqlBuilder setSelectNode(SqlNode select){
        this.select = select;
        return this;
    }
    
    public Sql build(){
        Map<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT ");
        select.append(sql);
        sql.append(" FROM ");
        table.append(sql);
        params.putAll(select.nameParams());
        if(null != where){
            sql.append(" WHERE ");
            where.append(sql);
            params.putAll(where.nameParams());
        }
        return new Sql(sql.toString(),params);
    }
    
}
