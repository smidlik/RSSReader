package gui;

import model.RSSItem;
import model.RSSSource;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddFrame extends JFrame {

    private JTextField txtName;
    private JTextField txtSource;
    private JButton add;
    private JLabel lblName;
    private JLabel lblSource;

    private String name;
    private String source;
    private MainFrame frame;



    public AddFrame(MainFrame frame){
        initAdd();
        this.frame=frame;
    }

    private void initAdd() {
        setTitle("Přidat zdroj");
        setSize(400,200);
        setLocationRelativeTo(null);
        setVisible(true);

        Container pane = this.getContentPane();
        pane.setLayout(new BoxLayout(pane,BoxLayout.PAGE_AXIS));

        txtName =new JTextField();
        txtSource=new JTextField();
        add = new JButton("Přidej rss");
        lblSource = new JLabel("Zdroj:");
        lblName = new JLabel("Název:");

        pane.add(lblName);
        pane.add(txtName);
        pane.add(lblSource);
        pane.add(txtSource);
        pane.add(add);

        add.addActionListener(e -> {
            name = txtName.getText();
            source = txtSource.getText();

            frame.addSource(new RSSSource(name,source));

            dispose();

        });



















    }
    public String getName(){
        return name;
    }

    public String getSource() {
        return source;
    }
}
