package com.cgnial.salesreports.util;

import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Component
public class FileExtensionChanger {

    public File changeExtension(File f, String newExtension) {
        int i = f.getName().lastIndexOf('.');
        String baseName = (i == -1) ? f.getName() : f.getName().substring(0, i);
        File newFile = new File(f.getParent(), baseName + newExtension);

        try {
            // Use Files.move for reliable renaming
            Path sourcePath = f.toPath();
            Path targetPath = newFile.toPath();
            Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File renamed to: " + newFile.getName());
            return newFile;
        } catch (IOException e) {
            System.err.println("Error renaming file: " + e.getMessage());
            return f; // Return original file if renaming fails
        }
    }
}

