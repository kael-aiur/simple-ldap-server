package com.kael.ldap.sql.filter;

import com.kael.ldap.sql.AbstractSqlNode;
import com.kael.ldap.sql.SqlNode;

/**
 * Created by KAEL on 2017/6/28.
 */
public class OrSqlNode extends AbstractSqlNode {
    @Override
    public void append(StringBuilder stmt) {
        if(null != children){
            StringBuilder or = new StringBuilder(" (");
            for(SqlNode node : children){
                node.append(or);
                or.append(" OR ");
                params.putAll(node.nameParams());
            }
            or.append("1 <> 1");
            or.append(") ");
            stmt.append(or.toString());
        }
    }
}
