
package compi2.multi.compilator.analysis.typet;

/**
 *
 * @author blue-dragon
 */
public enum PrimitiveType {
    IntegerPT("integer", 2, true, true),
    RealPT("real", 4, false, true),
    BooleanPT("boolean", 0, true, true),
    CharPT("char", 1, true, true),
    StringPT("string", 5, false, false),
    LongintPT("longint", 3, false, true)
    ;
    
    private String name;
    private Type type;
    private int id;
    private boolean isIntegerNumeric;
    private boolean isNumeric;
    
    private PrimitiveType(String name, int id, boolean isIntegerNumeric, boolean isNumeric){
        this.name = name;
        this.type = new Type(name, 1);
        this.id = id;
        this.isIntegerNumeric = isIntegerNumeric;
        this.isNumeric = isNumeric;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getCName(){
        return "";
    }
    
    public Type getType(){
        return this.type;
    }
    
    public int getId(){
        return this.id;
    }
    
    public boolean isIntegerNumeric(){
        return this.isIntegerNumeric;
    }
   
    public boolean isNumeric(){
        return this.isNumeric;
    }
    
}   
