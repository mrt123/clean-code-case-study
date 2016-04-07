package cleancode.junit.ref01;

import junit.framework.TestCase;

public class ComparisonCompactorTest extends TestCase {

    public void testPrefixes() {    // prefix = 1 , suffix = 1
        String failure = new ComparisonCompactor(1, "012345", "019395").formatCompactedComparison(null);
        assertEquals("expected:<...1[234]5> but was:<...1[939]5>", failure);
    }

    public void testMessage() {  // prefix = 0 , suffix = 0
        String failure = new ComparisonCompactor(0, "b", "c").formatCompactedComparison("a");
        assertTrue("a expected:<[b]> but was:<[c]>".equals(failure));
    }

    public void testStartSame() {    // prefix = 1 , suffix = 0
        String failure = new ComparisonCompactor(1, "ba", "bc").formatCompactedComparison(null);
        assertEquals("expected:<b[a]> but was:<b[c]>", failure);
    }

    public void testEndSame() {   // prefix = 0 , suffix = 1
        String failure = new ComparisonCompactor(1, "ab", "cb").formatCompactedComparison(null);
        assertEquals("expected:<[a]b> but was:<[c]b>", failure);
    }

    public void testSame() {   // prefix = 0 , suffix = 0
        String failure = new ComparisonCompactor(1, "ab", "ab").formatCompactedComparison(null);
        assertEquals("expected:<ab> but was:<ab>", failure);
    }

    public void testNoContextStartAndEndSame() {    // prefix = 1 , suffix = 1
        String failure = new ComparisonCompactor(0, "abc", "adc").formatCompactedComparison(null);
        assertEquals("expected:<...[b]...> but was:<...[d]...>", failure);
    }

    public void testStartAndEndContext() {    // prefix = 1 , suffix = 1
        String failure = new ComparisonCompactor(1, "abc", "adc").formatCompactedComparison(null);
        assertEquals("expected:<a[b]c> but was:<a[d]c>", failure);
    }

    public void testStartAndEndContext2() {    // prefix = 1 , suffix = 1
        String failure = new ComparisonCompactor(3, "abc", "adc").formatCompactedComparison(null);
        assertEquals("expected:<a[b]c> but was:<a[d]c>", failure);
    }

    public void testStartAndEndContextWithEllipses() {    // prefix = 2 , suffix = 2
        String failure =
                new ComparisonCompactor(1, "abcde", "abfde").formatCompactedComparison(null);
        assertEquals("expected:<...b[c]d...> but was:<...b[f]d...>", failure);
    }

    public void testComparisonErrorStartSameComplete() {    // prefix = 2 , suffix = 0
        String failure = new ComparisonCompactor(2, "ab", "abc").formatCompactedComparison(null);
        assertEquals("expected:<ab[]> but was:<ab[c]>", failure);
    }

    public void testComparisonErrorStartSameComplete0() {    // prefix = 2 , suffix = 0
        String failure = new ComparisonCompactor(0, "ab", "abc").formatCompactedComparison(null);
        assertEquals("expected:<...[]> but was:<...[c]>", failure);
    }

    public void testComparisonErrorEndSameComplete() {    // prefix = 0 , suffix = 2
        String failure = new ComparisonCompactor(0, "bc", "abc").formatCompactedComparison(null);
        assertEquals("expected:<[]...> but was:<[a]...>", failure);
    }

    public void testComparisonErrorEndSameCompleteContext() {  // prefix = 0 , suffix = 2
        String failure = new ComparisonCompactor(2, "bc", "abc").formatCompactedComparison(null);
        assertEquals("expected:<[]bc> but was:<[a]bc>", failure);
    }

    public void testComparisonErrorOverlapingMatches() {    // prefix = 2 , suffix = 1  xxxxxx
        String failure = new ComparisonCompactor(0, "abc", "abbc").formatCompactedComparison(null);
        assertEquals("expected:<...[]...> but was:<...[b]...>", failure);
    }

    public void testComparisonErrorOverlapingMatchesContext() {  // prefix = 2 , suffix = 1
        String failure = new ComparisonCompactor(2, "abc", "abbc").formatCompactedComparison(null);
        assertEquals("expected:<ab[]c> but was:<ab[b]c>", failure);
    }

    public void testComparisonErrorOverlapingMatches2() {
        String failure = new ComparisonCompactor(0, "abcdde",
                "abcde").formatCompactedComparison(null);
        assertEquals("expected:<...[d]...> but was:<...[]...>", failure);
    }

    public void testComparisonErrorOverlapingMatches2Context() {
        String failure =
                new ComparisonCompactor(2, "abcdde", "abcde").formatCompactedComparison(null);
        assertEquals("expected:<...cd[d]e> but was:<...cd[]e>", failure);
    }

    public void testComparisonErrorWithActualNull() {
        String failure = new ComparisonCompactor(0, "a", null).formatCompactedComparison(null);
        assertEquals("expected:<a> but was:<null>", failure);
    }

    public void testComparisonErrorWithActualNullContext() {
        String failure = new ComparisonCompactor(2, "a", null).formatCompactedComparison(null);
        assertEquals("expected:<a> but was:<null>", failure);
    }

    public void testComparisonErrorWithExpectedNull() {
        String failure = new ComparisonCompactor(0, null, "a").formatCompactedComparison(null);
        assertEquals("expected:<null> but was:<a>", failure);
    }

    public void testComparisonErrorWithExpectedNullContext() {
        String failure = new ComparisonCompactor(2, null, "a").formatCompactedComparison(null);
        assertEquals("expected:<null> but was:<a>", failure);
    }

    public void testBug609972() {
        String failure = new ComparisonCompactor(10, "S&P500", "0").formatCompactedComparison(null);
        assertEquals("expected:<[S&P50]0> but was:<[]0>", failure);
    }
}