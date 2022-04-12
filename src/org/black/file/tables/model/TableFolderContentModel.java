package org.black.file.tables.model;

import com.formdev.flatlaf.util.SwingUtils;
import java.io.File;
import java.util.Collections;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.black.file.dialogs.DlgException;
import org.black.file.root.Box;
import org.directorysystem.folders.DirectorySystem;
import org.externaltools.ExternalTools;
import org.filesystem.files.FileSystem;
import org.path.PathUtils;
import org.swing.ComponentIcon;
import org.swing.components.Table;

public class TableFolderContentModel extends DefaultTableModel {

    public TableFolderContentModel(String path, boolean showHiddens) {
        try {
            if (DirectorySystem.exists(path)) {
                List<File> folders = DirectorySystem.getDirectories(path, showHiddens), files = DirectorySystem.getFiles(path, showHiddens);
                ComponentIcon ci = new ComponentIcon(20, 20);

                if (folders.isEmpty() && files.isEmpty()) {
                    return;
                }

                addColumn("");
                addColumn("Name");
                addColumn("Size");
                addColumn("Modify date");
                addColumn("Read");
                addColumn("Write");

                Collections.sort(folders);
                Collections.sort(files);

                for (File folder : folders) {
                    path = folder.getAbsolutePath();

                    JLabel label = new JLabel(PathUtils.getName(path));
                    label.setFont(Box.defaultFont);
                    label.setIconTextGap(Box.ICON_TEXT_GAP);
                    label.setToolTipText(String.format("%s\n%s\n%s", PathUtils.getName(path), "Folder", DirectorySystem.getModifyDate(path)));
                    ci.setFromResource(label, Box.imageResourcePath + "folder.png");

                    addRow(new Object[]{false, label, "Folder", DirectorySystem.getModifyDate(path), DirectorySystem.canRead(path), DirectorySystem.canWrite(path)});
                }

                for (File file : files) {
                    path = file.getAbsolutePath();

                    JLabel label = new JLabel(PathUtils.getName(file.getAbsolutePath()));
                    label.setFont(Box.defaultFont);
                    label.setIconTextGap(Box.ICON_TEXT_GAP);
                    label.setToolTipText(String.format("%s\n%s\n%s", PathUtils.getName(path), ExternalTools.toReadableSize(FileSystem.getSize(path)),
                            FileSystem.getModifyDate(path)));

                    if (FileSystem.getFileType(file.getAbsolutePath(), false).equalsIgnoreCase("Audio file")) {
                        ci.setFromResource(label, Box.imageResourcePath + "audio.png");
                    } else if (FileSystem.getFileType(file.getAbsolutePath(), false).equalsIgnoreCase("Image file")) {
                        ci.setFromResource(label, Box.imageResourcePath + "picture.png");
                    } else if (FileSystem.getFileType(file.getAbsolutePath(), false).equalsIgnoreCase("Video file")) {
                        ci.setFromResource(label, Box.imageResourcePath + "video2.png");
                    } else if (FileSystem.getFileType(file.getAbsolutePath(), false).equalsIgnoreCase("Plain text file")) {
                        ci.setFromResource(label, Box.imageResourcePath + "notepad.png");
                    } else if (FileSystem.getFileType(file.getAbsolutePath(), false).equalsIgnoreCase("Windows executable file")) {
                        ci.setFromResource(label, Box.imageResourcePath + "windows.png");
                    } else if (FileSystem.getFileType(file.getAbsolutePath(), false).equalsIgnoreCase("Linux executable file(script)")) {
                        ci.setFromResource(label, Box.imageResourcePath + "linux.png");
                    } else if (FileSystem.getFileType(file.getAbsolutePath(), false).equalsIgnoreCase("Mac application")) {
                        ci.setFromResource(label, Box.imageResourcePath + "mac.png");
                    } else if (FileSystem.getFileType(file.getAbsolutePath(), false).equalsIgnoreCase("Archive file")) {
                        ci.setFromResource(label, Box.imageResourcePath + "archive.png");
                    } else if (FileSystem.getFileType(file.getAbsolutePath(), false).equalsIgnoreCase("Document file")) {
                        ci.setFromResource(label, Box.imageResourcePath + "document.png");
                    } else if (FileSystem.getFileType(file.getAbsolutePath(), false).equalsIgnoreCase("Font file")) {
                        ci.setFromResource(label, Box.imageResourcePath + "font.png");
                    } else if (FileSystem.getFileType(file.getAbsolutePath(), false).equalsIgnoreCase("Java source file")) {
                        ci.setFromResource(label, Box.imageResourcePath + "java.png");
                    } else if (FileSystem.getFileType(file.getAbsolutePath(), false).equalsIgnoreCase("cSharp source file")) {
                        ci.setFromResource(label, Box.imageResourcePath + "c#.png");
                    } else if (FileSystem.getFileType(file.getAbsolutePath(), false).equalsIgnoreCase("C source file")) {
                        ci.setFromResource(label, Box.imageResourcePath + "c.png");
                    } else if (FileSystem.getFileType(file.getAbsolutePath(), false).equalsIgnoreCase("C++ source file")) {
                        ci.setFromResource(label, Box.imageResourcePath + "c++.png");
                    } else if (FileSystem.getFileType(file.getAbsolutePath(), false).equalsIgnoreCase("Python source file")) {
                        ci.setFromResource(label, Box.imageResourcePath + "python.png");
                    } else if (FileSystem.getFileType(file.getAbsolutePath(), false).equalsIgnoreCase("Javascript source file")) {
                        ci.setFromResource(label, Box.imageResourcePath + "javascript.png");
                    } else if (FileSystem.getFileType(file.getAbsolutePath(), false).equalsIgnoreCase("Php source file")) {
                        ci.setFromResource(label, Box.imageResourcePath + "php.png");
                    } else if (FileSystem.getFileType(file.getAbsolutePath(), false).equalsIgnoreCase("Golang source file")) {
                        ci.setFromResource(label, Box.imageResourcePath + "golang.png");
                    } else if (FileSystem.getFileType(file.getAbsolutePath(), false).equalsIgnoreCase("Ruby source file")) {
                        ci.setFromResource(label, Box.imageResourcePath + "ruby.png");
                    } else if (FileSystem.getFileType(file.getAbsolutePath(), false).equalsIgnoreCase("C header file")) {
                        ci.setFromResource(label, Box.imageResourcePath + "c.png");
                    } else if (FileSystem.getFileType(file.getAbsolutePath(), false).equalsIgnoreCase("C++ header file")) {
                        ci.setFromResource(label, Box.imageResourcePath + "c.png");
                    } else if (FileSystem.getFileType(file.getAbsolutePath(), false).equalsIgnoreCase("Kotlin source file")) {
                        ci.setFromResource(label, Box.imageResourcePath + "kotlin.png");
                    } else {
                        ci.setFromResource(label, Box.imageResourcePath + "unkown.png");
                    }

                    addRow(new Object[]{false, label, ExternalTools.toReadableSize(FileSystem.getSize(path)),
                        FileSystem.getModifyDate(path), FileSystem.canRead(path), FileSystem.canWrite(path)});
                }
            } else {
                Box.createMessageBox("Invalid path", "The path you are trying to access is no longer exists or the name has been changed.").showError();
            }
        } catch (Exception e) {
            new DlgException(e, false).setVisible(true);
        }
    }

//
//    public void loadContent(JTable table, List<File> files) {
//        try {
//            if (DirectorySystem.exists(path)) {
//                ComponentIcon ci = new ComponentIcon(20, 20);
//                DefaultTableModel model = (DefaultTableModel) table.getModel();
//                Table.clear(table, false);
//
//                if (files.isEmpty()) {
//                    JLabel label = new JLabel("No item found.");
//                    label.setFont(Box.defaultFont);
//                    label.setIconTextGap(Box.ICON_TEXT_GAP);
//
//                    ci.setFromResource(label, Box.imageResourcePath + "close.png");
//                    model.addRow(new Object[]{false, label, "Not found", "Not found"});
//                }
//
//                Collections.sort(files);
//                for (File file : files) {
//                    String filePath = file.getAbsolutePath();
//                    JLabel label = new JLabel(PathUtils.getName(file.getAbsolutePath()));
//
//                    label.setFont(Box.defaultFont);
//                    label.setIconTextGap(Box.ICON_TEXT_GAP);
//                    ci.setFromResource(label, Box.imageResourcePath + "check.png");
//
//                    model.addRow(new Object[]{false, label, ExternalTools.toReadableSize(FileSystem.getSize(filePath)),
//                        FileSystem.getModifyDate(filePath)});
//                }
//            } else {
//                Box.createMessageBox("Invalid path", "The path you are trying to access is no longer exists or the name has been changed.")
//                        .showError();
//            }
//        } catch (Exception e) {
//            new DlgException(e, false).setVisible(true);
//        }
//    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Boolean.class;
            case 1:
                return JLabel.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return Boolean.class;
            case 5:
                return Boolean.class;
            default:
                return String.class;
        }
    }

}
