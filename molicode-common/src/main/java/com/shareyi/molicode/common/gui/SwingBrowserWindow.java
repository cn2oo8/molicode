package com.shareyi.molicode.common.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * swing 窗口
 *
 * @author david
 * @date 2018/9/23
 */
public class SwingBrowserWindow implements BrowserWindow, HyperlinkListener {

    /**
     * jframe
     */
    private JFrame jFrame;
    /**
     * 可修改的pane
     */
    private JEditorPane editorPane;

    private static Logger LOGGER = LoggerFactory.getLogger(SwingBrowserWindow.class);

    public SwingBrowserWindow() {
    }


    public void closeWindow() {
        LOGGER.info("close swing browser window");
    }

    public void openWindow() {
        this.jFrame.pack();
        this.jFrame.setSize(900, 560);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int x = (int) (toolkit.getScreenSize().getWidth() - (double) this.jFrame.getWidth()) / 2;
        int y = (int) (toolkit.getScreenSize().getHeight() - (double) this.jFrame.getHeight()) / 2;
        this.jFrame.setLocation(x, y);
        this.jFrame.setVisible(true);
        LOGGER.info("open swing browser window");
    }

    public void init() throws Exception {
        LOGGER.info("init swing browser start...");
        this.jFrame = new JFrame();
        this.jFrame.setTitle("joyWindow应用程序");
        this.jFrame.setResizable(true);
        this.jFrame.setLayout(new FlowLayout(0));
        this.jFrame.setPreferredSize(new Dimension(900, 560));
        this.jFrame.setLocationRelativeTo(null);
        this.jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.editorPane = new JEditorPane();
        JScrollPane scrollPane = new JScrollPane(this.editorPane);
        this.editorPane.setContentType("text/html");
        this.editorPane.setEditable(false);
        this.editorPane.setText("<html><h1>说明</h1> 本窗口仅仅提供文件选择器功能！</html>");
        this.editorPane.addHyperlinkListener(this);
        this.jFrame.setContentPane(scrollPane);
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        UIManager.setLookAndFeel(lookAndFeel);
        LOGGER.info("init swing browser end");

    }

    public void alertMessage(final String message) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JOptionPane.showMessageDialog(null, message, "提示信息", 2);
            }
        });
    }


    public void setUrl(final String url) {
        LOGGER.info("set swing browser url={}", url);
        final StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("<html><h1>说明</h1> 本窗口仅仅提供文件选择器功能！");
        stringBuilder.append("<p>服务已经启动，请用浏览器打开地址访问：</p>");
        stringBuilder.append("<a href=\"");
        stringBuilder.append(url).append("\">");
        stringBuilder.append(url).append("</a>");
        stringBuilder.append("</html>");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SwingBrowserWindow.this.editorPane.setText(stringBuilder.toString());
            }
        });
    }

    @Override
    public JFrame getTopJFrame() {
        return jFrame;
    }

    public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(e.getURL().toURI());
            } catch (IOException var4) {
                var4.printStackTrace();
            } catch (URISyntaxException var5) {
                var5.printStackTrace();
            }
        }

    }
}
