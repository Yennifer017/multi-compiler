
package compi2.multi.compilator.analyzator;

import compi2.multi.compilator.semantic.obj.DefAst;
import compi2.multi.compilator.semantic.obj.Label;
import compi2.multi.compilator.semantic.obj.Range;
import compi2.multi.compilator.semantic.obj.SubRangeDef;
import java.util.LinkedList;
import java.util.List;


/**
 *
 * @author blue-dragon
 */
public class GenTypeTab extends Generator {
    
    public List<DefAst> rangeDef(List<Label> ids, Range range){
        List<DefAst> definitions = new LinkedList<>();
        for (Label id : ids) {
            definitions.add(new SubRangeDef(id, range));
        }
        return definitions;
    }
    
    
}
