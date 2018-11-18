package gui;

import model.RSSItem;
import model.RSSList;
import model.RSSSource;
import org.xml.sax.SAXException;
import utils.RSSParser;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private static final String VALIDATION_TYPE = "VALIDATION_TYPE";
    private static final String IO_LOAD_TYPE = "IO_LOAD_TYPE";
    private static final String IO_SAVE_TYPE = "IO_SAVE_TYPE";

    private JLabel lblErrorMessage;
    private JComboBox source;
    private RSSList rssList;

    private AddFrame addFrame;

    private List<RSSSource> sourceList;
    private List<String> sourceName;


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
        JPanel btnPanel = new JPanel(new WrapLayout());
        sourceName=new ArrayList<>();
        sourceList = new ArrayList<>();

        JButton btnAdd = new JButton("Add");
        source = new JComboBox(sourceName.toArray());
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Delete");
        lblErrorMessage = new JLabel();
        lblErrorMessage.setForeground(Color.RED);
        lblErrorMessage.setHorizontalAlignment(SwingConstants.CENTER);




        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);

        btnAdd.addActionListener(e -> {
            addFrame=new  AddFrame();
            sourceList.add(new RSSSource(addFrame.getName(),addFrame.getSource()));
        });
        for (RSSSource rssSource : sourceList) {
            source.addItem(rssSource.getName());

        }

        controlPanel.add(source, BorderLayout.PAGE_START);

        controlPanel.add(btnPanel,BorderLayout.CENTER);

        controlPanel.add(lblErrorMessage, BorderLayout.PAGE_END);

        add(controlPanel, BorderLayout.NORTH);
        JPanel content = new JPanel(new WrapLayout());


        try {
            rssList = new RSSParser().getParseRSS("https://www.zive.cz/rss/sc-47/");
            for (RSSItem item : rssList.getAllItems()) {
                content.add(new CardView(item));
            }
            add(new JScrollPane(content), BorderLayout.CENTER);

        } catch (IOException | SAXException | ParserConfigurationException e1) {
            e1.printStackTrace();
        }








/*
        btnAdd.addActionListener(e -> {
            if(validateInput()) {
                try {
                    txtContent.setText(FileUtils.loadStringFromFile(source.getText()));
                } catch (IOException e1) {
                    showErrorMessage(IO_LOAD_TYPE);
                    e1.printStackTrace();
                }
            }
        });

        btnEdit.addActionListener(e -> {
            if(validateInput()) {

                try {
                    FileUtils.saveStringToFile(source.getText(), txtContent.getText().getBytes(StandardCharsets.UTF_8));
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

//    private boolean validateInput(){
//        lblErrorMessage.setVisible(false);
//        if(source.getText().trim().isEmpty()) {
//            showErrorMessage(VALIDATION_TYPE);
//            return false;
//        }
//        lblErrorMessage.setVisible(false);
//        return true;
//    }


}
