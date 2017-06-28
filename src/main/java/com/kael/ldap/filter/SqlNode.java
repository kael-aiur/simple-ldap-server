package com.kael.ldap.filter;

/**
 * Created by KAEL on 2017/6/28.
 */
public interface SqlNode {
    
    void append(StringBuilder stmt);
    
    SqlNode[] getChildren();

    void setChildren(SqlNode[] children);
}
