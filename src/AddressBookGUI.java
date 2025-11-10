import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class AddressBookGUI extends JFrame {
    private AddressBook addressBook;
    private JList<BuddyInfo> buddyList;

    public AddressBookGUI() {
        super("Address Book");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // Initialize with an empty book
        addressBook = new AddressBook();
        buddyList = new JList<>(addressBook);
        add(new JScrollPane(buddyList));

        createMenuBar();
        setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // AddressBook menu
        JMenu bookMenu = new JMenu("AddressBook");
        JMenuItem newBook = new JMenuItem("New");
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem exportBook = new JMenuItem("Export"); // New
        JMenuItem importBook = new JMenuItem("Import"); // New

        newBook.addActionListener(e -> addressBook.clear());
        exit.addActionListener(e -> System.exit(0));
        exportBook.addActionListener(e -> exportAddressBook()); // New listener
        importBook.addActionListener(e -> importAddressBook()); // New listener

        bookMenu.add(newBook);
        bookMenu.addSeparator(); // Separator for I/O functions
        bookMenu.add(exportBook);
        bookMenu.add(importBook);
        bookMenu.addSeparator();
        bookMenu.add(exit);

        // Buddy menu
        JMenu buddyMenu = new JMenu("Buddy");
        JMenuItem addBuddy = new JMenuItem("Add Buddy");
        JMenuItem removeBuddy = new JMenuItem("Remove Buddy");

        addBuddy.addActionListener(e -> addBuddy());
        removeBuddy.addActionListener(e -> removeBuddy());

        buddyMenu.add(addBuddy);
        buddyMenu.add(removeBuddy);

        menuBar.add(bookMenu);
        menuBar.add(buddyMenu);
        setJMenuBar(menuBar);
    }

    private void addBuddy() {
        String name = JOptionPane.showInputDialog(this, "Enter name:");
        if (name == null || name.isEmpty()) return;

        String address = JOptionPane.showInputDialog(this, "Enter address:");
        if (address == null) return;

        String phone = JOptionPane.showInputDialog(this, "Enter phone number:");
        if (phone == null) return;

        BuddyInfo buddy = new BuddyInfo(name, address, phone);
        addressBook.addBuddy(buddy);
    }

    private void removeBuddy() {
        int selected = buddyList.getSelectedIndex();
        if (selected >= 0) {
            addressBook.removeBuddy(selected);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a buddy to remove.");
        }
    }

    /**
     * Prompts the user for a file name and saves the current AddressBook. (Task 3)
     */
    private void exportAddressBook() {
        String fileName = JOptionPane.showInputDialog(this, "Enter file name to export to:");
        if (fileName == null || fileName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Export cancelled or file name empty.", "Export Failed", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (addressBook.save(fileName)) {
            JOptionPane.showMessageDialog(this, "Address Book successfully exported to " + fileName, "Export Successful", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to export Address Book to " + fileName, "Export Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Prompts the user for a file name and imports an AddressBook, replacing the current one. (Task 7)
     */
    private void importAddressBook() {
        String fileName = JOptionPane.showInputDialog(this, "Enter file name to import from:");
        if (fileName == null || fileName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Import cancelled or file name empty.", "Import Failed", JOptionPane.WARNING_MESSAGE);
            return;
        }

        File f = new File(fileName);
        if (!f.exists()) {
            JOptionPane.showMessageDialog(this, "File not found: " + fileName, "Import Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        AddressBook importedBook = AddressBook.importAddressBook(fileName);

        if (importedBook.getSize() > 0) {
            // Replace the current addressBook instance
            this.addressBook = importedBook;

            // Update the JList to reflect the new model
            buddyList.setModel(this.addressBook);

            JOptionPane.showMessageDialog(this, "Address Book successfully imported from " + fileName + " (" + importedBook.getSize() + " buddies imported).", "Import Successful", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Even if the file was found, if no buddies were imported, it's an issue or an empty file.
            JOptionPane.showMessageDialog(this, "Failed to import Address Book from " + fileName + ". The file may be empty or corrupted.", "Import Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddressBookGUI::new);
    }
}