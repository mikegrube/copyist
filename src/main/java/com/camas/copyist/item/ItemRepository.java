package com.camas.copyist.item;

import org.springframework.data.repository.CrudRepository;

interface ItemRepository extends CrudRepository<Item, Long> {

	Iterable<Item> findAllByOrderByPosition();
}

