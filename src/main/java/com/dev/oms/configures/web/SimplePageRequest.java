package com.dev.oms.configures.web;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SimplePageRequest implements Pageable {

    private final long offset;

    private final int size;

    public SimplePageRequest() {
        this(0, 5);
    }

    public SimplePageRequest(long offset, int size) {
        if (offset < 0) offset = 0;
        if (size < 0 || size > 5) size = 5;

        this.offset = offset;
        this.size = size;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("offset", offset)
                .append("size", size)
                .toString();
    }

}