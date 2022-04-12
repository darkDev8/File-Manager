package org.black.file.root;

import java.io.IOException;
import javax.swing.UIManager;
import org.black.file.dialogs.DlgException;
import org.data.type.Strings;
import org.file.text.TextFile;
import org.filesystem.files.FileSystem;
import com.formdev.flatlaf.FlatDarkLaf;
import java.io.File;
import java.io.FileReader;
import org.os.OS;
import org.security.Security;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.black.file.dialogs.DlgSettings;
import org.black.file.forms.FrmMain;
import org.directory.operations.DirectoryOperations;
import org.directorysystem.folders.DirectorySystem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Boot {

    public boolean loadBuildNumber() {
        try {
            if (FileSystem.exists("build.txt")) {
                String firstLine = TextFile.readFirstLine("build.txt", true);

                if (Strings.isNumber(firstLine)) {
                    int number = Integer.parseInt(firstLine) + 1;
                    Box.variables.put("build", String.valueOf(number));
                    TextFile.write("build.txt", number);
                } else {
                    Box.variables.put("build", "1");
                    TextFile.write("build.txt", "1");
                }
            } else {
                TextFile.write("build.txt", "1");
                Box.variables.put("build", "1");
            }

            return true;
        } catch (IOException e) {
            new DlgException(e, true).setVisible(true);
            return false;
        }
    }

    public boolean loadPath() {
        try {
            Box.variables.put("path", OS.getHomeDirectory());
            return true;
        } catch (Exception e) {
            new DlgException(e, true).setVisible(true);
            return false;
        }
    }

    public boolean loadEncryptionKey() {
        try {
            Box.variables.put("FilencryptionKey", "Mnl23549918?*9731");
            return true;
        } catch (Exception e) {
            new DlgException(e, true).setVisible(true);
            return false;
        }
    }
    
    public boolean loadSettings() {
        try {
            Preferences prefs = Preferences.userRoot().node("BlackFile");
            Map<String, String> registeryKeys = new HashMap<>() {
                {
                    put("searchWholeWord", "false");
                    put("searchCaseSensitive", "false");
                    put("searchType", "file");
                    put("showHiddenFiles", "false");
                    put("theme", "light");
                }
            };
            
            for (var entry : registeryKeys.entrySet()) {
                if (Strings.isNullOrEmpty(prefs.get(entry.getKey(), null))) {
                    prefs.put(entry.getKey(), entry.getValue());
                }
            }

            return true;
        } catch (Exception e) {
            new DlgException(e, true).setVisible(true);
            return false;
        }
    }
}
