package com.hooke.zdl.admin.module.business.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.hooke.zdl.admin.common.annoation.NoNeedLogin;
import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.module.support.oos.FileInfoModel;
import com.hooke.zdl.admin.module.support.oos.ObjectStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/file")
@Tag(name = "文件系统", description = "文件系统")
public class FileAdminController {

    @Autowired
    private ObjectStorageService objectStorageService;

    @Operation(summary = "创建桶")
    @PostMapping("/create-bucket")
    public ResponseDTO<String> createBucket() throws Exception {
        objectStorageService.createBucket();
        return ResponseDTO.ok("创建桶成功");
    }

    @Operation(summary = "删除桶")
    @PostMapping("/remove-bucket")
    public ResponseDTO<String> removeBucket() throws Exception {
        objectStorageService.removeBucket();
        return ResponseDTO.ok("删除桶成功");
    }

    @NoNeedLogin
    @Operation(summary = "文件上传（根据名称）")
    @PostMapping("/upload-file-with-name")
    public ResponseDTO<String> uploadFileWithName(@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName) throws Exception {
        objectStorageService.uploadFile(fileName, file);
        return ResponseDTO.ok("上传成功");
    }

    @Operation(summary = "文件上传")
    @PostMapping("/upload")
    public ResponseDTO<FileInfoModel> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        String md5 = MD5.create().digestHex(file.getInputStream());
        String fileName = StrUtil.blankToDefault(file.getOriginalFilename(), "");
        String objectName = md5 + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
        objectStorageService.uploadFile(objectName, file);
        FileInfoModel fileInfoModel = new FileInfoModel();
        fileInfoModel.setFileName(fileName);
        fileInfoModel.setObjectName(objectName);
        return ResponseDTO.ok(fileInfoModel);
    }

    @NoNeedLogin
    @Operation(summary = "文件下载")
    @GetMapping("/download/{objectName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String objectName) throws Exception {
        byte[] fileContent = objectStorageService.downloadFile(objectName);

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.APPLICATION_OCTET_STREAM;
        if (objectName.endsWith(".jpg") || objectName.endsWith(".png")) {
            type = MediaType.IMAGE_JPEG;
        } else if (objectName.endsWith(".pdf")) {
            type = MediaType.APPLICATION_PDF;
        }
        headers.setContentType(type);
        return ResponseEntity.ok()
                .headers(headers)
                .body(fileContent);
    }
}
