package com.changxin.demo.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Demo extends JFrame implements ActionListener {

    private static final long serialVersionUIT = 1L;

    JButton btn = null;

    JTextField textField = null;

    public Demo() {
        this.setTitle("选择文件窗口");
        FlowLayout layout = new FlowLayout();// 布局
        JLabel label = new JLabel("请选择文件：");// 标签
        textField = new JTextField(30);// 文本域
        btn = new JButton("浏览");// 钮1

        // 设置布局
        layout.setAlignment(FlowLayout.LEFT);// 左对齐
        this.setLayout(layout);
        this.setBounds(400, 200, 600, 70);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        btn.addActionListener(this);
        this.add(label);
        this.add(textField);
        this.add(btn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.showDialog(new JLabel(), "选择");
        File file = chooser.getSelectedFile();
        textField.setText(file.getAbsoluteFile().toString());
    }

    public static void main(String[] args) {
        new Demo();
    }
}

