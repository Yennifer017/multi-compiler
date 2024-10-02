package compi2.multi.compilator.files;

import compi2.multi.compilator.exceptions.DirectoryException;
import compi2.multi.compilator.files.model.FileProject;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JFileChooser;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author yenni
 */
public class UtilForDirectories {

    public String getPathFolder() throws IOException {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.showOpenDialog(null);
            return chooser.getSelectedFile().getAbsolutePath();
        } catch (NullPointerException e) {
            throw new IOException();
        }
    }
    
    public void openProject(String superPath, JTree treeDisplay) throws DirectoryException{
        File directory = new File(superPath);
        if (directory.exists() && directory.isDirectory()) {
            //inicializando el arbol
            DefaultMutableTreeNode firstNode = new DefaultMutableTreeNode(new FileProject(directory));
            DefaultTreeModel defaultTreeModel = new DefaultTreeModel(firstNode);
            treeDisplay.setModel(defaultTreeModel);
            
            //teniendo un nodo con una carpeta padre
            DefaultMutableTreeNode currentParentModel = firstNode;
            
            //Desglosando los archivos internos
            openCarpet(treeDisplay, directory, defaultTreeModel, currentParentModel);
        } else {
            throw new DirectoryException("La carpeta seleccionada es invalida");
        }
    }
    
    public void openCarpet(JTree treeDisplay, File directory, 
            DefaultTreeModel defaultTreeModel,  DefaultMutableTreeNode currentParentModel) throws DirectoryException{
        File[] subFiles = directory.listFiles();
        if (subFiles == null) {
            throw new DirectoryException("Sin acceso a una carpeta");
        }
        for (File subFile : subFiles) {
            DefaultMutableTreeNode currentNode = new DefaultMutableTreeNode(
                    new FileProject(subFile)
            );
            defaultTreeModel.insertNodeInto(currentNode, currentParentModel,
                    currentParentModel.getChildCount());

            if (subFile.isDirectory()) {
                openCarpet(treeDisplay, subFile, defaultTreeModel, currentNode);
            }
        }
    }
    
    /**
     * Crea una carpeta
     * @param rootPath
     * @param name
     * @return el path de la carpeta creada
     */
    public String createDirectory(String rootPath, String name) throws IOException, DirectoryException{
        String path = rootPath + getCarpetSeparator() + name;
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdir();
        } else {
            throw new DirectoryException("El nombre del proyecto ya existe");
        }
        return path;
    }

    public String copyFilesToPath(String rootPath, File[] files) {
        String mss = "";
        for (int i = 0; i < files.length; i++) {
            try {
                Path source = Paths.get(files[i].getAbsolutePath());
                Path out = Paths.get(rootPath, getCarpetSeparator() + files[i].getName());// Create a new path for each file
                Files.copy(source, out);
            } catch (Exception e) {
                mss += "No se pudo copiar el archivo: " + files[i].getName() + "\n";
            }
        }
        return mss;
    }

    public String getCarpetSeparator() {
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("windows")) {
            return "\\";
        } else {
            return "/";
        }
    }
    
    public static String getCarpetSeparatorStatic() {
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("windows")) {
            return "\\";
        } else {
            return "/";
        }
    }
}
