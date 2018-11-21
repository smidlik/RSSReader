package App;

import gui.MainFrame;
import utils.FileUtils;

import javax.swing.*;
import java.io.IOException;

public  class  App{
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);

                FileUtils.saveSources(mainFrame.getSourceList());
            }
        });
    }
}
