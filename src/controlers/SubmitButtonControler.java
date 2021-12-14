package controlers;

import gui.MainFrame;
import queryBuilder.kompajler.Compiler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubmitButtonControler implements ActionListener {


    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("cao");
        MainFrame.getInstance().postaviBazu();
    }
}
