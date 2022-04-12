package org.black.file.dialogs;

import java.awt.Color;
import java.io.File;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.JFrame;
import org.black.file.root.Box;
import org.data.thread.CNumber;
import org.directorysystem.folders.DirectorySystem;
import org.externaltools.ExternalTools;
import org.filesystem.files.FileSystem;
import org.path.PathUtils;
import org.swing.ComponentIcon;
import org.swing.SwingEvents;
import org.swing.components.Form;
import org.swing.options.textfield.TextFieldPopupMenuLanguageOption;

public class DlgMultipleProperties extends javax.swing.JDialog {

    private List<String> paths;

    private Thread fileThread;
    private Thread folderThread;
    private Thread pathThread;
    private Thread sizeThread;

    public DlgMultipleProperties(List<String> paths) {
        super(new JFrame(), true);
        initComponents();
        initForm();

        this.paths = paths;

        loadTheme();
        loadProperties();
    }

    private void initForm() {
        try {
            Form.initializeForm(this, true, TextFieldPopupMenuLanguageOption.ENGLISH, Box.defaultFont);
            SwingEvents.enableWindowEscKey(this);

            ComponentIcon ci = new ComponentIcon(60, 60);
            ci.setFromResource(lblIcon, Box.imageResourcePath + "process.png");
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }

    private void loadProperties() {
        try {
            //Sub files
            CNumber files = new CNumber("0");
            fileThread = new Thread(() -> {
                try {
                    for (String path : paths) {
                        lblFiles.setText(String.format("%s (calculating...)", lblFiles.getText()));

                        if (FileSystem.exists(path) && FileSystem.canRead(path)) {
                            files.increment();
                        } else if (DirectorySystem.exists(path) && DirectorySystem.canRead(path)) {
                            files.add(String.valueOf(DirectorySystem.countSubFiles(path, true, true)));
                        }

                        lblFiles.setText(String.format("Files %s", files.toString()));
                    }
                } catch (Exception e) {
                    lblFiles.setText("Error detected");
                    new DlgException(e, false).setVisible(true);
                }
            });

            fileThread.start();

            //Sub folders
            CNumber folders = new CNumber("0");
            folderThread = new Thread(() -> {
                try {
                    for (String path : paths) {
                        lblFolders.setText(String.format("%s (calculating...)", lblFolders.getText()));

                        if (DirectorySystem.exists(path) && DirectorySystem.canRead(path)) {
                            folders.add(String.valueOf(DirectorySystem.countSubDirectories(path, true, true)))
                                    .increment();
                        }

                        lblFolders.setText(String.format("Folders %s", folders.toString()));
                    }
                } catch (Exception e) {
                    lblFolders.setText("Error detected");
                    new DlgException(e, false).setVisible(true);
                }
            });

            folderThread.start();

            //Path
            pathThread = new Thread(() -> {
                try {
                    lblPath.setText(String.format("%s (calculating...)", lblPath.getText()));

                    String path = PathUtils.getParent(paths.get(0), true);

                    if (path.length() > 55) {
                        path = path.substring(0, 55).concat("...");
                    }

                    lblPath.setToolTipText(PathUtils.getParent(paths.get(0), true));
                    lblPath.setText(path);
                } catch (Exception e) {
                    lblPath.setText("Error detected");
                    new DlgException(e, false).setVisible(true);
                }
            });

            pathThread.start();

            //Size
            CNumber size = new CNumber("0");
            sizeThread = new Thread(() -> {
                try {
                    for (String path : paths) {
                        lblSize.setText(String.format("%s (calculating...)", lblSize.getText()));

                        if (DirectorySystem.exists(path) && DirectorySystem.canRead(path)) {
                            size.add(String.valueOf(DirectorySystem.getSize(path)));
                        } else {
                            size.add(String.valueOf(new File(path).length()));
                        }

                        lblSize.setText(String.format("Size %s (%s bytes)", ExternalTools.toReadableSize(size.getLongValue()), size.toString()));
                    }
                } catch (Exception e) {
                    lblSize.setText("Error detected");
                    new DlgException(e, false).setVisible(true);
                }
            });

            sizeThread.start();
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
                panelBottom.setBackground(new Color(232, 232, 232));
            } else {
                panelTop.setkEndColor(new Color(192, 192, 192));
                panelTop.setkStartColor(new Color(60, 63, 65));
                panelBottom.setBackground(new Color(80, 83, 85));
            }
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTop = new com.k33ptoo.components.KGradientPanel();
        lblIcon = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        panelDetails = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        lblFiles = new javax.swing.JLabel();
        lblFolders = new javax.swing.JLabel();
        lblPath = new javax.swing.JLabel();
        lblSize = new javax.swing.JLabel();
        panelBottom = new javax.swing.JPanel();
        btnOk = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Properties");
        setResizable(false);

        panelTop.setkBorderRadius(0);
        panelTop.setkEndColor(new java.awt.Color(192, 192, 192));
        panelTop.setkGradientFocus(650);
        panelTop.setkStartColor(new java.awt.Color(255, 255, 255));

        lblIcon.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        lblTitle.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lblTitle.setText("Properties");

        javax.swing.GroupLayout panelTopLayout = new javax.swing.GroupLayout(panelTop);
        panelTop.setLayout(panelTopLayout);
        panelTopLayout.setHorizontalGroup(
            panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitle)
                .addContainerGap(239, Short.MAX_VALUE))
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

        panelDetails.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Details", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 13))); // NOI18N

        lblFiles.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblFiles.setText("Files");

        lblFolders.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblFolders.setText("Folders");

        lblPath.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblPath.setText("Path");

        lblSize.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblSize.setText("Size");

        javax.swing.GroupLayout panelDetailsLayout = new javax.swing.GroupLayout(panelDetails);
        panelDetails.setLayout(panelDetailsLayout);
        panelDetailsLayout.setHorizontalGroup(
            panelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(panelDetailsLayout.createSequentialGroup()
                        .addGroup(panelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDetailsLayout.createSequentialGroup()
                                .addComponent(lblFiles)
                                .addGap(18, 18, 18)
                                .addComponent(lblFolders))
                            .addComponent(lblPath)
                            .addComponent(lblSize))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelDetailsLayout.setVerticalGroup(
            panelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailsLayout.createSequentialGroup()
                .addGroup(panelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFiles)
                    .addComponent(lblFolders))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPath)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblSize)
                .addContainerGap())
        );

        panelBottom.setBackground(new java.awt.Color(232, 232, 232));

        btnOk.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnOk.setText("Ok");
        btnOk.setFocusPainted(false);
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBottomLayout = new javax.swing.GroupLayout(panelBottom);
        panelBottom.setLayout(panelBottomLayout);
        panelBottomLayout.setHorizontalGroup(
            panelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBottomLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelBottomLayout.setVerticalGroup(
            panelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBottomLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnOk)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        try {
            fileThread.stop();
//            folderThread.stop();
//            typeThread.start();
//            pathThread.stop();
//            sizeThread.stop();
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        } finally {
            dispose();
        }
    }//GEN-LAST:event_btnOkActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOk;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblFiles;
    private javax.swing.JLabel lblFolders;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblPath;
    private javax.swing.JLabel lblSize;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel panelBottom;
    private javax.swing.JPanel panelDetails;
    private com.k33ptoo.components.KGradientPanel panelTop;
    // End of variables declaration//GEN-END:variables
}
