package com.example.controller;

import cn.hutool.core.io.FileUtil;
import com.example.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/files")
public class FileController {

    @Value("${ip}")
    private String ip;

    @Value("${server.port}")
    private String port;

    private static final String ROOT_PATH = System.getProperty("user.dir") + "/files";

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String flaggedName = System.currentTimeMillis() + "_" + fileName;

        File newFile = new File(ROOT_PATH + "/" + flaggedName);
        if (!newFile.getParentFile().exists()) {
            newFile.getParentFile().mkdirs();
        }

        file.transferTo(newFile);

        String url = "http://"+ ip +":"+ port +"/files/download?=fileName" + flaggedName;
        return Result.success(url);
    }

    @GetMapping("/download")
    public void download(String fileName, HttpServletResponse response) throws IOException {
        File file = new File(ROOT_PATH + "/" + fileName);
        ServletOutputStream os = response.getOutputStream();

        // 设置响应头
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/octet-stream");

        FileUtil.writeToStream(file, os);
        os.flush();
        os.close();
    }
}
