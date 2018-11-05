package gui;

import model.RSSItem;
import model.RSSList;
import org.xml.sax.SAXException;
import utils.FileUtils;
import utils.RSSParser;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MainFrame extends JFrame {

    private static final String VALIDATION_TYPE = "VALIDATION_TYPE";
    private static final String IO_LOAD_TYPE = "IO_LOAD_TYPE";
    private static final String IO_SAVE_TYPE = "IO_SAVE_TYPE";

    private JLabel lblErrorMessage;
    private JTextField txtPathField;
    private RSSList rssList;

    public MainFrame() {
        init();
    }




    private void init() {
     setTitle("RSS Reader");
     setSize(800,600);
     setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
     setLocationRelativeTo(null);

        initUI();

    }


    private void initUI() {
        JPanel controlPanel = new JPanel(new BorderLayout());
        JPanel btnPanel = new JPanel(new BorderLayout());

        JButton btnLoad = new JButton("Load");
        txtPathField = new JTextField();
        JButton btnSave = new JButton("Save");
        JButton btnParse = new JButton("Parse");
        lblErrorMessage = new JLabel();
        lblErrorMessage.setForeground(Color.RED);
        lblErrorMessage.setHorizontalAlignment(SwingConstants.CENTER);

        controlPanel.add(btnLoad, BorderLayout.WEST);
        controlPanel.add(txtPathField, BorderLayout.CENTER);

        btnPanel.add(btnSave, BorderLayout.WEST);
        btnPanel.add(btnParse, BorderLayout.EAST);
        controlPanel.add(btnPanel,BorderLayout.EAST);

        controlPanel.add(lblErrorMessage, BorderLayout.SOUTH);

        add(controlPanel, BorderLayout.NORTH);
        JPanel content = new JPanel(new WrapLayout());


        try {
            rssList = new RSSParser().getParseRSS(txtPathField.getText());
            for (RSSItem item : rssList.getAllItems()) {
                content.add(new CardView(item));
            }

        } catch (IOException | SAXException | ParserConfigurationException e1) {
            e1.printStackTrace();
        }

      btnParse.addActionListener(e -> {
            if(validateInput()) {
                try {
                    rssList = new RSSParser().getParseRSS(txtPathField.getText());
                    for (RSSItem item : rssList.getAllItems()) {
                        content.add(new CardView(item));
                    }

                    add(new JScrollPane(content), BorderLayout.CENTER);

                } catch (IOException | SAXException | ParserConfigurationException e1) {
                    e1.printStackTrace();
                }
            }
        });





/*
        btnLoad.addActionListener(e -> {
            if(validateInput()) {
                try {
                    txtContent.setText(FileUtils.loadStringFromFile(txtPathField.getText()));
                } catch (IOException e1) {
                    showErrorMessage(IO_LOAD_TYPE);
                    e1.printStackTrace();
                }
            }
        });

        btnSave.addActionListener(e -> {
            if(validateInput()) {

                try {
                    FileUtils.saveStringToFile(txtPathField.getText(), txtContent.getText().getBytes(StandardCharsets.UTF_8));
                } catch (IOException e1) {
                    showErrorMessage(IO_SAVE_TYPE);
                    e1.printStackTrace();
                }
            }
        });*/

    }
    private void showErrorMessage(String type) {
        String message;
        switch(type){
            case VALIDATION_TYPE:
                message = "Zadávací pole nemůže být prázdné!";
                break;
            case IO_LOAD_TYPE:
                message = "Chyba při načítání souboru!";
                break;
            case IO_SAVE_TYPE:
                message = "Chyba při ukládání souboru!";
                break;
            default:
                message = "Jiná chyba.";
                break;
        }
        lblErrorMessage.setText(message);
        lblErrorMessage.setVisible(true);
    }

    private boolean validateInput(){
        lblErrorMessage.setVisible(false);
        if(txtPathField.getText().trim().isEmpty()) {
            showErrorMessage(VALIDATION_TYPE);
            return false;
        }
        lblErrorMessage.setVisible(false);
        return true;
    }

}
