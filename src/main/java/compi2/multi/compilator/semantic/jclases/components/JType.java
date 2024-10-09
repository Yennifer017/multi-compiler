
package compi2.multi.compilator.semantic.jclases.components;

import compi2.multi.compilator.semantic.util.Label;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JType {
    protected JReferType refType;
    protected Label name;

    public JType(JReferType refType, Label name) {
        this.refType = refType;
        this.name = name;
    }

    public JType() {
        this.refType = JReferType.Void;
    }
    
    
}
