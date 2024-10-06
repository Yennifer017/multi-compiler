
package compi2.multi.compilator.semantic.past;


import compi2.multi.compilator.semantic.Statement;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
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
public class SimpleStmt extends Statement{
    public final static int CONTINUE = 1;
    public final static int BREAK = 2;
    private int typeStruct;
    
    public SimpleStmt(int typeStruct, Position pos){
        super(pos);
        this.typeStruct = typeStruct;
    }

    @Override
    public ReturnCase validate(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions) {
        switch (typeStruct) {
            case CONTINUE -> {
                if(!restrictions.allowContinue()){
                    semanticErrors.add(super.errorsRep.ilegalStmt("CONTINUE", initPos));
                }
            }
            case BREAK -> {
                if(!restrictions.allowBreak()){
                    semanticErrors.add(super.errorsRep.ilegalStmt("BREAK", initPos));
                }
            }
            default -> throw new AssertionError();
        }
        return new ReturnCase(false);
    }

    
}
