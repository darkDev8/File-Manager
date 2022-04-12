package org.black.file.forms;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import net.lingala.zip4j.ZipFile;
import org.black.file.dialogs.DlgException;
import org.black.file.root.Box;
import org.data.type.Strings;
import org.directory.operations.DirectoryOperations;
import org.file.text.TextFile;
import org.swing.ComponentIcon;
import org.swing.SwingEvents;
import org.swing.components.Form;
import org.swing.options.textfield.TextFieldPopupMenuLanguageOption;

public class FrmCreateNewItem extends javax.swing.JFrame {

    private String path;

    public FrmCreateNewItem() {
        initComponents();
        initVariables();
        initForm();
        
        loadTheme();
    }

    private void initVariables() {
        try {
            path = Box.variables.get("path");
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }

    private void initForm() {
        try {
            Form.initializeForm(this, true, TextFieldPopupMenuLanguageOption.ENGLISH, Box.defaultFont);
            SwingEvents.enableWindowEscKey(this);

            ComponentIcon ci = new ComponentIcon(60, 60);
            ci.setFromResource(lblIcon, Box.imageResourcePath + "create.png");
            cmbFileType.setToolTipText(cmbFileType.getSelectedItem().toString());
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

    private void createItem() {
        try {
            String path = null;

            if (this.path.endsWith("\\")) {
                path = String.format("%s%s", this.path, txtItemName.getText());
            } else {
                path = String.format("%s\\%s", this.path, txtItemName.getText());
            }

            if (!Strings.isNullWhiteSpaceOrEmpty(txtItemName.getText())) {
                if (new File(path).exists()) {
                    Box.createMessageBox("Item exists", "The file or folder already exists in this path.").showWarning();
                } else {
                    switch (cmbFileType.getSelectedIndex()) {
                        case 0: {
                            if (DirectoryOperations.create(path)) {
                                updatePath(path);
                            } else {
                                Box.createMessageBox("Folder creation failed", "Failed to create the folder.").showError();
                            }

                            break;
                        }

                        case 1: {
                            if (!path.endsWith(".txt")) {
                                path += ".txt";
                            }

                            if (TextFile.write(path, "")) {
                                updatePath(path);
                            } else {
                                Box.createMessageBox("File creation failed", "Failed to create the file.").showError();
                            }

                            break;
                        }

                        case 2: {
                            if (!path.endsWith(".png")) {
                                path += ".png";
                            }

                            int width = 1000, height = 1000;

                            BufferedImage bimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                            Graphics2D g2d = bimg.createGraphics();

                            File file = new File(path);
                            if (ImageIO.write(bimg, "png", file)) {
                                updatePath(path);
                            } else {
                                Box.createMessageBox("File creation failed", "Failed to create the file.").showError();
                            }

                            break;
                        }

                        case 3: {
                            if (path.endsWith(".html")) {
                                path += ".html";
                            }

                            String htmlTemplate = "<!doctype html>\n"
                                    + "<html>\n"
                                    + " <head>\n"
                                    + "     <title>Our Funky HTML Page</title>\n"
                                    + "     <meta name=\"description\" content=\"Our first page\">\n"
                                    + "     <meta name=\"keywords\" content=\"html tutorial template\">\n"
                                    + " </head>\n"
                                    + " <body>\n"
                                    + "     Content goes here.\n"
                                    + " </body>\n"
                                    + "</html>";

                            if (TextFile.write(path, htmlTemplate)) {
                                updatePath(path);
                            } else {
                                Box.createMessageBox("File creation failed", "Failed to create the file.").showError();
                            }

                            break;
                        }

                        case 4: {
                            if (!path.endsWith(".sh")) {
                                path += ".sh";
                            }

                            if (TextFile.write(path, "#! /bin/bash")) {
                                updatePath(path);
                            } else {
                                Box.createMessageBox("File creation failed", "Failed to create the file.").showError();
                            }

                            break;
                        }

                        case 5: {
                            if (!path.endsWith(".zip")) {
                                path += ".zip";
                            }

                            ZipFile zipFile = new ZipFile(path);
                            URL resource = getClass().getResource(Box.imageResourcePath + "blackFile.png");
                            if (resource == null) {
                                Box.createMessageBox("File creation failed", "Failed to create the file.").showError();
                            } else {
                                zipFile.addFile(new File(resource.toURI()));
                                updatePath(path);
                            }

                            break;
                        }

                        case 6: {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }

    private void updatePath(String path) {
        try {
            Box.variables.put("updateContent", "true");
            dispose();
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtItemName = new javax.swing.JTextField();
        cmbFileType = new javax.swing.JComboBox<>();
        panelTop = new com.k33ptoo.components.KGradientPanel();
        lblIcon = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Create new item");
        setUndecorated(true);
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });

        txtItemName.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtItemName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtItemNameKeyPressed(evt);
            }
        });

        cmbFileType.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cmbFileType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Folder", "Text File", "Image File", "Html File", "Bash File", "Zip File", "Rar File" }));
        cmbFileType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbFileTypeItemStateChanged(evt);
            }
        });

        panelTop.setkBorderRadius(0);
        panelTop.setkEndColor(new java.awt.Color(192, 192, 192));
        panelTop.setkGradientFocus(650);
        panelTop.setkStartColor(new java.awt.Color(255, 255, 255));

        lblIcon.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        lblTitle.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lblTitle.setText("Create new item");

        javax.swing.GroupLayout panelTopLayout = new javax.swing.GroupLayout(panelTop);
        panelTop.setLayout(panelTopLayout);
        panelTopLayout.setHorizontalGroup(
            panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitle)
                .addContainerGap(88, Short.MAX_VALUE))
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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtItemName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbFileType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtItemName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbFileType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtItemNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItemNameKeyPressed
        try {
            if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
                createItem();
            }
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }//GEN-LAST:event_txtItemNameKeyPressed

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus
        dispose();
    }//GEN-LAST:event_formWindowLostFocus

    private void cmbFileTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbFileTypeItemStateChanged
        try {
            cmbFileType.setToolTipText(cmbFileType.getSelectedItem().toString());
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }//GEN-LAST:event_cmbFileTypeItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbFileType;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblTitle;
    private com.k33ptoo.components.KGradientPanel panelTop;
    private javax.swing.JTextField txtItemName;
    // End of variables declaration//GEN-END:variables
}
