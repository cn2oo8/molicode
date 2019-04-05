package com.shareyi.molicode.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 系统命令调用工具
 */
public class SystemInvoker {

    public static void executeWithOutReturn(String command) {
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行并返回结果，如果脚本一直不返回，可能会出现问题
     *
     * @param command
     * @return
     * @throws IOException
     */
    public static String executeWithReturn(String command) throws IOException {
        StringBuilder output = new StringBuilder();
        Runtime run = Runtime.getRuntime();
        Process process = run.exec(command);
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while (true) {
            String s = br.readLine();
            if (s == null) break;
            output.append(s + "\n");
        }
        return output.toString();
    }

}
