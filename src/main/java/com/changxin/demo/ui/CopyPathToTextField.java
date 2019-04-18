package com.changxin.demo.ui;

import com.changxin.demo.module.CheckInJob;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * 拖拽文件至文本框显示文件路径
 */
public class CopyPathToTextField extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField fieldA;
    private JTextField fieldB;
//    private JButton clearA = new JButton("重置");
//    private JButton clearB = new JButton("重置");


    private JButton outputTarget = new JButton("浏览");
    private JTextField targetFile = new JTextField("输出地址");

    public static final String OFFICE_EXCEL_XLS = "xls";
    public static final String OFFICE_EXCEL_XLSX = "xlsx";

    private JButton button;

    public CopyPathToTextField(){
        this.setTitle("签到记录审核");
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fieldA = new JTextField("拖拽插入指纹机签到记录");
        fieldA.setBounds(50, 50, 400, 50);
        fieldB = new JTextField("拖拽插入钉钉签到记录");
        fieldB.setBounds(50, 150, 400, 50);
        fieldA.setTransferHandler(new CustomTransferHandler(fieldA));
        fieldB.setTransferHandler(new CustomTransferHandler(fieldB));

//        clearA.setBounds(50, 50, 50, 50);
//        clearA.addActionListener();
//        clearB.setBounds(50, 150, 50, 50);

        targetFile.setBounds(50, 250, 300, 50);
        targetFile.setEditable(false);
        outputTarget.setBounds(370, 250, 80, 50);
        outputTarget.addActionListener(new BrowseAction(targetFile));

        button = new JButton("生成签到结果");
        button.setBounds(140,350, 120,50);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result;
                try {
                    result = actionResult();
                    switch (result) {
                        case "文件不能为空":
                            JOptionPane.showMessageDialog(null, "文件输入不能为空");
                            break;
                        case "指纹机签到记录不能为空":
                            JOptionPane.showMessageDialog(null, "指纹机签到记录不是正确的Excel文件");
                            break;
                        case "钉钉签到记录不能为空":
                            JOptionPane.showMessageDialog(null, "钉钉签到记录不是正确的Excel文件");
                            break;
                        case "任务完成":
                            JOptionPane.showMessageDialog(null, "任务完成");
                            break;
                        case "任务失败，可能是相同文件正被占用，请先关闭":
                            JOptionPane.showMessageDialog(null, "任务失败，可能是相同文件正被占用，请先关闭");
                            break;
                    }
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "任务失败，检查输入文件");
                }
            }
        });


        this.add(targetFile);
        this.add(outputTarget);
        this.add(fieldA);
        this.add(fieldB);
        this.add(button);
        this.setVisible(true);
    }

    public String actionResult() {
        if( "拖拽插入指纹机签到记录".equals(fieldA.getText())|| "拖拽插入钉钉签到记录".equals(fieldB.getText())) {
            return "文件不能为空";
        }

        if (isFileNotExist(fieldA.getText())) {
            return "指纹机签到记录不能为空";
        }

        if (isFileNotExist(fieldB.getText())) {
            return "钉钉签到记录不能为空";
        }

        CheckInJob job = CheckInJob.newJob(fieldA.getText(), fieldB.getText());


        if(job.generateResultWithHyperLink(targetFile.getText())) {
            return "任务完成";
        } else {
            return "任务失败，可能是相同文件正被占用，请先关闭";
        }
    }

    private boolean isFileNotExist(String path) {
        File file = new File(path);
        return !(file.exists() || isExcelFile(path));
    }

    private boolean isExcelFile(String filepath) {
        if (StringUtils.isBlank(filepath)) {
            return false;
        }
        int index = filepath.lastIndexOf(".");
        if (index == -1) {
            return false;
        }
        String suffix = filepath.substring(index + 1);
        if(OFFICE_EXCEL_XLS.equals(suffix) || OFFICE_EXCEL_XLSX.equals(suffix)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        new CopyPathToTextField();
    }
}