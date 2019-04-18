package com.changxin.demo.ui;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import javax.swing.JTextField;


public class CustomTransferHandler extends TransferHandler{
    private static final long serialVersionUID = 1L;

    private JTextField field;

    public CustomTransferHandler(JTextField field) {
        this.field = field;
    }
    @Override
    public boolean importData(JComponent comp, Transferable t) {
        try {
            Object o = t.getTransferData(DataFlavor.javaFileListFlavor);

            String filepath = o.toString();
            if (filepath.startsWith("[")) {
                filepath = filepath.substring(1);
            }
            if (filepath.endsWith("]")) {
                filepath = filepath.substring(0, filepath.length() - 1);
            }
            System.out.println(filepath);
            field.setText(filepath);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean canImport(JComponent comp, DataFlavor[] flavors) {
        for (int i = 0; i < flavors.length; i++) {
            if (DataFlavor.javaFileListFlavor.equals(flavors[i])) {
                return true;
            }
        }
        return false;
    }
}

