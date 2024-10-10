package compi2.multi.compilator.analyzator;

import compi2.multi.compilator.semantic.DefiniteOperation;
import compi2.multi.compilator.semantic.pmodule.Argument;
import compi2.multi.compilator.semantic.util.PassIf;
import compi2.multi.compilator.semantic.past.IfAst;
import compi2.multi.compilator.semantic.past.SimpleCase;
import compi2.multi.compilator.semantic.Statement;
import compi2.multi.compilator.semantic.Expression;
import compi2.multi.compilator.semantic.jast.JIfAst;
import compi2.multi.compilator.semantic.jast.inv.JInvocation;
import compi2.multi.compilator.semantic.jexp.JOperation;
import compi2.multi.compilator.semantic.util.JPassExp;
import compi2.multi.compilator.semantic.util.JPassIf;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.Position;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class AstGen {

    public LinkedList<Statement> oneStmtInList(Statement stmt) {
        LinkedList<Statement> list = new LinkedList<>();
        if (stmt != null) {
            list.add(stmt);
        }
        return list;
    }

    public LinkedList<Argument> generateArgs(List<Label> params, Label varType, boolean isForReference) {
        LinkedList<Argument> list = new LinkedList<>();
        try {
            for (Label param : params) {
                list.add(new Argument(isForReference, param, varType));
            }
        } catch (NullPointerException e) {

        }
        return list;
    }

    public PassIf genPassIf(PassIf pass, IfAst ifAst) {
        try {
            if (pass.getIfs() == null) {
                pass.setIfs(new LinkedList<>());
            }
            pass.getIfs().add(0, ifAst);
        } catch (NullPointerException e) {

        }
        return pass;
    }

    public IfAst transformPassIf(PassIf pass) {
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

    public List<SimpleCase> genSimpleCaseList(List<Expression> labels, List<Statement> stmts) {
        List<SimpleCase> list = new LinkedList<>();
        try {
            list.add(new SimpleCase(labels, stmts));
        } catch (NullPointerException e) {
        }
        return list;
    }

    public JIfAst transformPassJIf(JPassIf pass) {
        try {
            JIfAst first = pass.getIfs().get(0);
            pass.getIfs().remove(0);
            return new JIfAst(
                    first.getInitPos(),
                    first.getCondition(),
                    first.getInternalStmts(),
                    pass.getIfs(),
                    pass.getElseAst()
            );
        } catch (Exception e) {
            return null;
        }
    }

    public JPassIf generateJPassIf(JPassIf pass, JIfAst ifAst) {
        try {
            if (pass.getIfs() == null) {
                pass.setIfs(new LinkedList<>());
            }
            pass.getIfs().add(0, ifAst);
        } catch (NullPointerException e) {

        }
        return pass;
    }

    public List<JInvocation> genListInv(JInvocation inv) {
        List<JInvocation> list = new LinkedList<>();
        list.add(inv);
        return list;
    }
    
    

}
