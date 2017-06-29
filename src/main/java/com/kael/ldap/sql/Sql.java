package com.kael.ldap.sql;

import java.util.Map;

/**
 * @author kael.
 */
public class Sql {
    private String sql;
    private Map<String, Object> params;

    public Sql(String sql, Map<String, Object> params) {
        this.sql = sql;
        this.params = params;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
