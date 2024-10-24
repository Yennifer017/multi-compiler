
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
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JIfAst extends JControlStmt{
    private JExpression condition;
    private List<JIfAst> elifs;
    private JElseAst elseStmt;

    public JIfAst(Position initPos, JExpression condition, List<JStatement> statements) {
        super(initPos);
        this.condition = condition;
        super.internalStmts = statements;
    }

    public JIfAst(Position initPos, JExpression condition, List<JStatement> statements, 
            List<JIfAst> elifs, JElseAst elseStmt) {
        super(initPos);
        this.condition = condition;
        this.elifs = elifs;
        this.elseStmt = elseStmt;
        super.internalStmts = statements;
    }

    @Override
    public ReturnCase validate(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, 
            SemanticRestrictions restrictions) {
        super.validateCondition(globalST, symbolTable, typeTable, jerar, 
                semanticErrors, restrictions, condition);
        ReturnCase internalRC = super.validateInternalStmts(
                globalST, symbolTable, typeTable, jerar, semanticErrors, restrictions
        );
        
        if(elifs != null && !elifs.isEmpty()){
            for (JIfAst ifAst : elifs) {
                ReturnCase pathRC = ifAst.validate(
                        globalST, symbolTable, typeTable, jerar, semanticErrors, restrictions
                );
                if(internalRC.isAllScenaries() && !pathRC.isAllScenaries()){
                    internalRC.setAllScenaries(false);
                }
            }
        }
        
        if(elseStmt != null){
            ReturnCase elseRC = elseStmt.validate(
                    globalST, symbolTable, typeTable, jerar, semanticErrors, restrictions
            );
            if(internalRC.isAllScenaries() && !elseRC.isAllScenaries()){
                internalRC.setAllScenaries(false);
            }
        } else {
            internalRC.setAllScenaries(false);
        }
        return internalRC;
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass) {
        int trueLabel = admiMemory.getCountLabels();
        int falseLabel = trueLabel + 1;
        int endLabel = trueLabel + 2;
        admiMemory.setCountLabels(admiMemory.getCountLabels() + 3);
        
        super.generateConditionCuartetas(
                admiMemory, internalCuartetas, temporals, pass, pass, condition, trueLabel
        );
        internalCuartetas.add(
                new GotoC3D(falseLabel)
        );
        internalCuartetas.add(
                new LabelC3D(trueLabel)
        );
        super.generateInternalCuartetas(admiMemory, internalCuartetas, temporals, pass);
        internalCuartetas.add(
                new GotoC3D(endLabel)
        );
        internalCuartetas.add(
                new LabelC3D(falseLabel)
        );
        
        //generate elifs
        pass.setEndIfLabel(endLabel);
        if(elifs != null && !elifs.isEmpty()){
            for (JIfAst elif : elifs) {
                elif.generateElifCuartetas(admiMemory, internalCuartetas, temporals, pass);
            }
        }
        //generate else
        if(elseStmt != null){
            elseStmt.generateCuartetas(admiMemory, internalCuartetas, temporals, pass);
        }
        
        internalCuartetas.add(
                new LabelC3D(endLabel)
        );
    }
    
    public void generateElifCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass){
        int trueLabel = admiMemory.getCountLabels();
        int falseLabel = trueLabel + 1;
        admiMemory.setCountLabels(admiMemory.getCountLabels() + 2);
        
        super.generateConditionCuartetas(
                admiMemory, internalCuartetas, temporals, pass, pass, condition, trueLabel
        );
        internalCuartetas.add(
                new GotoC3D(falseLabel)
        );
        internalCuartetas.add(
                new LabelC3D(trueLabel)
        );
        super.generateInternalCuartetas(admiMemory, internalCuartetas, temporals, pass);
        internalCuartetas.add(
                new GotoC3D(pass.getEndIfLabel())
        );
        internalCuartetas.add(
                new LabelC3D(falseLabel)
        );
    }
    
}
