/**
 * This program is used to demonstrate behind the scene of RSA algorithm in generating key pair and signing signature .
 * Author: Viet Tran
 * Date created: April 15, 2023
 */

package com.viettran.rsa.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Sketch.fxml"));
			BorderPane root = (BorderPane) loader.load();

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			// Get the dimensions of the BorderPane element in the FXML file
			double width = root.getPrefWidth();
			double height = root.getPrefHeight();
			root.setPrefSize(width, height);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
