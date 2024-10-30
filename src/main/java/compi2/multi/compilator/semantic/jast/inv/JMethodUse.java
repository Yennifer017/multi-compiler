
package compi2.multi.compilator.semantic.jast.inv;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.util.RetJInvC3D;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.Position;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JMethodUse extends JInvocation{
    
    private String name;
    private JContextRef context;
    private List<JExpression> args;
    
    public JMethodUse(Position position, String name, JContextRef context) {
        super(position, context);
        this.name = name;
        this.args = new LinkedList<>();
    }
    
    
    public JMethodUse(Position position, String name, JContextRef context, List<JExpression> args) {
        super(position, context);
        this.name = name;
        this.args = args;
    }

    @Override
    public Label validate(JSymbolTable globalST, SymbolTable symbolTable, TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Label validate(JSymbolTable globalST, SymbolTable symbolTable, TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, Label previus) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isStatement() {
        return true;
    }

    @Override
    public boolean refersStack() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public RetJInvC3D generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, int instanceStackRef) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public RetJInvC3D generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, RetJInvC3D previus) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}
