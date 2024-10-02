package compi2.multi.compilator.files;


import compi2.multi.compilator.exceptions.DirectoryException;
import compi2.multi.compilator.exceptions.FileException;
import compi2.multi.compilator.exceptions.FileExtensionException;
import compi2.multi.compilator.exceptions.FileOpenException;
import compi2.multi.compilator.exceptions.ProjectOpenException;
import compi2.multi.compilator.files.model.FileProject;
import compi2.multi.compilator.files.model.OpenFile;
import compi2.multi.compilator.util.BinarySearch;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author yenni
 */
public class AdmiFiles {

    public static final String aceptedExtensions[] = {"pass", "pp", "txt"};
    private static final String EMPTY_NOTATION = "[none]";
    private static final String FILE_NAME_REGEX = "[A-Za-z][A-Za-z0-9_]*";

    private boolean isOpenProject;
    
    private List<OpenFile> openFiles;
    private OpenFile currentFile;

    private UtilForFiles filesU;
    private UtilForDirectories directoryU;

    private JTree treeDisplay;
    private JPanel filesBar;
    private JTextPane displayContent;
    private JLabel labelForFileName;


    public AdmiFiles(JTree treeDisplay, JPanel filesBar, JTextPane displayContent, JLabel labelForFileName) {
        this.treeDisplay = treeDisplay;
        this.filesU = new UtilForFiles();
        this.directoryU = new UtilForDirectories();
        openFiles = new ArrayList<>();
        this.filesBar = filesBar;
        this.displayContent = displayContent;
        this.labelForFileName = labelForFileName;
    }

    public void openProject() throws IOException, ProjectOpenException, DirectoryException, FileOpenException {
        openProject(directoryU.getPathFolder());
    }
    
    public List<OpenFile> getOpenFiles(){
        return this.openFiles;
    }

    /**
     * Abre un proyecto que el usuario selecionara
     *
     * @param path
     * @throws java.io.IOException
     */
    public void openProject(String path)
            throws ProjectOpenException, DirectoryException, IOException, FileOpenException {
        if (isOpenProject) {
            this.closeDirectoryView();
        }
        //abrir el proyecto
        this.isOpenProject = true;
        directoryU.openProject(path, treeDisplay);
        treeDisplay.revalidate();
        treeDisplay.repaint();
    }

    /**
     * Abre un archivo al tener seleccionado un elemento del arbol de trabajo
     *
     * @throws java.io.IOException

     */
    public void openFileFromProject()
            throws IOException, FileOpenException, FileException, FileExtensionException {
        DefaultMutableTreeNode selectedNode
                = (DefaultMutableTreeNode) treeDisplay.getLastSelectedPathComponent();
        if (selectedNode != null) {
            if (!(selectedNode.getUserObject() instanceof FileProject)) {
                throw new FileException();
            }
            
            FileProject selectedFileProject = (FileProject) selectedNode.getUserObject();
            if(selectedFileProject.getFile().isFile()){
                File file = selectedFileProject.getFile();
                boolean isAceptedExtension = filesU.hasAceptedPath(aceptedExtensions, file);

                if(!isAceptedExtension){
                    throw new FileExtensionException();
                } else {
                    openFile(file);
                }
            }
        }
    }
    
    /**
     *   Abre un archivo con la particularidad de que setea botones en la parte superior
     *   y los agrega a la lista de archivos abiertos
     * @param file
     * @throws java.io.IOException
    */
    public void openFile(File file) throws IOException, FileOpenException, FileException {
        if (file.isFile() // y el archivo no esta abierto aun
                && BinarySearch.search(openFiles, file.getAbsolutePath()) == -1) {
            if (currentFile != null) {
                currentFile.setOpenContent(displayContent.getText());
            }
            String content = filesU.readTextFile(file.getAbsolutePath());
            currentFile = new OpenFile(file, content);
            openFiles.add(currentFile);
            Collections.sort(openFiles);
            displayContent.setText(filesU.readTextFile(file.getAbsolutePath()));
            labelForFileName.setText(file.getName());
            //anadir los botones
            currentFile.init(displayContent, labelForFileName, this);
            filesBar.add(currentFile);
        } else if (file.isFile()) { //cuando ya esta abierto
            throw new FileOpenException();
        } else {
            throw new FileException("No se pudo abrir el archivo");
        }
    }

