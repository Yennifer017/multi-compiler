
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
public class JWhileAst extends JControlStmt{

    private JExpression condition;

    public JWhileAst(Position initPos, JExpression condition, List<JStatement> internalStmts) {
        super(initPos);
        this.condition = condition;
        super.internalStmts = internalStmts;
    }

    @Override
    public ReturnCase validate(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, 
            SemanticRestrictions restrictions) {
        super.validateCondition(globalST, symbolTable, typeTable, jerar, 
                semanticErrors, restrictions, condition);
        return super.validateInternalStmts(globalST, symbolTable, typeTable, 
                jerar, semanticErrors, restrictions);
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass) {
        int firstLabel = admiMemory.getCountLabels();
        int trueLabel = firstLabel + 1;
        int falseLabel = firstLabel + 2;
        admiMemory.setCountLabels(admiMemory.getCountLabels() + 3);
        C3Dpass internalPass = new C3Dpass(falseLabel,firstLabel);
        internalCuartetas.add(
                new LabelC3D(firstLabel)
        );
        super.generateConditionCuartetas(
                admiMemory, internalCuartetas, temporals, pass, internalPass, condition, trueLabel
        );
        internalCuartetas.add(
                new GotoC3D(falseLabel)
        );
        internalCuartetas.add(
                new LabelC3D(trueLabel)
        );
        super.generateInternalCuartetas(admiMemory, internalCuartetas, temporals, internalPass);
        internalCuartetas.add(
                new GotoC3D(firstLabel)
        );
        internalCuartetas.add(
                new LabelC3D(falseLabel)
        );
    }
    
}
