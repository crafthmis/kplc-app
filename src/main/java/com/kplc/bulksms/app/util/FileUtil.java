package com.kplc.bulksms.app.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static File getTargetFile(String fileExtn, String fileName, String uploadDir) {
        File targetFile = new File(uploadDir + fileName);
        return targetFile;
    }

    public static String getFileExtension(MultipartFile inFile) {
        String fileExtention = inFile.getOriginalFilename().substring(inFile.getOriginalFilename().lastIndexOf('.'));
        return fileExtention;
    }

    public static List<File> outageFiles(String uploadLocation){

        File folder = new File(uploadLocation);
        File[] listOfFiles = folder.listFiles();
        List<File> outageFiles = new ArrayList<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                outageFiles.add(listOfFiles[i]);
            }
        }
        return outageFiles;
    }
}
