package com.shareyi.molicode.controller.common;

import com.shareyi.fileutil.FileIo;
import com.shareyi.fileutil.FileUtil;
import com.shareyi.joywindow.window.FileChooserHelper;
import com.shareyi.molicode.common.constants.CommonConstant;
import com.shareyi.molicode.common.gui.GuiWindowFactory;
import com.shareyi.molicode.common.utils.LogHelper;
import com.shareyi.molicode.common.utils.Profiles;
import com.shareyi.molicode.common.utils.SystemFileUtils;
import com.shareyi.molicode.common.utils.SystemInvoker;
import com.shareyi.molicode.common.vo.FileVo;
import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.service.common.FileService;
import com.shareyi.molicode.web.base.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * 文件操作相关控制
 * <p>
 * this controller have hack problem,
 * 应该加上只允许localhost的主机访问，即只允许自己访问自己
 *
 * @author david
 * @date 2018-08-04
 */
@Controller
@RequestMapping("/common/file")
public class FileController extends BaseController {

    protected FileChooserHelper fileChooserHelper = null;

    @Resource
    private FileService fileService;

    @ResponseBody
    @RequestMapping(value = "fileChooser", method = {RequestMethod.GET, RequestMethod.POST})
    public Map fileChooser(FileVo fileVo) {
        CommonResult result = CommonResult.create();
        if (Profiles.getInstance().isHeadLess()) {
            return result.failed("headless下无法支持").getReturnMap();
        }
        String filePath = null;
        //转换文件路径为正确路径
        String parentPath = SystemFileUtils.parseFilePath(fileVo.getParentPath());
        if (StringUtils.isEmpty(parentPath)) {
            parentPath = FileUtil.getRunPath();
        }
        if (Objects.equals("open", fileVo.getDialogType())) {
            File file = new File(parentPath);
            if (file.exists() && file.isFile()) {
                parentPath = file.getParent();
            }
            String[] fileExtensions = getExtensions(fileVo);
            filePath = getFileChooserHelper().getOpenPath(parentPath, true, fileExtensions, fileExtensions);
        } else if (Objects.equals("directory", fileVo.getDialogType())) {
            filePath = getFileChooserHelper().getDirectoryPath(parentPath);
        } else {
            String[] fileExtensions = getExtensions(fileVo);
            filePath = getFileChooserHelper().getSavePath(parentPath, true, fileExtensions, fileExtensions);
        }

        if (CommonConstant.STD_YN_YES_STR.equals(fileVo.getChangeCurPath())) {
            filePath = SystemFileUtils.changeCurFilePath(filePath);
        }
        result.succeed().addDefaultModel(filePath);
        return result.getReturnMap();
    }


    private String[] getExtensions(FileVo fileVo) {
        String fileType = fileVo.getFileType();
        if (StringUtils.isEmpty(fileType) || Objects.equals("all", fileType))
            return allExtensions;
        else if (Objects.equals("other", fileType)) {
            if (StringUtils.isEmpty(fileVo.getFileExt()))
                return allExtensions;
            else {
                if (StringUtils.startsWith(fileVo.getFileExt(), "*.")) {
                    return new String[]{fileVo.getFileExt()};
                } else {
                    String s = "*." + fileVo.getFileExt().trim();
                    return new String[]{s};
                }
            }
        } else if (Objects.equals("img", fileType)) {
            return imgExtensions;
        } else if (Objects.equals("audio", fileType)) {
            return audioExtensions;
        } else if (Objects.equals("vedio", fileType)) {
            return vedioExtensions;
        } else if (Objects.equals("doc", fileType)) {
            return docExtensions;
        }
        return allExtensions;
    }


