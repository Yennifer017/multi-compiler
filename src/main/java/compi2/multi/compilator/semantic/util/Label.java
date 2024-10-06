
package compi2.multi.compilator.semantic.util;

import compi2.multi.compilator.util.Position;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class Label {
    private String name;
    private Position position;

    public Label(String name, Position position) {
        this.name = name;
        this.position = position;
    }
    
    
}
