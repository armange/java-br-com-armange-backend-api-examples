package br.com.armange.backend.api.upf.rest.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.core.api.config.ConfigResolver;

import br.com.armange.backend.api.upf.server.configuration.PropertyKeyHandler;
import br.com.armange.backend.api.upf.server.configuration.ServerProperties;

public class FileSystemService {
    public static final String FILE_ON_LEFT_NAME = "from";
    public static final String FILE_ON_RIGHT_NAME = "to";

    private final String id;
    
    private FileSystemService(final String id) {
        this.id = id;
    }
    
    public static FileSystemService fromId(final String id) {
        return new FileSystemService(id);
    }
    
    public Stream<String> getFileContentOnLeft() throws IOException {
        validate();
        
        return Files.lines(Paths.get(composeFileNameOnLeft()));
    }
    
    public String composeFileNameOnLeft() {
        return composeFileNameOn(FILE_ON_LEFT_NAME);
    }
    
    public Stream<String> getFileContentOnRight() throws IOException {
        validate();
        
        return Files.lines(Paths.get(composeFileNameOnRight()));
    }
    
    public String composeFileNameOnRight() {
        return composeFileNameOn(FILE_ON_RIGHT_NAME);
    }
    
    private String composeFileNameOn(final String fileName) {
        return StringUtils.join(getBaseDirectory(), id, File.pathSeparator, fileName);
    }
    
    public boolean mkdir() {
        return FileUtils.getFile(getBaseDirectory(), id).mkdir();
    }
    
    public void validate() throws IOException {
        if (!exists()) {
            throw new IOException("The ID was not found.");
        }
    }
    
    public boolean exists() throws IOException {
        final String baseDirectory = getBaseDirectory();
        
        if (!FileUtils.getFile(baseDirectory).exists()) {
            throw new IOException("The base directory was not found.");
        }
        
        return FileUtils.getFile(baseDirectory, id).exists();
    }
    
    public static String getBaseDirectory() {
        return ConfigResolver
                .resolve(PropertyKeyHandler.build(ServerProperties.BASE_DIRECTORY))
                .as(String.class)
                .getValue();
    }
}
