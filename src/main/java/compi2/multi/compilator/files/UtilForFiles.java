package compi2.multi.compilator.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author yenni
 */
public class UtilForFiles {

    /**
     * Leer el contenido de un archivo
     *
     * @param path del archivo
     * @return el texto leido
     * @throws java.io.FileNotFoundException
     */
    public String readTextFile(String path) throws FileNotFoundException, IOException { //obtiene el texto contenido en un archivo y lo devuelve
        String texto = "";
        try {
            File archivo = new File(path); //creando el archivo
            FileReader lector = new FileReader(archivo); //lector del archivo
            BufferedReader buffer = new BufferedReader(lector); //para leer mas rapido el archivo
            String linea;
            while ((linea = buffer.readLine()) != null) {
                texto += linea + "\n";
            }
            buffer.close();
            lector.close();
        } catch (NullPointerException error) {
            System.out.println("No se ha podido abrir un archivo, excepcion controlada");
        }
        return texto;
    }

    /**
     * Muestra un selector de un archivo con cualquier extension
     *
     * @return el path del archivo
     * @throws java.io.IOException
     */
    public String getPath() throws IOException {
        try {
            JFileChooser buscador = new JFileChooser(); //creando el buscador de archivos
            buscador.showOpenDialog(null); //abrir el buscador
            return buscador.getSelectedFile().getAbsolutePath();
        } catch (NullPointerException e) {
            throw new IOException();
        }
    }

    /**
     * Obtiene el path a partir de un selector de archivos
     *
     * @param description para el usuario
     * @param aceptedExtensionFiles, lista de extensiones aceptadas
     * @return
     */
    public String getPath(String description, String[] aceptedExtensionFiles) {
        try {
            JFileChooser buscador = new JFileChooser(); //creando el buscador de archivos
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    description, aceptedExtensionFiles);
            buscador.setFileFilter(filter);
            buscador.showOpenDialog(null); //abrir el buscador
            return buscador.getSelectedFile().getAbsolutePath();
        } catch (NullPointerException e) {
            return "not-found";
        }
    }

    /**
     * Reescribe un archivo a partir de un texto
     *
     * @param texto
     * @param ruta
     */
    public void saveFile(String texto, String ruta) { //reescribe un archivo a partir de un texto
        File archivo = new File(ruta); //obtiene el archivo de la ruta
        saveFile(texto, archivo);
    }

    public void saveFile(String texto, File file) {
        try {
            FileWriter escritor = new FileWriter(file, false);
            BufferedWriter buffer = new BufferedWriter(escritor);
            buffer.write(texto);
            buffer.close();
            escritor.close();
        } catch (IOException error) {
            System.out.println(error);
        }
    }

    public void deleteFile(String path) {
        File archivo = new File(path);
        archivo.delete();
    }

    public boolean saveAs(String text, String extension, String rootFolder, String path) {
        if (path != null && !path.equals("")) {
            File file = new File(path + extension);
            if (!file.exists()) {
                this.saveFile(text, rootFolder + UtilForDirectories.getCarpetSeparatorStatic() 
                        +  path + extension);
                return true;
            } else {
                int opcion = JOptionPane.showConfirmDialog(null, """
                        Se ha encontrado un archivo con el mismo nombre especificado.
                        ¿Deseas sobreescribirlo?""", "sobreescribiendo archivo...",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.OK_CANCEL_OPTION);
                if (opcion == 0) {
                    this.saveFile(text, path + extension);
                    return true;
                }
            }
        } else if (path != null && path.equals("")) {
            JOptionPane.showMessageDialog(null, """
                                            No has ingresado un nombre de archivo valido,
                                            no se ha podido guardar, intentalo de nuevo.""",
                    "Se ha producido un error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public void saveFromExistentPath(String text, String path, String fileName) {
        int option = JOptionPane.showConfirmDialog(null, "¿Guardar el archivo "
                + fileName + "?", "Guardando archivo..",
                JOptionPane.CANCEL_OPTION, JOptionPane.OK_CANCEL_OPTION);
        if (option == 0) { //cuando confirma
            this.saveFile(text, path);
            JOptionPane.showMessageDialog(null, "Se ha guardado el archivo", ""
                    + "Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public boolean hasAceptedPath(String[] aceptedPaths, File file) {
        for (String aceptedPath : aceptedPaths) {
            if (file.getName().contains(aceptedPath)) {
                return true;
            }
        }
        return false;
    }

    public File[] getFiles(String description, String[] aceptedExtensions) throws IOException {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setMultiSelectionEnabled(true);
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    description, aceptedExtensions);
            chooser.setFileFilter(filter);
            chooser.showOpenDialog(null); //abrir el buscador
            return chooser.getSelectedFiles();
        } catch (NullPointerException e) {
            throw new IOException();
        }
    }
}
