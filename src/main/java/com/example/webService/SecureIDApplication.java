package com.example.webService;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootApplication
@EnableScheduling
public class SecureIDApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecureIDApplication.class, args);
	}

	@Autowired
	private DataSource dataSource;

	@PostConstruct
	public void createDatabaseIfNotExist() throws SQLException {
		try (Connection conn = dataSource.getConnection()) {
			// Verificar si la base de datos ya existe
			ResultSet resultSet = conn.getMetaData().getCatalogs();
			boolean databaseExists = false;
			while (resultSet.next()) {
				String databaseName = resultSet.getString(1);
				if ("secureid".equalsIgnoreCase(databaseName)) {
					databaseExists = true;
					break;
				}
			}
			resultSet.close();

			// Si la base de datos no existe, crearla
			if (!databaseExists) {
				conn.createStatement().execute("CREATE DATABASE secureid");
			}
		}
	}

}
