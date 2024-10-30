
package compi2.multi.compilator.semantic.cast.defaultfuncs;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.access.MemoryAccess;
import compi2.multi.compilator.c3d.cuartetas.funcs.PrintC3D;
import compi2.multi.compilator.c3d.generators.AccessGenC3D;
import compi2.multi.compilator.c3d.generators.ExpGenC3D;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.exceptions.ConvPrimitiveException;
import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.c.CImports;
import compi2.multi.compilator.semantic.c.CStatement;
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
public class CPrint extends CStatement {
    
    private boolean withLn;
    private List<CExp> expressions;
    
    private ExpGenC3D expGenC3D;
    private AccessGenC3D accessGenC3D;
    
    public CPrint(Position initPos, List<CExp> expressions, boolean withLn) {
        super(initPos);
        this.expressions = expressions;
        this.withLn = withLn;
        
        this.expGenC3D = new ExpGenC3D();
        this.accessGenC3D = new AccessGenC3D();
    }

    @Override
    public ReturnCase validate(CImports imports, JSymbolTable clasesST, SymbolTable symbolTable, 
            SymbolTable pascalST, TypeTable typeTable, List<String> semanticErrors, 
            SemanticRestrictions restrictions) {
        if(!expressions.isEmpty()){
            for (CExp exp : expressions) {
                Label type = exp.validateComplexData(
                        imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors
                );
                try {
                    tConvert.convertPrimitive(type.getName());
                } catch (ConvPrimitiveException e) {
                    semanticErrors.add(errorsRep.incorrectTypeError(
                            type.getName(), "tipo primitivo", type.getPosition())
                    );
                }
            }
        }
        return new ReturnCase(false);
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        for (CExp exp : expressions) {
            MemoryAccess expAccess = accessGenC3D.getAccess(
                    exp, admiMemory, internalCuartetas, temporals, pass
            );
            internalCuartetas.add(
                    new PrintC3D(expAccess, withLn)
            );
        }
    }
    
}
