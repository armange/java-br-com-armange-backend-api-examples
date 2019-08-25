package br.com.armange.backend.api.upf.rest.resource;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import br.com.armange.backend.api.upf.server.configuration.PropertyKeyHandler;
import br.com.armange.backend.api.upf.server.configuration.ServerProperties;

public abstract class AbstractFileResource {
    protected static final String INPUT_KEY = "file";

    protected File buildDestination(
            final String id, 
            final String filePrefix,
            final FormDataContentDisposition fileDetail) throws IOException {
        return new File(StringUtils.join(configureBaseDirectory(id), filePrefix, fileDetail.getFileName()));
    }
    
    protected String configureBaseDirectory(final String id) throws IOException {
        final String baseDirectory = getBaseDirectory();
        
        File verifying = new File(baseDirectory);
        
        if (!verifying.exists()) {
            throw new IOException("Base directory was not found.");
        }
        
        verifying = new File(StringUtils.join(baseDirectory, id));
        
        if (!verifying.exists()) {
            verifying.mkdir();
        }
        
        return StringUtils.join(baseDirectory, id, File.pathSeparator);
    }
    
    protected String getBaseDirectory() {
        return ConfigResolver
                .resolve(PropertyKeyHandler.build(ServerProperties.BASE_DIRECTORY))
                .as(String.class)
                .getValue();
    }
}
