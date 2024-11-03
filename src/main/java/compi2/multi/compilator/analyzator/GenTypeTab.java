
package compi2.multi.compilator.analyzator;

import compi2.multi.compilator.semantic.p.DefAst;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.semantic.pobj.Range;
import compi2.multi.compilator.semantic.pobj.SubRangeDef;
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
