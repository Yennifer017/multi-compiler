
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
@Getter @Setter
public class Operation extends Expression{
    private DefiniteOperation operation;
    private Expression leftExp;
    private Expression rightExp;
    
    private ExpGenC3D expGenC3D;
    private PrimitiveType type;

    public Operation(DefiniteOperation operation, Expression leftExp, Expression rightExp, Position pos) {
        super();
        this.operation = operation;
        this.leftExp = leftExp;
        this.rightExp = rightExp;
        super.pos = pos;
        this.expGenC3D = new ExpGenC3D();
    }

    @Override
    public Label validateSimpleData(SymbolTable symbolTable, List<String> semanticErrors) {
        Label leftType = leftExp.validateSimpleData(symbolTable, semanticErrors);
        Label rightType = rightExp.validateSimpleData(symbolTable, semanticErrors);
        try {
            String typeString = super.tConvert.simpleConvert(operation, leftType, rightType, semanticErrors);
            this.type = super.tConvert.convertAllPrimitive(typeString);
            return new Label(typeString,pos);
        } catch (ConvPrimitiveException ex) {
            return new Label(Analyzator.ERROR_TYPE, pos);
        }
    }

    @Override
    public Label validateComplexData(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors) {
        Label leftType = leftExp.validateComplexData(symbolTable, typeTable, semanticErrors);
        Label rightType = rightExp.validateComplexData(symbolTable, typeTable, semanticErrors);
        String typeString = super.tConvert.complexConvert(
                operation, leftType, rightType, semanticErrors, false
        );
        this.type = super.tConvert.convertAllPrimitive(typeString);
        return new Label(typeString, pos);
    }

    @Override
    public RetParamsC3D generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        switch (operation) {
            case DefiniteOperation.Addition, 
                    DefiniteOperation.Division,
                    DefiniteOperation.Module,
                    DefiniteOperation.Multiplication,
                    DefiniteOperation.Power,
                    DefiniteOperation.Substraction,
                    
                    DefiniteOperation.EqualsTo,
                    DefiniteOperation.GraterEq,
                    DefiniteOperation.GraterThan,
                    DefiniteOperation.LessEq,
                    DefiniteOperation.LessThan
                    :
                return expGenC3D.generateAritCuartetas(
                        admiMemory, internalCuartetas, temporals, pass, leftExp, rightExp, type, operation
                );
            case DefiniteOperation.Or:
                return expGenC3D.generateOrCuartetas(
                        admiMemory, internalCuartetas, temporals, pass, leftExp, rightExp, type, operation
                );
            case DefiniteOperation.And:
                return expGenC3D.generateAndCuartetas(
                        admiMemory, internalCuartetas, temporals, pass, leftExp, rightExp, type, operation
                );
            default:
                throw new RuntimeException("Cuarteas en una pascal operation no definida");
        }
    }

}
