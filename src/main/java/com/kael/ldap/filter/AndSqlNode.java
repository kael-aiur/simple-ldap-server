package com.kael.ldap.filter;

/**
 * Created by KAEL on 2017/6/28.
 */
public class AndSqlNode implements SqlNode {
    
    private SqlNode[] conditions;
    
    @Override
    public void append(StringBuilder stmt) {
        if(null != conditions){
            StringBuilder and = new StringBuilder(" (");
            for(SqlNode node : conditions){
                node.append(and);
                and.append(" AND ");
            }
            and.append("1=1");
            and.append(") ");
            stmt.append(and.toString());
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
