
package compi2.multi.compilator.semantic.j;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.generators.ExpGenC3D;
import compi2.multi.compilator.analyzator.StmtsAnalizator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.util.Label;
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
public abstract class JControlStmt extends JStatement{
    protected StmtsAnalizator stmtsAnalizator;
    protected List<JStatement> internalStmts;
    protected ExpGenC3D expGenC3D;

    public JControlStmt(Position initPos) {
        super(initPos);
        this.expGenC3D = new ExpGenC3D();
        this.stmtsAnalizator = new StmtsAnalizator();
    }
    
    protected ReturnCase validateInternalStmts(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, 
            SemanticRestrictions restrictions){
        SymbolTable currentST = new SymbolTable();
        currentST.setFather(symbolTable);
        currentST.setLastDir(symbolTable.getLastDir());
        return stmtsAnalizator.validateInternalStmts(globalST, symbolTable, 
                typeTable, jerar, semanticErrors, restrictions, internalStmts);
    }
    
    protected void validateCondition(JSymbolTable globalST, SymbolTable symbolTable, TypeTable typeTable, 
            NodeJerarTree jerar, List<String> semanticErrors, SemanticRestrictions restrictions, 
            JExpression condition){
        Label typeCond = condition.validateData(globalST, symbolTable, 
                typeTable, jerar, semanticErrors, restrictions);
        if(!tConvert.isNumericIntegerType(typeCond.getName())){
            semanticErrors.add(errorsRep.incorrectTypeError(
                    typeCond.getName(), 
                    PrimitiveType.BooleanPT.getName(), 
                    typeCond.getPosition())
            );
        }
    }
    
    protected void generateInternalCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass){
        if(!internalCuartetas.isEmpty()){
            for (JStatement internalStmt : internalStmts) {
                internalStmt.generateCuartetas(admiMemory, internalCuartetas, temporals, pass);
            }
        }
    }
    
    
}
