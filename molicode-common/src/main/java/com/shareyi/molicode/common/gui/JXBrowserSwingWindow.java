package com.shareyi.molicode.common.gui;

import com.shareyi.fileutil.FileUtil;
import com.shareyi.joywindow.window.FileChooserHelper;
import com.shareyi.molicode.common.utils.LogHelper;
import com.shareyi.molicode.common.utils.Profiles;
import com.shareyi.molicode.common.utils.SystemUtils;
import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.events.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;

/**
 * jx browser swing 窗口
 *
 * @author david
 * @date 2018/8/11
 */
public class JXBrowserSwingWindow implements BrowserWindow {


    private String indexUrl;
    /**
     * 浏览器对象
     */
    Browser browser;
    /**
     * Top jFrame
     */
    private javax.swing.JFrame topJFrame;


    FileChooserHelper fileChooserHelper;

    /**
     * 初始化
     */
    public void init() {
        //this.indexUrl = url;
        setLocalLookAndFeel();
        //解决前台下拉菜单被遮挡
        if(SystemUtils.isWindows()){
            browser = new Browser(BrowserType.LIGHTWEIGHT);
        }else{
            browser = new Browser();
        }
       /* BrowserPreferences preferences = new BrowserPreferences();
        preferences.setJavaScriptCanAccessClipboard(true);
        browser.setPreferences(preferences);*/
        BrowserView view = new BrowserView(browser);
        final JFrame frame = new JFrame();
        this.topJFrame = frame;
        frame.add(view, BorderLayout.CENTER);
        this.initSizeAndSetLocationCenter(frame);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
                System.out.println("window closing");
            }

            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("window closing");
                System.exit(0);
            }
        });
        frame.setExtendedState(JFrame.EXIT_ON_CLOSE);

        fileChooserHelper = new FileChooserHelper(frame);
        fileChooserHelper.init();

        this.addMenu(frame, browser);
        browser.addTitleListener(titleEvent -> frame.setTitle(titleEvent.getTitle()));

        browser.addConsoleListener(consoleEvent -> {
            String msg = consoleEvent.getLineNumber() + ":" + consoleEvent.getMessage();
            switch (consoleEvent.getLevel()) {
                case ERROR:
                    LogHelper.BROWSER_CONSOLE.error(msg);
                    break;
                case DEBUG:
                    LogHelper.BROWSER_CONSOLE.debug(msg);
                    break;
                case WARNING:
                    LogHelper.BROWSER_CONSOLE.warn(msg);
                    break;
                default:
                    LogHelper.BROWSER_CONSOLE.info(msg);
            }
        });
    }


    @Override
    public void openWindow() {
        topJFrame.setVisible(true);

    }

    @Override
    public void closeWindow() {
        browser.dispose();
        topJFrame.dispose();
    }

    @Override
    public void alertMessage(String message) {
    }

    @Override
    public void setUrl(String url) {
        this.indexUrl = url;
        browser.loadURL(url);
    }

    private void initSizeAndSetLocationCenter(JFrame jFrame) {
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        double ration = 0.75;
        int width = (int) (screenWidth * ration);
        int height = (int) (screenHeight * ration);
        jFrame.setSize(width, height);
        // 设置窗体位置和大小
        jFrame.setBounds((screenWidth - width) / 2,
                (screenHeight - height) / 2, width, height);
    }

    /**
     * 更改一下外观为本地开发系统的
     */
    public static void setLocalLookAndFeel() {
        if (UIManager.getLookAndFeel().isSupportedLookAndFeel()) {
            final String platform = UIManager.getSystemLookAndFeelClassName();
            // If the current Look & Feel does not match the platform Look & Feel,  change it so it does.
            if (!UIManager.getLookAndFeel().getName().equals(platform)) {
                try {
                    UIManager.setLookAndFeel(platform);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    /**
     * 增加菜单
     *
     * @param frame
     */
    public void addMenu(final JFrame frame, final Browser browser) {
        JMenuBar menuBar = new JMenuBar();

        JMenu ctrlMenu = new JMenu("控制");
        menuBar.add(ctrlMenu);

        JMenuItem refreshMenu = new JMenuItem("更新界面");
        ctrlMenu.add(refreshMenu);
        JMenuItem exitMenu = new JMenuItem("退出");
        ctrlMenu.add(exitMenu);


        refreshMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser.reload();
            }
        });
        exitMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        JMenu viewMenu = new JMenu("查看");
        menuBar.add(viewMenu);

        JMenuItem indexMenu = new JMenuItem("返回主界面");
        viewMenu.add(indexMenu);
        JMenuItem fullMenu = new JMenuItem("全屏");
        viewMenu.add(fullMenu);

        JMenuItem loadScript = new JMenuItem("执行脚本");
        viewMenu.add(loadScript);

        indexMenu.addActionListener(e -> {
            if (StringUtils.isNotEmpty(indexUrl)) {
                browser.loadURL(indexUrl);
            }
        });


        fullMenu.addActionListener(e -> {
            if (frame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                frame.setExtendedState(JFrame.NORMAL);

            } else {
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            }
            // GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
        });


        loadScript.addActionListener(e -> {


            new Thread(new Runnable() {
                @Override
                public void run() {
                    chooserScriptToExecute();
                }
            }).start();
        });
        frame.setJMenuBar(menuBar);

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    JSValue window = browser.executeJavaScriptAndReturnValue("window");
                    // 给jswindows对象添加一个扩展的属性
                    window.asObject().setProperty("callbackUtils", Profiles.getInstance().getBrowserCallbackCenter());
                }
            }
        });
    }

    private void chooserScriptToExecute() {
        String openPath = fileChooserHelper.getOpenPath(FileUtil.getRunPath(), true, new String[]{"*.js"}, new String[]{"*.js"});
        if (StringUtils.isEmpty(openPath)) {
            return;
        }

        FileInputStream input = null;
        try {
            File file = new File(openPath);
            if (file.exists() && file.isFile() && file.canRead()) {
                input = new FileInputStream(file);
                browser.executeJavaScript(IOUtils.toString(input, Profiles.getInstance().getFileEncoding()));
            } else {
                LogHelper.DEFAULT.info("文件不能读取：" + openPath);
            }
        } catch (Exception ee) {
            LogHelper.EXCEPTION.error("执行脚本出现异常,filePath=" + openPath, ee);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }


    public JFrame getTopJFrame() {
        return topJFrame;
    }
}
