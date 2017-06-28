package com.kael.ldap.filter;

import leap.core.annotation.Bean;
import org.apache.directory.api.ldap.model.filter.AssertionType;
import org.apache.directory.api.ldap.model.filter.BranchNode;
import org.apache.directory.api.ldap.model.filter.ExprNode;
import org.apache.directory.api.ldap.model.filter.FilterVisitor;
import org.apache.directory.api.ldap.model.filter.LeafNode;
import org.apache.directory.api.ldap.model.filter.PresenceNode;
import org.apache.directory.api.ldap.model.filter.SubstringNode;
import org.apache.directory.api.ldap.model.schema.AttributeType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kael.
 */
@Bean
public class ConvertSqlFilterVisitor implements FilterVisitor {
    
    @Override
    public Object visit(ExprNode node) {
        if(node instanceof PresenceNode){
            return null;
        }
        AssertionType t = node.getAssertionType();
        if(node instanceof BranchNode){
            List<ExprNode> children = ((BranchNode) node).getChildren();
            SqlNode n = null;
            switch (t){
                case AND:
                    n = new AndSqlNode();
                    break;
                case OR:
                    n = new OrSqlNode();
                    break;
                case NOT:
                    return null;
            }
            if(null != n){
                List<SqlNode> cs = new ArrayList<>(children.size());
                if(null != children){
                    children.forEach(exprNode -> {
                        SqlNode cn = (SqlNode)visit(exprNode);
                        if(null != cn){
                            cs.add(cn);
                        }
                    });
                }
                n.setChildren(cs.toArray(new SqlNode[0]));
            }
        }else if(node instanceof LeafNode){
            String attr = ((LeafNode) node).getAttribute();
            switch (t){
                case SUBSTRING:
                    ((SubstringNode) node).getAny();
                    break;
            }           
        }
        return " 1<>1 ";
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
        return children;
    }
}
