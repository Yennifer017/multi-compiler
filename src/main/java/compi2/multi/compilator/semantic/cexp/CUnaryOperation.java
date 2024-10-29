
package compi2.multi.compilator.semantic.cexp;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.Analyzator;
import compi2.multi.compilator.c3d.generators.ExpGenC3D;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.util.AdmiRegisters;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.c3d.util.RetParamsC3D;
import compi2.multi.compilator.exceptions.ConvPrimitiveException;
import compi2.multi.compilator.semantic.DefiniteOperation;
import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.c.CImports;
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
public class CUnaryOperation extends CExp{
    private CExp exp;
    private DefiniteOperation operation;
    
    private PrimitiveType type;
    private AdmiRegisters admiRegisters;
    private ExpGenC3D expGenC3D;
    
    public CUnaryOperation(Position pos, DefiniteOperation operation, CExp exp) {
        super(pos);
        this.operation = operation;
        this.exp = exp;
        admiRegisters = new AdmiRegisters();
        expGenC3D = new ExpGenC3D();
    }

    @Override
    public Label validateComplexData(CImports imports, JSymbolTable clasesST, SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable, List<String> semanticErrors) {
        Label typeLabel = exp.validateComplexData(
                imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors
        );
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
    public Label validateSimpleData(SymbolTable symbolTable, List<String> semanticErrors) {
        Label typeLabel = exp.validateSimpleData(symbolTable, semanticErrors);
        try {
            String typeString = super.tConvert.simpleConvert(operation, typeLabel, semanticErrors);
            this.type = super.tConvert.convertAllPrimitive(typeString);
            return new Label( typeString,pos);
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
                        admiMemory, internalCuartetas, temporals, pass, exp, type, operation
                );
            case DefiniteOperation.Not:
                return expGenC3D.generateNotCuartetas(
                        admiMemory, internalCuartetas, temporals, pass, exp, type, operation
                );
            default:
                throw new RuntimeException("Cuarteas en una c Unary operation no definida");
        }
    }
    
}
