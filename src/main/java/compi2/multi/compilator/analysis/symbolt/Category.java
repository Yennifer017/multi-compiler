
package compi2.multi.compilator.analysis.symbolt;

/**
 *
 * @author blue-dragon
 */
public enum Category {
    Retorno("Retorno "),
    InternalAnonymusST("tabla de simbolos interna"),
    
    Variable("Variable"),
    Constant("Constante"),
    Function("Funcion"),
    Procedure("Procedimiento"),
    Param_val("Parametro por valor"), 
    Param_ref("Parametro por referencia"),
    Subrange("Subrango"),
    Array("arreglo"),
    Record("Record"),
    
    JClass("Clase Java"),
    JConstruct("Constructor"),
    JField("atributo de clase"), 
    JHeapDir("direccion dentro del heap"),
    JMethod("metodo de una clase"), 
    JDirInstance("direccion de instancia de una clase");
    ;
    
    private String name;
    private Category(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
    
}
