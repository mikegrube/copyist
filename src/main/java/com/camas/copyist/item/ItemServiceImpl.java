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
import java.io.*;


@Service
public class ItemServiceImpl implements ItemService, ClipboardOwner {
	private static final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

	private ItemRepository repository;
	@Autowired
	public void setRepository(ItemRepository repository) {
		this.repository = repository;
	}

	private final int interval = 5;

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

		if (index >= 0) {
			StringSelection stringSelection = new StringSelection(getElementAt(index).getContent());
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, this);
//			runPaste();
		}
	}

	@Override
	public void renumber() {

		Iterable<Item> items = list();

		int next = 5;

		for (Item item : items) {
			item.setPosition(next);
			save(item);

			next += interval;
		}
	}

	private void runPaste() {

		String script = "tell application \"Safari\" to activate\n" +
				"delay 0.5\n" +
				"tell application \"System Events\"\n" +
				"tell process \"Safari\"\n" +
				"keystroke \"v\" using command down\n" +
				"end tell\n" +
				"end tell\n";
/*
		String script = "tell application \"Safari\"\n" +
				"activate\n" +
				"delay 0.5\n" +
				"" +
				"end tell\n";
*/
		String[] args = { "osascript", "-e", script };

		try {
			Runtime runtime = Runtime.getRuntime();
			ProcessBuilder processBuilder = new ProcessBuilder(args);
			processBuilder.redirectErrorStream(true);
			Process process = processBuilder.start();

			InputStream inputStream = process.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				log.warn(line);
			}
			bufferedReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
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
