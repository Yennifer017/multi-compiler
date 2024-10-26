
package compi2.multi.compilator.semantic.jast;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.cuartetas.GotoC3D;
import compi2.multi.compilator.c3d.cuartetas.LabelC3D;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.j.JControlStmt;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.Position;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class JDoWhileAst extends JControlStmt{
    private JExpression condition;

    public JDoWhileAst(Position initPos, JExpression condition, List<JStatement> internalStmts) {
        super(initPos);
        this.condition = condition;
        super.internalStmts = internalStmts;
    }

    @Override
    public ReturnCase validate(JSymbolTable globalST, SymbolTable symbolTable, TypeTable typeTable, 
            NodeJerarTree jerar, List<String> semanticErrors, SemanticRestrictions restrictions) {
        super.validateCondition(globalST, symbolTable, typeTable, jerar, 
                semanticErrors, restrictions, condition
        );
        return super.validateInternalStmts(
                globalST, symbolTable, typeTable, jerar, semanticErrors, 
                new SemanticRestrictions(
                        true, 
                        true, 
                        restrictions.getReturnType(), 
                        restrictions.getReturnType())
        );
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass) {
        int firstLabel = admiMemory.getCountLabels();
        int finalLabel = firstLabel + 1;
        admiMemory.setCountLabels(admiMemory.getCountLabels() + 2);
        
        internalCuartetas.add(
                new LabelC3D(firstLabel)
        );
        
        C3Dpass passInternal = new C3Dpass(finalLabel, firstLabel);
        super.generateInternalCuartetas(admiMemory, internalCuartetas, temporals, passInternal);
        
        super.generateConditionCuartetas(
                admiMemory, internalCuartetas, temporals, pass, passInternal, condition, firstLabel
        );
        
        internalCuartetas.add(
                new GotoC3D(finalLabel)
        );
        internalCuartetas.add(
                new LabelC3D(finalLabel)
        );
    }
}
