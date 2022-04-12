package org.black.file.forms;

import com.k33ptoo.components.KButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import org.black.file.dialogs.DlgAboutBlackFile;
import org.black.file.dialogs.DlgException;
import org.black.file.dialogs.DlgMultipleProperties;
import org.black.file.dialogs.DlgSettings;
import org.black.file.root.Box;
import org.black.file.tables.model.TableFolderContentModel;
import org.console.color.ConsoleForeground;
import org.data.type.Strings;
import org.directory.operations.DirectoryOperations;
import org.directorysystem.folders.DirectorySystem;
import org.externaltools.ExternalTools;
import org.filesystem.files.FileSystem;
import org.os.OS;
import org.os.PartitionInformation;
import org.path.PathUtils;
import org.swing.ComponentIcon;
import org.swing.components.Form;
import org.swing.components.Table;
import org.swing.options.table.TableColumnHeaderPositionOption;
import org.swing.options.table.TableCountOption;
import org.swing.options.textfield.TextFieldPopupMenuLanguageOption;
import org.swing.render.TableLabelRender;

public class FrmMain extends javax.swing.JFrame {

    private Map<String, String> homeFolders;
    private Thread contentThread;

    private Preferences prefs;
    private ComponentIcon directionToolbarIcons;
    private ComponentIcon menuItemIcons;

    private boolean showHiddenFiles;

    public FrmMain() {
        initComponents();
        initVariables();
        initForm();

        loadSettings();
        loadDrives();
        loadFolderContent();

        updateDrives();
        updateFolderContent();
    }

    private void initVariables() {
        try {
            prefs = Preferences.userRoot().node("BlackFile");
            homeFolders = new HashMap<>() {
                {
                    put(String.format("%s", OS.getHomeDirectory()), Box.imageResourcePath + "pc.png");
                    put(String.format("%s\\Desktop", OS.getHomeDirectory()), Box.imageResourcePath + "desktop.png");
                    put(String.format("%s\\Documents", OS.getHomeDirectory()), Box.imageResourcePath + "document.png");
                    put(String.format("%s\\Downloads", OS.getHomeDirectory()), Box.imageResourcePath + "cloud.png");
                    put(String.format("%s\\Music", OS.getHomeDirectory()), Box.imageResourcePath + "music.png");
                    put(String.format("%s\\Pictures", OS.getHomeDirectory()), Box.imageResourcePath + "picture.png");
                    put(String.format("%s\\Public", OS.getHomeDirectory()), Box.imageResourcePath + "onedrive2.png");
                    put(String.format("%s\\3D Objects", OS.getHomeDirectory()), Box.imageResourcePath + "3dObjects.png");
                    put(String.format("%s\\Videos", OS.getHomeDirectory()), Box.imageResourcePath + "video.png");
                    put(String.format("%s\\Links", OS.getHomeDirectory()), Box.imageResourcePath + "share.png");
                    put(String.format("%s\\Saved Games", OS.getHomeDirectory()), Box.imageResourcePath + "game.png");
                    put(String.format("%s\\Contacts", OS.getHomeDirectory()), Box.imageResourcePath + "phone.png");
                }
            };

            directionToolbarIcons = new ComponentIcon(16, 16);
            menuItemIcons = new ComponentIcon(16, 16);
        } catch (Exception e) {
            new DlgException(e, true).setVisible(true);
        }
    }

