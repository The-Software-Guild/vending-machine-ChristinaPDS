package controller;

import dao.Dao;
import dao.PersistenceException;
import dto.Item;
import service.InsufficientFundsException;
import service.NoItemInventoryException;
import ui.View;
import service.ServiceLayer;

import java.math.BigDecimal;
import java.util.List;

public class Controller {
    private View view;
    private Dao dao;

    private ServiceLayer serviceLayer;

    public Controller(Dao dao, View view, ServiceLayer serviceLayer) {
        this.dao = dao;
        this.view = view;
        this.serviceLayer = serviceLayer;
    }
    public void run() throws PersistenceException, InsufficientFundsException {
        boolean keepGoing = true;
        int menuSelection = 0;
        view.printMenu();
        BigDecimal userMoney = getUserMoney();
        try {
            while (keepGoing) {
                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        String id = getItemIdFromMenu();
                        view.displayReturnedChangeBanner();
                        BigDecimal userChangeAmount = getUserChange(userMoney, id);
                        String itemPrice = dao.getItem(id).getItemPrice();
                        BigDecimal BGItemPrice = new BigDecimal(itemPrice);
                        view.displayChangeCoins(serviceLayer.changeDuePerCoin(BGItemPrice, userMoney));
                        int updatedQuantity = serviceLayer.decrementItemInventoryQty(id);
                        dao.updateItemQuantity(id, String.valueOf(updatedQuantity));
                        break;
                    case 2:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }

            exitMessage();
        }catch (PersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        } catch (NoItemInventoryException e) {
            throw new RuntimeException(e);
        }

    }


    private int getMenuSelection() throws PersistenceException{
        return view.getMenuSelection();
    }



    private void listItems() throws PersistenceException {
        view.displayDisplayAllBanner();
        List<Item> itemList = dao.getAllItems();
        view.displayItemList(itemList);
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }



    private String getItemIdFromMenu(){
        view.displaySelectItemIdFromMenu();
        return view.getItemIdChoice();
    }

    private BigDecimal getUserMoney(){
        return view.userMoneyInput();
    }

    private BigDecimal getUserChange(BigDecimal userMoney, String id) throws PersistenceException, InsufficientFundsException {
        Item item = dao.getItem(id);
        return serviceLayer.calculateReturnedChange(userMoney, item);
    }

    private void displayUserChange(BigDecimal userMoney, String id) throws PersistenceException, InsufficientFundsException {
        System.out.println(getUserChange(userMoney,id));
    }

}
