package com.camas.copyist;

import com.camas.copyist.item.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
@EnableJpaRepositories
public class Copyist implements ApplicationContextAware {
	private static final Logger log = LoggerFactory.getLogger(Copyist.class);

	private static JFrame frame = null;
	private static ApplicationContext applicationContext;

	public static void main(String[] args) {

		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Copyist.class)
				.headless(false).run(args);
		//FIXME: Figure out how not to create extra context (or whatever it is)

		EventQueue.invokeLater(() -> {

			Copyist ex = ctx.getBean(Copyist.class);
			ex.start();
		});
	}

	public Copyist() {

	}

	public void start() {

		frame = new JFrame("Copyist");
		frame.setContentPane(new Main().pnlMain);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(new Dimension(600,800));
		frame.setVisible(true);
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		applicationContext = context;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
