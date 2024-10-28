
package compi2.multi.compilator.semantic.jexp;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.c3d.util.RetParamsC3D;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.util.Label;
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
public class JLiteral extends JExpression{
    private Object object;
    private PrimitiveType type;
    
    public JLiteral(Position initPos, Object object, PrimitiveType type){
        super(initPos);
        this.object = object;
        this.type = type;
    }

    @Override
    public Label validateData(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, 
            SemanticRestrictions restrictions) {
        return new Label(this.type.getName(), pos);
    }

    @Override
    public Label validateSimpleData(List<String> semanticErrors) {
        return new Label(this.type.getName(), pos);
    }

    @Override
    public RetParamsC3D generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass) {
        return new RetParamsC3D(object, type);
    }
    
}
