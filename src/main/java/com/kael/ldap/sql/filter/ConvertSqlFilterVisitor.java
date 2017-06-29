package com.kael.ldap.sql.filter;

import com.kael.ldap.sql.FieldMappingStrategy;
import com.kael.ldap.sql.SqlNode;
import leap.core.annotation.Bean;
import leap.core.annotation.Inject;
import leap.lang.Strings;
import org.apache.directory.api.ldap.model.filter.AssertionType;
import org.apache.directory.api.ldap.model.filter.BranchNode;
import org.apache.directory.api.ldap.model.filter.EqualityNode;
import org.apache.directory.api.ldap.model.filter.ExprNode;
import org.apache.directory.api.ldap.model.filter.FilterVisitor;
import org.apache.directory.api.ldap.model.filter.LeafNode;
import org.apache.directory.api.ldap.model.filter.PresenceNode;
import org.apache.directory.api.ldap.model.filter.SubstringNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kael.
 */
@Bean
public class ConvertSqlFilterVisitor implements FilterVisitor {
    
    protected @Inject FieldMappingStrategy strategy;
    
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
                default:
                    System.out.println(node);
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
            return n;
        }else if(node instanceof LeafNode){
            String attr = strategy.getSqlField(((LeafNode) node).getAttribute());
            switch (t){
                case SUBSTRING:
                    List<String> values = ((SubstringNode) node).getAny();
                    if(null != values){
                        SqlNode n = new OrSqlNode();
                        List<SqlNode> children = new ArrayList<>(values.size());
                        values.forEach(s -> {
                            if(Strings.contains(s,"*")){
                                String v = Strings.replace(s,"*","%");
                                LikeSqlNode like = new LikeSqlNode();
                                like.setField(attr);
                                like.setValue(v);
                                children.add(like);
                            }else {
                                EqSqlNode eq = new EqSqlNode();
                                eq.setField(attr);
                                eq.setValue(s);
                                children.add(eq);
                            }
                        });
                        n.setChildren(children.toArray(new SqlNode[0]));
                        return n;
                    }else {
                        return null;
                    }
                case EQUALITY:
                    String value = ((EqualityNode)node).getValue().getString();
                    EqSqlNode eq = new EqSqlNode();
                    eq.setField(attr);
                    eq.setValue(value);
                    return eq;
                default:
                    System.out.println(node);
            }           
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
        return children;
    }
}
