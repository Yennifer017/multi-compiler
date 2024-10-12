
package compi2.multi.compilator.semantic.jast;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.Statement;
import compi2.multi.compilator.semantic.util.JPassExp;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.Position;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class JConstructUse extends Statement{
    
    private boolean isFatherConst;
    private List<JPassExp> args;

    public JConstructUse(Position initPos, List<JPassExp> args, boolean isFatherConst) {
        super(initPos);
        this.args = args;
        this.isFatherConst = isFatherConst;
    }
    
    public JConstructUse(Position initPos, boolean isFatherConst) {
        super(initPos);
        this.args = new LinkedList<>();
        this.isFatherConst = isFatherConst;
    }

    @Override
    public ReturnCase validate(SymbolTable symbolTable, TypeTable typeTable, List<String> semanticErrors, SemanticRestrictions restrictions) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
