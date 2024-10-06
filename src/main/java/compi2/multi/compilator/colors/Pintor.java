
package compi2.multi.compilator.colors;

import java.io.IOException;
import java.io.StringReader;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;


/**
 *
 * @author blue-dragon
 */
public class Pintor extends Thread{
    
    private JTextPane pane;
    
    public Pintor(JTextPane pane){
        this.pane = pane;
    }
    
    private void pintar(Coloreado coloreado){
        StyledDocument doc = this.pane.getStyledDocument();
        doc.setCharacterAttributes(
                (int) coloreado.getInitPos(), 
                coloreado.getLength(), 
                coloreado.getType().getAttributeSet(), 
                false
        );
    }
    
    @Override
    public void run(){
        StringReader reader = new StringReader(pane.getText());
        PintorLexer lexer = new PintorLexer(reader);
        while (!lexer.yyatEOF()) {            
            try {
                Coloreado coloreado = lexer.yylex();
                pintar(coloreado);
            } catch (IOException | NullPointerException ex) {
            }
        }
    }
}
