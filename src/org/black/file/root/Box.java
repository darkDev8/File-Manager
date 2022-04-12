package org.black.file.root;

import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.black.file.dialogs.DlgException;
import org.filesystem.files.FileSystem;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.swing.dialogs.MessageBox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Box {

    public final static Map<String, String> variables;
    public final static Font defaultFont;

    public final static String imageResourcePath;
    public final static String audioResourcePath;

    public final static int TABLE_ROW_MARGIN;
    public final static int ICON_TEXT_GAP;
    public final static int FORM_RADIUS;

    static {
        variables = new HashMap<>();
        defaultFont = new Font("Segoe UI", Font.PLAIN, 13);

        imageResourcePath = "/org/black/file/res/image/";
        audioResourcePath = "/org/black/file/res/audio/";

        TABLE_ROW_MARGIN = 1;
        ICON_TEXT_GAP = 8;
        FORM_RADIUS = 5;
    }

    public static MessageBox createMessageBox(String title, String message) {
        MessageBox box = new MessageBox(title, message);
        box.setButtonFont(defaultFont);
        box.setMessageFont(defaultFont);

        return box;
    }

    public static MessageBox createMessageBox(String message) {
        return createMessageBox("", message);
    }

    public static void updateLookAndFeel(boolean light, Component component) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    if (light) {
                        UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
                    } else {
                        UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
                    }

                    SwingUtilities.updateComponentTreeUI(component);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
