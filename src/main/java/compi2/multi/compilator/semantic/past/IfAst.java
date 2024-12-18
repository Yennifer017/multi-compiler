
package compi2.multi.compilator.semantic.past;


import compi2.multi.compilator.semantic.p.ControlStruct;
import compi2.multi.compilator.semantic.p.Statement;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.generators.stmts.ConditionalStmtsGenC3D;
import compi2.multi.compilator.c3d.interfaces.ElifGenerateC3D;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.semantic.p.Expression;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class IfAst extends ControlStruct implements ElifGenerateC3D{
    private Expression condition;
    private List<IfAst> elifs;
    private ElseAst elseStmt;

    private ConditionalStmtsGenC3D stmtGenC3D;
    
    public IfAst(Expression condition, List<Statement> statements, Position initPos) {
        super(initPos);
        this.condition = condition;
        super.internalStmts = statements;
        stmtGenC3D = new ConditionalStmtsGenC3D();
    }

    public IfAst(Expression condition, List<Statement> statements, 
            List<IfAst> elifs, ElseAst elseStmt, Position initPos) {
        super(initPos);
        this.condition = condition;
        this.elifs = elifs;
        this.elseStmt = elseStmt;
        super.internalStmts = statements;
    }

    @Override
    public ReturnCase validate(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions) {
        super.validateCondition(condition, symbolTable, typeTable, semanticErrors);
        
        ReturnCase internalRC = super.validateInternalStmts(symbolTable, typeTable, semanticErrors, 
                restrictions
        );
        
        if(elifs != null && !elifs.isEmpty()){
            for (IfAst ifAst : elifs) {
                ReturnCase pathRC = ifAst.validate(symbolTable, typeTable, semanticErrors, restrictions);
                if(internalRC.isAllScenaries() && !pathRC.isAllScenaries()){
                    internalRC.setAllScenaries(false);
                }
            }
        }
        
        if(elseStmt != null){
            ReturnCase elseRC = elseStmt.validate(symbolTable, typeTable, semanticErrors, restrictions);
            if(internalRC.isAllScenaries() && !elseRC.isAllScenaries()){
                internalRC.setAllScenaries(false);
            }
        } else {
            internalRC.setAllScenaries(false);
        }
        return internalRC;
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        stmtGenC3D.generateIfCuartetas(
                admiMemory, 
                internalCuartetas, 
                temporals, 
                pass, 
                internalStmts, 
                condition, 
                elifs, 
                elseStmt
        );
    }

    @Override
    public void generateElifCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        stmtGenC3D.generateElifCuartetas(
                admiMemory, 
                internalCuartetas, 
                temporals, 
                pass, 
                condition, 
                internalStmts
        );
    }

    
}
