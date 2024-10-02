package compi2.multi.compilator.files.model;

import compi2.multi.compilator.files.AdmiFiles;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author yenni
 */
@Getter
@Setter
@AllArgsConstructor
public class OpenFile extends JButton implements Comparable<OpenFile> {

    private File file;
    private String openContent;

    @Override
    public int compareTo(OpenFile o) {
        return this.file.getAbsolutePath().compareTo(o.getFile().getAbsolutePath());
    }

    public void init(JTextPane displayContent, JLabel displayName, final AdmiFiles admi) {
        this.reset();
        this.setText(file.getName());
        final OpenFile thisOb = this;
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //anadimos el evento de los botones
                OpenFile lastOpen = admi.getCurrentFile();
                if (lastOpen != null) {
                    lastOpen.setOpenContent(displayContent.getText());
                }
                displayContent.setText(openContent);
                displayName.setText(file.getName());
                admi.setCurrentFile(thisOb);
            }
        });
    }

    public void executeAction(JTextPane displayContent, JLabel displayName, final AdmiFiles admi) {
        OpenFile lastOpen = admi.getCurrentFile();
        if (lastOpen != null) {
            lastOpen.setOpenContent(displayContent.getText());
        }
        displayContent.setText(openContent);
        displayName.setText(file.getName());
        admi.setCurrentFile(this);
    }

    private void reset() {
        for (int i = 0; i < this.getActionListeners().length; i++) {
            ActionListener action = this.getActionListeners()[i];
            this.removeActionListener(action);
        }

    }
}
