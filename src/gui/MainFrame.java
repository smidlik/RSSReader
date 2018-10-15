package gui;

import utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainFrame extends JFrame {
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


    public void initUI(){
        JPanel controlPanel = new JPanel(new GridLayout(0,1));

        JPanel formPanel = new JPanel(new BorderLayout());
        JButton btnLoad = new JButton("Load");
        JTextField txtAddTodo = new JTextField();
        JButton btnSave = new JButton("Save");

        formPanel.add(btnLoad, BorderLayout.WEST);
        formPanel.add(txtAddTodo,BorderLayout.CENTER);
        formPanel.add(btnSave,BorderLayout.EAST);

        add(formPanel,BorderLayout.NORTH);

        JTextArea txtContent = new JTextArea();
        add(new JScrollPane(txtContent),BorderLayout.CENTER);

        try {
            txtContent.setText(FileUtils.loadStringFromFile("zive.xml"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        //TODO: přidat listenery na LOAD a SAVE btn
        //TODO: napsat metodu validateInput --> Bool
        //TODO: pridat Jlabel lblError ---> RED

    }

}
