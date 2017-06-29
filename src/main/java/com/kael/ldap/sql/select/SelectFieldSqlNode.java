package com.kael.ldap.sql.select;

import com.kael.ldap.sql.AbstractSqlNode;
import leap.lang.Strings;

/**
 * @author kael.
 */
public class SelectFieldSqlNode extends AbstractSqlNode {
    
    protected String field;
    protected String alias;
    
    @Override
    public void append(StringBuilder stmt) {
        stmt.append(field);
        if(Strings.isNotEmpty(alias)){
            stmt.append(" AS ");
            stmt.append(alias);
        }
    }

    public SelectFieldSqlNode(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
