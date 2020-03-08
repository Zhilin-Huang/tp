package seedu.tp.utils;

import java.util.Arrays;
import java.util.List;

public class ExampleInputConstants {
    public static final String NAME = "Example name";
    public static final String START_DATE = "10/5/1784";
    public static final String END_DATE = "17/4/1892";
    public static final String SUMMARY = "Example summary";
    public static final String DETAIL_1 = "Detail 1";
    public static final String DETAIL_2 = "Detail 2";
    public static final List<String> DETAILS = Arrays.asList(DETAIL_1, DETAIL_2);
    public static final String NEWLINE = System.lineSeparator();
    public static final String FULL_SIMULATED_INPUT = NAME + NEWLINE
            + START_DATE + NEWLINE
            + END_DATE + NEWLINE
            + SUMMARY + NEWLINE
            + DETAIL_1 + NEWLINE
            + DETAIL_2 + NEWLINE
            + NEWLINE;
    public static final String PARTIAL_SIMULATED_INPUT = NAME + NEWLINE
            + SUMMARY + NEWLINE
            + DETAIL_1 + NEWLINE
            + DETAIL_2 + NEWLINE
            + NEWLINE;
}