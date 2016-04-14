package cleancode.junit.ref01;

import junit.framework.Assert;

public class ComparisonCompactor {

    private static final String ELLIPSIS = "...";   // shows when  contextLength < diffIndexFromStart
    private static final String DELTA_END = "]";
    private static final String DELTA_START = "[";
    private int contextLength;
    private String expected;
    private String actual;
    private int diffIndexFromStart;       // index at which first character differs counting from start of expected string
    private int diffIndexFromEnd;    // index at which first character differs counting from the end of expected string

    public ComparisonCompactor(int contextLength, String expected, String actual) {
        this.contextLength = contextLength;
        this.expected = expected;
        this.actual = actual;
    }

    public String formatCompactedComparison(String message) {
        String compactExpected = expected;
        String compactActual = actual;

        if (shouldBeCompacted()) {
            findCommonPrefixAndSuffix();
            compactExpected = compact(expected);
            compactActual = compact(actual);
        }
        // System.out.print(diffIndexFromStart + "," + diffIndexFromEnd);
        return Assert.format(message, compactExpected, compactActual);
    }

    private boolean shouldBeCompacted() {
        return expected != null &&
                actual != null && !expected.equals(actual);
    }

    private void findCommonPrefixAndSuffix() {
        this.diffIndexFromStart = getDiffIndexFromStart();   // initializes diffIndexFromStart   - index at which first character differs
        this.diffIndexFromEnd = getDiffIndexFromEnd();
    }

    private int getDiffIndexFromEnd() {
        int diffIndexFromEnd = 0;

        // TODO: show meaning of suffixOverlapsPrefix() via name or clean logic
        for (; !suffixOverlapsPrefix(diffIndexFromEnd); diffIndexFromEnd++) {

            if (!expectedCharactersFromTailEquals(expected, actual, diffIndexFromEnd)) {
                break;
            }
        }
        return diffIndexFromEnd;
    }

    private boolean expectedCharactersFromTailEquals(String expected, String actual, int index) {
        char expectedCharacterFromEnd = getCharacterFromStringTail(expected, index);
        char actualCharacterFromEnd = getCharacterFromStringTail(actual, index);
        return expectedCharacterFromEnd == actualCharacterFromEnd;
    }


    private char getCharacterFromStringTail(String s, int i) {
        return s.charAt(s.length() - i - 1);
    }

    private boolean suffixOverlapsPrefix(int suffixIndex) {
        return actual.length() - suffixIndex <= diffIndexFromStart || expected.length() - suffixIndex <= diffIndexFromStart;
    }

    private int getDiffIndexFromStart() {
        int diffStartIndex = 0;
        int end = Math.min(expected.length(), actual.length());
        for (; diffStartIndex < end; diffStartIndex++) {
            if (expected.charAt(diffStartIndex) != actual.charAt(diffStartIndex))
                break;
        }
        return diffStartIndex;
    }

    private String compact(String s) {
        return new StringBuilder()
                .append(startingEllipsis())     // "..." or ""
                .append(startingContext())
                .append(DELTA_START)
                .append(delta(s))
                .append(DELTA_END)
                .append(endingContext())
                .append(endingEllipsis())
                .toString();
    }

    private String startingEllipsis() {
        return diffIndexFromStart > contextLength ? ELLIPSIS : "";
    }

    private String startingContext() {
        int contextStart = Math.max(0, diffIndexFromStart - contextLength);
        int contextEnd = diffIndexFromStart;
        return expected.substring(contextStart, contextEnd);
    }

    private String delta(String s) {
        int deltaStart = diffIndexFromStart;
        int deltaEnd = s.length() - diffIndexFromEnd;
        return s.substring(deltaStart, deltaEnd);
    }

    private String endingContext() {
        int contextStart = expected.length() - diffIndexFromEnd;
        int contextEnd = Math.min(contextStart + contextLength, expected.length());
        return expected.substring(contextStart, contextEnd);
    }

    private String endingEllipsis() {
        return (diffIndexFromEnd > contextLength ? ELLIPSIS : "");
    }
}