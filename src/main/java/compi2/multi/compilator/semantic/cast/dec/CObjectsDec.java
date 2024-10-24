
package compi2.multi.compilator.semantic.cast.dec;

import compi2.multi.compilator.semantic.c.CDef;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class CObjectsDec extends CDef{
    private List<CObjectDec> objects;
    
    public CObjectsDec(List<CObjectDec> objects){
        this.objects = objects;
    }

    
}
