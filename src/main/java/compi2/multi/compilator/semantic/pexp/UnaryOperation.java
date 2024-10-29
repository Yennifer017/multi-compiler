
package compi2.multi.compilator.semantic.pexp;

import compi2.multi.compilator.semantic.DefiniteOperation;
import compi2.multi.compilator.semantic.Expression;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.Analyzator;
import compi2.multi.compilator.c3d.generators.ExpGenC3D;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.c3d.util.RetParamsC3D;
import compi2.multi.compilator.exceptions.ConvPrimitiveException;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Setter @Getter
public class UnaryOperation extends Expression{
    private DefiniteOperation operation;
    private Expression expression;
    
    private ExpGenC3D expGenC3D;
    private PrimitiveType type;

    public UnaryOperation(DefiniteOperation operation, Expression expression, Position pos) {
        this.operation = operation;
        this.expression = expression;
        super.pos = pos;
        this.expGenC3D = new ExpGenC3D();
    }

    @Override
    public boolean canRecoveryIntValue() {
        if((operation == DefiniteOperation.Addition || operation == DefiniteOperation.Substraction) 
                && expression.canRecoveryIntValue() && (expression instanceof PLiteral)){
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public int recoveryIntegerData(){
        if(expression instanceof PLiteral){
            if(operation == DefiniteOperation.Substraction){
                return expression.recoveryIntegerData() * -1;
            }
            return expression.recoveryIntegerData();
        } else {
            throw new RuntimeException("Can't recovery value");
        }
    }

    @Override
    public Label validateSimpleData(SymbolTable symbolTable, List<String> semanticErrors) {
        Label typeLabel = expression.validateSimpleData(symbolTable, semanticErrors);
        try {
            String typeString = super.tConvert.simpleConvert(operation, typeLabel, semanticErrors);
            type = super.tConvert.convertAllPrimitive(typeString);
            return new Label(typeString,pos);
        } catch (ConvPrimitiveException ex) {
            return new Label(Analyzator.ERROR_TYPE, pos);
        }
    }

    @Override
    public Label validateComplexData(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors) {
        Label typeLabel = expression.validateComplexData(symbolTable, typeTable, semanticErrors);
        try {
            String typeString = super.tConvert.simpleConvert(operation, typeLabel, semanticErrors);
            type = super.tConvert.convertAllPrimitive(typeString);
            return new Label(typeString,pos);
        } catch (ConvPrimitiveException ex) {
            semanticErrors.add(errorsRep.incorrectTypeError(
                    typeLabel.getName(), typeLabel.getPosition())
            );
            return new Label(Analyzator.ERROR_TYPE, pos);
        }
    }

    @Override
    public RetParamsC3D generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        switch (operation) {
            case DefiniteOperation.Addition, 
                    DefiniteOperation.Substraction
                    :
                return expGenC3D.generateAritUnaryCuartetas(
                        admiMemory, internalCuartetas, temporals, pass, expression, type, operation
                );
            case DefiniteOperation.Not:
                return expGenC3D.generateNotCuartetas(
                        admiMemory, internalCuartetas, temporals, pass, expression, type, operation
                );
            default:
                throw new RuntimeException("Cuarteas en una pascal Unary operation no definida");
        }
    }


}
