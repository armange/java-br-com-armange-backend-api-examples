package br.com.armange.backend.api.upf.rest.service;

import java.io.IOException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.github.difflib.algorithm.DiffException;
import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;

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
    
    public String readFilesAndGetDiff() throws IOException, DiffException {
        try (
                final Stream<String> streamOnLeft = FileSystemService.fromId(id).getFileContentOnLeft();
                final Stream<String> streamOnRight = FileSystemService.fromId(id).getFileContentOnRight();) {
            final DiffRowGenerator generator = DiffRowGenerator.create().build();
            
            return generator.generateDiffRows(
                        streamOnLeft.collect(Collectors.toList()),
                        streamOnRight.collect(Collectors.toList()))
                    .stream()
                    .map(mapToLineWithChanges())
                    .collect(Collectors.joining(StringUtils.LF));
        } catch (final IOException e) {
            e.printStackTrace();

            throw e;
        }
    }

    private Function<? super DiffRow, ? extends String> mapToLineWithChanges() {
        return r -> {
            if (isNotNull(r)) {
                return getNotNullChanges(r);
            } else if (r.getNewLine() != null) {
                return getCreatedOnRevision(r);
            } else {
                return getRemovedOnRevision(r);
            }
        };
    }

    private String getNotNullChanges(DiffRow r) {
        if (isEquals(r)) {
            return getEqualsOnRevision(r);
        } else if(isChangedOnRevision(r)) {
            return StringUtils.join(getRemovedOnRevision(r), getCreatedOnRevision(r));
        } else if(isRemovedOnRevision(r)) {
            return getRemovedOnRevision(r);
        } else {
            return getCreatedOnRevision(r);
        }
    }

    private boolean isNotNull(final DiffRow row) {
        return row.getNewLine() != null && row.getOldLine() != null;
    }

    private boolean isEquals(final DiffRow row) {
        return row.getOldLine().equals(row.getNewLine());
    }

    private boolean isChangedOnRevision(final DiffRow row) {
        return !row.getNewLine().isEmpty() && !row.getOldLine().isEmpty();
    }

    private boolean isRemovedOnRevision(final DiffRow row) {
        return row.getNewLine().isEmpty() && !row.getOldLine().isEmpty();
    }
    
    private String getEqualsOnRevision(DiffRow r) {
        return StringUtils.join(EQUAL_LINE_PREFIX, r.getNewLine(), StringUtils.LF);
    }

    private String getCreatedOnRevision(DiffRow r) {
        return StringUtils.join(CREATED_LINE_PREFIX, r.getNewLine(), StringUtils.LF);
    }

    private String getRemovedOnRevision(DiffRow r) {
        return StringUtils.join(DELETED_LINE_PREFIX, r.getOldLine(), StringUtils.LF);
    }
}
