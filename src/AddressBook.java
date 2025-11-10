import javax.swing.DefaultListModel;
import java.io.*;
import java.util.Objects;

public class AddressBook extends DefaultListModel<BuddyInfo> {

    public AddressBook() {
        super();
    }

    public void addBuddy(BuddyInfo buddy) {
        if (buddy != null) {
            addElement(buddy); // DefaultListModel handles notification to JList
        }
    }

    public void removeBuddy(int index) {
        if (index >= 0 && index < getSize()) {
            removeElementAt(index); // Notifies JList automatically
        }
    }

    public static AddressBook importAddressBook(String fileName) {
        AddressBook newBook = new AddressBook();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Delegate line parsing to the factory method in BuddyInfo
                BuddyInfo buddy = BuddyInfo.importBuddyInfo(line);
                if (buddy != null) {
                    newBook.addBuddy(buddy);
                }
            }
        } catch (IOException e) {
            System.err.println("Error importing address book from file " + fileName + ": " + e.getMessage());
            // Return the book with whatever buddies were successfully read, or empty book
        }
        return newBook;
    }


    public boolean save(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (int i = 0; i < getSize(); i++) {
                // Get the BuddyInfo object and use its custom toString()
                writer.println(getElementAt(i).toString());
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error saving address book to file " + fileName + ": " + e.getMessage());
            return false;
        }
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressBook)) return false;

        AddressBook other = (AddressBook) o;

        // Check if the size is the same
        if (this.getSize() != other.getSize()) {
            return false;
        }

        // Check each BuddyInfo object for equality in the same order
        for (int i = 0; i < this.getSize(); i++) {
            if (!Objects.equals(this.getElementAt(i), other.getElementAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        BuddyInfo buddy = new BuddyInfo("Tom", "Carleton", "613");
        AddressBook addressBook = new AddressBook();
        addressBook.addBuddy(buddy);
        addressBook.removeBuddy(0);
    }
}
