package ui;
import dao.PersistenceException;
import dto.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import dao.Dao;
public class View {
    private UserIO io;
    private Dao dao;

    public View(UserIO io, Dao dao) {

        this.io = io;
        this.dao = dao;
    }

    public int getMenuSelection() throws PersistenceException {
        io.print("1. Continue with next purchase");
        io.print("2. Exit");

        return io.readInt("Please select an option from the above choices.", 1, 2);
    }

    public void printMenu() throws PersistenceException{
        io.print("Main Menu");
        displayItemList(dao.getAllItems());
    }

    public BigDecimal getMoneyEntered(){
        return io.readBigDecimal("Please enter amount");
    }


    public void displayItemList(List<Item> itemList) {
        for (Item currentItem : itemList) {
            String itemInfo = String.format("#%s : %s %s",
                    currentItem.getId(),
                    currentItem.getItemName(),
                    currentItem.getItemPrice());
            io.print(itemInfo);
        }
        io.readString("Please hit enter to continue.", false);
    }

    public void displayChangeCoins(Map<BigDecimal, BigDecimal> changeCoins){
        if (changeCoins.isEmpty()){
            io.print("No change");
            }
        io.print("====== Returned Coins ======");
        for ( BigDecimal key: changeCoins.keySet()) {
            io.print(key + ": " + changeCoins.get(key));
        }
    }

    public void displayEnterMoneyBanner(){
        io.print("Coins accepted: ");
        io.print("5, 10, 20, 50, 100");
        io.print("Please enter amount in coins");
    }

    public void displayDisplayAllBanner() {
        io.print("=== Display All Items ===");
    }

    public void displayDisplayItemBanner() {
        io.print("=== Display Item ===");
    }

    public String getItemIdChoice() {
        return io.readString("Please enter Item id", true);
    }

    public BigDecimal userMoneyInput(){
        return io.readBigDecimal("Please enter amount");
    }

    public void displayItem(Item item) {
        if (item != null) {
            io.print(item.getId());
            io.print(item.getItemName());
            io.print(item.getItemQuantity());
            io.print(item.getItemPrice());
            io.print("");
        } else {
            io.print("No such Item.");
        }
        io.readString("Please hit enter to continue.", false);
    }


    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }

    public void displayReturnedChangeBanner(){
        io.print("====== Change returned: ========");
    }

    public void displaySelectItemIdFromMenu(){
        io.print("===== Please enter item number =====");
    }

    }

