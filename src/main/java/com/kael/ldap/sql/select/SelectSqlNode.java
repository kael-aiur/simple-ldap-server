package com.kael.ldap.sql.select;

import com.kael.ldap.sql.AbstractSqlNode;
import com.kael.ldap.sql.SqlNode;

/**
 * @author kael.
 */
public class SelectSqlNode extends AbstractSqlNode {
    @Override
    public void append(StringBuilder stmt) {
        if(null != children){
            for(int i = 0; i < children.length; i++){
                SqlNode n = children[i];
                if(n instanceof SelectFieldSqlNode){
                    n.append(stmt);
                    if(i != children.length-1){
                        stmt.append(",");
                    }
                }else {
                    // todo: other select field
                }
            }
        }
    }

}
