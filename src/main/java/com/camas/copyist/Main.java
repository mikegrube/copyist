package com.camas.copyist;

import com.camas.copyist.item.Item;
import com.camas.copyist.item.ItemService;
import com.camas.copyist.item.ItemServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public JPanel pnlMain;
	private JPanel pnlList;
	private JList lstItems;
	private JPanel pnlButtons;
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnDelete;

	private ItemService itemService;

	public Main() {

		itemService = (ItemService) Copyist.getApplicationContext().getBean(ItemServiceImpl.class);

		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onAdd();
			}
		});

		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onEdit();
			}
		});

		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onDelete();
			}
		});

		populateList();

		ListSelectionModel selectionModel = lstItems.getSelectionModel();
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {

				ListSelectionModel lsm = (ListSelectionModel)e.getSource();

				// Find out which indexes are selected.
				int index = lsm.getMinSelectionIndex();

				itemService.select(index);

			}
		});

		log.info("Finished initialization");
	}

	private void populateList() {

		lstItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstItems.setModel(itemService);

	}

	private void onDelete() {

		if (lstItems.getSelectedValue() != null) {
			Item item = (Item) lstItems.getSelectedValue();
			itemService.delete(item.getId());
			lstItems.setModel(itemService);
			lstItems.updateUI();
		}
	}

	private void onEdit() {

		if (lstItems.getSelectedValue() != null) {
			Item item = (Item) lstItems.getSelectedValue();
			editItem(item);
		}
	}

	private void onAdd() {

		Item item = new Item();
		editItem(item);
	}

	private void editItem(Item item) {

		ItemDialog dialog = new ItemDialog(item);

		dialog.txtName.setText(item.getName());
		dialog.txtPosition.setText(String.valueOf(item.getPosition()));
		dialog.txtContent.setText(item.getContent());

		dialog.setLocationRelativeTo(this.pnlList);
		dialog.setPreferredSize(new Dimension(300, 300));
		dialog.pack();
		dialog.setVisible(true);

		// Returns here after modal set invisible

		item.setName(dialog.txtName.getText());
		item.setPosition(Integer.parseInt(dialog.txtPosition.getText()));
		item.setContent(dialog.txtContent.getText());

		item = itemService.save(item);

		lstItems.setModel(itemService);
		lstItems.updateUI();

	}
}
