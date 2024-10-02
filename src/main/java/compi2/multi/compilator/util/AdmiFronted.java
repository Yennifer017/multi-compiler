
package compi2.multi.compilator.util;

import compi2.multi.compilator.Fronted;
import compi2.multi.compilator.exceptions.FileException;
import compi2.multi.compilator.exceptions.FileExtensionException;
import compi2.multi.compilator.exceptions.FileOpenException;
import compi2.multi.compilator.exceptions.ProjectOpenException;
import compi2.multi.compilator.files.AdmiFiles;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

/**
 *
 * @author blue-dragon
 */
public class AdmiFronted {
    public void rescribeTab(KeyEvent evt, JTextPane display){
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume(); // Evita que se inserte la tabulación.
            int spacesCount = 4; // Número de espacios que deseas insertar.
            String spaces = " ".repeat(spacesCount); // Crea una cadena con los espacios.
            int caretPosition = display.getCaretPosition(); // Obtiene la posición del caret.
            try {
                // Inserta el texto en la posición correcta.
                display.getDocument().insertString(caretPosition, spaces, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            display.setCaretPosition(caretPosition + spacesCount); // Mueve el caret hacia adelante.
        }
    }
    
    public void showInesperatedError() {
        JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado",
                "Error", JOptionPane.PLAIN_MESSAGE);
    }
    
    public void closeFile(AdmiFiles admiFiles) {
        try {
            admiFiles.closeFile();
        } catch (FileException ex) {
            JOptionPane.showInternalMessageDialog(
                    null, 
                    "No hay ningun archivo abierto", 
                    "Error al cerrar un archivo", 
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e){
            showInesperatedError();
        }
    }
    
    public void saveAs(AdmiFiles admiFiles, JTextPane display){
        try {
            String path = admiFiles.saveAs(display.getText());
            admiFiles.openFile(new File(path));
        } catch (FileException ex1) {
            JOptionPane.showMessageDialog(null, "No se guardo el archivo");
        } catch (IOException | FileOpenException ex1) {
            showInesperatedError();
        } 
    }
    
    public void openFileOp(AdmiFiles admiFiles){
        try {
            admiFiles.openFile();
        } catch (ProjectOpenException ex) {
            JOptionPane.showMessageDialog(null,
                    "Hay un archivo o proyecto abierto cierralo y vuelve a intentarlo");
        } catch (IOException ex) {
            System.out.println("Excepcion controlada");
        } catch (FileExtensionException ex) {
            JOptionPane.showMessageDialog(null,
                    "El archivo seleccionado no tiene una extension aceptada por el ide");
        } catch (FileOpenException ex) {
            JOptionPane.showMessageDialog(null,
                    "El archivo ya esta abierto");
        } catch (FileException ex) {
            Logger.getLogger(Fronted.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void openFilesInTreeEvent(MouseEvent evt, AdmiFiles admiFiles){
        if (evt.getClickCount() == 2) {
            try {
                admiFiles.openFileFromProject();
            } catch (IOException ex) {
                showInesperatedError();
            } catch (FileOpenException ex) {
                JOptionPane.showMessageDialog(null, "El archivo ya esta abierto");
            } catch (FileException ex) {
                System.out.println("Excepcion controlada");
            } catch (FileExtensionException ex) {
                JOptionPane.showMessageDialog(null,
                    "El archivo no es compatible con los aceptados por el ide (deber ser .txt o .csv)");
            } catch (Exception ex) {
                showInesperatedError();
            }
        }
    }
}
