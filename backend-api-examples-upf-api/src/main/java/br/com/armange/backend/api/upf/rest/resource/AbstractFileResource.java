package br.com.armange.backend.api.upf.rest.resource;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import br.com.armange.backend.api.upf.rest.service.FileSystemService;

public abstract class AbstractFileResource {
    protected static final String INPUT_KEY = "file";

    protected File buildDestination(
            final String id, 
            final String fileName) throws IOException {
        return FileUtils.getFile(configureBaseDirectory(id), fileName);
    }
    
    protected String configureBaseDirectory(final String id) throws IOException {
        final FileSystemService fileId = FileSystemService.fromId(id);
        
        if (!fileId.exists()) {
            fileId.mkdir();
        }
        
        return StringUtils.join(FileSystemService.getBaseDirectory(), id, File.pathSeparator);
    }
}
