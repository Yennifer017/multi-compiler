
package compi2.multi.compilator.colors;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class Coloreado {
    private TypeTkn type;
    private long initPos;
    private int length;

    public Coloreado(TypeTkn type, long initPos, int length) {
        this.type = type;
        this.initPos = initPos;
        this.length = length;
    }
    
}
