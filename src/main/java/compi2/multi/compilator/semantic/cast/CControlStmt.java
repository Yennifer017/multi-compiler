
package compi2.multi.compilator.semantic.cast;

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
import compi2.multi.compilator.c3d.util.Register;
import compi2.multi.compilator.c3d.util.RetParamsC3D;
import compi2.multi.compilator.semantic.DefiniteOperation;
import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.c.CImports;
import compi2.multi.compilator.semantic.c.CStatement;
import compi2.multi.compilator.semantic.j.JExpression;
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
public abstract class CControlStmt extends CStatement{
    
    private List<CStatement> internalStmts;
    
    protected StmtsAnalizator stmtsAnalizator;
    
    public CControlStmt(Position initPos, List<CStatement> internalStmt) {
        super(initPos);
        this.internalStmts = internalStmt;
        this.stmtsAnalizator = new StmtsAnalizator();
    }
    
    protected void validateCondition(CImports imports, JSymbolTable clasesST, 
            SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions, CExp condition){
        Label typeCond = condition.validateComplexData(
                imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors
        );
        if(!tConvert.isNumericIntegerType(typeCond.getName())){
            semanticErrors.add(errorsRep.incorrectTypeError(
                    typeCond.getName(), 
                    PrimitiveType.BooleanPT.getName(), 
                    typeCond.getPosition())
            );
        }
    }
    
    protected ReturnCase validateInternal(CImports imports, JSymbolTable clasesST, 
            SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions){
        return stmtsAnalizator.validateInternalStmts(
                imports, 
                clasesST, 
                symbolTable, 
                pascalST, 
                typeTable, 
                semanticErrors, 
                restrictions, 
                internalStmts
        );
    }
    
    protected void generateInternalCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass){
        if(!internalCuartetas.isEmpty()){
            for (CStatement internalStmt : internalStmts) {
                internalStmt.generateCuartetas(admiMemory, internalCuartetas, temporals, pass);
            }
        }
    }
    
    protected void generateConditionCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass, C3Dpass passInternal, CExp condition, int trueLabel){
        RetParamsC3D retCondition = condition.generateCuartetas(
                admiMemory, internalCuartetas, temporals, passInternal
        );
        
        if(retCondition.getTemporalUse() != null){
            internalCuartetas.add(
                    new AssignationC3D(
                            new RegisterUse(Register.AX_INT), 
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
                            new RegisterUse(Register.AX_INT), 
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
