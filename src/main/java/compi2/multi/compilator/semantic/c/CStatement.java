
package compi2.multi.compilator.semantic.c;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analysis.typet.convert.TConvertidor;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
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
public abstract class CStatement {
    protected Position initPos;
    protected ErrorsRep errorsRep;
    protected TConvertidor tConvert;
    
    public CStatement(Position initPos){
        this.initPos = initPos;
        this.errorsRep = new ErrorsRep();
        this.tConvert = new TConvertidor();
    }
    
    public abstract ReturnCase validate(CImports imports, JSymbolTable clasesST, 
            SymbolTable symbolTable, SymbolTable pascalST,
            TypeTable typeTable, List<String> semanticErrors, SemanticRestrictions restrictions);
}
