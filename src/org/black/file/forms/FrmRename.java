package org.black.file.forms;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.prefs.Preferences;
import org.black.file.dialogs.DlgException;
import org.black.file.root.Box;
import org.data.type.Strings;
import org.directorysystem.folders.DirectorySystem;
import org.path.PathUtils;
import org.swing.ComponentIcon;
import org.swing.SwingEvents;
import org.swing.components.Form;
import org.swing.options.textfield.TextFieldPopupMenuLanguageOption;

public class FrmRename extends javax.swing.JFrame {

    private String path;

    private String name;
    private String extension;

    public FrmRename(String path) {
        initComponents();
        initForm();

        this.path = path;
        loadName();
        loadTheme();
    }

    private void initForm() {
        try {
            Form.initializeForm(this, true, TextFieldPopupMenuLanguageOption.ENGLISH, Box.defaultFont);
            SwingEvents.enableWindowEscKey(this);

            ComponentIcon ci = new ComponentIcon(60, 60);
            ci.setFromResource(lblIcon, Box.imageResourcePath + "edit.png");
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }

    private void loadTheme() {
        try {
            Preferences prefs = Preferences.userRoot().node("BlackFile");
            updateUI(prefs.get("theme", "light").equals("light"));
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }

    private void updateUI(boolean light) {
        try {
            Box.updateLookAndFeel(light, this);

            if (light) {
                panelTop.setkEndColor(new Color(192, 192, 192));
                panelTop.setkStartColor(new Color(255, 255, 255));
            } else {
                panelTop.setkEndColor(new Color(192, 192, 192));
                panelTop.setkStartColor(new Color(60, 63, 65));
            }
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }

    private void loadName() {
        try {
            name = PathUtils.getName(path);
            extension = PathUtils.getExtension(path);

            txtNewName.setText(name);
            if (DirectorySystem.exists(path)) {
                txtNewName.selectAll();
            } else {
                int dotLastIndex = txtNewName.getText().lastIndexOf(".");

                if (dotLastIndex == -1) {
                    txtNewName.selectAll();
                } else {
                    txtNewName.select(0, dotLastIndex);
                }
            }
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }

    private void rename() {
        try {
            String newPath = PathUtils.getParent(path, true).concat("\\").concat(txtNewName.getText());

            if (new File(path).renameTo(new File(newPath))) {
                Box.variables.put("updateContent", "true");
                dispose();
            } else {
                Box.createMessageBox("Rename failed", "Failed to rename this file or folder.").showError();
            }

        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblName = new javax.swing.JLabel();
        txtNewName = new javax.swing.JTextField();
        panelTop = new com.k33ptoo.components.KGradientPanel();
        lblIcon = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Rename");
        setUndecorated(true);
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });

        lblName.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblName.setText("Name");

        txtNewName.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtNewName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNewNameKeyPressed(evt);
            }
        });

        panelTop.setkBorderRadius(0);
        panelTop.setkEndColor(new java.awt.Color(192, 192, 192));
        panelTop.setkGradientFocus(650);
        panelTop.setkStartColor(new java.awt.Color(255, 255, 255));

        lblIcon.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        lblTitle.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lblTitle.setText("Rename");

        javax.swing.GroupLayout panelTopLayout = new javax.swing.GroupLayout(panelTop);
        panelTop.setLayout(panelTopLayout);
        panelTopLayout.setHorizontalGroup(
            panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitle)
                .addContainerGap(158, Short.MAX_VALUE))
        );
        panelTopLayout.setVerticalGroup(
            panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTopLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblIcon, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNewName)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNewName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblName))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNewNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNewNameKeyPressed
        try {
            if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
                if (!Strings.isNullWhiteSpaceOrEmpty(txtNewName.getText())) {
                    if (txtNewName.getText().equals(PathUtils.getName(path))) {
                        Box.createMessageBox("Name exists", "The name already exists in this folder.\nTry another name.").showWarning();
                    } else {
                        if (extension == null) {
                            rename();
                        } else {
                            if (PathUtils.getExtension(txtNewName.getText()).equals(extension)) {
                                rename();
                            } else {
                                if (Box.createMessageBox("File extension",
                                        "Are you sure you want to change the file extension?").addButtons(new String[]{"Yes", "No"}, true).showWarning() == 1) {
                                    rename();
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }//GEN-LAST:event_txtNewNameKeyPressed

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus
        dispose();
    }//GEN-LAST:event_formWindowLostFocus


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblTitle;
    private com.k33ptoo.components.KGradientPanel panelTop;
    private javax.swing.JTextField txtNewName;
    // End of variables declaration//GEN-END:variables
}
