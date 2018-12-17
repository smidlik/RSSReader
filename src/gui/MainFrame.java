package gui;

import model.RSSItem;
import model.RSSList;
import model.RSSSource;
import org.xml.sax.SAXException;
import utils.FileUtils;
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
    private static final String WRITE_TYPE = "WRITE_TYPE";
    private static final String IO_LOAD_TYPE = "IO_LOAD_TYPE";
    private static final String IO_SAVE_TYPE = "IO_SAVE_TYPE";


    private JLabel lblErrorMessage;
    private JComboBox<String> comboBox;
    private RSSList rssList;
    private RSSSource source;
    private JPanel content;

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
        content = new JPanel(new WrapLayout());
        add(new JScrollPane(content), BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new BorderLayout());

        JPanel btnPanel = new JPanel(new WrapLayout());

        sourceList = new ArrayList<>();

        try {
           sourceList = FileUtils.loadSorces();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage(IO_LOAD_TYPE);
        }

        sourceList.add(new RSSSource("Živě.cz", "https://www.zive.cz/rss/sc-47/"));
        sourceList.add(new RSSSource("Letem Světem Applem", "http://letemsvetemapplem.eu/feed.xml"));
        sourceList.add(new RSSSource("Lupa", "https://www.lupa.cz/rss/clanky/"));
        sourceList.add(new RSSSource("Novinky.cz", "https://www.novinky.cz/rss/"));

        JButton btnAdd = new JButton("Přidat");
        JButton btnRef = new JButton("Refresh");
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
        btnPanel.add(btnRef);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);



        btnAdd.addActionListener(e -> {
            new AddFrame(this);
        });
        btnDelete.addActionListener(e -> {
            sourceList.remove(source);
            comboBox.removeItem(comboBox.getSelectedItem());
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddFrame();
            }
        });

        btnRef.addActionListener(e -> contentWrite());

        controlPanel.add(comboBox, BorderLayout.PAGE_START);

        controlPanel.add(btnPanel, BorderLayout.CENTER);

        controlPanel.add(lblErrorMessage, BorderLayout.PAGE_END);

        add(controlPanel, BorderLayout.NORTH);

        comboBox.addItemListener(e -> {
            for (RSSSource rssSource : sourceList) {
                if (rssSource.getName().equalsIgnoreCase(comboBox.getSelectedItem().toString())) {
                    source = rssSource;
                }
            }
            contentWrite();

        });


    }

    private void contentWrite() {
        if (sourceList!=null) {
            content.removeAll();
            System.out.println("contentWrite");
            try {
                rssList = new RSSParser().getParseRSS(source.getSource());
                for (RSSItem item : rssList.getAllItems()) {
                    content.add(new CardView(item));
                }
                content.updateUI();
            } catch (IOException | SAXException | ParserConfigurationException e1) {
                e1.printStackTrace();
            }
        }
    }
    private void showErrorMessage(String type) {
        String message;
        switch (type) {
            case VALIDATION_TYPE:
                message = "Zadávací pole nemůže být prázdné!";
                break;
            case WRITE_TYPE:
                message = "Chyba při výpisu!";
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
