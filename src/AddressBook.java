import javax.swing.DefaultListModel;

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

    public static void main(String[] args) {
        BuddyInfo buddy = new BuddyInfo("Tom", "Carleton", "613");
        AddressBook addressBook = new AddressBook();
        addressBook.addBuddy(buddy);
        addressBook.removeBuddy(0);
    }
}
