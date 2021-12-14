package error;

import javax.swing.*;

public class Error implements IError {

    private static Error instance=null;

    public Error() {
    }

    public static Error getInstance(){
        if(instance == null){
            instance = new Error();
        }
        return instance;
    }

    @Override
    public void errorHandler(String string) {
        if(string.equals("Pogresan unos")){
            JOptionPane.showMessageDialog(null, "Greska sa upitom, pogresno uneta imena tabela ili kolona");
        }
        if(string.equals("Nedeklarisana promenljiva")){
            JOptionPane.showMessageDialog(null, "Nedeklarisana promenljiva");
        }
        if(string.equals("Prazan text")){
            JOptionPane.showMessageDialog(null, "Prazan text");
        }
        if(string.equals("Nepravilan Join")){
            JOptionPane.showMessageDialog(null, "Nepravilan Join");
        }
        if(string.equals("Nepravilan WhereIn")){
            JOptionPane.showMessageDialog(null, "Nepravilan WhereIn");
        }
        if(string.equals("Nepravilan Having")){
            JOptionPane.showMessageDialog(null, "Nepravilan Having");
        }
        if(string.equals("Nepravilan Where")){
            JOptionPane.showMessageDialog(null, "Nepravilan Where");
        }
        if(string.equals("Nepravilan HavingGroupBy")){
            JOptionPane.showMessageDialog(null, "GroupBy je obavezan");
        }
        if(string.equals("Nepravilan HavingAgregacija")){
            JOptionPane.showMessageDialog(null, "Nepravilan HavingAgregacija");
        }
        if(string.equals("Pogresan upit u WhereInQ")){
            JOptionPane.showMessageDialog(null, "Pogresan upit u WhereInQ");
        }
        if(string.equals("WhereInQ")){
            JOptionPane.showMessageDialog(null, "WhereInQ");
        }
        if(string.equals("2 querya")){
            JOptionPane.showMessageDialog(null, "2 querya u jednom upitu");
        }
        if(string.equals("Select")){
            JOptionPane.showMessageDialog(null, "Nepravilan Select");
        }
    }

    @Override
    public Error getError() {
        return null;
    }


}
