package univ.evector.assertion;

import java.util.Collection;
import java.util.List;

import org.junit.Assert;

public class CommonAssertionHelper {

    public final static int INT_0 = 0;
    public final static int INT_1 = 1;
    public final static String EMPTY_STR = "";

    public static <E> void assertHasMoreThanOneItem(Collection<E> collection) {
        int size = INT_0;

        if (collection != null) {
            size = collection.size();
        }

        if (size <= INT_1) {
            Assert.fail("Collection is EMPTY|NULL valued.");
        }
    }

    public static void assertEquals(String value1, String value2) {
        Assert.assertEquals(value1, value2);
    }

    // FIXME OLEG LUKAŠONOK -> Ask for suppressing issue.
    @SuppressWarnings("unchecked")
    public static <E> void assertHasAtLeadtOneNullItem(E... values) {
        boolean hasValue = false;
        if (values != null) {
            for (E value : values) {
                if (value == null) {
                    hasValue = true;
                    break;
                }
            }
        }

        if (!hasValue) {
            Assert.fail("All input values are not NULL valued.");
        }
    }

    @SuppressWarnings("unchecked")
    public static <E> void assertHasAtLeastOneItem(E... values) {
        boolean hasValue = false;
        if (values != null) {
            for (E value : values) {
                if (value != null) {
                    hasValue = true;
                    break;
                }
            }
        }

        if (!hasValue) {
            Assert.fail("All input values have NULL value.");
        }

    }

    public static <E> void assertHasAtLeastOneItem(Collection<E> collection) {
        int size = INT_0;
        if (collection != null) {
            size = collection.size();
        }

        if (size <= INT_0) {
            Assert.fail("Collection is EMPTY|NULL valued.");
        }
    }

    public static <E> void assertValueIsNotNull(E value) {
        if (value == null) {
            Assert.fail("Input variable value has NULL value.");
        }
    }

    public static <E> void assertValueIsNull(E value) {
        if (value != null) {
            Assert.fail("Input variable value does not have NULL value.");
        }
    }

    public static <E> void assertListAndItemsAreNotNull(List<E> items) {
        assertValueIsNotNull(items);
        for (E item : items) {
            assertValueIsNotNull(item);
        }
    }

    public static <E> void assertCollectionIsEmpty(List<E> items) {
        if (items != null && !items.isEmpty()) {
            Assert.fail("Input variable items is not EMPTY|NULL.");
        }

    }

    public static void assertStringNotNullOrEmpty(String str) {
        if (str == null) {
            Assert.fail("Test String has null value");
        } else {
            Assert.assertNotEquals(str, EMPTY_STR);
        }
    }

}
