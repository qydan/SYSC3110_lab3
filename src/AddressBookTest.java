import org.junit.Before;
import org.junit.Test;
import java.io.File;

import static org.junit.Assert.*;

/**
 * JUnit 4 test class for AddressBook export and import functionality.
 * This class assumes AddressBook.java and BuddyInfo.java have been updated
 * with the required file I/O methods.
 */
public class AddressBookTest {

    private final String TEST_FILENAME = "test_address_book.txt";

    /**
     * Cleans up the test file before each test run.
     */
    @Before
    public void setUp() {
        File file = new File(TEST_FILENAME);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Test case to cover tasks 6a, 6b, and 6c:
     * 1. Exports an AddressBook to a file.
     * 2. Creates an AddressBook by importing from the file created.
     * 3. Asserts that both AddressBooks contain the same data.
     */
    @Test
    public void testExportAndImportAddressBook() {
        // 1. Create a reference AddressBook and populate it
        AddressBook originalBook = new AddressBook();
        originalBook.addBuddy(new BuddyInfo("John Doe", "123 Main St", "555-0101"));
        originalBook.addBuddy(new BuddyInfo("Jane Smith", "456 Oak Ave", "555-0202"));
        originalBook.addBuddy(new BuddyInfo("Buddy The Third", "789 Elm Rd", "555-0303"));

        // 2. Export the AddressBook to a file (Task 6a)
        assertTrue("The address book should be saved successfully.", originalBook.save(TEST_FILENAME));
        File exportedFile = new File(TEST_FILENAME);
        assertTrue("The exported file should exist.", exportedFile.exists());

        // 3. Import the AddressBook from the file (Task 6b)
        AddressBook importedBook = AddressBook.importAddressBook(TEST_FILENAME);
        assertNotNull("Imported book should not be null.", importedBook);

        // 4. Assert that both AddressBooks contain the same data (Task 6c)
        assertEquals("The size of the imported book should match the original book.",
                originalBook.getSize(), importedBook.getSize());

        // Use the custom equals method in AddressBook
        assertTrue("The imported AddressBook should contain the exact same data as the original.",
                originalBook.equals(importedBook));
    }

    /**
     * Test to verify the static factory method BuddyInfo.importBuddyInfo()
     * works correctly with the custom delimiter. (Task 4)
     */
    @Test
    public void testImportBuddyInfoFactoryMethod() {
        String dataLine = "Mr. Buddy#111 Fake Street#613-555-5555";

        BuddyInfo buddy = BuddyInfo.importBuddyInfo(dataLine);
        assertNotNull("The imported BuddyInfo object should not be null.", buddy);

        // Assert the properties were parsed correctly
        assertEquals("Mr. Buddy", buddy.getName());
        assertEquals("111 Fake Street", buddy.getAddress());
        assertEquals("613-555-5555", buddy.getPhoneNumber());

        // Assert that the object can be converted back to the original string
        assertEquals(dataLine, buddy.toString());
    }
}