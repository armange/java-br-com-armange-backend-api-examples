package br.com.armange.backend.api.upf.rest.service;

import java.io.IOException;
import java.util.stream.Stream;

public class FileDiffService {
    private final String id;
    
    private FileDiffService(final String id) {
        this.id = id;
    }
    
    public static FileDiffService fromId(final String id) {
        return new FileDiffService(id);
    }
    
    public Stream<String> readFilesAndGetDiff() throws IOException {
        try (
                final Stream<String> streamOnLeft = FileSystemService.fromId(id).getFileContentOnLeft();
                final Stream<String> streamOnRight = FileSystemService.fromId(id).getFileContentOnLeft();) {
            
            return null;
        } catch (final IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
