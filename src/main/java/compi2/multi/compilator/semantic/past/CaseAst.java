
package compi2.multi.compilator.semantic.past;

import compi2.multi.compilator.semantic.p.Statement;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.semantic.p.Expression;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class CaseAst extends Statement{
    private Expression expression;
    private List<SimpleCase> cases;
    private ElseAst elseAst;

    public CaseAst(Expression expression, List<SimpleCase> cases, 
            ElseAst elseAst, Position initPos) {
        super(initPos);
        this.expression = expression;
        this.cases = cases;
        this.elseAst = elseAst;
    }

    @Override
    public ReturnCase validate(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions) {
        Label typeCase = expression.validateComplexData(symbolTable, typeTable, semanticErrors);
        if(!typeCase.getName().equals(PrimitiveType.IntegerPT.getName())
                && !typeCase.getName().equals(PrimitiveType.CharPT.getName())
                && !typeCase.getName().equals(PrimitiveType.BooleanPT.getName())
                ){
            semanticErrors.add(errorsRep.incorrectTypeError(
                    typeCase.getName(), 
                    "[" + PrimitiveType.IntegerPT.getName() + ", " 
                            + PrimitiveType.CharPT.getName() + ", " 
                            + PrimitiveType.BooleanPT.getName() 
                            + "]",
                    typeCase.getPosition())
            );
        }
        ReturnCase returnCase = new ReturnCase(true);
        if(cases != null && !cases.isEmpty()){
            for (SimpleCase simpleCase : cases) {
                ReturnCase currentCase = simpleCase.validate(
                        symbolTable, typeTable, semanticErrors, restrictions, typeCase.getName()
                );
                if(!currentCase.isAllScenaries()){
                    returnCase.setAllScenaries(false);
                }
            }
        }
        
        if(elseAst != null){
            ReturnCase elseRC = elseAst.validate(symbolTable, typeTable, semanticErrors, restrictions);
            if(returnCase.isAllScenaries() && !elseRC.isAllScenaries()){
                returnCase.setAllScenaries(false);
            }
        } else {
            returnCase.setAllScenaries(false);
        }
        
        return returnCase;
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}
