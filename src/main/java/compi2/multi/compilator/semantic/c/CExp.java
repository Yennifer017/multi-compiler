
package compi2.multi.compilator.semantic.c;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analysis.typet.convert.TConvertidor;
import compi2.multi.compilator.analyzator.RefAnalyzator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.c3d.util.RetParamsC3D;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.ErrorsRep;
import compi2.multi.compilator.util.Position;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public abstract class CExp {
    protected Position pos;
    protected TConvertidor tConvert;
    protected ErrorsRep errorsRep;
    protected RefAnalyzator refAnalyzator;
    
    public CExp(Position pos){
        this.pos = pos;
        this.tConvert = new TConvertidor();
        this.errorsRep = new ErrorsRep();
        this.refAnalyzator = new RefAnalyzator();
    }
    
    public abstract Label validateComplexData(CImports imports, JSymbolTable clasesST, 
            SymbolTable symbolTable, SymbolTable pascalST,
            TypeTable typeTable, List<String> semanticErrors);
    
    public abstract Label validateSimpleData(SymbolTable symbolTable, 
            List<String> semanticErrors);
    
    public abstract RetParamsC3D generateCuartetas(AdmiMemory admiMemory, 
            List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass);
}
