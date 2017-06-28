package com.kael.ldap.filter;

/**
 * Created by KAEL on 2017/6/28.
 */
public class OrSqlNode implements SqlNode {
    private SqlNode[] conditions;

    @Override
    public void append(StringBuilder stmt) {
        if(null != conditions){
            StringBuilder or = new StringBuilder(" (");
            for(SqlNode node : conditions){
                node.append(or);
                or.append(" OR ");
            }
            or.append("1 <> 1");
            or.append(") ");
            stmt.append(or.toString());
        }
    }

    @Override
    public void setChildren(SqlNode[] children) {
        this.conditions = children;
    }

    @Override
    public SqlNode[] getChildren() {
        return conditions;
    }
}
