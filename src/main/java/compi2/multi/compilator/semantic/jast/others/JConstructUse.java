
package compi2.multi.compilator.semantic.jast.others;


import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
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
public class JConstructUse extends JStatement{
    
    private boolean isFatherConst;
    private List<JExpression> args;

    public JConstructUse(Position initPos, List<JExpression> args, boolean isFatherConst) {
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
    public ReturnCase validate(JSymbolTable globalST, SymbolTable symbolTable, TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, SemanticRestrictions restrictions) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
