import javax.swing.*;
import java.awt.event.*;

public class AddressBookGUI extends JFrame {
    private AddressBook addressBook;
    private JList<BuddyInfo> buddyList;

    public AddressBookGUI() {
        super("Address Book");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

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

        newBook.addActionListener(e -> addressBook.clear());
        exit.addActionListener(e -> System.exit(0));

        bookMenu.add(newBook);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddressBookGUI::new);
    }
}
