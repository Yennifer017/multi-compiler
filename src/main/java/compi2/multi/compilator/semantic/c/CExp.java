
package compi2.multi.compilator.semantic.c;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analysis.typet.convert.TConvertidor;
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
    
    public CExp(Position pos){
        this.pos = pos;
        this.tConvert = new TConvertidor();
        this.errorsRep = new ErrorsRep();
    }
    
    public abstract Label validateComplexData(CImports imports, JSymbolTable clasesST, 
            SymbolTable symbolTable, SymbolTable pascalST,
            TypeTable typeTable, List<String> semanticErrors);
    
    public abstract Label validateSimpleData(SymbolTable symbolTable, 
            List<String> semanticErrors);
}
