
package compi2.multi.compilator.semantic.j;

import compi2.multi.compilator.analysis.typet.convert.TConvertidor;
import compi2.multi.compilator.analyzator.RefAnalyzator;
import compi2.multi.compilator.util.ErrorsRep;
import compi2.multi.compilator.util.Position;

/**
 *
 * @author blue-dragon
 */
public abstract class JExpression {
    protected Position pos;
    protected ErrorsRep errorsRep;
    protected TConvertidor tConvert;
    protected RefAnalyzator refAnalyzator;
    
    public JExpression(Position pos){
        this.pos = pos;
        errorsRep = new ErrorsRep();
        tConvert = new TConvertidor();
        refAnalyzator = new RefAnalyzator();
    }
    
}
