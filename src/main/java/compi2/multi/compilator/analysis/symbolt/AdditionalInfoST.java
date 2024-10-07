
package compi2.multi.compilator.analysis.symbolt;

/**
 *
 * @author blue-dragon
 */
public enum AdditionalInfoST {
    DIR_RETORNO_ROW("#return"),
    DIR_HEAP_ROW("#dirHeap");

    private String nameRow;
    private AdditionalInfoST(String name){
        nameRow = name;
    }
    
    public String getNameRow(){
        return this.nameRow;
    }
}
