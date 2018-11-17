package com.camas.copyist.item;


import javax.swing.*;

public interface ItemService  extends ListModel<Item> {

	Iterable<Item> list();

	Item get(Long id);

	Item save(Item item);

	void delete(Long id);

	void select(int index);
}
