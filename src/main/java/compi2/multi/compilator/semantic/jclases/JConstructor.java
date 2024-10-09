
package compi2.multi.compilator.semantic.jclases;

import compi2.multi.compilator.semantic.jclases.components.JArg;
import compi2.multi.compilator.analysis.symbolt.AccessMod;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.Statement;
import compi2.multi.compilator.semantic.util.Label;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class JConstructor extends JFunction{

    public JConstructor(Label name, AccessMod access, List<JArg> args, List<Statement> internalStmts) {
        super(access, args, internalStmts);
        super.name = name;
    }
    

    @Override
    public RowST generateRowST(SymbolTable symbolTable, TypeTable typeTable, List<String> semanticErrors) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
