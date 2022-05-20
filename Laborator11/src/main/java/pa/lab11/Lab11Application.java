package pa.lab11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.util.Collections;

@SpringBootApplication
public class Lab11Application {

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(Lab11Application.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "1444"));
		app.run(args);
	}

}