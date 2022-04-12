package org.black.file.main;

import org.black.file.dialogs.DlgBoot;
import org.black.file.dialogs.DlgException;
import org.black.file.forms.FrmMain;

public class BlackFile {

    public static void main(String[] args) {
        try {
            new DlgBoot(null, true).setVisible(true);

            java.awt.EventQueue.invokeLater(() -> {
                new FrmMain().setVisible(true);
            });
        } catch (Exception e) {
            new DlgException(e, true).setVisible(true);
        }
    }
}
