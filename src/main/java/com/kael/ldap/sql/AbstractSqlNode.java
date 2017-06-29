package com.kael.ldap.sql;

import com.kael.ldap.sql.SqlNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kael.
 */
public abstract class AbstractSqlNode implements SqlNode {
    protected Map<String, Object> params = new HashMap<>();
    protected SqlNode[] children;
    @Override
    public Map<String, Object> nameParams() {
        return params;
    }

    @Override
    public SqlNode[] getChildren() {
        return children;
    }

    @Override
    public void setChildren(SqlNode[] children) {
        this.children = children;
    }
}
