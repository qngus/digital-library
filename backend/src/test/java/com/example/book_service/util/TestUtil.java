package com.example.book_service.util;

import java.time.LocalDate;
import java.util.UUID;

public class TestUtil {
    
    public static final UUID BOOK_ID = UUID.randomUUID();

    public static final UUID AUTHOR_ID = UUID.randomUUID();

    public static final String TITLE = "How to Brew";

    public static final Integer PAGE_COUNT = 400;
    
    public static final LocalDate PUBLICATION_DATE = LocalDate.of(2017, 6, 1);

    public static final String SUMMARY = """
            From the simplest of recipes to the construction of all grain systems, this 400 page volume covers the widest variety of homebrewing subjects we have seen.
            """;

    public static final String AUTHOR_NAME = "John Palmer";

}
