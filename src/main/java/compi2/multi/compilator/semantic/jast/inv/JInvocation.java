
package compi2.multi.compilator.semantic.jast.inv;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analysis.typet.convert.TConvertidor;
import compi2.multi.compilator.c3d.generators.ExpGenC3D;
import compi2.multi.compilator.analyzator.FunctionRefAnalyzator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.util.InvocationGenerateC3D;
import compi2.multi.compilator.c3d.util.RetJInvC3D;
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
public abstract class JInvocation implements InvocationGenerateC3D{
    protected Label inv;
    protected JContextRef context;
    
    protected ErrorsRep errorsRep;
    protected TConvertidor tconvert;
    protected ExpGenC3D expGenC3D;
            
    protected FunctionRefAnalyzator refFun;
    
    public JInvocation(Label inv, JContextRef context){
        this.inv = inv;
        this.context = context;
        this.refFun =  new FunctionRefAnalyzator();
        this.errorsRep = new ErrorsRep();
        this.tconvert = new TConvertidor();
        this.expGenC3D = new ExpGenC3D();
    }
    
    public abstract Label validate(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors);
    
    public abstract Label validate(JSymbolTable globalST, SymbolTable symbolTable,
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, Label previus);
    
    /**
     * Genera cuartetas
     * @param admiMemory
     * @param internalCuartetas
     * @param temporals
     * @param instanceStackRef
     * @return un objeto que contiene la posicion de memoria del objeto
     * ya sea en el heap o en el stack
     */
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
    
    @Override
    public abstract boolean canAssign();
}
