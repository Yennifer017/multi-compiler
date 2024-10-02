
package compi2.multi.compilator.util;

import lombok.Getter;

/**
 *
 * @author blue-dragon
 */
@Getter
public class Index {
    private int number;
    
    public void increment(){
        number++;
    }
    
    public void increment(int number){
        this.number += number;
    }
}
