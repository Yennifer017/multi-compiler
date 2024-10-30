
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
import compi2.multi.compilator.c3d.access.TemporalUse;
import compi2.multi.compilator.c3d.util.RetJInvC3D;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.ErrorsRep;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public abstract class JInvocation {
    protected Position position;
    protected JContextRef context;
    
    protected ErrorsRep errorsRep;
    protected TConvertidor tconvert;
    protected ExpGenC3D expGenC3D;
            
    protected FunctionRefAnalyzator refFun;
    
    public JInvocation(Position position, JContextRef context){
        this.position = position;
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
    
    public abstract boolean isStatement();
    public abstract boolean refersStack();
    
    public abstract RetJInvC3D generateCuartetas(AdmiMemory admiMemory, 
            List<Cuarteta> internalCuartetas, 
            Memory temporals, int instanceStackRef);
    
    public abstract RetJInvC3D generateCuartetas(AdmiMemory admiMemory,
            List<Cuarteta> internalCuartetas,
            Memory temporals, RetJInvC3D previus);
}
