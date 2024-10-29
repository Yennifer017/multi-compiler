
package compi2.multi.compilator.semantic.jexp;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
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
import compi2.multi.compilator.semantic.DefiniteOperation;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.util.Label;
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
public class JOperation extends JExpression{
    
    private JExpression right;
    private JExpression left;
    
    private DefiniteOperation operation;
    private ExpGenC3D expGenC3D;
    private PrimitiveType type;
    
    public JOperation(Position pos, DefiniteOperation operation, 
            JExpression left, JExpression right){
        super(pos);
        this.operation = operation;
        this.left = left;
        this.right = right;
        this.expGenC3D = new ExpGenC3D();
    }

    @Override
    public Label validateData(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, 
            SemanticRestrictions restrictions) {
        Label leftType = left.validateData(
                globalST, symbolTable, typeTable, jerar, semanticErrors, restrictions
        );
        Label rightType = right.validateData(
                globalST, symbolTable, typeTable, jerar, semanticErrors, restrictions
        );
        String typeString = super.tConvert.complexConvert(operation, leftType, rightType, semanticErrors);
        this.type = super.tConvert.convertAllPrimitive(typeString);
        return new Label(typeString, pos);
    }

    @Override
    public Label validateSimpleData(List<String> semanticErrors) {
        Label leftType = left.validateSimpleData(semanticErrors);
        Label rightType = right.validateSimpleData(semanticErrors);
        try {
            String typeString = super.tConvert.simpleConvert(operation, leftType, rightType, semanticErrors);
            this.type = super.tConvert.convertAllPrimitive(typeString);
            return new Label(typeString,pos);
        } catch (ConvPrimitiveException ex) {
            return new Label(Analyzator.ERROR_TYPE, pos);
        }
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
                        admiMemory, internalCuartetas, temporals, pass, 
                        left, right, type, operation
                );
            case DefiniteOperation.Or:
                return expGenC3D.generateOrCuartetas(
                        admiMemory, internalCuartetas, temporals, pass, 
                        left, right, type, operation
                );
            case DefiniteOperation.And:
                return expGenC3D.generateAndCuartetas(
                        admiMemory, internalCuartetas, temporals, pass, 
                        left, right, type, operation
                );
            default:
                throw new RuntimeException("Cuarteas en una j operation no definida");
        }
    }
    
}
