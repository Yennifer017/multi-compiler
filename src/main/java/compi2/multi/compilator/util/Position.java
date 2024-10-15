
package compi2.multi.compilator.util;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class Position {
    private int line, col;

    public Position(int line, int col) {
        this.line = line;
        this.col = col;
    }
    
}
