package gui;

import app.AppCore;
import controlers.SubmitButtonControler;
import observer.Notification;
import observer.Subscriber;
import observer.enumi.NotificationCode;
import resource.implementation.InformationResource;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

public class MainFrame extends JFrame implements Subscriber {

    private static MainFrame instance = null;

    private AppCore appCore;
    private JTable jTable;
    private JButton jButton;
    private JTextArea jTextArea;
    private JScrollPane jsp;
    private JPanel bottomStatus;


    private MainFrame() {

    }

    public static MainFrame getInstance(){
        if (instance==null){
            instance=new MainFrame();
            instance.initialise();
        }
        return instance;
    }


    private void initialise() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jTable = new JTable();
        jTable.setPreferredScrollableViewportSize(new Dimension(500, 400));
        jTable.setFillsViewportHeight(true);

        jButton = new JButton("Submit");
        jButton.addActionListener(new SubmitButtonControler());

        jTextArea = new JTextArea();
        jTextArea.setColumns(10);
        jTextArea.setRows(10);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setLineWrap(true);
        Dimension dim = jTextArea.getPreferredSize();
        Dimension dim2 = new Dimension(dim.width , dim.height);
        jTextArea.setPreferredSize(dim2);

        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(jTable),BorderLayout.SOUTH);
        this.add(jTextArea, BorderLayout.NORTH);
        this.add(jButton, BorderLayout.CENTER);


        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }

    public void setAppCore(AppCore appCore) {
        this.appCore = appCore;
        this.appCore.addSubscriber(this);
        this.jTable.setModel(appCore.getTableModel());
    }

    public void postaviBazu(){
        String s = appCore.getQb().buildQuery(jTextArea.getText());
        if(s == null){
            return;
        }
        else
            appCore.readDataFromTable(s);
    }

    @Override
    public void update(Notification notification) {

        if (notification.getCode() == NotificationCode.RESOURCE_LOADED){
            System.out.println((InformationResource)notification.getData());
        }

        else{
            jTable.setModel((TableModel) notification.getData());
        }

    }

    public static void setInstance(MainFrame instance) {
        MainFrame.instance = instance;
    }

    public AppCore getAppCore() {
        return appCore;
    }

    public JTable getjTable() {
        return jTable;
    }

    public void setjTable(JTable jTable) {
        this.jTable = jTable;
    }

    public JScrollPane getJsp() {
        return jsp;
    }

    public void setJsp(JScrollPane jsp) {
        this.jsp = jsp;
    }

    public JPanel getBottomStatus() {
        return bottomStatus;
    }

    public void setBottomStatus(JPanel bottomStatus) {
        this.bottomStatus = bottomStatus;
    }

    public JButton getjButton() {
        return jButton;
    }

    public void setjButton(JButton jButton) {
        this.jButton = jButton;
    }

    public JTextArea getjTextArea() {
        return jTextArea;
    }

    public void setjTextArea(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }
}

