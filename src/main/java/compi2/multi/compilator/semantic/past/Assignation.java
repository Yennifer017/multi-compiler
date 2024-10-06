
package compi2.multi.compilator.semantic.past;

import compi2.multi.compilator.semantic.Statement;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.semantic.Expression;
import compi2.multi.compilator.semantic.util.Label;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class Assignation extends Statement{
    private Label variable;
    private Expression expToAsign;
    
    public Assignation(Label variable, Expression expToAsign){
        super(variable.getPosition());
        this.variable = variable;
        this.expToAsign = expToAsign;
    }

    @Override
    public ReturnCase validate(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions) {
        ReturnCase rCase = validateReturn(symbolTable, typeTable, semanticErrors, restrictions);
        if(rCase != null){
            return rCase;
        }
        
        //validar de que la variable exista 
        if(super.refAnalyzator.existReference(symbolTable, semanticErrors, 
                variable)){ 
            RowST row = refAnalyzator.getFromST(symbolTable, variable.getName());
            
            //validar que la variable sea de la categoria adecuada
            switch (row.getCategory()) { 
                case Variable, Param_ref, Param_val -> {
                    Label type = expToAsign.validateComplexData(symbolTable, typeTable, semanticErrors);
                    
                    //validar el tipo de la expresion
                    if(!row.getType().equals(type.getName())
                            && !tConvert.canUpgradeType(row.getType(), 
                                    type.getName()) 
                            ){
                        
                        semanticErrors.add(errorsRep.incorrectTypeError(
                                type.getName(), 
                                row.getType(), 
                                type.getPosition())
                        );
                    }
                }
                default -> {
                    semanticErrors.add(errorsRep.ilegalAssignation(
                            variable.getName(), 
                            row.getCategory().getName(), 
                            variable.getPosition()
                    ));
                }
            }
        }
        return new ReturnCase(false);
    }
    
    private ReturnCase validateReturn(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions){
        //validar si es un retorno
        if(restrictions.getNameFunction() != null 
                && restrictions.getNameFunction().equals(variable.getName())){
            Label type = expToAsign.validateComplexData(symbolTable, typeTable, semanticErrors);
            if(!type.getName().equals(restrictions.getReturnType())
                    && !tConvert.canUpgradeType(restrictions.getReturnType(), type.getName()) 
                    ){
                semanticErrors.add(errorsRep.incorrectTypeError(
                        type.getName(),
                        restrictions.getReturnType(),
                        type.getPosition())
                );
            }
            return new ReturnCase(true);
        }
        return null;
    }

}
