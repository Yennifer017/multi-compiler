
package compi2.multi.compilator.semantic.jexp;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.Analyzator;
import compi2.multi.compilator.analyzator.ExpGenC3D;
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
public class JUnaryOp extends JExpression{
    
    private JExpression passExp;
    private DefiniteOperation operation;
    
    private ExpGenC3D expGenC3D;
    private PrimitiveType type;

    public JUnaryOp(Position pos, JExpression passExp, DefiniteOperation operation) {
        super(pos);
        this.passExp = passExp;
        this.operation = operation;
        this.expGenC3D = new ExpGenC3D();
    }

    @Override
    public Label validateData(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, 
            SemanticRestrictions restrictions) {
        Label typeLabel = passExp.validateData(globalST, symbolTable, typeTable, 
                jerar, semanticErrors, restrictions);
        try {
            String typeString = super.tConvert.simpleConvert(operation, typeLabel, semanticErrors);
            this.type = super.tConvert.convertAllPrimitive(typeString);
            return new Label(typeString,pos);
        } catch (ConvPrimitiveException ex) {
            semanticErrors.add(errorsRep.incorrectTypeError(
                    typeLabel.getName(), typeLabel.getPosition())
            );
            return new Label(Analyzator.ERROR_TYPE, pos);
        }
    }

    @Override
    public Label validateSimpleData(List<String> semanticErrors) {
        Label typeLabel = passExp.validateSimpleData(semanticErrors);
        try {
            String typeString = super.tConvert.simpleConvert(operation, typeLabel, semanticErrors);
            this.type = super.tConvert.convertAllPrimitive(typeString);
            return new Label(typeString,pos);
        } catch (ConvPrimitiveException ex) {
            return new Label(Analyzator.ERROR_TYPE, pos);
        }
    }

    @Override
    public RetParamsC3D generateCuartetas(AdmiMemory admiMemory, 
            List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        switch (operation) {
            case DefiniteOperation.Addition, 
                    DefiniteOperation.Substraction
                    :
                return expGenC3D.generateAritUnaryCuartetas(
                        admiMemory, internalCuartetas, temporals, pass, passExp, type, operation
                );
            case DefiniteOperation.Not:
                return expGenC3D.generateNotCuartetas(
                        admiMemory, internalCuartetas, temporals, pass, passExp, type, operation
                );
            default:
                throw new RuntimeException("Cuarteas en una j Unary operation no definida");
        }
    }
    
    
}
