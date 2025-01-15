package controller.items;

import model.Item;

import java.util.List;

public interface ItemService {
    public boolean addItem(Item item);
    public boolean deleteItem(String code);
    public  boolean updateItem(Item item);
    public Item searchItem(String code);
    public List<Item> getAll();
}
