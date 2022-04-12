package org.black.file.dialogs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.black.file.root.Boot;
import org.black.file.root.Box;
import org.data.type.Strings;
import org.filesystem.files.FileSystem;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.swing.ComponentIcon;
import org.swing.SwingEvents;
import org.swing.components.Form;
import org.swing.options.textfield.TextFieldPopupMenuLanguageOption;

public class DlgSettings extends javax.swing.JDialog {

    private String selectValue;
    private Preferences prefs;

    public DlgSettings(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initVariables();
        initForm();

        loadSettings();

    }

    private void initVariables() {
        try {
            prefs = Preferences.userRoot().node("BlackFile");
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }

    private void initForm() {
        try {
            Form.initializeForm(this, true, TextFieldPopupMenuLanguageOption.ENGLISH, Box.defaultFont);
            SwingEvents.enableWindowEscKey(this);

            ComponentIcon ci = new ComponentIcon(60, 60);
            ci.setFromResource(lblIcon, Box.imageResourcePath + "settings.png");
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }

    public void loadSettings() {
        try {
            chkSearchCaseSensitive.setSelected(prefs.getBoolean("searchCaseSensitive", false));
            chkSearchWholeWord.setSelected(prefs.getBoolean("searchWholeWord", false));
            rBtnSearchFiles.setSelected(prefs.get("searchType", "file").equalsIgnoreCase("file"));
            rBtnSearchTable.setSelected(prefs.get("searchType", "file").equalsIgnoreCase("table"));
            chkShowHiddenFiles.setSelected(prefs.getBoolean("showHiddenFiles", true));

            if (prefs.get("theme", "light").equals("light")) {
                cmbTheme.setSelectedIndex(0);
            } else {
                cmbTheme.setSelectedIndex(1);
            }
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
            dispose();
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

        btnGroupSearch = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        chkSearchWholeWord = new javax.swing.JCheckBox();
        chkSearchCaseSensitive = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        rBtnSearchTable = new javax.swing.JRadioButton();
        rBtnSearchFiles = new javax.swing.JRadioButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        chkShowHiddenFiles = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        cmbTheme = new javax.swing.JComboBox<>();
        panelTop = new com.k33ptoo.components.KGradientPanel();
        lblIcon = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        panelBottom = new javax.swing.JPanel();
        btnOk = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Settings");
        setResizable(false);

        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "General", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 13))); // NOI18N

        chkSearchWholeWord.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        chkSearchWholeWord.setText("Whole word");
        chkSearchWholeWord.setFocusPainted(false);
        chkSearchWholeWord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSearchWholeWordActionPerformed(evt);
            }
        });

        chkSearchCaseSensitive.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        chkSearchCaseSensitive.setText("Case sensitive");
        chkSearchCaseSensitive.setFocusPainted(false);
        chkSearchCaseSensitive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSearchCaseSensitiveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkSearchWholeWord)
                    .addComponent(chkSearchCaseSensitive))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(chkSearchWholeWord)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkSearchCaseSensitive)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search tyoe", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 13))); // NOI18N

        btnGroupSearch.add(rBtnSearchTable);
        rBtnSearchTable.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        rBtnSearchTable.setText("Search in table");
        rBtnSearchTable.setFocusPainted(false);
        rBtnSearchTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rBtnSearchFileTableActionPerformed(evt);
            }
        });

        btnGroupSearch.add(rBtnSearchFiles);
        rBtnSearchFiles.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        rBtnSearchFiles.setText("Search in files");
        rBtnSearchFiles.setFocusPainted(false);
        rBtnSearchFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rBtnSearchFileTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rBtnSearchTable)
                    .addComponent(rBtnSearchFiles))
                .addContainerGap(330, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(rBtnSearchTable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rBtnSearchFiles)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        jTabbedPane1.addTab("Search", jPanel1);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Table preferences", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 13))); // NOI18N

        chkShowHiddenFiles.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        chkShowHiddenFiles.setText("Show hidden files");
        chkShowHiddenFiles.setFocusPainted(false);
        chkShowHiddenFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkShowHiddenFilesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkShowHiddenFiles)
                .addContainerGap(314, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(chkShowHiddenFiles)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(134, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Table", jPanel6);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Theme", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 13))); // NOI18N

        cmbTheme.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cmbTheme.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Light", "Dark" }));
        cmbTheme.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbThemeItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmbTheme, 0, 433, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(cmbTheme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(132, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Software", jPanel4);

        panelTop.setkBorderRadius(0);
        panelTop.setkEndColor(new java.awt.Color(192, 192, 192));
        panelTop.setkGradientFocus(650);
        panelTop.setkStartColor(new java.awt.Color(255, 255, 255));

        lblIcon.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        lblTitle.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lblTitle.setText("Settings");

        javax.swing.GroupLayout panelTopLayout = new javax.swing.GroupLayout(panelTop);
        panelTop.setLayout(panelTopLayout);
        panelTopLayout.setHorizontalGroup(
            panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelTopLayout.setVerticalGroup(
            panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTopLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)))
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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(panelBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        dispose();
    }//GEN-LAST:event_btnOkActionPerformed

    private void cmbThemeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbThemeItemStateChanged
        try {
            prefs.put("theme", cmbTheme.getSelectedItem().toString().toLowerCase());
            Box.variables.put("updateSettings", "true");

            updateUI(cmbTheme.getSelectedItem().equals("Light"));
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }//GEN-LAST:event_cmbThemeItemStateChanged

    private void chkShowHiddenFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkShowHiddenFilesActionPerformed
        try {
            prefs.put("showHiddenFiles", String.valueOf(chkShowHiddenFiles.isSelected()));
            Box.variables.put("updateSettings", "true");
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }//GEN-LAST:event_chkShowHiddenFilesActionPerformed

    private void chkSearchWholeWordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSearchWholeWordActionPerformed
        try {
            prefs.put("searchWholeWord", String.valueOf(chkSearchWholeWord.isSelected()));
            Box.variables.put("updateSettings", "true");
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }//GEN-LAST:event_chkSearchWholeWordActionPerformed

    private void chkSearchCaseSensitiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSearchCaseSensitiveActionPerformed
        try {
            prefs.put("searchCaseSensitive", String.valueOf(chkSearchCaseSensitive.isSelected()));
            Box.variables.put("updateSettings", "true");
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }//GEN-LAST:event_chkSearchCaseSensitiveActionPerformed

    private void rBtnSearchFileTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rBtnSearchFileTableActionPerformed
        try {
            if (rBtnSearchFiles.isSelected()) {
                selectValue = "file";
            } else if (rBtnSearchTable.isSelected()) {
                selectValue = "table";
            }

            prefs.put("searchType", selectValue);
            Box.variables.put("updateSettings", "true");
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }//GEN-LAST:event_rBtnSearchFileTableActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGroupSearch;
    private javax.swing.JButton btnOk;
    private javax.swing.JCheckBox chkSearchCaseSensitive;
    private javax.swing.JCheckBox chkSearchWholeWord;
    private javax.swing.JCheckBox chkShowHiddenFiles;
    private javax.swing.JComboBox<String> cmbTheme;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel panelBottom;
    private com.k33ptoo.components.KGradientPanel panelTop;
    private javax.swing.JRadioButton rBtnSearchFiles;
    private javax.swing.JRadioButton rBtnSearchTable;
    // End of variables declaration//GEN-END:variables
}
