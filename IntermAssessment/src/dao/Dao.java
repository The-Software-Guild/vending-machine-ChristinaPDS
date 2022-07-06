package dao;

import dto.Item;

import java.util.List;

public interface Dao {

    List<Item> getAllItems() throws PersistenceException;

    Item getItem(String id) throws PersistenceException;

    Item updateItemQuantity(String id, String itemQuantity) throws PersistenceException;
}
