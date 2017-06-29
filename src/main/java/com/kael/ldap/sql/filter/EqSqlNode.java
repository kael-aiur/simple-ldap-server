package com.kael.ldap.sql.filter;

import com.kael.ldap.sql.AbstractSqlNode;

/**
 * @author kael.
 */
public class EqSqlNode extends AbstractSqlNode {

    private String field;
    private String value;

    public EqSqlNode(String field, String value) {
        this.field = field;
        this.value = value;
    }

    public EqSqlNode() {
    }

    @Override
    public void append(StringBuilder stmt) {
        String sql = field + "= :"+field;
        params.put(field,value);
        stmt.append(" ");
        stmt.append(sql);
        stmt.append(" ");
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
