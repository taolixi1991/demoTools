package com.changxin.demo.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BrowseAction implements ActionListener {

    private JTextField field;

    public BrowseAction(JTextField field) {
        this.field = field;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fcDlg = new JFileChooser();
        fcDlg.setDialogTitle("请选择文件输入地址");
        fcDlg.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fcDlg.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String filepath = fcDlg.getSelectedFile().getPath();
            field.setText(filepath);
        }

    }
}
