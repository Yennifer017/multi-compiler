
package compi2.multi.compilator.semantic.cast.defaultfuncs;

import compi2.multi.compilator.semantic.c.CStatement;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.Position;

/**
 *
 * @author blue-dragon
 */
public class CScanf extends CStatement{
    
    private Label mascara;
    private Label variable;
    public CScanf(Position initPos, Label mascara, Label variable) {
        super(initPos);
        this.mascara = mascara;
        this.variable = variable;
    }

    
}
