package com.viettran.rsa.view;

import com.viettran.rsa.model.RSA_Algorithm;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class SketchController {

	@FXML
	private Button CopyKey1;

	@FXML
	private Button CopyKey3;

	@FXML
	private Button copyKey2;

	@FXML
	private Button generateBtn;

	@FXML
	private TextField keyVal;

	@FXML
	private AnchorPane privateKey;

	@FXML
	private TextArea privateKeyToSign;

	@FXML
	private Text privateKeyVal;

	@FXML
	private ScrollPane publicKey;

	@FXML
	private TextArea publicKeyToVerify;

	@FXML
	private Text publicKeyVal;

	@FXML
	private Button sign;

	@FXML
	private TextField signature;

	@FXML
	private TextArea signatureToVerify;

	@FXML
	private TextArea textToSign;

	@FXML
	private TextArea textToVerify;

	@FXML
	private Button verify;

	@FXML
	public void initialize() {
		generateBtn.setOnAction(event -> {
			publicKeyVal.setText(RSA_Algorithm.generatePublicKey(keyVal.getText()));
			privateKeyVal.setText(RSA_Algorithm.generatePrivateKey(keyVal.getText()));
		});
		CopyKey1.setOnAction(event -> {
			copyAndGenerateMessage(publicKeyVal.getText());

		});
		copyKey2.setOnAction(event -> {
			copyAndGenerateMessage(privateKeyVal.getText());
		});
		sign.setOnAction(event -> {
			if (textToSign.getText().isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Empty text to sign");
				alert.showAndWait();
				return;
			} else if (privateKeyToSign.getText().isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Empty private key");
				alert.showAndWait();
				return;
			}
			try {
				String sign = RSA_Algorithm.sign(textToSign.getText(), privateKeyToSign.getText());
				signature.setText(sign);
				textToSign.setText("");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		CopyKey3.setOnAction(event -> {
			copyAndGenerateMessage(signature.getText());
		});
		verify.setOnAction(event -> {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			if (textToVerify.getText().isEmpty()) {
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Empty text to verify");
				alert.showAndWait();
				return;
			} else if (signatureToVerify.getText().isEmpty()) {
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Empty signature to verify");
				alert.showAndWait();
				return;
			} else if (publicKeyToVerify.getText().isEmpty()) {
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Empty public key to verify");
				alert.showAndWait();
				return;
			}
			alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("RSA signature verifying");
			alert.setHeaderText("Verify message");
			try {
				Boolean isVerified = RSA_Algorithm.verifyMessage(textToVerify.getText(), signatureToVerify.getText(),
						publicKeyToVerify.getText());
				alert.setContentText((isVerified == true ? "Your message is verified"
						: "Your message is not verified, please check your public key, your signature and your text again !"));
				alert.showAndWait();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

	public static void copyAndGenerateMessage(String messageVal) {
		Clipboard clipboard = Clipboard.getSystemClipboard();
		ClipboardContent content = new ClipboardContent();
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		if (messageVal.isEmpty()) {
			alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Empty content");
			alert.showAndWait();
		} else {
			content.putString(messageVal);
			clipboard.setContent(content);

			alert.setTitle("Nofification");
			alert.setHeaderText(null);
			alert.setContentText("Content copied to clipboard");

			alert.showAndWait();
		}
	}

}