    @ResponseBody
    @RequestMapping(value = "openFile", method = {RequestMethod.GET, RequestMethod.POST})
    public Map startFile(FileVo fileVo) {
        CommonResult result = CommonResult.create();
        if (Profiles.getInstance().isHeadLess()) {
            return result.failed("headless下无法支持").getReturnMap();
        }
        if (StringUtils.isEmpty(fileVo.getEditFilePath())) {
            return result.failed("文件路径为空！").getReturnMap();
        } else {
            File file = new File(SystemFileUtils.parseFilePath(fileVo.getEditFilePath()));
            if (!file.exists()) {
                return result.failed("文件或目录不存在!").getReturnMap();
            }
            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(file);
            } catch (IOException e) {
                LogHelper.EXCEPTION.error("打开文件失败, fileVo={}", fileVo, e);
            }
            result.setSuccess(true);
            result.setMessage("文件已打开");

        }
        return result.getReturnMap();
    }

    @ResponseBody
    @RequestMapping(value = "editFile", method = {RequestMethod.GET, RequestMethod.POST})
    public Map editFile(FileVo fileVo) {
        String editFilePath = fileVo.getEditFilePath();
        CommonResult result = CommonResult.create();
        if (Profiles.getInstance().isHeadLess()) {
            return result.failed("headless下无法支持").getReturnMap();
        }
        if (StringUtils.isEmpty(editFilePath)) {
            result.setSuccess(false);
            result.setMessage("文件路径为空！");
        } else {
            File file = new File(SystemFileUtils.parseFilePath(editFilePath));
            /*     if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    return result.failed("创建文件失败：" + e.getMessage()).getReturnMap();
                }
            }*/
            if (file.isFile()) {
                try {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.edit(file);
                } catch (IOException e) {
                }
                result.setSuccess(true);
                result.setMessage("文件已打开");
            } else {
                result.setMessage("文件为文件夹");
            }
        }
        return result.getReturnMap();
    }


    @ResponseBody
    @RequestMapping(value = "saveFile", method = {RequestMethod.GET, RequestMethod.POST})
    public Map saveFile(FileVo fileVo) {
        String editFilePath = fileVo.getEditFilePath();
        CommonResult result = CommonResult.create();
        if (Profiles.getInstance().isHeadLess()) {
            return result.failed("headless下无法支持").getReturnMap();
        }
        if (StringUtils.isEmpty(editFilePath) || "null".equals(editFilePath)) {
            result.setSuccess(false);
            result.setMessage("文件路径为空！");
        } else {
            File file = new File(SystemFileUtils.parseFilePath(editFilePath));
            if (!file.exists()) {
                file = FileUtil.makeSureFileExsit(file);
                if (file == null) {
                    return result.failed("创建文件失败：" + editFilePath).getReturnMap();
                }
            }

            if (file.isFile() && file.canWrite()) {
                FileIo.writeToFile(file, fileVo.getFileContent(), "utf-8");
                result.succeed("文件保存成功");
            } else {
                result.setMessage("文件无法写入,无法保存！");
            }
        }
        return result.getReturnMap();
    }

    @ResponseBody
    @RequestMapping(value = "getFileContent", method = {RequestMethod.GET, RequestMethod.POST})
    public Map getFileContent(FileVo fileVo) {
        CommonResult result = CommonResult.create();
        if (Profiles.getInstance().isHeadLess()) {
            return result.failed("headless下无法支持").getReturnMap();
        }

        String editFilePath = fileVo.getEditFilePath();
        if (StringUtils.isEmpty(editFilePath)) {
            result.failed("文件路径为空！");
        } else {
            File file = new File(SystemFileUtils.parseFilePath(editFilePath));
            if (!file.exists()) {
                return result.failed("文件不存在：" + editFilePath).getReturnMap();
            }
            if (file.isFile() && file.canRead()) {
                result.setSuccess(true);
                result.setMessage(FileIo.readFileAsString(file, "utf-8"));
            } else {
                result.setMessage("文件无法读取！");
            }
        }
        return result.getReturnMap();
    }

    @ResponseBody
    @RequestMapping(value = "deleteFile", method = {RequestMethod.GET, RequestMethod.POST})
    public Map deleteFile(FileVo fileVo) {
        CommonResult result = CommonResult.create();
        String editFilePath = fileVo.getEditFilePath();
        if (StringUtils.isEmpty(editFilePath)) {
            result.failed("文件路径为空！");
        } else {
            File file = new File(SystemFileUtils.parseFilePath(editFilePath));
            if (!file.exists()) {
                return result.failed("文件不存在：" + editFilePath).getReturnMap();
            }

            if (file.isFile() && file.canWrite()) {
                file.delete();
                result.succeed("文件删除成功:" + file.getAbsolutePath());
            } else {
                result.failed("文件无法删除！");
            }
        }
        return result.getReturnMap();
    }


    /**
     * 打开文件目录
     */
    @ResponseBody
    @RequestMapping(value = "/openDirectory", method = {RequestMethod.GET, RequestMethod.POST})
    public Map openDirectory(FileVo fileVo) {
        CommonResult result = CommonResult.create();
        if (Profiles.getInstance().isHeadLess()) {
            return result.failed("headless下无法支持").getReturnMap();
        }

        String parentPath = fileVo.getParentPath();
        if (StringUtils.isEmpty(parentPath)) {
            result.setMessage("directory path is null");
        } else {
            String path = SystemFileUtils.parseFilePath(parentPath);
            File file = new File(path);
            if (file.exists() && file.isDirectory()) {
                SystemInvoker.executeWithOutReturn("cmd.exe /c start " + path);
                result.setMessage("open dirctory success [" + path + "]");
                result.setSuccess(true);
            } else {
                result.setMessage("dirctory [" + path + "] is not exists");
            }
        }
        return result.getReturnMap();
    }


    /**
     * 打开文件目录
     */
    @ResponseBody
    @RequestMapping(value = "listFiles", method = {RequestMethod.GET, RequestMethod.POST})
    public Map listFiles(final FileVo fileVo) {
        CommonResult result = fileService.listFiles(fileVo);
        return result.getReturnMap();
    }


    /**
     * 列举仓库列表
     */
    @ResponseBody
    @RequestMapping(value = "listRepo", method = {RequestMethod.GET, RequestMethod.POST})
    public Map listRepo(String repoName) {
        CommonResult result = fileService.listRepo(repoName);
        return result.getReturnMap();
    }


    public FileChooserHelper getFileChooserHelper() {
        if (fileChooserHelper == null) {
            fileChooserHelper = new FileChooserHelper(GuiWindowFactory.getInstance().getBrowserWindow().getTopJFrame());
            fileChooserHelper.init();
        }
        return fileChooserHelper;
    }

    public static final String[] allExtensions = {"*.*"};

    public static final String[] imgExtensions = {"*.bmp;*.gif;*.ico;*.jpg;*.pcx;*.png;*.tif", "*.bmp",
            "*.gif", "*.ico", "*.jpg", "*.pcx", "*.png", "*.tif"};

    public static final String[] audioExtensions = {"*.wav;*.wma;*.rm;*.ram;*.rmvb;*.ra;*.rt;*.mpa;*.mpg;*.mpeg;*.mov;*.3gp;*.mp3",
            "*.wav;*.wma", "*.rm;*.ram;*.rmvb;*.ra;*.rt",
            "*.mpa;*.mpg;*.mpeg", "*.mov;*.3gp", "*.mp3"};

    public static final String[] vedioExtensions = {"*.avi;*.wmv;*.asf;*.rm;*.rmvb;*.ra;*.rt;*.mpg;*.mpeg;*.mpe;*.vob;*.mp3;*.mov;*.3gp",
            "*.avi;*.wmv;*.asf", "*.rm;*.rmvb;*.ra;*.rt", "*.mpg;*.mpeg;*.mpe",
            "*.vob", "*.mp3", "*.mov;*.3gp"};

    public static final String[] docExtensions = {"*.txt;*.doc;*.xls;*.ppt;*.docx;*.xlsx;*.pptx", "*.txt", "*.doc", "*.xls", "*.ppt"};


}
