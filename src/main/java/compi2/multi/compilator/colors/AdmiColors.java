
package compi2.multi.compilator.colors;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.StyledDocument;

/**
 *
 * @author blue-dragon
 */
public class AdmiColors {
    
    private static Timer timer;
    
    public AdmiColors(JTextPane pane){
        StyledDocument doc = pane.getStyledDocument();

        // Agregar un DocumentListener para detectar cambios en el texto
        doc.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                textoCambiado(pane);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                textoCambiado(pane);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                
            }
        });
    }
    
     private void textoCambiado(JTextPane pane) {
        if (timer != null) {
            timer.cancel(); 
        }
        
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Pintor pintor = new Pintor(pane);
                pintor.start();
            }
        }, 500); 
    }

}
