
package compi2.multi.compilator.semantic.cast.inv.objs;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analysis.typet.convert.TConvertidor;
import compi2.multi.compilator.analyzator.FunctionRefAnalyzator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.generators.ExpGenC3D;
import compi2.multi.compilator.c3d.interfaces.InvocationGenerateC3D;
import compi2.multi.compilator.c3d.util.RetJInvC3D;
import compi2.multi.compilator.semantic.c.CImports;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.ErrorsRep;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public abstract class CInvocation implements InvocationGenerateC3D{
    protected Label inv;
    
    protected ErrorsRep errorsRep;
    protected TConvertidor tconvert;
    protected ExpGenC3D expGenC3D;
    protected FunctionRefAnalyzator refFun;
    
    public CInvocation(Label inv){
        this.inv = inv;
        this.refFun =  new FunctionRefAnalyzator();
        this.errorsRep = new ErrorsRep();
        this.tconvert = new TConvertidor();
        this.expGenC3D = new ExpGenC3D();
    }
    
    public abstract Label validate(CImports imports, JSymbolTable clasesST, 
            SymbolTable symbolTable, SymbolTable pascalST,
            TypeTable typeTable, List<String> semanticErrors);
    
    public abstract Label validate(CImports imports, JSymbolTable clasesST, 
            SymbolTable symbolTable, SymbolTable pascalST,
            TypeTable typeTable, List<String> semanticErrors, 
            Label previus);
    
    
    @Override
    public abstract RetJInvC3D generateCuartetas(AdmiMemory admiMemory, 
            List<Cuarteta> internalCuartetas, 
            Memory temporals, int instanceStackRef);
    
    @Override
    public abstract RetJInvC3D generateCuartetas(AdmiMemory admiMemory,
            List<Cuarteta> internalCuartetas,
            Memory temporals, RetJInvC3D previus);
    
    @Override
    public abstract boolean hasReturnType();
    @Override
    public abstract boolean isStatement();
    
    
}