    /**
     * Ciera todo lo actual
     *
     *  cuando no hay un
     * proyecto abierto
     */
    public void closeAll() throws DirectoryException {
        if (!isOpenProject) {
            throw new DirectoryException("No hay ningun proyecto abierto");
        } else {
            openFiles.clear();
            closeDirectoryView();
            closeOpenFiles();
            isOpenProject = false;
        }
    }
    
    public void closeDirectoryView(){
        treeDisplay.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("root")));
        treeDisplay.revalidate();
        treeDisplay.repaint();
    }

    public void closeFile() throws FileException {
        int position = BinarySearch.search(openFiles, currentFile.getFile().getAbsolutePath());
        if (position != -1) {
            openFiles.remove(position);
            filesBar.remove(currentFile);
            filesBar.revalidate();
            filesBar.repaint();
            currentFile = null;
            displayContent.setText("");
            labelForFileName.setText(EMPTY_NOTATION);
        } else {
            throw new FileException();
        }
    }

    /**
     * Cierra todos los archivos
     */
    public void closeOpenFiles() {
        filesBar.removeAll();
        filesBar.revalidate();
        filesBar.repaint();
        openFiles.clear();
        currentFile = null;
        displayContent.setText("");
        labelForFileName.setText(EMPTY_NOTATION);
    }

    /**
     * Guarda el archivo actual
     *
     */
    public void saveFile() throws FileException {
        if (currentFile != null) {
            filesU.saveFile(displayContent.getText(), currentFile.getFile());
        } else {
            throw new FileException();
        }
    }
    
    /**
     * Guarda el contenido actual como un nuevo archivo y lo abre a su vez
     * @param content
     * @return el path
     * @throws compi2.pascal.valitations.exceptions.FileException 
     * @throws java.io.IOException 
     */
    public String saveAs(String content) throws FileException, IOException{
        JOptionPane.showMessageDialog(null, "Selecciona la carpeta donde se guardara el archivo");
        String root = directoryU.getPathFolder();
        
        String path = JOptionPane.showInputDialog(null, "Ingresa un nombre para guardar el archivo",
                "Guardando un nuevo archivo", JOptionPane.QUESTION_MESSAGE);
        if (path == null || !path.matches(FILE_NAME_REGEX)) {
            throw new FileException("El nombre del archivo es invalido");
        }
        filesU.saveAs(content, ".pass", root, path);
        return root + directoryU.getCarpetSeparator() + path + ".pass";
    }

    /** 
     * Guarda un nuevo archivo en blanco
     * @return el path del archivo creado
     * @throws compi2.pascal.valitations.exceptions.FileException
     * @throws java.io.IOException
     */
    public String saveNewFile() throws FileException, IOException{
        return this.saveAs("");
    }
    
    /**
     * Abre un archivo pidiendo al usuario un path
     *
     * @throws compi2.pascal.valitations.exceptions.ProjectOpenException
     * @throws java.io.IOException por cualquier excepcion extra
     * @throws compi2.pascal.valitations.exceptions.FileExtensionException
     */
    public void openFile()
            throws ProjectOpenException, IOException, FileExtensionException, FileOpenException, FileException {
        File file = new File(filesU.getPath("Archivos pascal",
                aceptedExtensions));
        if (!filesU.hasAceptedPath(aceptedExtensions, file)) {
            throw new FileExtensionException();
        }
        openFile(file);
    }    
    
    /*public String createProject() throws IOException, InvalidDataException {
        JOptionPane.showMessageDialog(null,
                "A continuacion selecciona el path donde sera guardado el proyecto");
        String rootPath = directoryU.getPathFolder();
        String nameProject = JOptionPane.showInputDialog("Ingresa el nombre del proyecto");
        if (nameProject.matches("([a-z]|[A-Z]| _ )([a-z]|[A-Z]| _ |[0-9]| - | @ | + | [*] | #)*")) {
            directoryU.createDirectory(rootPath, nameProject);
            rootPath += directoryU.getCarpetSeparator() + nameProject;
            JOptionPane.showMessageDialog(null,
                    "A continuacion seleccina los archivos que sean copiados al proyecto");
            String error = directoryU.copyFilesToPath(rootPath,
                    filesU.getFiles("Archivos csv", aceptedExtensions));
            if (!error.isEmpty()) {
                throw new InvalidDataException(error
                        + "\n Advertencia: la carpeta se ha creado, por favor ingresa los archivos manualmente");
            }
            return rootPath;
        } else {
            throw new InvalidDataException("El nombre de la carpeta no es valida");
        }
    }
*/
    /*private String convertToAbsolutePath(String path) {
        try {
            path = path.replace(".", directoryU.getCarpetSeparator());
            DefaultTreeModel model = (DefaultTreeModel) treeDisplay.getModel();
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
            FileProject rootNode = (FileProject) root.getUserObject();
            String rootPath = rootNode.getFile().getAbsolutePath();
            rootPath = rootPath.substring(0, rootPath.length() - rootNode.getFile().getName().length());
            return rootPath + path + ".csv";
        } catch (Exception e) {
            return path;
        }
    }*/

    public void setCurrentFile(OpenFile currentFile) {
        this.currentFile = currentFile;
    }

    public OpenFile getCurrentFile() {
        return this.currentFile;
    }

    /*public String getCurrentDisplayTxt() {
        return this.displayContent.getText().replace("\t", "");
    }*/

    /*private String getRootFolderProject() throws DirectoryException {
        if (currentProject.isEmpty()) {
            throw new DirectoryException();
        }
        DefaultTreeModel model = (DefaultTreeModel) treeDisplay.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        FileProject rootNode = (FileProject) root.getUserObject();
        String rootPath = rootNode.getFile().getAbsolutePath();
        return rootPath;
    }
*/
    /*public String saveNewFileInProject() throws InvalidDataException, IOException, DirectoryException {
        JOptionPane.showMessageDialog(null, """
                                            Selecciona la carpeta donde se guardara el archivo, esta tiene que estar dentro del proyecto actual, 
                                            
                                            IMPORTANTE!!!: guarda los archivos abiertos antes""");
        String rootFolder = directoryU.getPathFolder();
        if (rootFolder.contains(this.getRootFolderProject())) {
            String content = JOptionPane.showInputDialog(
                    "Ingresa las columnas que iran en el archivo, separado con comas y sin espacios");
            if (!content.matches(CSV_COLUMNS_FORMAT)) {
                throw new InvalidDataException("El formato de columnas es invalido");
            }
            String path = JOptionPane.showInputDialog(null, "Ingresa un nombre para guardar el archivo",
                    "Guardando un nuevo archivo", JOptionPane.QUESTION_MESSAGE);
            if (!path.matches(COLUMN_FORMAT)) {
                throw new InvalidDataException("El nombre del archivo es invalido");
            }

            filesU.saveAs(content, ".csv", rootFolder, path);
            return this.getRootFolderProject();
        } else {
            throw new InvalidDataException("La carpeta seleccionada, no esta dentro del proyecto");
        }
    }*/

    public boolean isOpenProject() {
        return this.isOpenProject;
    }

    /*public String createFolder() throws InvalidDataException, IOException, DirectoryException {
        if (!currentProject.isEmpty()) {
            JOptionPane.showMessageDialog(null, """
                                                Selecciona la carpeta donde se guardara el archivo, esta tiene que estar dentro del proyecto actual, 
                                                
                                                IMPORTANTE: guarda los archivos abiertos antes""");
            String rootFolder = directoryU.getPathFolder();
            if (rootFolder.contains(this.getRootFolderProject())) {
                String path = JOptionPane.showInputDialog(null, "Ingresa un nombre para la carpeta",
                        "Guardando una carpeta", JOptionPane.QUESTION_MESSAGE);
                if (!path.matches(COLUMN_FORMAT)) {
                    throw new InvalidDataException("El nombre del archivo es invalido");
                }
                directoryU.createDirectory(rootFolder, path);
                JOptionPane.showMessageDialog(null, "Carpeta creada exitosamente");
                return this.getRootFolderProject();
            } else {
                throw new InvalidDataException("La carpeta seleccionada, no esta dentro del proyecto");
            }
        } else {
            throw new InvalidDataException("No hay un proyecto abierto, no se puede crear una carpeta");
        }
    }*/

}
