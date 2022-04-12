package org.black.file.dialogs;

import java.awt.Color;
import java.awt.Frame;
import java.util.prefs.Preferences;
import org.black.file.root.Box;
import org.data.array.StringArray;
import org.data.type.Strings;
import org.externaltools.ExternalTools;
import org.swing.ComponentIcon;
import org.swing.SwingEvents;
import org.swing.components.Form;
import org.swing.components.TextField;
import org.swing.options.textfield.TextFieldPopupMenuLanguageOption;

public class DlgException extends javax.swing.JDialog {

    private boolean exit;

    public DlgException(Exception e, boolean exit) {
        super(new Frame(), true);
        initComponents();
        initForm();

        loadException(e);
        loadTheme();

        this.exit = exit;
    }

    private void initForm() {
        try {
            Form.initializeForm(this, false, TextFieldPopupMenuLanguageOption.ENGLISH, Box.defaultFont);
            SwingEvents.enableWindowEscKey(this);

            TextField.enablePopupMenu(txtExceptionStackTrace, TextFieldPopupMenuLanguageOption.ENGLISH, Box.defaultFont);
            new ComponentIcon(60, 60).setFromResource(lblIcon, Box.imageResourcePath + "error.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadException(Exception e) {
        try {
            String exceptionMessage = e.getMessage();

            if (Strings.isNullOrEmpty(exceptionMessage)) {
                lblErrorMessage.setText("Failed to detect the exception error message.");
            } else {
                lblErrorMessage.setText(exceptionMessage);
                exceptionMessage = StringArray.toString(exceptionMessage.split("(?<=\\G.{80})"), "\n");
                lblErrorMessage.setToolTipText(exceptionMessage);
            }

            txtExceptionStackTrace.setText(ExternalTools.getExceptionStackTrace(e));
        } catch (Exception ex) {
            ex.printStackTrace();
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
                
                lblErrorMessage.setForeground(Color.red);
            } else {
                panelTop.setkEndColor(new Color(192, 192, 192));
                panelTop.setkStartColor(new Color(60, 63, 65));
                panelBottom.setBackground(new Color(80, 83, 85));
                
                lblErrorMessage.setForeground(Color.yellow);
            }
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtExceptionStackTrace = new javax.swing.JTextArea();
        panelTop = new com.k33ptoo.components.KGradientPanel();
        lblIcon = new javax.swing.JLabel();
        lblErrorMessage = new javax.swing.JLabel();
        panelBottom = new javax.swing.JPanel();
        btnOk = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Crash error detected");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        txtExceptionStackTrace.setEditable(false);
        txtExceptionStackTrace.setColumns(20);
        txtExceptionStackTrace.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtExceptionStackTrace.setRows(5);
        jScrollPane1.setViewportView(txtExceptionStackTrace);

        panelTop.setkBorderRadius(0);
        panelTop.setkEndColor(new java.awt.Color(192, 192, 192));
        panelTop.setkStartColor(new java.awt.Color(255, 255, 255));

        lblErrorMessage.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblErrorMessage.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorMessage.setText("Error message");

        javax.swing.GroupLayout panelTopLayout = new javax.swing.GroupLayout(panelTop);
        panelTop.setLayout(panelTopLayout);
        panelTopLayout.setHorizontalGroup(
            panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelTopLayout.setVerticalGroup(
            panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTopLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblErrorMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
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
            .addComponent(panelTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 888, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(panelBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        dispose();
    }//GEN-LAST:event_btnOkActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        if (exit) {
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOk;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblErrorMessage;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JPanel panelBottom;
    private com.k33ptoo.components.KGradientPanel panelTop;
    private javax.swing.JTextArea txtExceptionStackTrace;
    // End of variables declaration//GEN-END:variables
}
