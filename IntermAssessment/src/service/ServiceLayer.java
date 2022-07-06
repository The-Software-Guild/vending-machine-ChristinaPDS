package service;

import dao.PersistenceException;
import dto.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ServiceLayer {

    int decrementItemInventoryQty(String itemId) throws NoItemInventoryException, PersistenceException;

    List<Item> getAllItems() throws PersistenceException;

    BigDecimal calculateReturnedChange(BigDecimal userMoneyBalance, Item item) throws InsufficientFundsException;


    BigDecimal changeDueInPennies(BigDecimal itemCost, BigDecimal money);
    Map<BigDecimal, BigDecimal> changeDuePerCoin(BigDecimal itemCost, BigDecimal money);
}
