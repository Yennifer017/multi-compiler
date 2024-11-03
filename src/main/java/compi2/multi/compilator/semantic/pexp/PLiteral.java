
package compi2.multi.compilator.semantic.pexp;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.c3d.util.RetParamsC3D;
import compi2.multi.compilator.semantic.p.Expression;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.Position;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class PLiteral extends Expression{
    
    private Object object;
    private PrimitiveType type;
    
    public PLiteral(PrimitiveType type, Object object, Position pos){
        this.type = type;
        this.object = object;
        super.pos = pos;
    }

    @Override
    public boolean canRecoveryIntValue() {
        return object != null && !(object instanceof String);
    }
    
    @Override
    public int recoveryIntegerData(){
        if(object instanceof Boolean){
            boolean bool = (boolean) object;
            return bool == true ? 1 : 0;
        } 
        return Integer.parseInt(object.toString());
        
    }
    
    @Override
    public Label validateSimpleData(SymbolTable symbolTable, List<String> semanticErrors) {
        return new Label(this.type.getName(), pos);
    }

    @Override
    public Label validateComplexData(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors) {
        return new Label(this.type.getName(), pos);
    }

    @Override
    public RetParamsC3D generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass) {
        return new RetParamsC3D(object, type);
    }
}
