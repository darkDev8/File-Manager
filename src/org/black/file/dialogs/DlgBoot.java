package org.black.file.dialogs;

import java.awt.Color;
import org.black.file.root.Boot;
import org.black.file.root.Box;
import org.externaltools.ExternalTools;
import org.os.OS;
import org.swing.ComponentIcon;
import org.swing.components.Form;
import org.swing.dialogs.MessageBox;
import org.swing.options.textfield.TextFieldPopupMenuLanguageOption;

public class DlgBoot extends javax.swing.JDialog {

    private boolean accessToContinue;

    public DlgBoot(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initForm();

        new Thread(() -> {
            startBoot();
        }).start();
    }

    private void initForm() {
        try {
            Form.initializeForm(this, false, TextFieldPopupMenuLanguageOption.ENGLISH, Box.defaultFont);
            Form.setFormRadius(this, Box.FORM_RADIUS);
            new ComponentIcon(100, 100).setFromResource(lblIcon, Box.imageResourcePath + "blackFile.png");
        } catch (Exception e) {

        }
    }

    private void startBoot() {
        try {
            Boot boot = new Boot();

            if (OS.isWindows()) {
                pbBootProgress.setValue(pbBootProgress.getValue() + 14);
            } else {
                showError("No windows detected", "The operating system you have is unsupported.\n"
                        + "This software only runs on Microsoft Windows platforms.");
            }

            if (boot.loadBuildNumber()) {
                pbBootProgress.setValue(pbBootProgress.getValue() + 14);
            } else {
                showError("Build number", "Failed to increase and load build number.");
            }

            if (boot.loadPath()) {
                pbBootProgress.setValue(pbBootProgress.getValue() + 14);
            } else {
                showError("Loading path failed", "Failed to load the startup path.");
            }

            if (boot.loadSettings()) {
                pbBootProgress.setValue(pbBootProgress.getValue() + 14);
            } else {
                showError("Loading settings failed", "Failed to load the software settings.");
            }

            if (pbBootProgress.getValue() != 100) {
                pbBootProgress.setValue(pbBootProgress.getValue() + (100 - pbBootProgress.getValue()));
            }

            accessToContinue = true;
            dispose();
        } catch (Exception e) {
            new DlgException(e, true).setVisible(true);
        }
    }

    private void showError(String title, String message) {
        try {
            lblBootStatus.setText("Error detected.");
            lblBootStatus.setForeground(Color.red);
            
            Box.createMessageBox(title, message).showError();
            System.exit(-1);
        } catch (Exception e) {
            new DlgException(e, true).setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIcon = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        pbBootProgress = new javax.swing.JProgressBar();
        lblBootStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Booting...");
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        lblTitle.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("BlackFile");

        lblBootStatus.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblBootStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBootStatus.setText("Boot status");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 100, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pbBootProgress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblBootStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pbBootProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblBootStatus)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (!accessToContinue) {
            System.exit(-1);
        }
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblBootStatus;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JProgressBar pbBootProgress;
    // End of variables declaration//GEN-END:variables
}
