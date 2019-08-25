package br.com.armange.backend.api.upf.rest.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

public class FileDiffService {
    private static final String DELETED_LINE_PREFIX = "- ";
    private static final String CREATED_LINE_PREFIX = "+ ";
    private static final String EQUAL_LINE_PREFIX   = "= ";
    
    private final String id;
    
    private FileDiffService(final String id) {
        this.id = id;
    }
    
    public static FileDiffService fromId(final String id) {
        return new FileDiffService(id);
    }
    
    public String readFilesAndGetDiff() throws IOException {
        try (
                final Stream<String> streamOnLeft = FileSystemService.fromId(id).getFileContentOnLeft();
                final Stream<String> streamOnRight = FileSystemService.fromId(id).getFileContentOnRight();) {
            return generateDiffResult(streamOnLeft, streamOnRight);
        } catch (final IOException e) {
            e.printStackTrace();
            
            throw e;
        }
    }

    private String generateDiffResult(final Stream<String> streamOnLeft, final Stream<String> streamOnRight) {
        final Iterator<String> iteratorOnLeft = streamOnLeft.iterator();
        final Iterator<String> iteratorOnRight = streamOnRight.iterator();
        final StringBuilder result = new StringBuilder();
        
        if (isAllLinesDeleted(iteratorOnLeft, iteratorOnRight)) {
            iteratorOnLeft.forEachRemaining(markAsDeletedLine(result));
        } else if (isAllLinesCreated(iteratorOnLeft, iteratorOnRight)) {
            iteratorOnRight.forEachRemaining(markAsCreatedLine(result));
        } else if (noLinesFound(iteratorOnLeft, iteratorOnRight)) {
            return null;
        } else {
            iteratorOnLeft.forEachRemaining(markChanges(iteratorOnRight, result));
            markRemainingLinesIfExists(iteratorOnRight, result);
        }
        
        return result.toString();
    }

    private Consumer<? super String> markAsDeletedLine(final StringBuilder result) {
        return l -> result.append(DELETED_LINE_PREFIX).append(l).append(StringUtils.LF);
    }
    
    private Consumer<? super String> markAsCreatedLine(final StringBuilder result) {
        return l -> result.append(CREATED_LINE_PREFIX).append(l).append(StringUtils.LF);
    }
    
    private Consumer<? super String> markAsEqualLine(final StringBuilder result) {
        return l -> result.append(EQUAL_LINE_PREFIX).append(l).append(StringUtils.LF);
    }
    
    private Consumer<? super String> markChanges(final Iterator<String> iteratorOnRight, final StringBuilder result) {
        return le -> {
            if (iteratorOnRight.hasNext()) {
                final String nextOnRight = iteratorOnRight.next();
                
                if (!le.equals(nextOnRight)) {
                    result.append(DELETED_LINE_PREFIX).append(le).append(StringUtils.LF);
                    result.append(CREATED_LINE_PREFIX).append(nextOnRight).append(StringUtils.LF);
                } else {
                    result.append(EQUAL_LINE_PREFIX).append(nextOnRight).append(StringUtils.LF);
                }
            } else {
                result.append(EQUAL_LINE_PREFIX).append(le).append(StringUtils.LF);
            }
        };
    }
    
    private boolean isAllLinesDeleted(final Iterator<String> iteratorOnLeft, final Iterator<String> iteratorOnRight) {
        return iteratorOnLeft.hasNext() && !iteratorOnRight.hasNext();
    }
    
    private boolean isAllLinesCreated(final Iterator<String> iteratorOnLeft, final Iterator<String> iteratorOnRight) {
        return !iteratorOnLeft.hasNext() && iteratorOnRight.hasNext();
    }

    private boolean noLinesFound(final Iterator<String> iteratorOnLeft, final Iterator<String> iteratorOnRight) {
        return !iteratorOnLeft.hasNext() && !iteratorOnRight.hasNext();
    }
    
    private void markRemainingLinesIfExists(final Iterator<String> iteratorOnRight, final StringBuilder result) {
        if (iteratorOnRight.hasNext()) {
            iteratorOnRight.forEachRemaining(markAsEqualLine(result));
        }
    }
}
