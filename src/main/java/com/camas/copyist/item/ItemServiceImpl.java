package com.camas.copyist.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;


@Service
public class ItemServiceImpl implements ItemService, ClipboardOwner {
	private static final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

	private ItemRepository repository;

	@Autowired
	public void setRepository(ItemRepository repository) {
		this.repository = repository;
	}

	@Override
	public Iterable<Item> list() {
		return repository.findAllByOrderByPosition();
	}

	@Override
	public Item get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public Item save(Item item) {
		return repository.save(item);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public void select(int index) {

		StringSelection stringSelection = new StringSelection(getElementAt(index).getContent());
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, this);

	}

	@Override
	public int getSize() {

		int ct = 0;
		for (Item item : list()) {
			ct++;
		}
		return ct;

	}

	@Override
	public Item getElementAt(int index) {

		int ct = 0;
		for (Item item : list()) {
			if (ct++ == index) {
				return item;
			}
		}
		return null;
	}

	@Override
	public void addListDataListener(ListDataListener l) {

	}

	@Override
	public void removeListDataListener(ListDataListener l) {

	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {

	}
}
