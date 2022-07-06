package dao;

import dto.Item;

import java.io.*;
import java.util.*;

public class DaoImpl implements Dao{
    public static final String VENDING_MACHINE_FILE = "/Users/christinapavlou/Downloads/VendingMachine/vendingMachineFile";
    public static final String DELIMITER = "::";

    private Map<String, Item> items = new HashMap<>();


    @Override
    public List<Item> getAllItems() throws PersistenceException {
        loadVendingMachine();
        return new ArrayList<Item>(items.values());
    }

    @Override
    public Item getItem(String id) throws PersistenceException {
        loadVendingMachine();
        return items.get(id);
    }

    @Override
    public Item updateItemQuantity(String id, String itemQuantity) throws PersistenceException {
        loadVendingMachine();
        Item item = items.get(id);
        item.setItemQuantity(itemQuantity);
        writeVendingMachine();
        return null;
    }

    private Item unmarshallItem(String itemAsText){

        String[] itemTokens = itemAsText.split(DELIMITER);

        String id = itemTokens[0];

        Item itemFromFile = new Item(id);

        itemFromFile.setItemName(itemTokens[1]);

        itemFromFile.setItemQuantity(itemTokens[2]);

        itemFromFile.setItemPrice(itemTokens[3]);

        return itemFromFile;
    }

    private void loadVendingMachine() throws PersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(VENDING_MACHINE_FILE)));
        } catch (FileNotFoundException e) {
            throw new PersistenceException(
                    "-_- Could not load DVD library data into memory.", e);
        }
        String currentLine;

        Item currentItem;

        while (scanner.hasNextLine()) {

            currentLine = scanner.nextLine();

            currentItem = unmarshallItem(currentLine);

            items.put(currentItem.getId(), currentItem);
        }

        scanner.close();
    }

    private String marshallItem(Item anItem){

        String itemAsText = anItem.getId() + DELIMITER;

        itemAsText += anItem.getItemName() + DELIMITER;

        itemAsText += anItem.getItemQuantity() + DELIMITER;

        itemAsText += anItem.getItemPrice() + DELIMITER;

        return itemAsText;
    }

    private void writeVendingMachine() throws PersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(VENDING_MACHINE_FILE));
        } catch (IOException e) {
            throw new PersistenceException(
                    "Could not save Item data.", e);
        }

        String itemAsText;
        List<Item> itemList = this.getAllItems();
        for (Item currentItem : itemList) {
            itemAsText = marshallItem(currentItem);
            out.println(itemAsText);
            out.flush();
        }
        out.close();
    }
}
