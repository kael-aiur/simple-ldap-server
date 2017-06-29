package com.kael.ldap.sql.filter;

import com.kael.ldap.sql.AbstractSqlNode;

/**
 * @author kael.
 */
public class LikeSqlNode extends AbstractSqlNode {
    
    private String field;
    private String value;
    
    @Override
    public void append(StringBuilder stmt) {
        String sql = field + " LIKE :"+field;
        stmt.append(" ");
        stmt.append(sql);
        stmt.append(" ");
        params.put(field,value);
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
