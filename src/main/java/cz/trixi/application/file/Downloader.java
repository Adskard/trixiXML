package cz.trixi.application.file;

import lombok.extern.java.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

@Log
public class Downloader {

    /**
     * Downloads a file from source path into file located at destination path
     *
     * @param source path to download source
     * @param destination path to destination file
     * @throws IOException when file cannot be downloaded, or saved to destination path
     */
    public static void download(String source, String destination) throws IOException {
        log.info(String.format("Downloading %s to %s", source, destination));
        URL sourceUrl = new URL(source);
        ReadableByteChannel readableByteChannel = Channels.newChannel(sourceUrl.openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(destination);
        FileChannel channel = fileOutputStream.getChannel();

        channel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
    }
}
