/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monopoly.view.guiView.connection;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * FXML Controller class
 *
 * @author Ruslan
 */
public class ClientServerConnectionController implements Initializable 
{
    @FXML
    private Label statusLabel, alertLabel;

    @FXML
    private Button changeServerButton, nextSceneButton;

    @FXML
    private TextField serverIP, serverPort;

    private String serverIpTxt, serverPortTxt;
    private boolean isInViewOnlyMode = true;
    private NextListener nextListener;

    private static final String DEFAULT_SERVER = "localhost";
    private static final String DEFAULT_PORT = "8080";

    @FXML
    private void onNextSceneButtonClicked() {
        if (isInViewOnlyMode) {
            nextListener.onNextButtonPressed();
        } else {
            setServerParameters();
            changeButtonsToViewMode();
        }
    }

    @FXML
    private void onChangeServerButtonClicked() {
        if (isInViewOnlyMode) {
            isInViewOnlyMode = false;
            changeButtonsToEditMode();
        } else //in edit mode 
         if (saveConfigurationSettingsToFile()) {
                changeButtonsToViewMode();
                isInViewOnlyMode = true;
            }
    }

    public void setNextListener(NextListener nextListener) {
        this.nextListener = nextListener;
    }

    private void changeButtonsToEditMode() {
        serverIP.setEditable(true);
        serverPort.setEditable(true);
        changeServerButton.setText("Save");
        nextSceneButton.setText("Cancel");
    }

    private void changeButtonsToViewMode() {
        serverIP.setEditable(false);
        serverPort.setEditable(false);
        changeServerButton.setText("Change IP/Port");
        nextSceneButton.setText("Next");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        changeServerButton.setId("changeServerButton");
        nextSceneButton.setId("nextSceneButton");

        if (isServerConfigFileExists()) {
            fetchDataFromExistingXML();
        } else {
            createDefaultConfigurationXML();
            fetchDataFromExistingXML();
        }
    }

    private void fetchDataFromExistingXML() throws DOMException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse("serverConfiguration.xml");
            serverIpTxt = doc.getElementsByTagName("serverIp").item(0).getTextContent();
            serverPortTxt = doc.getElementsByTagName("port").item(0).getTextContent();
            setServerParameters();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ClientServerConnectionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(ClientServerConnectionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientServerConnectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean isServerConfigFileExists() {
        File file = new File("serverConfiguration.xml");

        boolean exists = file.exists();
        System.out.println("Temp file exists : " + exists);
        return exists;
    }

    private void createDefaultConfigurationXML() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder;

        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element rootElement = doc.createElement("ServerConfiguraion");
            doc.appendChild(rootElement);

            Element serverIp = doc.createElement("serverIp");
            serverIp.appendChild(doc.createTextNode(DEFAULT_SERVER));

            Element serverPort = doc.createElement("port");
            serverPort.appendChild(doc.createTextNode(DEFAULT_PORT));

            rootElement.appendChild(serverIp);
            rootElement.appendChild(serverPort);

            writeToXmlFile(doc);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ClientServerConnectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writeToXmlFile(Document doc) {
        TransformerFactory transformerFactory
                = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result
                    = new StreamResult(new File("serverConfiguration.xml"));
            transformer.transform(source, result);
            // Output to console for testing
            StreamResult consoleResult
                    = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(ClientServerConnectionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(ClientServerConnectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setServerParameters() {
        serverIP.setText(serverIpTxt);
        serverPort.setText(serverPortTxt);
        serverIP.setEditable(false);
        serverPort.setEditable(false);
        isInViewOnlyMode = true;
    }

    private boolean saveConfigurationSettingsToFile() {
        boolean isSuccesseded = true;
        if (!serverIP.getText().isEmpty() && !serverPort.getText().isEmpty()) {
            if (isInteger(serverPort.getText())) {
                serverIpTxt = serverIP.getText();
                serverPortTxt = serverPort.getText();
                updateCurrentXML();
                alertLabel.setText("Saved to XML file");
            } else {
                alertLabel.setText("Wrong port, please try again");
                isSuccesseded = false;
            }
        } else {
            alertLabel.setText("Empty IP or Port, please try again");
            isSuccesseded = false;
        }

        return isSuccesseded;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    public String getServerIp() {
        return serverIpTxt;
    }

    public String getServerPort() {
        return serverPortTxt;
    }

    private void updateCurrentXML() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse("serverConfiguration.xml");
            doc.getElementsByTagName("serverIp").item(0).setTextContent(serverIpTxt);
            doc.getElementsByTagName("port").item(0).setTextContent(serverPortTxt);
            setServerParameters();
            writeToXmlFile(doc);
            System.out.println("Server parameters has changed in XML file");
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ClientServerConnectionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(ClientServerConnectionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientServerConnectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public interface NextListener {
        void onNextButtonPressed();
    }

}
