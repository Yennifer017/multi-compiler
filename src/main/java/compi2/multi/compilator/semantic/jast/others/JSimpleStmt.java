
package compi2.multi.compilator.semantic.jast.others;

import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.util.Position;

/**
 *
 * @author blue-dragon
 */
public class JSimpleStmt extends JStatement{
    
    private boolean isBreak;

    public JSimpleStmt(Position initPos, boolean isBreak) {
        super(initPos);
        this.isBreak = isBreak;
    }
    
    public boolean isBreak(){
        return this.isBreak;
    }
    
}
