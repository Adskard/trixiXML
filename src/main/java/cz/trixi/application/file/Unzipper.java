package cz.trixi.application.file;

import lombok.extern.java.Log;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Log
public class Unzipper {

    /**
     * Extracts the first file from a zip archive and saves it to destinationPath
     *
     * @param sourcePath path to source zip archive
     * @param destinationPath where to save extracted file
     * @throws IOException
     */
    public static void unzipFirstEntry(String sourcePath, String destinationPath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(sourcePath);
        ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);

        File destinationDir = new File(destinationPath);
        if(!destinationDir.exists()) destinationDir.mkdirs();

        ZipEntry entry = zipInputStream.getNextEntry();

        if(Objects.nonNull(entry)){
            Files.copy(zipInputStream, Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
            log.info(String.format("Successful unzipping of %s to destination path: %s",
                    sourcePath, destinationPath));
            return;
        }

        throw new IOException(String.format("Zip file %s is empty",
                sourcePath));
    }
}
