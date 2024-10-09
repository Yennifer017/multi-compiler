
package compi2.multi.compilator.semantic.jclases.components;

import compi2.multi.compilator.semantic.util.Label;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JArrayType extends JType{
    private int dims;
    
    public JArrayType(Label name, JReferType refType, int dims){
        super.name = name;
        super.refType = refType;
        this.dims = dims;
    }
}
