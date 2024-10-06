
package compi2.multi.compilator.analyzator;


import compi2.multi.compilator.semantic.module.Argument;
import compi2.multi.compilator.semantic.PassIf;
import compi2.multi.compilator.semantic.ast.IfAst;
import compi2.multi.compilator.semantic.ast.SimpleCase;
import compi2.multi.compilator.semantic.Statement;
import compi2.multi.compilator.semantic.exp.Expression;
import compi2.multi.compilator.semantic.obj.Label;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class AstGen {
    public LinkedList<Statement> oneStmtInList(Statement stmt){
        LinkedList<Statement> list = new LinkedList<>();
        if(stmt != null){
            list.add(stmt);
        }
        return list;
    }
    
    public LinkedList<Argument> generateArgs(List<Label> params, Label varType, boolean isForReference){
        LinkedList<Argument> list = new LinkedList<>();
        try {
            for (Label param : params) {
                list.add(new Argument(isForReference, param, varType));
            }
        } catch (NullPointerException e) {
            
        }
        return list;
    }
    
    public PassIf genPassIf(PassIf pass, IfAst ifAst){
        try {
            if (pass.getIfs() == null) {
                pass.setIfs(new LinkedList<>());
            }
            pass.getIfs().add(0, ifAst);
        } catch (NullPointerException e) {
            
        }
        return pass;
    }
    
    public IfAst transformPassIf(PassIf pass){
        try {
            IfAst first = pass.getIfs().get(0);
            pass.getIfs().remove(0);
            return new IfAst(first.getCondition(),
                    first.getInternalStmts(),
                    pass.getIfs(),
                    pass.getElseAst(),
                    first.getInitPos()
            );
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<SimpleCase> genSimpleCaseList(List<Expression> labels, List<Statement> stmts){
        List<SimpleCase> list = new LinkedList<>();
        try {
            list.add(new SimpleCase(labels, stmts));
        } catch (NullPointerException e) {
        }
        return list;
    }
    
}
