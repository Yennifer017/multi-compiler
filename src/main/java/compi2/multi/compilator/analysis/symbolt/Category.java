
package compi2.multi.compilator.analysis.symbolt;

/**
 *
 * @author blue-dragon
 */
public enum Category {
    Variable("Variable"),
    Constant("Constante"),
    Function("Funcion"),
    Procedure("Procedimiento"),
    Param_val("Parametro por valor"), 
    Param_ref("Parametro por referencia"),
    Subrange("Subrango"),
    Array("arreglo"),
    Record("Record");
    
    private String name;
    private Category(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
    
}
