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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private static final String VALIDATION_TYPE = "VALIDATION_TYPE";
    private static final String IO_LOAD_TYPE = "IO_LOAD_TYPE";
    private static final String IO_SAVE_TYPE = "IO_SAVE_TYPE";


    private JLabel lblErrorMessage;
    private JComboBox<String> comboBox;
    private RSSList rssList;
    private RSSSource source;

    private List<RSSSource> sourceList;
    private String sourceName;

    public MainFrame() {
        init();
    }


    private void init() {
        setTitle("RSS Reader");
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        initUI();

    }


    private void initUI() {
        JPanel controlPanel = new JPanel(new BorderLayout());
        JPanel btnPanel = new JPanel(new WrapLayout());

        sourceList = new ArrayList<>();


        sourceList.add(new RSSSource("Živě.cz", "https://www.zive.cz/rss/sc-47/"));
        sourceList.add(new RSSSource("Letem Světem Applem", "http://letemsvetemapplem.eu/feed.xml"));
        sourceList.add(new RSSSource("Lupa", "https://www.lupa.cz/rss/clanky/"));
        sourceList.add(new RSSSource("Novinky.cz", "https://www.novinky.cz/rss/"));

        JButton btnAdd = new JButton("Přidat");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Smazat");

        comboBox = new JComboBox<>();


        for (RSSSource rssSource : sourceList) {
            comboBox.addItem(rssSource.getName());
            if (rssSource.getName().equalsIgnoreCase(comboBox.getSelectedItem().toString())) {
                source = rssSource;
            }
        }


        lblErrorMessage = new JLabel();
        lblErrorMessage.setForeground(Color.RED);
        lblErrorMessage.setHorizontalAlignment(SwingConstants.CENTER);

        

        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);

        btnAdd.addActionListener(e -> {
            new AddFrame(this);
            // if(addFrame.getName()!=null)sourceList.add(new RSSSource(addFrame.getName(),addFrame.getSource()));
        });

        controlPanel.add(comboBox, BorderLayout.PAGE_START);

        controlPanel.add(btnPanel, BorderLayout.CENTER);

        controlPanel.add(lblErrorMessage, BorderLayout.PAGE_END);

        add(controlPanel, BorderLayout.NORTH);

        contentWrite();

        comboBox.addItemListener(e -> {
            for (RSSSource rssSource : sourceList) {
                if (rssSource.getName().equalsIgnoreCase(comboBox.getSelectedItem().toString())) {
                    source = rssSource;
                }
            }
            contentWrite();

        });








/*
        btnAdd.addActionListener(e -> {
            if(validateInput()) {
                try {
                    txtContent.setText(FileUtils.loadStringFromFile(comboBox.getText()));
                } catch (IOException e1) {
                    showErrorMessage(IO_LOAD_TYPE);
                    e1.printStackTrace();
                }
            }
        });

        btnEdit.addActionListener(e -> {
            if(validateInput()) {

                try {
                    FileUtils.saveStringToFile(comboBox.getText(), txtContent.getText().getBytes(StandardCharsets.UTF_8));
                } catch (IOException e1) {
                    showErrorMessage(IO_SAVE_TYPE);
                    e1.printStackTrace();
                }
            }
        });*/

    }
    private void contentWrite(){
        JPanel content = new JPanel(new WrapLayout());

        try {
            rssList = new RSSParser().getParseRSS(source.getSource());
            for (RSSItem item : rssList.getAllItems()) {
                content.add(new CardView(item));
            }
            add(new JScrollPane(content), BorderLayout.CENTER);

        } catch (IOException | SAXException | ParserConfigurationException e1) {
            e1.printStackTrace();
        }
    }

    private void showErrorMessage(String type) {
        String message;
        switch (type) {
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
//        if(comboBox.getText().trim().isEmpty()) {
//            showErrorMessage(VALIDATION_TYPE);
//            return false;
//        }
//        lblErrorMessage.setVisible(false);
//        return true;
//    }


    public void setSourceList(List<RSSSource> sourceList) {
        this.sourceList = sourceList;
    }

    public List<RSSSource> getSourceList() {
        return sourceList;
    }

    public void addSource(RSSSource source) {
        sourceList.add(source);
        this.comboBox.addItem(source.getName());
    }
}
