package com.shareyi.molicode.common.gui;

import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.io.File;

/**
 * 文件窗口选择器
 *
 * @author david
 * @date 2016/6/9
 */
public class FileChooserHelper {


    String openFilePath;
    String saveFilePath;
    String dirFilePath;
    boolean openChoosedFlag;
    boolean saveChoosedFlag;
    boolean dirChoosedFlag;

    /**
     * 3种选择器
     */
    JFileChooser openFileChooser, saveFileChooser, dirFileChooser;
    /**
     * 父jframe窗口
     */
    JFrame parentJFrame;

    public FileChooserHelper(JFrame jFrame) {
        this.parentJFrame = jFrame;
    }


    public synchronized void init() {
        if (saveFileChooser != null) {
            return;
        }

        saveFileChooser = new JFileChooser();
        saveFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        saveFileChooser.setMultiSelectionEnabled(false);

        openFileChooser = new JFileChooser();
        openFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        openFileChooser.setMultiSelectionEnabled(false);

        dirFileChooser = new JFileChooser();
        dirFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        dirFileChooser.setMultiSelectionEnabled(false);
    }


    public synchronized String getOpenPath(final String parentPath,
                                           final boolean filter, final String[] filterExtensions,
                                           final String[] filterNames) {

        openChoosedFlag = false;

        openFileChooser.setVisible(true);
        Runnable runnable = new Runnable() {

            public void run() {
                FileChooserHelper.this.toFront();

                if (StringUtils.isNotEmpty(parentPath)) {
                    File parentFile = new File(parentPath);
                    if (parentFile.exists()) {
                        if (parentFile.isFile()) {
                            openFileChooser.setSelectedFile(parentFile);
                        } else {
                            openFileChooser.setCurrentDirectory(parentFile);
                        }
                    }
                }
                if (filter) {
                    openFileChooser.setFileFilter(new FileNameFilter(filterExtensions[0]));
                } else {
                    openFileChooser.setFileFilter(null);
                }
                int result = openFileChooser.showOpenDialog(parentJFrame);
                switch (result) {
                    case JFileChooser.APPROVE_OPTION:
                        openFilePath = openFileChooser.getSelectedFile().getAbsolutePath();
                        break;
                    case JFileChooser.CANCEL_OPTION:
                        openFilePath = "";
                        break;
                    case JFileChooser.ERROR_OPTION:
                        openFilePath = "";
                }
                openChoosedFlag = true;
            }
        };

        SwingUtilities.invokeLater(runnable);

        while (!openChoosedFlag) {
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
            }
        }

        openChoosedFlag = false;
        return openFilePath;


    }


    public synchronized String getSavePath(final String parentPath, final boolean filter,
                                           final String[] filterExtensions, final String[] filterNames) {
        saveChoosedFlag = false;

        saveFileChooser.setVisible(true);
        Runnable runnable = new Runnable() {

            public void run() {

                FileChooserHelper.this.toFront();
                if (parentPath != null) {
                    File parentFile = new File(parentPath);
                    if (parentFile.exists()) {
                        if (parentFile.isFile()) {
                            saveFileChooser.setSelectedFile(parentFile);
                        } else {
                            saveFileChooser.setCurrentDirectory(parentFile);
                        }
                    }
                }
                if (filter) {
                    saveFileChooser.setFileFilter(new FileNameFilter(filterExtensions[0]));
                } else {
                    saveFileChooser.setFileFilter(null);
                }
                int result = saveFileChooser.showSaveDialog(parentJFrame);
                switch (result) {
                    case JFileChooser.APPROVE_OPTION:
                        saveFilePath = saveFileChooser.getSelectedFile().getAbsolutePath();
                        break;
                    case JFileChooser.CANCEL_OPTION:
                        saveFilePath = "";
                        break;
                    case JFileChooser.ERROR_OPTION:
                        saveFilePath = "";
                }
                saveChoosedFlag = true;
            }
        };

        SwingUtilities.invokeLater(runnable);

        while (!saveChoosedFlag) {
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
            }
        }

        saveChoosedFlag = false;
        return saveFilePath;
    }


    /**
     * 转到前台
     */
    private void toFront() {
        parentJFrame.toFront();
        parentJFrame.requestFocus();
    }


    public synchronized String getDirectoryPath(final String parentPath) {
        dirChoosedFlag = false;

        dirFileChooser.setVisible(true);
        Runnable runnable = new Runnable() {

            public void run() {
                FileChooserHelper.this.toFront();
                if (parentPath != null) {
                    File parentFile = new File(parentPath);
                    if (parentFile.exists()) {
                        if (parentFile.isFile()) {
                            dirFileChooser.setSelectedFile(parentFile);
                        } else {
                            dirFileChooser.setCurrentDirectory(parentFile);
                        }
                    }
                }

                int result = dirFileChooser.showDialog(parentJFrame, "选择");
                switch (result) {
                    case JFileChooser.APPROVE_OPTION:
                        dirFilePath = dirFileChooser.getSelectedFile().getAbsolutePath();
                        break;
                    case JFileChooser.CANCEL_OPTION:
                        dirFilePath = "";
                        break;
                    case JFileChooser.ERROR_OPTION:
                        dirFilePath = "";
                }
                dirChoosedFlag = true;
            }
        };

        SwingUtilities.invokeLater(runnable);

        while (!dirChoosedFlag) {
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
            }
        }

        dirChoosedFlag = false;
        return dirFilePath;
    }

}
