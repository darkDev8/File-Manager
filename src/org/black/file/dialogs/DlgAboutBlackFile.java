package org.black.file.dialogs;

import java.awt.Color;
import java.util.prefs.Preferences;
import org.black.file.root.Box;
import org.os.OS;
import org.swing.ComponentIcon;
import org.swing.SwingEvents;
import org.swing.components.Form;
import org.swing.options.textfield.TextFieldPopupMenuLanguageOption;

public class DlgAboutBlackFile extends javax.swing.JDialog {

    public DlgAboutBlackFile(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initForm();

        loadLabelsText();
        loadTheme();
    }

    private void initForm() {
        try {
            Form.initializeForm(this, true, TextFieldPopupMenuLanguageOption.ENGLISH, Box.defaultFont);
            SwingEvents.enableWindowEscKey(this);

            ComponentIcon ci = new ComponentIcon(100, 100);
            ci.setFromResource(lblIcon, Box.imageResourcePath + "blackFile.png");
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }

    private void loadLabelsText() {
        try {
            lblBuild.setText(String.format("Build %s", Box.variables.get("build")));
            lblGithubRepository.setText(
                    "<html><body>"
                    + "Github repository "
                    + "<span color=\"#FF3364\">https://github.com/darkDev8/BlackFile</span>"
                    + "</body></html>"
            );

            lblAbout.setText(
                    "<html><body>"
                    + "Product version 1.0 Pre-Alpha <br>"
                    + "Java version +17 Oracle corporation <br><br>"
                    + String.format("System %s%s", OS.getSystemName(), "<br>")
                    + String.format("Home directory %s%s", OS.getHomeDirectory(), "</body></html>")
            );
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
                panelBottom.setBackground(new Color(232, 232, 232));
            } else {
                panelBottom.setBackground(new Color(80, 83, 85));
            }
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIcon = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblBuild = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblGithubRepository = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblAbout = new javax.swing.JLabel();
        panelBottom = new javax.swing.JPanel();
        btnCopy = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("About BlackFile");
        setResizable(false);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel2.setText("BlackFile");

        lblBuild.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblBuild.setText("Build 0");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel1.setText("Created by darkDev8");

        lblGithubRepository.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblGithubRepository.setText("Github repository https://github.com/darkDev8/BlackFile");
        lblGithubRepository.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblGithubRepository.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblGithubRepositoryMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel3.setText("BlackFile is under Apache 2.0 license");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "About", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 13))); // NOI18N

        lblAbout.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblAbout.setText("About");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAbout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblAbout, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelBottom.setBackground(new java.awt.Color(232, 232, 232));

        btnCopy.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnCopy.setText("Copy");
        btnCopy.setFocusPainted(false);
        btnCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopyActionPerformed(evt);
            }
        });

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelBottomLayout.setVerticalGroup(
            panelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBottomLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk)
                    .addComponent(btnCopy))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblBuild))
                                    .addComponent(lblGithubRepository)))
                            .addComponent(jLabel3))
                        .addGap(0, 56, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(panelBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(lblBuild))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblGithubRepository))
                    .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblGithubRepositoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGithubRepositoryMouseClicked
        try {
            OS.openBrowser("https://github.com/darkDev8/BlackFile");
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }//GEN-LAST:event_lblGithubRepositoryMouseClicked

    private void btnCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopyActionPerformed
        try {
            OS.copyText(lblAbout.getText()
                    .replace("<html><body>", "")
                    .replace("</body></html>", "")
                    .replace("<br>", "\n")
            );
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }//GEN-LAST:event_btnCopyActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        dispose();
    }//GEN-LAST:event_btnOkActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCopy;
    private javax.swing.JButton btnOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblAbout;
    private javax.swing.JLabel lblBuild;
    private javax.swing.JLabel lblGithubRepository;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JPanel panelBottom;
    // End of variables declaration//GEN-END:variables
}