    private void initForm() {
        try {
            Form.initializeForm(this, true, TextFieldPopupMenuLanguageOption.ENGLISH, Box.defaultFont);

            tblFolderContent.setDefaultRenderer(Object.class, new TableLabelRender(null, null));

            Table.disableTableEdit(tblFolderContent);

            tblFolderContent.setRowMargin(Box.TABLE_ROW_MARGIN);

            Table.setColumnHeaderPosition(tblFolderContent, TableColumnHeaderPositionOption.LEFT);

            menuItemIcons.setFromResource(miCreateNewItem, Box.imageResourcePath + "create.png");
            menuItemIcons.setFromResource(miSettings, Box.imageResourcePath + "settings.png");
            menuItemIcons.setFromResource(miDownloadFile, Box.imageResourcePath + "download.png");
            menuItemIcons.setFromResource(miConnectToServer, Box.imageResourcePath + "server.png");
            menuItemIcons.setFromResource(miVideoPlayer, Box.imageResourcePath + "video2.png");
            menuItemIcons.setFromResource(miTextEditor, Box.imageResourcePath + "edit.png");
            menuItemIcons.setFromResource(miShowHelp, Box.imageResourcePath + "help.png");
            menuItemIcons.setFromResource(miReportBug, Box.imageResourcePath + "bug.png");
            menuItemIcons.setFromResource(miAboutBlackFile, Box.imageResourcePath + "blackFile.png");

            directionToolbarIcons.setFromResource(btnBack, Box.imageResourcePath + "backArrowDark.png");
            directionToolbarIcons.setFromResource(btnRefresh, Box.imageResourcePath + "refresh.png");

            menuItemIcons.setFromResource(miShowInExplorer, Box.imageResourcePath + "fileExplorer.png");
            menuItemIcons.setFromResource(menuSecurity, Box.imageResourcePath + "key.png");
            menuItemIcons.setFromResource(miCreateArchive, Box.imageResourcePath + "archive.png");
            menuItemIcons.setFromResource(miCopy, Box.imageResourcePath + "copy.png");
            menuItemIcons.setFromResource(miMove, Box.imageResourcePath + "cut.png");
            menuItemIcons.setFromResource(miDelete, Box.imageResourcePath + "delete.png");

            txtPath.setText(OS.getHomeDirectory());
            txtPath.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent e) {
                    loadFolderContent();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    loadFolderContent();
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    loadFolderContent();
                }
            });

            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (Box.variables.containsKey("updateContent")) {
                        String updateContentValue = Box.variables.get("updateContent");
                        Box.variables.remove("updateContent");

                        if (updateContentValue.equals("true")) {
                            loadFolderContent();
                        }
                    }
                    if (Box.variables.containsKey("updateSettings")) {
                        String updateSettingsValue = Box.variables.get("updateSettings");
                        Box.variables.remove("updateSettings");

                        if (updateSettingsValue.equals("true")) {
                            loadSettings();
                        }
                    }

                    if (!homeFolders.containsKey(txtPath.getText())) {
                        for (Component c : panelQuickAccess.getComponents()) {
                            if (c instanceof JToggleButton) {
                                btnGroupQuickAccessDrives.clearSelection();
                            }
                        }
                    }

                    updateTableCheckBoxes();
                }
            }, 0, 100);

        } catch (Exception e) {
            new DlgException(e, true).setVisible(true);
        }
    }

    private void loadQuickAccessPanel() {
        SwingUtilities.invokeLater(() -> {
            try {
                ComponentIcon ci = new ComponentIcon(20, 20);
                panelQuickAccess.removeAll();

                for (var entry : homeFolders.entrySet()) {
                    if (DirectorySystem.exists(entry.getKey())) {
                        JToggleButton button = new JToggleButton(PathUtils.getName(entry.getKey()));
                        btnGroupQuickAccessDrives.add(button);

                        button.setFont(Box.defaultFont);
                        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        button.setBorder(null);
                        button.setFocusPainted(false);
                        button.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
                        button.setIconTextGap(Box.ICON_TEXT_GAP);

                        if (prefs.get("theme", "light").equals("light")) {
                            button.setBackground(new Color(242, 242, 242));
                        } else {
                            button.setBackground(new Color(60, 63, 65));
                        }

                        button.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 6, 1, 1));

                        button.addActionListener((ActionEvent e) -> {
                            JToggleButton selectedButton = (JToggleButton) e.getSource();
                            String path = null;

                            if (selectedButton.getText().equalsIgnoreCase("home")) {
                                path = OS.getHomeDirectory();
                            } else {
                                path = String.format("%s\\%s", OS.getHomeDirectory(), selectedButton.getText());
                            }

                            txtPath.setText(path);
                        });

                        if (entry.getKey().equalsIgnoreCase(OS.getHomeDirectory())) {
                            button.setText("Home");
                        }

                        ci.setFromResource(button, entry.getValue());
                        panelQuickAccess.add(button);
                    }
                }

                panelQuickAccess.revalidate();
                panelQuickAccess.repaint();
            } catch (Exception e) {
                new DlgException(e, false).setVisible(true);
            }
        });
    }

    private void loadFolderContent() {
        if (contentThread != null) {
            if (contentThread.isAlive()) {
                contentThread.stop();
            }
        }

        contentThread = new Thread(() -> {
            try {
                Table.clear(tblFolderContent, true);
                Box.variables.replace("path", txtPath.getText());

                tblFolderContent.setModel(new TableFolderContentModel(txtPath.getText(), Boolean.parseBoolean(prefs.get("showHiddenFiles", "false"))));
                Table.setColumnHeaderPosition(tblFolderContent, TableColumnHeaderPositionOption.LEFT);

                JTableHeader tableHeader = tblFolderContent.getTableHeader();
                tableHeader.setFont(Box.defaultFont);

                if (Table.countRowsColumns(tblFolderContent, TableCountOption.ROWS) > 0) {
                    tblFolderContent.getColumnModel().getColumn(0).setPreferredWidth(30);
                    tblFolderContent.getColumnModel().getColumn(0).setMaxWidth(30);
                    tblFolderContent.getColumnModel().getColumn(0).setMinWidth(30);

                    tblFolderContent.getColumnModel().getColumn(2).setPreferredWidth(80);
                    tblFolderContent.getColumnModel().getColumn(2).setMinWidth(100);
                    tblFolderContent.getColumnModel().getColumn(2).setMaxWidth(80);

                    tblFolderContent.getColumnModel().getColumn(3).setPreferredWidth(100);
                    tblFolderContent.getColumnModel().getColumn(3).setMinWidth(100);
                    tblFolderContent.getColumnModel().getColumn(3).setMaxWidth(100);

                    tblFolderContent.getColumnModel().getColumn(4).setPreferredWidth(40);
                    tblFolderContent.getColumnModel().getColumn(4).setMaxWidth(40);
                    tblFolderContent.getColumnModel().getColumn(4).setMinWidth(40);

                    tblFolderContent.getColumnModel().getColumn(5).setPreferredWidth(40);
                    tblFolderContent.getColumnModel().getColumn(5).setMaxWidth(40);
                    tblFolderContent.getColumnModel().getColumn(5).setMinWidth(40);
                }
            } catch (Exception e) {
                new DlgException(e, false).setVisible(true);
            }
        });

        contentThread.start();
    }

    private void loadFolderContent(List<File> files) {
//        if (contentThread != null) {
//            if (contentThread.isAlive()) {
//                contentThread.stop();
//            }
//        }
//
//        contentThread = new Thread(() -> {
//            try {
//                new TableFolderContentModel(txtPath.getText(), Boolean.parseBoolean(Box.variables.get("settingsShowHiddenFiles"))).loadContent(tblFolderContent, files);
//                Box.variables.replace("path", txtPath.getText());
//
//                JTableHeader tableHeader = tblFolderContent.getTableHeader();
//                tableHeader.setFont(Box.defaultFont);
//
//                tblFolderContent.getColumnModel().getColumn(0).setMaxWidth(40);
//                tblFolderContent.getColumnModel().getColumn(0).setMinWidth(40);
//
//                if (Table.countRowsColumns(tblFolderContent, TableCountOption.COLUMNS) > 1) {
//                    tblFolderContent.getColumnModel().getColumn(2).setMaxWidth(120);
//                    tblFolderContent.getColumnModel().getColumn(2).setMinWidth(120);
//
//                    tblFolderContent.getColumnModel().getColumn(3).setMaxWidth(120);
//                    tblFolderContent.getColumnModel().getColumn(3).setMinWidth(120);
//
//                    tblFolderContent.getColumnModel().getColumn(5).setMaxWidth(40);
//                    tblFolderContent.getColumnModel().getColumn(5).setMinWidth(40);
//
//                    tblFolderContent.getColumnModel().getColumn(6).setMaxWidth(40);
//                    tblFolderContent.getColumnModel().getColumn(6).setMinWidth(40);
//
//                    tblFolderContent.getColumnModel().getColumn(7).setMaxWidth(40);
//                    tblFolderContent.getColumnModel().getColumn(7).setMinWidth(40);
//                }
//            } catch (Exception e) {
//                new DlgException(e, false).setVisible(true);
//            }
//        });
//
//        contentThread.start();
    }

    public void loadDrives() {
        try {
            SwingUtilities.invokeLater(() -> {
                DefaultComboBoxModel model = (DefaultComboBoxModel) cmbDrives.getModel();

                model.removeAllElements();
                model.addAll(Arrays.asList(OS.getPartitionsInfo(PartitionInformation.LETTER)));
                cmbDrives.setSelectedIndex(0);
            });
        } catch (Exception e) {
            new DlgException(e, true).setVisible(true);
        }
    }

    private void loadSettings() {
        try {
            boolean hiddenFiles = Boolean.parseBoolean(prefs.get("showHiddenFiles", "false"));
            if (showHiddenFiles != hiddenFiles) {
                showHiddenFiles = hiddenFiles;
                loadFolderContent();
            }

            updateUI(prefs.get("theme", "light").equals("light"));
        } catch (Exception e) {
            new DlgException(e, true).setVisible(true);
        }
    }

    private void updateDrives() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    List<String> systemDrives = Arrays.asList(OS.getPartitionsInfo(PartitionInformation.LETTER));
                    ComboBoxModel model = (ComboBoxModel) cmbDrives.getModel();

                    if (systemDrives.size() != model.getSize()) {
                        loadDrives();
                    } else {
                        for (int i = 0; i < systemDrives.size(); i++) {
                            Object element = model.getElementAt(i);

                            if (element != null) {
                                if (!element.toString().equals(systemDrives.get(i))) {
                                    loadDrives();
                                    break;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    new DlgException(e, false).setVisible(true);
                }
            }
        }, 0, 100);
    }

    private void updateFolderContent() {
        try {
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }

    private void updateTableCheckBoxes() {
        try {
            List<Integer> selectedRows = Arrays.stream(tblFolderContent.getSelectedRows()).boxed().toList();

            for (int i = 0; i < Table.countRowsColumns(tblFolderContent, TableCountOption.ROWS); i++) {
                tblFolderContent.setValueAt(selectedRows.contains(i), i, 0);
            }
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }

    private void updateUI(boolean light) {
        try {
            Box.updateLookAndFeel(light, this);
            Box.updateLookAndFeel(light, popMenuFolderContent);
            loadQuickAccessPanel();

            if (light) {
                 directionToolbarIcons.setFromResource(btnBack, Box.imageResourcePath + "backArrowDark.png");
            } else {
                directionToolbarIcons.setFromResource(btnBack, Box.imageResourcePath + "backArrowLight.png");
            }
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupQuickAccessDrives = new javax.swing.ButtonGroup();
        popMenuFolderContent = new javax.swing.JPopupMenu();
        menuOpen = new javax.swing.JMenu();
        miOpenSystem = new javax.swing.JMenuItem();
        miOpenTools = new javax.swing.JMenuItem();
        miShowInExplorer = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuSecurity = new javax.swing.JMenu();
        miEncrypt = new javax.swing.JMenuItem();
        miDecrypt = new javax.swing.JMenuItem();
        miCreateArchive = new javax.swing.JMenuItem();
        miExtract = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        miCopy = new javax.swing.JMenu();
        miCopyFile = new javax.swing.JMenuItem();
        miCopyPath = new javax.swing.JMenuItem();
        miCopyProperties = new javax.swing.JMenuItem();
        miMove = new javax.swing.JMenuItem();
        miDelete = new javax.swing.JMenuItem();
        miRename = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        miProperties = new javax.swing.JMenuItem();
        panelTop = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnBack = new javax.swing.JButton();
        txtPath = new javax.swing.JTextField();
        txtSearch = new javax.swing.JTextField();
        cmbDrives = new javax.swing.JComboBox<>();
        jToolBar2 = new javax.swing.JToolBar();
        btnRefresh = new javax.swing.JButton();
        splitterMain = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblFolderContent = new javax.swing.JTable();
        panelLeft = new javax.swing.JPanel();
        lblQuickAccess = new javax.swing.JLabel();
        panelQuickAccess = new javax.swing.JPanel();
        mbMain = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        miCreateNewItem = new javax.swing.JMenuItem();
        miSettings = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        miExit = new javax.swing.JMenuItem();
        menuTools = new javax.swing.JMenu();
        miDownloadFile = new javax.swing.JMenuItem();
        miConnectToServer = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        miVideoPlayer = new javax.swing.JMenuItem();
        miTextEditor = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        miShowHelp = new javax.swing.JMenuItem();
        miReportBug = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        miAboutBlackFile = new javax.swing.JMenuItem();

        menuOpen.setText("Open");
        menuOpen.setFont(new java.awt.Font("Inter Medium", 0, 13)); // NOI18N

        miOpenSystem.setFont(new java.awt.Font("Inter Medium", 0, 13)); // NOI18N
        miOpenSystem.setText("System");
        menuOpen.add(miOpenSystem);

        miOpenTools.setFont(new java.awt.Font("Inter Medium", 0, 13)); // NOI18N
        miOpenTools.setText("Tools");
        menuOpen.add(miOpenTools);

        popMenuFolderContent.add(menuOpen);

        miShowInExplorer.setFont(new java.awt.Font("Inter Medium", 0, 13)); // NOI18N
        miShowInExplorer.setText("Show in explorer");
        miShowInExplorer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miShowInExplorerActionPerformed(evt);
            }
        });
        popMenuFolderContent.add(miShowInExplorer);
        popMenuFolderContent.add(jSeparator2);

        menuSecurity.setText("Security");
        menuSecurity.setFont(new java.awt.Font("Inter Medium", 0, 13)); // NOI18N

        miEncrypt.setFont(new java.awt.Font("Inter Medium", 0, 13)); // NOI18N
        miEncrypt.setText("Encrypt");
        menuSecurity.add(miEncrypt);

        miDecrypt.setFont(new java.awt.Font("Inter Medium", 0, 13)); // NOI18N
        miDecrypt.setText("Decrypt");
        menuSecurity.add(miDecrypt);

        popMenuFolderContent.add(menuSecurity);

        miCreateArchive.setFont(new java.awt.Font("Inter Medium", 0, 13)); // NOI18N
        miCreateArchive.setText("Create archive");
        popMenuFolderContent.add(miCreateArchive);

        miExtract.setFont(new java.awt.Font("Inter Medium", 0, 13)); // NOI18N
        miExtract.setText("Extract");
        popMenuFolderContent.add(miExtract);
        popMenuFolderContent.add(jSeparator3);

        miCopy.setText("Copy");
        miCopy.setFont(new java.awt.Font("Inter Medium", 0, 13)); // NOI18N

        miCopyFile.setFont(new java.awt.Font("Inter Medium", 0, 13)); // NOI18N
        miCopyFile.setText("File");
        miCopy.add(miCopyFile);

        miCopyPath.setFont(new java.awt.Font("Inter Medium", 0, 13)); // NOI18N
        miCopyPath.setText("Path");
        miCopy.add(miCopyPath);

        miCopyProperties.setFont(new java.awt.Font("Inter Medium", 0, 13)); // NOI18N
        miCopyProperties.setText("Properties");
        miCopy.add(miCopyProperties);

        popMenuFolderContent.add(miCopy);

        miMove.setFont(new java.awt.Font("Inter Medium", 0, 13)); // NOI18N
        miMove.setText("Move");
        popMenuFolderContent.add(miMove);

        miDelete.setFont(new java.awt.Font("Inter Medium", 0, 13)); // NOI18N
        miDelete.setText("Delete");
        popMenuFolderContent.add(miDelete);

        miRename.setFont(new java.awt.Font("Inter Medium", 0, 13)); // NOI18N
        miRename.setText("Rename");
        miRename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miRenameActionPerformed(evt);
            }
        });
        popMenuFolderContent.add(miRename);
        popMenuFolderContent.add(jSeparator6);

        miProperties.setFont(new java.awt.Font("Inter Medium", 0, 13)); // NOI18N
        miProperties.setText("Properties");
        miProperties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miPropertiesActionPerformed(evt);
            }
        });
        popMenuFolderContent.add(miProperties);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BlackFIle");
        setMinimumSize(new java.awt.Dimension(900, 600));

        jToolBar1.setBorder(null);
        jToolBar1.setRollover(true);

        btnBack.setToolTipText("Back");
        btnBack.setFocusPainted(false);
        btnBack.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBack.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        jToolBar1.add(btnBack);

        txtPath.setEditable(false);
        txtPath.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        txtSearch.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
        });

        cmbDrives.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cmbDrives.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbDrivesItemStateChanged(evt);
            }
        });

        jToolBar2.setBorder(null);
        jToolBar2.setRollover(true);

        btnRefresh.setToolTipText("Back");
        btnRefresh.setFocusPainted(false);
        btnRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        jToolBar2.add(btnRefresh);

        javax.swing.GroupLayout panelTopLayout = new javax.swing.GroupLayout(panelTop);
        panelTop.setLayout(panelTopLayout);
        panelTopLayout.setHorizontalGroup(
            panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPath)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbDrives, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelTopLayout.setVerticalGroup(
            panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTopLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelTopLayout.createSequentialGroup()
                        .addGroup(panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmbDrives, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        splitterMain.setDividerLocation(200);
        splitterMain.setDividerSize(3);
        splitterMain.setOneTouchExpandable(true);
        splitterMain.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                splitterMainPropertyChange(evt);
            }
        });

        tblFolderContent.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblFolderContent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Name", "Size", "Modify date", "Read", "Write"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblFolderContent.setShowGrid(true);
        tblFolderContent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFolderContentMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblFolderContent);

        splitterMain.setRightComponent(jScrollPane2);

        lblQuickAccess.setText("Quick access");

        panelQuickAccess.setLayout(new java.awt.GridLayout(20, 0));

        javax.swing.GroupLayout panelLeftLayout = new javax.swing.GroupLayout(panelLeft);
        panelLeft.setLayout(panelLeftLayout);
        panelLeftLayout.setHorizontalGroup(
            panelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLeftLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLeftLayout.createSequentialGroup()
                        .addComponent(lblQuickAccess)
                        .addContainerGap(126, Short.MAX_VALUE))
                    .addComponent(panelQuickAccess, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panelLeftLayout.setVerticalGroup(
            panelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLeftLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQuickAccess)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelQuickAccess, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
                .addContainerGap())
        );

        splitterMain.setLeftComponent(panelLeft);

        menuFile.setText("File");
        menuFile.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        miCreateNewItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_DOWN_MASK));
        miCreateNewItem.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        miCreateNewItem.setText("Create new item");
        miCreateNewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCreateNewItemActionPerformed(evt);
            }
        });
        menuFile.add(miCreateNewItem);

        miSettings.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_DOWN_MASK));
        miSettings.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        miSettings.setText("Settings");
        miSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSettingsActionPerformed(evt);
            }
        });
        menuFile.add(miSettings);
        menuFile.add(jSeparator4);

        miExit.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        miExit.setText("Exit");
        menuFile.add(miExit);

        mbMain.add(menuFile);

        menuTools.setText("Tools");
        menuTools.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        miDownloadFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_DOWN_MASK));
        miDownloadFile.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        miDownloadFile.setText("Download file");
        menuTools.add(miDownloadFile);

        miConnectToServer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.ALT_DOWN_MASK));
        miConnectToServer.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        miConnectToServer.setText("Connect to server");
        menuTools.add(miConnectToServer);
        menuTools.add(jSeparator7);

        miVideoPlayer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.ALT_DOWN_MASK));
        miVideoPlayer.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        miVideoPlayer.setText("Video player");
        menuTools.add(miVideoPlayer);

        miTextEditor.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        miTextEditor.setText("Text editor");
        menuTools.add(miTextEditor);

        mbMain.add(menuTools);

        menuHelp.setText("Help");
        menuHelp.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        miShowHelp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.ALT_DOWN_MASK));
        miShowHelp.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        miShowHelp.setText("Show help");
        menuHelp.add(miShowHelp);

        miReportBug.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_DOWN_MASK));
        miReportBug.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        miReportBug.setText("Report bug");
        menuHelp.add(miReportBug);
        menuHelp.add(jSeparator1);

        miAboutBlackFile.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        miAboutBlackFile.setText("About BlackFile");
        miAboutBlackFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAboutBlackFileActionPerformed(evt);
            }
        });
        menuHelp.add(miAboutBlackFile);

        mbMain.add(menuHelp);

        setJMenuBar(mbMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(splitterMain, javax.swing.GroupLayout.DEFAULT_SIZE, 1198, Short.MAX_VALUE)
                        .addGap(1, 1, 1))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(splitterMain)
                .addGap(1, 1, 1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblFolderContentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFolderContentMouseClicked
        try {
            if (SwingUtilities.isLeftMouseButton(evt)) {
                if (evt.getClickCount() == 2) {
                    String path = null;

                    if (txtPath.getText().endsWith("\\")) {
                        path = String.format("%s%s", txtPath.getText(),
                                ((JLabel) tblFolderContent.getValueAt(tblFolderContent.getSelectedRow(), 1)).getText());
                    } else {
                        path = String.format("%s\\%s", txtPath.getText(),
                                ((JLabel) tblFolderContent.getValueAt(tblFolderContent.getSelectedRow(), 1)).getText());
                    }

                    if (DirectorySystem.exists(path)) {
                        txtPath.setText(path);
                    } else if (FileSystem.exists(path)) {
                        OS.openFile(path);
                    } else {
                        Box.createMessageBox("Invalid path", "The path you are trying to access is no longer exists or the name has been changed.")
                                .showError();
                    }
                }
            } else {
                JTable source = (JTable) evt.getSource();

                int row = source.rowAtPoint(evt.getPoint());
                int column = source.columnAtPoint(evt.getPoint());

                if (!source.isRowSelected(row)) {
                    source.changeSelection(row, column, false, false);
                }

                miRename.setVisible(tblFolderContent.getSelectedRowCount() == 1);
                menuOpen.setVisible(tblFolderContent.getSelectedRowCount() == 1);
                miExtract.setVisible(tblFolderContent.getSelectedRowCount() == 1);
                miCopyProperties.setVisible(tblFolderContent.getSelectedRowCount() == 1);

                popMenuFolderContent.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }//GEN-LAST:event_tblFolderContentMouseClicked

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        try {
            String currentPath = txtPath.getText();

            if (!Arrays.asList(OS.getPartitionsInfo(PartitionInformation.LETTER)).contains(currentPath)) {
                txtPath.setText(DirectorySystem.getParentPath(txtPath.getText()));
            }
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }//GEN-LAST:event_btnBackActionPerformed

    private void miAboutBlackFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAboutBlackFileActionPerformed
        try {
            new DlgAboutBlackFile(this, true).setVisible(true);
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }//GEN-LAST:event_miAboutBlackFileActionPerformed

    private void cmbDrivesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDrivesItemStateChanged
        try {
            Object selectedItem = cmbDrives.getSelectedItem();

            if (selectedItem != null) {
                txtPath.setText(selectedItem.toString());
            }
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }//GEN-LAST:event_cmbDrivesItemStateChanged

    private void miCreateNewItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCreateNewItemActionPerformed
        try {
            new FrmCreateNewItem().setVisible(true);
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }//GEN-LAST:event_miCreateNewItemActionPerformed

    private void miShowInExplorerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miShowInExplorerActionPerformed
        try {
            OS.openFileExplorer(txtPath.getText());
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }//GEN-LAST:event_miShowInExplorerActionPerformed

    private void miRenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miRenameActionPerformed
        try {
            String path = null;

            if (txtPath.getText().endsWith("\\")) {
                path = String.format("%s%s", txtPath.getText(), ((JLabel) tblFolderContent.getValueAt(tblFolderContent.getSelectedRow(), 1)).getText());
            } else {
                path = String.format("%s\\%s", txtPath.getText(), ((JLabel) tblFolderContent.getValueAt(tblFolderContent.getSelectedRow(), 1)).getText());
            }

            new FrmRename(path).setVisible(true);
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }//GEN-LAST:event_miRenameActionPerformed

    private void miPropertiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miPropertiesActionPerformed
        try {
            int[] selectedRows = tblFolderContent.getSelectedRows();
            List<String> paths = new ArrayList<>();

            for (int row : selectedRows) {
                if (txtPath.getText().endsWith("\\")) {
                    paths.add(String.format("%s%s", txtPath.getText(), ((JLabel) tblFolderContent.getValueAt(row, 1)).getText()));
                } else {
                    paths.add(String.format("%s\\%s", txtPath.getText(), ((JLabel) tblFolderContent.getValueAt(row, 1)).getText()));
                }
            }

            if (paths.size() > 1) {
                new DlgMultipleProperties(paths).setVisible(true);
            } else {

            }
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }//GEN-LAST:event_miPropertiesActionPerformed

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<File> found = new ArrayList<>();

                    if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
                        if (Strings.isNullWhiteSpaceOrEmpty(txtSearch.getText())) {
                            loadFolderContent();
                        } else {
                            if (Box.variables.get("settingsSearchTable").equals("true")) {
                                //Bug
                                DefaultTableModel Model = (DefaultTableModel) tblFolderContent.getModel();
                                TableRowSorter tr = new TableRowSorter<>(Model);

                                tblFolderContent.setRowSorter(tr);
                                tr.setRowFilter(RowFilter.regexFilter(txtSearch.getText().trim()));
                            } else {
                                List<File> files = DirectorySystem.getSubItems(txtPath.getText(), true, true);
                                String name = null, key = txtSearch.getText();

                                for (File file : files) {
                                    name = file.getName();

                                    if (!Box.variables.get("settingsCaseSensitive").equals("true")) {
                                        name = file.getName().toLowerCase();
                                        key = key.toLowerCase();
                                    }

                                    if (Box.variables.get("settingsWholeWord").equals("true")) {
                                        if (name.equals(key)) {
                                            found.add(file);
                                        }
                                    } else {
                                        if (name.contains(key)) {
                                            found.add(file);
                                        }
                                    }
                                }

                                loadFolderContent(found);
                                found.forEach(System.out::println);
                                System.out.println(found.size());
                            }
                        }

                    }
                } catch (Exception e) {
                    new DlgException(e, false).setVisible(true);
                }
            }
        }).start();
    }//GEN-LAST:event_txtSearchKeyPressed

    private void miSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSettingsActionPerformed
        try {
            new DlgSettings(this, true).setVisible(true);
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }//GEN-LAST:event_miSettingsActionPerformed

    private void splitterMainPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_splitterMainPropertyChange
        try {
            int currentDividerLocation = splitterMain.getDividerLocation();
            if (currentDividerLocation > 220) {
                splitterMain.setDividerLocation(220);
            }
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }//GEN-LAST:event_splitterMainPropertyChange

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        try {
            loadFolderContent();
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }//GEN-LAST:event_btnRefreshActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.ButtonGroup btnGroupQuickAccessDrives;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JComboBox<String> cmbDrives;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JLabel lblQuickAccess;
    private javax.swing.JMenuBar mbMain;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenu menuOpen;
    private javax.swing.JMenu menuSecurity;
    private javax.swing.JMenu menuTools;
    private javax.swing.JMenuItem miAboutBlackFile;
    private javax.swing.JMenuItem miConnectToServer;
    private javax.swing.JMenu miCopy;
    private javax.swing.JMenuItem miCopyFile;
    private javax.swing.JMenuItem miCopyPath;
    private javax.swing.JMenuItem miCopyProperties;
    private javax.swing.JMenuItem miCreateArchive;
    private javax.swing.JMenuItem miCreateNewItem;
    private javax.swing.JMenuItem miDecrypt;
    private javax.swing.JMenuItem miDelete;
    private javax.swing.JMenuItem miDownloadFile;
    private javax.swing.JMenuItem miEncrypt;
    private javax.swing.JMenuItem miExit;
    private javax.swing.JMenuItem miExtract;
    private javax.swing.JMenuItem miMove;
    private javax.swing.JMenuItem miOpenSystem;
    private javax.swing.JMenuItem miOpenTools;
    private javax.swing.JMenuItem miProperties;
    private javax.swing.JMenuItem miRename;
    private javax.swing.JMenuItem miReportBug;
    private javax.swing.JMenuItem miSettings;
    private javax.swing.JMenuItem miShowHelp;
    private javax.swing.JMenuItem miShowInExplorer;
    private javax.swing.JMenuItem miTextEditor;
    private javax.swing.JMenuItem miVideoPlayer;
    private javax.swing.JPanel panelLeft;
    private javax.swing.JPanel panelQuickAccess;
    private javax.swing.JPanel panelTop;
    private javax.swing.JPopupMenu popMenuFolderContent;
    private javax.swing.JSplitPane splitterMain;
    private javax.swing.JTable tblFolderContent;
    private javax.swing.JTextField txtPath;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
