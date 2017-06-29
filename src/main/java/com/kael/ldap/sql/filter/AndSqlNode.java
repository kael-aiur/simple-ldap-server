package com.kael.ldap.sql.filter;

import com.kael.ldap.sql.AbstractSqlNode;
import com.kael.ldap.sql.SqlNode;

/**
 * Created by KAEL on 2017/6/28.
 */
public class AndSqlNode extends AbstractSqlNode {
    @Override
    public void append(StringBuilder stmt) {
        if(null != children){
            StringBuilder and = new StringBuilder(" (");
            for(SqlNode node : children){
                node.append(and);
                and.append(" AND ");
                params.putAll(node.nameParams());
            }
            and.append("1=1");
            and.append(") ");
            stmt.append(and.toString());
        }
    }
}
