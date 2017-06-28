package com.kael.ldap.filter;

import org.apache.directory.api.ldap.model.filter.BranchNode;
import org.apache.directory.api.ldap.model.filter.ExprNode;
import org.apache.directory.api.ldap.model.filter.FilterVisitor;
import org.apache.directory.api.ldap.model.filter.PresenceNode;

import java.util.List;

/**
 * @author kael.
 */
public class ConvertSqlFilterVisitor implements FilterVisitor {
    @Override
    public Object visit(ExprNode node) {
        if(node instanceof PresenceNode){
            
        }
        return null;
    }

    @Override
    public boolean canVisit(ExprNode node) {
        return true;
    }

    @Override
    public boolean isPrefix() {
        return false;
    }

    @Override
    public List<ExprNode> getOrder(BranchNode node, List<ExprNode> children) {
        return null;
    }
}
