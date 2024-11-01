
package compi2.multi.compilator.semantic.jast.others;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.semantic.jast.inv.InvocationsUtil;
import compi2.multi.compilator.semantic.jast.inv.JContextRef;
import compi2.multi.compilator.semantic.jast.inv.JInvocation;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.Position;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JMethodUseStmt extends JStatement {
    
    private List<JInvocation> invocations;
    
    private InvocationsUtil invUtil;
    
    private int instanceRef;
    
    public JMethodUseStmt(Position initPos, List<JInvocation> invocations) {
        super(initPos);
        this.invocations = invocations;
        this.invUtil = new InvocationsUtil();
    }
    
    public JMethodUseStmt(Position initPos, List<JInvocation> invocations, JContextRef firstContextRef){
        super(initPos);
        this.invUtil = new InvocationsUtil();
        try {
            invocations.get(0).setContext(firstContextRef);
            this.invocations = invocations;
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            this.invocations = new LinkedList<>();
        }
    }

    @Override
    public ReturnCase validate(JSymbolTable globalST, SymbolTable symbolTable, TypeTable typeTable, 
            NodeJerarTree jerar, List<String> semanticErrors, SemanticRestrictions restrictions) {
        this.instanceRef = super.refAnalyzator.findInstanceRef(symbolTable);
        invUtil.validateInvocation(
                globalST, symbolTable, typeTable, jerar, semanticErrors, invocations, initPos, false
        );
        return new ReturnCase(false);
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        invUtil.generateC3DInvocations(admiMemory, internalCuartetas, temporals, invocations, instanceRef);
    }

}
