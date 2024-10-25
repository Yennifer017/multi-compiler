
package compi2.multi.compilator.semantic.cexp;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.Analyzator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
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
public class COperation extends CExp {
    
    private DefiniteOperation operation;
    private CExp leftExp;
    private CExp rightExp;
    
    public COperation(Position pos, DefiniteOperation operation, 
            CExp leftExp, CExp rightExp) {
        super(pos);
        this.operation = operation;
        this.leftExp = leftExp;
        this.rightExp = rightExp;
    }

    @Override
    public Label validateComplexData(CImports imports, JSymbolTable clasesST, SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable, List<String> semanticErrors) {
        Label leftType = leftExp.validateComplexData(
                imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors
        );
        Label rightType = rightExp.validateComplexData(
                imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors
        );
        return new Label(
                super.tConvert.complexConvert(operation, leftType, rightType, semanticErrors), 
                pos
        );
    }

    @Override
    public Label validateSimpleData(SymbolTable symbolTable, List<String> semanticErrors) {
        Label leftType = leftExp.validateSimpleData(symbolTable, semanticErrors);
        Label rightType = rightExp.validateSimpleData(symbolTable, semanticErrors);
        try {
            return new Label(
                    super.tConvert.simpleConvert(operation, leftType, rightType, semanticErrors),
                    pos);
        } catch (ConvPrimitiveException ex) {
            return new Label(Analyzator.ERROR_TYPE, pos);
        }
    }

    @Override
    public RetParamsC3D generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
