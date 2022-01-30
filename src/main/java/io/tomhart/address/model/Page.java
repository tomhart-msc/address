package io.tomhart.address.model;

import java.util.List;

/**
 * Represents a page of resources.
 *
 * @param <T> The type of resource, eg. {@link User}.
 */
public class Page<T> {
    private final int offset;
    private final boolean more;
    private final List<T> results;

    public Page(int offset, boolean more, List<T> results) {
        this.offset = offset;
        this.more = more;
        this.results = results;
    }

    public int getOffset() {
        return offset;
    }

    public boolean getMore() {
        return more;
    }

    public List<T> getResults() {
        return results;
    }
}
