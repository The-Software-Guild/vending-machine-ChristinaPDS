package service;

import dao.Dao;
import dao.PersistenceException;
import dto.Coins;
import dto.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ui.View;

public class ServiceLayerImpl implements ServiceLayer {
    Dao dao;
    View view;

    public ServiceLayerImpl(Dao dao, View view) {
        this.dao = dao;
        this.view = view;
    }

    @Override
    public List<Item> getAllItems() throws
            PersistenceException {
        return dao.getAllItems();
    }

//    @Override
//    public BigDecimal calculateMoneyBalance(String coin ) throws InsufficientFundsException, PersistenceException {
//        BigDecimal balance = new BigDecimal(0);
//
//        BigDecimal total = view.getMoneyEntered();
//        String totalString = total.toString();
//
//        boolean keepGoing = true;
//
//        while(keepGoing){
//            switch (totalString) {
//                case "FIVE":
//                    BigDecimal coinValue = new BigDecimal(Coins.FIVE.getValue());
//                    balance = balance.add(coinValue);
//                    break;
//                case "TEN":
//                    coinValue = new BigDecimal(Coins.TEN.getValue());
//                    balance = balance.add(coinValue);
//                    break;
//                case "TWENTY":
//                    coinValue = new BigDecimal(Coins.TWENTY.getValue());
//                    balance = balance.add(coinValue);
//                    break;
//                case "FIFTY":
//                    coinValue = new BigDecimal(Coins.FIFTY.getValue());
//                    balance = balance.add(coinValue);
//                    break;
//                case "ONE_HUNDRED":
//                    coinValue = new BigDecimal(Coins.ONE_HUNDRED.getValue());
//                    balance = balance.add(coinValue);
//                    break;
//                default:
//                    System.out.println("Wrong coins");
//                    break;
//            }
//        }
//        return balance;
//    }

    @Override
    public int decrementItemInventoryQty(String itemId) throws NoItemInventoryException, PersistenceException {
        Item item = dao.getItem(itemId);
        int currentItemQuantity = Integer.parseInt(item.getItemQuantity());
        if (currentItemQuantity == 0) {
            String itemName = item.getItemName();
            System.out.println("Insufficient quantity: " + itemName + " not in stock");
            return 0;
        }
        currentItemQuantity = currentItemQuantity - 1;
        return currentItemQuantity;
    }

    @Override
    public BigDecimal calculateReturnedChange(BigDecimal userMoneyBalance, Item item) throws InsufficientFundsException {
        BigDecimal price = new BigDecimal(item.getItemPrice());
        if (userMoneyBalance.compareTo(price) == -1) {
            throw new InsufficientFundsException("You have not entered enough coins." + "The item price is: £ " + price);
        }
        return userMoneyBalance.subtract(price);
    }
    //  @Override
//    public Map<String, Integer> getReturnedCoins(BigDecimal changeAmount){
//       // int change = changeAmount.intValue() * 100 ;
//        BigDecimal change = changeAmount.multiply(new BigDecimal("100"));
//        change = changeAmount.divide(new BigDecimal("100"), 2 , RoundingMode.HALF_UP);
//
//        //BigDecimal changeDueInPennies = (money.subtract(itemCost)).multiply(new BigDecimal("100"));
//        //        System.out.println("Change due: $" + (changeDueInPennies.divide(new BigDecimal("100"),2,RoundingMode.HALF_UP).toString()));
//
//        Coins[] coinEnumArray = Coins.values();
//        ArrayList<String> coinStringList = new ArrayList<>();
//        for (Coins coin : coinEnumArray) {
//            coinStringList.add(coin.toString());
//        }
//        ArrayList<BigDecimal> coins = new ArrayList<BigDecimal>();
//        for (String coin:coinStringList) {
//            coins.add(new BigDecimal(coin));
//        }
//
//        Map<String,Integer> changeCoins = new HashMap<>();
//        changeCoins.put("100", 0);
//        changeCoins.put("50",0);
//        changeCoins.put("20",0);
//        changeCoins.put("10", 0);
//        changeCoins.put("5",0);
//        changeCoins.put("1",0);
//        for (BigDecimal coin : coins) {
//            if (changeAmount.compareTo(coin) >= 0){
//                Map<String,Integer> emptyChangeCoins = new HashMap<>();
//               emptyChangeCoins.put("100", 0);
//                emptyChangeCoins.put("50",0);
//                emptyChangeCoins.put("20",0);
//                emptyChangeCoins.put("10",0);
//                emptyChangeCoins.put("5",0);
//               emptyChangeCoins.put("1",0);
//               return emptyChangeCoins;
//          }
//           //boolean isPositive = true;
//          outerloop:
//          for (String key : changeCoins.keySet()){
//             System.out.println("Inside the loop");
//
////            if (!isPositive){
////                continue;
////            }
//              while (true) {
//                  System.out.println("Inside the while loop");
//                  change = change.subtract(BigDecimal.valueOf(Integer.parseInt(key)));
//                    int currentCoinsQty = changeCoins.get(key);
//                  changeCoins.put(key, currentCoinsQty + 1 );
//                  System.out.println("current change: " + currentCoinsQty);
//                  System.out.println(changeCoins.get(key));
//                 if (changeAmount.compareTo(coin) < 0){
//                      continue outerloop;
//                  }
//              }
//
//        }
//
//    }
//        return changeCoins;

    @Override
    public BigDecimal changeDueInPennies(BigDecimal itemCost, BigDecimal money) {
        BigDecimal changeDueInPennies = (money.subtract(itemCost)).multiply(new BigDecimal("100"));
        System.out.println("Change due: £" + (changeDueInPennies.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP).toString()));
        return changeDueInPennies;
    }
    @Override
    public Map<BigDecimal, BigDecimal> changeDuePerCoin(BigDecimal itemCost, BigDecimal money) {
        Coins[] coinEnumArray = Coins.values();
        ArrayList<String> coinStringList = new ArrayList<>();
        for (Coins coin : coinEnumArray) {
            int coinInt = coin.getValue();
            coinStringList.add(String.valueOf(coinInt));
        }

        ArrayList<BigDecimal> coins = new ArrayList<BigDecimal>();
        for (String coin : coinStringList) {
            coins.add(new BigDecimal(coin));
        }

        BigDecimal changeDueInPennies = changeDueInPennies(itemCost, money);

        BigDecimal noOfCoin;
        BigDecimal zero = new BigDecimal("0");

        Map<BigDecimal, BigDecimal> amountPerCoin = new HashMap<>();


        for (BigDecimal coin : coins) {

            if (changeDueInPennies.compareTo(coin) >= 0) {

                if (!changeDueInPennies.remainder(coin).equals(zero)) {

                    noOfCoin = changeDueInPennies.divide(coin, 0, RoundingMode.DOWN);

                    amountPerCoin.put(coin, noOfCoin);

                    changeDueInPennies = changeDueInPennies.remainder(coin);

                    if (changeDueInPennies.compareTo(zero) < 0) {
                        break;
                    }

                } else if (changeDueInPennies.remainder(coin).equals(zero)) {  //could change to just else
                    noOfCoin = changeDueInPennies.divide(coin, 0, RoundingMode.DOWN);
                    amountPerCoin.put(coin, noOfCoin);

                    if ((changeDueInPennies.compareTo(zero)) < 0) {
                        break;
                    }
                }
            } else {

            }
        }
        return amountPerCoin;
    }
}
