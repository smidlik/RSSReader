package gui;

import model.RSSItem;
import model.RSSSource;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.*;
import java.awt.event.*;

public class AddFrame extends JFrame {

    private static final String NAME = "NAME";
    private static final String SOURCE = "SOURCE";

    private JTextField txtName;
    private JTextField txtSource;
    private JButton add;
    private JLabel lblName;
    private JLabel lblSource;

    private RSSSource source;
    private MainFrame frame;


    public AddFrame(MainFrame frame, RSSSource source) {
        this.source = source;
        this.frame = frame;
        init("Uprav");
        initEdit();


    }

    public AddFrame(MainFrame frame) {
        this.frame = frame;
        initAdd();
        init("Přidej");
    }


    private void init(String s) {
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);

        Container pane = this.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        txtName = new JTextField();
        txtSource = new JTextField();
        lblSource = new JLabel("Zdroj:");
        lblName = new JLabel("Název:");
        add = new JButton(s);

        pane.add(lblName);
        pane.add(txtName);
        pane.add(lblSource);
        pane.add(txtSource);
        pane.add(add);

        txtName.addInputMethodListener(new InputMethodListener() {
            @Override
            public void inputMethodTextChanged(InputMethodEvent event) {

            }

            @Override
            public void caretPositionChanged(InputMethodEvent event) {

            }
        });

    }

    private void initAdd() {
        setTitle("Přidat zdroj");
        add.addActionListener(e -> {
            source.setName(txtName.getText());
            source.setSource(txtSource.getText());
            frame.addSource(source);
            dispose();

        });
    }

    private void initEdit() {
        setTitle("Uravit zdroj");
        txtName.setText(source.getName());
        txtSource.setText(source.getSource());
        add.addActionListener(e -> {
            source.setName(txtName.getText());
            source.setSource(txtSource.getText());
            frame.addSource(source);
            frame.removeSource(source);
            dispose();
        });
    }

    private boolean isValidated(String s, String item) {
        switch (item) {
            case "NAME":
                return false;
            case "SOURCE":
                return s.contains("https");

            default:
                return false;
        }

    }
}