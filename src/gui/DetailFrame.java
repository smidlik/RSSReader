package gui;

import model.RSSItem;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DetailFrame extends JFrame {

    private RSSItem item;
    private JPanel content;
    private JPanel lowerPanel;
    private JTextArea title;
    private JTextArea description;
    private JLabel author;
    private JLabel pubDate;


    public DetailFrame(RSSItem item){
        this.item = item;
        init();
    }

    private void init() {
        setTitle("Článek");
        setSize(400,600);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);

        Container pane = this.getContentPane();
        pane.setLayout(new BorderLayout());


        lowerPanel=new JPanel();
        title=new JTextArea();
        description=new JTextArea();
        author = new JLabel();
        pubDate=new JLabel();



        title.setLineWrap(true);
        title.setEditable(false);
        title.setBackground(Color.GRAY);
        title.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        title.setFont(new Font("Courier",Font.BOLD,15));
        description.setLineWrap(true);
        description.setEditable(false);
        description.setBackground(Color.LIGHT_GRAY);




        pane.add(description,BorderLayout.CENTER);
        pane.add(title,BorderLayout.PAGE_START);
        pane.add(lowerPanel,BorderLayout.PAGE_END);


        lowerPanel.add(pubDate,BorderLayout.EAST);
        lowerPanel.add(author,BorderLayout.WEST);

        title.setText(item.getTitle());
        description.setText(item.getDescription());
        author.setText(item.getAutor());
        pubDate.setText(item.getPubDate());

        description.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton()== MouseEvent.BUTTON3)
                    dispose();
            }
        });



    }
}
