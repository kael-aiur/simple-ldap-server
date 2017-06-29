package com.kael.ldap.sql.select;

import com.kael.ldap.sql.AbstractSqlNode;
import leap.lang.Strings;

/**
 * @author kael.
 */
public class TableSqlNode extends AbstractSqlNode {
    
    protected String table;
    protected String alias;

    public TableSqlNode(String table) {
        this.table = table;
    }

    @Override
    public void append(StringBuilder stmt) {
        stmt.append(" ");
        stmt.append(table);
        stmt.append(" ");
        if(Strings.isNotEmpty(alias)){
            stmt.append(" AS ");
            stmt.append(alias);
        }
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
