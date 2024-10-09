
package compi2.multi.compilator.semantic.jclases.components;

import compi2.multi.compilator.semantic.util.Label;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JArg {
    private JType type;
    private Label name;

    public JArg(JType type, Label name) {
        this.type = type;
        this.name = name;
    }
    
}
