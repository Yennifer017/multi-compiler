package compi2.multi.compilator.analyzator;

import compi2.multi.compilator.semantic.pmodule.Argument;
import compi2.multi.compilator.semantic.util.PassIf;
import compi2.multi.compilator.semantic.past.IfAst;
import compi2.multi.compilator.semantic.past.SimpleCase;
import compi2.multi.compilator.semantic.Statement;
import compi2.multi.compilator.semantic.Expression;
import compi2.multi.compilator.semantic.c.CDef;
import compi2.multi.compilator.semantic.cast.CIfAst;
import compi2.multi.compilator.semantic.cast.dec.CArrayObjDec;
import compi2.multi.compilator.semantic.cast.dec.CObjectDec;
import compi2.multi.compilator.semantic.cast.inv.objs.CInvocation;
import compi2.multi.compilator.semantic.jast.JIfAst;
import compi2.multi.compilator.semantic.jast.inv.JInvocation;
import compi2.multi.compilator.semantic.util.CPassIf;
import compi2.multi.compilator.semantic.util.JPassIf;
import compi2.multi.compilator.semantic.util.Label;
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
    
    public CIfAst transformPassCIf(CPassIf pass) {
        try {
            CIfAst first = pass.getIfs().get(0);
            pass.getIfs().remove(0);
            return new CIfAst(
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
    
    public CPassIf generateCPassIf(CPassIf pass, CIfAst ifAst){
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
    
    public List<JInvocation> genListInv(List<JInvocation> list, JInvocation first){
        try {
            list.add(0, first);
            return list;
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            return new LinkedList<>();
        }
    }
    
    public List<CInvocation> genListCInv(CInvocation inv) {
        List<CInvocation> list = new LinkedList<>();
        list.add(inv);
        return list;
    }
    
    public List<CInvocation> genListCInv(List<CInvocation> list, CInvocation first){
        try {
            list.add(0, first);
            return list;
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            return new LinkedList<>();
        }
    }
    
    public List<? extends CDef> setTypeCObject(List<CObjectDec> list, Label type){
        if(list == null){
            return new LinkedList<CObjectDec>();
        }
        if(!list.isEmpty()){
            for (CObjectDec cObjectDec : list) {
                cObjectDec.setObjectName(type);
            }
        }
        return list;
    }
    
    public List<? extends CDef> setTypeArrayObject(List<CArrayObjDec> list, Label type){
        if(list == null){
            return new LinkedList<CArrayObjDec>();
        }
        if(!list.isEmpty()){
            for (CArrayObjDec cObjectDec : list) {
                cObjectDec.setObjectName(type);
            }
        }
        return list;
    }
    
    
}
