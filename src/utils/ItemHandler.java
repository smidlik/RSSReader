package utils;

import model.RSSItem;
import model.RSSList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.security.PublicKey;
import java.util.List;

public class ItemHandler extends DefaultHandler {

    private static final String ITEM = "item";
    private static final String TITLE = "title";
    private static final String LINK = "link";
    private static final String DESCRIPTION = "description";
    private static final String PUB_DATE = "pubDate";
    private static final String AUTHOR = "dc:creator";

    private RSSItem item;
    private RSSList list;

    private boolean title;
    private boolean link;
    private boolean description;
    private boolean pubDate;
    private boolean author;

    public ItemHandler(RSSList list){
        this.list=list;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
       if (qName.equalsIgnoreCase(ITEM)){
           item = new RSSItem();
       }
        if (qName.equalsIgnoreCase(TITLE)&&item!=null);{
            title = true;
        }
        if (qName.equalsIgnoreCase(LINK)&&item!=null){
            link = true;
        }
        if (qName.equalsIgnoreCase(DESCRIPTION)&&item!=null){
            description = true;
        }
        if (qName.equalsIgnoreCase(PUB_DATE)&&item!=null){
            pubDate = true;
        }
        if (qName.equalsIgnoreCase(AUTHOR)&&item!=null){
            author = true;
        }
    }
    @Override
    public void endElement(String uri, String localName, String qName) {
        if(qName.equalsIgnoreCase(ITEM)){
            list.addItem(item);
        }
    }
    @Override
    public void characters(char[] ch, int start, int length) {
        if(title){
            item.setTitle(new String(ch,start,length));
            title = false;
        }
        if(link){
            item.setTitle(new String(ch,start,length));
            link = false;
        }
        if(description){
            item.setTitle(new String(ch,start,length));
            description = false;
        }
        if(pubDate){
            item.setTitle(new String(ch,start,length));
            pubDate = false;
        }
        if(author){
            item.setTitle(new String(ch,start,length));
            author = false;
        }
    }


}
