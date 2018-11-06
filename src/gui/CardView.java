package gui;

import model.RSSItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.VetoableChangeListener;

public class CardView extends JPanel {

    private static final int ITEM_WIDTH = 180;
    private static final int COMPONENT_WIDTH = 160;
    private static final int HEIGHT = 1;

    final String startHtml = "<html><p style='width: "+COMPONENT_WIDTH +"px'>";
    final String endHtml = "</p></html>";


    public CardView(RSSItem item){
        setLayout(new WrapLayout());
        setSize(ITEM_WIDTH, HEIGHT);
        setBackground(setColor(item));
        setTitle(item.getTitle());
        setDescription(item.getDescription());
        setAdditionlInfo(String.format("%s - %s",item.getAutor(),item.getPubDate()));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(e.getButton()==MouseEvent.BUTTON1){
                    if(e.getClickCount() ==2 && !e.isConsumed()){
                        e.consume();

                        new DetailFrame(item);
                    }
                }
            }
        });
    }

    public Color setColor(RSSItem item){
        int r;
        int g;
        int b;
        r = item.getAutor().length()*23;
        g = item.getDescription().length()*2;
        b = item.getTitle().length()*4;

        while (r>255)
        {
            r -= 125;
        }
        while (g>255)
        {
            g -= 125;

        }
        while (b>255)
        {
            b -= 125;

        }
        return new Color(r,g,b);
    }

    public void setTitle(String title) {
    JLabel lblTitle = new JLabel();
    lblTitle.setFont(new Font("Courier",Font.BOLD,12));
    lblTitle.setSize(COMPONENT_WIDTH,HEIGHT);
    lblTitle.setText(String.format("%s%s%s",startHtml,title,endHtml));
    add(lblTitle);
    }
    public void setDescription(String description){
        JLabel lblDescription = new JLabel();
        lblDescription.setFont(new Font("Courier",Font.PLAIN,11));
        lblDescription.setSize(COMPONENT_WIDTH,HEIGHT);
        lblDescription.setText(String.format("%s%s%s",startHtml,description,endHtml));
        add(lblDescription);
    }
    public void setAdditionlInfo(String info){
        JLabel lblInfo = new JLabel();
        lblInfo.setFont(new Font("Courier",Font.ITALIC,10));
        lblInfo.setSize(COMPONENT_WIDTH,HEIGHT);
        lblInfo.setText(String.format("%s%s%s",startHtml,info,endHtml));
        lblInfo.setForeground(Color.LIGHT_GRAY);
        add(lblInfo);
    }

}

