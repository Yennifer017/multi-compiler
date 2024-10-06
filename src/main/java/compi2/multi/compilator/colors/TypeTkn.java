
package compi2.multi.compilator.colors;

import java.awt.Color;
import javax.swing.text.AttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author blue-dragon
 */
public enum TypeTkn {
    OPERATOR(new Color(0, 251, 242)), //celeste vibrante
    DELIMITATOR(Color.GREEN),
    COMPARATOR(new Color(0, 251, 242)), //celeste vibrante
    RESERVED_WORD(new Color(158,0,255)), //morado
    SECTION(Color.YELLOW),
    COMMENTARY(Color.GRAY),
    LITERALS(Color.ORANGE),
    STRINGS(Color.pink),
    BOOLEANS(Color.ORANGE),
    OTHERS(Color.pink),
    ID(Color.WHITE),
    ERROR(Color.RED)
    ;
    
    private AttributeSet attributeSet;
    private TypeTkn(Color color){
        StyleContext cont = StyleContext.getDefaultStyleContext();
        this.attributeSet = cont.addAttribute(
                cont.getEmptySet(), 
                StyleConstants.Foreground,
                color
        );
    }
    
    public AttributeSet getAttributeSet(){
        return this.attributeSet;
    }
}
