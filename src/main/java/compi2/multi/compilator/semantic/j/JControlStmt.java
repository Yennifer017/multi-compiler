
package compi2.multi.compilator.semantic.j;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.StmtsAnalizator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.access.AtomicValue;
import compi2.multi.compilator.c3d.access.RegisterUse;
import compi2.multi.compilator.c3d.access.TemporalUse;
import compi2.multi.compilator.c3d.cuartetas.AssignationC3D;
import compi2.multi.compilator.c3d.cuartetas.GotoC3D;
import compi2.multi.compilator.c3d.cuartetas.IfC3D;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.c3d.util.RetParamsC3D;
import compi2.multi.compilator.semantic.DefiniteOperation;
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

    public JControlStmt(Position initPos) {
        super(initPos);
    }
    
    protected ReturnCase validateInternalStmts(JSymbolTable globalST, SymbolTable symbolTable, TypeTable typeTable, 
            NodeJerarTree jerar, List<String> semanticErrors, SemanticRestrictions restrictions){
        SymbolTable currentST = new SymbolTable();
        currentST.setFather(symbolTable);
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
    
    protected void generateConditionCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass, C3Dpass passInternal, JExpression condition, int trueLabel){
        RetParamsC3D retCondition = condition.generateCuartetas(
                admiMemory, internalCuartetas, temporals, passInternal
        );
        
        if(retCondition.getTemporalUse() != null){
            internalCuartetas.add(
                    new AssignationC3D(
                            new RegisterUse(RegisterUse.AX_INT), 
                            new TemporalUse(
                                    PrimitiveType.IntegerPT, 
                                    retCondition.getTemporalUse().getCountTemp(),
                                    temporals
                            )
                    )
            );
            temporals.setIntegerCount(temporals.getIntegerCount() + 1);
            internalCuartetas.add(
                    new IfC3D(
                            new RegisterUse(RegisterUse.AX_INT), 
                            new AtomicValue(1), 
                            DefiniteOperation.GraterEq, 
                            new GotoC3D(trueLabel)
                    )
            );
        } else {
            internalCuartetas.add(
                    new IfC3D(
                            new AtomicValue(retCondition.getAtomicValue()), 
                            new AtomicValue(1), 
                            DefiniteOperation.GraterEq, 
                            new GotoC3D(trueLabel)
                    )
            );
        }
    }
    
    
}
