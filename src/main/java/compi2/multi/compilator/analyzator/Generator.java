
package compi2.multi.compilator.analyzator;


import compi2.multi.compilator.semantic.obj.ArrayDef;
import compi2.multi.compilator.semantic.obj.DefAst;
import compi2.multi.compilator.semantic.obj.Label;
import compi2.multi.compilator.semantic.obj.Range;
import compi2.multi.compilator.semantic.obj.SingleDef;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public abstract class Generator {
    
    public List<DefAst> userDef(List<Label> ids, Label type){
        List<DefAst> definitions = new LinkedList<>();
        for (Label id : ids) {
            definitions.add(new SingleDef(id, type));
        }
        return definitions; 
    }
    
    
    public List<DefAst> arrayDef(List<Label> ids, Label type, Range range){
        List<DefAst> definitions = new LinkedList<>();
        for (Label id : ids) {
            definitions.add(new ArrayDef(id, type, range));
        }
        return definitions; 
    }
    
    
}
