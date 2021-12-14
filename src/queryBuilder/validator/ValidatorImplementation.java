package queryBuilder.validator;


import java.util.ArrayList;
import java.util.List;

public class ValidatorImplementation implements Validator{
    @Override

    public List<String> validate(String s) {
        List<String> upit1 = new ArrayList<>();
        List<String> upit2 = new ArrayList<>();
        int br = 0;
        String[] str = s.split("\\r?\\n");
        if(str.length < 2){
            upit1 = check(s);
        }
        else {
            if(str[0].contains("var")){
                br++;
            }
            if(str[1].contains("var")){
                br++;
            }
            String[] dupli = s.split("[.]");

            int brojac = 0;
            for(int d = 0; d<dupli.length;d++){
                if(dupli[d].contains("WhereInQ")){
                    brojac++;
                }
            }
            if(brojac != 1){
                System.out.println(brojac);
                System.out.println("USAO U 1");
                upit1.add("WhereInQ");
                return upit1;
            }
            if(br < 2){
                upit1.add("Nedeklarisana promenljiva");
            }
            else{
                if(str[0].contains("WhereInQ(")){
                    String[] provera = str[0].split("[.]");
                    for(int i = 0; i<provera.length; i++){
                        if(provera[i].contains("WhereInQ(")){
                            String[] promenljiva = provera[i].split(",");
                            String[] promenljiva2 = str[1].split(" ");
                            if(promenljiva2[1].charAt(0) != promenljiva[1].charAt(1)){
                                System.out.println("USAO U 2");
                                upit1.add("Pogresan upit u WhereInQ");
                                return upit1;
                            }
                        }
                    }
                }

                if(str[1].contains("WhereInQ(")){
                    String[] provera = str[1].split("[.]");
                    for(int g = 0; g<provera.length; g++){
                        if(provera[g].contains("WhereInQ(")){
                            System.out.println("USAO U IFFFFF");
                            String[] promenljiva = provera[g].split(",");
                            String[] promenljiva2 = str[0].split(" ");
                            if(promenljiva2[1].charAt(0) != promenljiva[1].charAt(1)){
                                upit1.add("Pogresan upit u WhereInQ");
                                return upit1;
                            }
                        }
                    }
                }
                upit1 = check(str[0]);
                upit2 = check(str[1]);
                upit1.addAll(upit2);
            }
        }
        return upit1;
    }

    private List<String> check(String s){
        List<String> lista = new ArrayList<>();
        int having = 0, orHaving = 0, andHaving = 0, where = 0, andWhere = 0, orWhere = 0, between = 0, whereIn = 0, start = 0
                ,end = 0,contain = 0,groupBy = 0, orderBy = 0, orderByDesc = 0, select = 0, query = 0,avg = 0, max = 0
                ,min = 0, count = 0;
        String[] a = s.split("[.]");
        if(s.equals(""))
            lista.add("Prazan text");
        if(!s.contains("= new Query(")){
            lista.add("Nedeklarisana promenljiva");
            return lista;
        }
        if(!s.contains("var ")){
            if(!lista.contains("Nedeklarisana promenljiva")){
                lista.add("Nedeklarisana promenljiva");
            }

        }
        String[]razmak = s.split(" ");
        if(razmak[1].equals("=")){
            if(!lista.contains("Nedeklarisana promenljiva")){
                lista.add("Nedeklarisana promenljiva");
            }
        }
        for(int j = 0; j<a.length; j++) {

            if (a[j].contains("Where(")) {
                where++;
            }
            if (a[j].contains("WhereIn(")) {
                whereIn++;
            }
            if (a[j].contains("WhereStartsWith(")) {
                start++;
            }
            if (a[j].contains("WhereEndsWith(")) {
                end++;
            }
            if (a[j].contains("WhereContains(")) {
                contain++;
            }
            if (a[j].contains("WhereBetween(")) {
                System.out.println("kita");
                between++;
            }
            if (a[j].contains("OrWhere")) {
                orWhere++;
            }
            if (a[j].contains("AndWhere")) {
                andWhere++;
            }
            if (a[j].contains("Having(")) {
                having++;
            }
            if (a[j].contains("OrHaving(")) {
                orHaving++;
            }
            if (a[j].contains("AndHaving(")) {
                andHaving++;
            }
            if(a[j].contains("OrderBy(")){
                orderBy++;
            }
            if(a[j].contains("OrderByDesc(")){
                orderByDesc++;
            }
            if(a[j].contains("GroupBy(")){
                groupBy++;
            }
            if(a[j].contains("Avg(")){
                avg++;
            }
            if(a[j].contains("Max(")){
                max++;
            }
            if(a[j].contains("Min(")){
                min++;
            }
            if(a[j].contains("Count(")){
                count++;
            }
            if(a[j].contains("Select(")){
                select++;
            }
            if(a[j].contains("Query(")){
                query++;
            }
        }
        if(query > 1){
            lista.add("2 querya");
        }
        if(s.contains("Join(") && !s.contains("On(")){
            if(!lista.contains("Nepravilan Join")){
                lista.add("Nepravilan Join");
            }
        }
        if(!s.contains("Join(") && s.contains("On(")){
            if(!lista.contains("Nepravilan Join")){
                lista.add("Nepravilan Join");
            }
        }
        for(int i = 0; i<a.length; i++){
            if(a[i].contains("Join(") && !a[i+1].contains("On(")){
                if(!lista.contains("Nepravilan Join")){
                    lista.add("Nepravilan Join");
                }
            }
            if(a[i].contains("WhereIn(") && !a[i+1].contains("ParametarList(")){
                if(!lista.contains("Nepravilan WhereIn")){
                    lista.add("Nepravilan WhereIn");
                }
            }
        }
        if(s.contains("WhereIn(") && !s.contains("ParametarList(")){
            lista.add("Nepravilan WhereIn");
        }

        if(!s.contains("WhereIn(") && s.contains("ParametarList(")){
            lista.add("Nepravilan WhereIn");
        }
        if(where == 1 && !(whereIn == 0 && between == 0 && start == 0 && end == 0 && contain == 0)){
            if(!lista.contains("Nepravilan Where")){
                lista.add("Nepravilan Where");
            }
        }
        if(whereIn == 1 && !(where == 0 &&  between == 0 && start == 0 && end == 0 && contain == 0)){
            if(!lista.contains("Nepravilan Where")){
                lista.add("Nepravilan Where");
            }
        }
        if(between == 1 && !(where == 0 && whereIn == 0 &&  start == 0 && end == 0 && contain == 0)){
            if(!lista.contains("Nepravilan Where")){
                lista.add("Nepravilan Where");
            }
        }
        if(start == 1 && !(where == 0 && whereIn == 0 && between == 0 && end == 0 && contain == 0)){
            if(!lista.contains("Nepravilan Where")){
                lista.add("Nepravilan Where");
            }
        }
        if(end == 1 && !(where == 0 && whereIn == 0 && between == 0 && start == 0 &&  contain == 0)){
            if(!lista.contains("Nepravilan Where")){
                lista.add("Nepravilan Where");
            }
        }
        if(contain == 1 && !(where == 0 && whereIn == 0 && between == 0 && start == 0 &&  end == 0)){
            if(!lista.contains("Nepravilan Where")){
                lista.add("Nepravilan Where");
            }
        }
        if(whereIn > 1 || where > 1 || between > 1 || start > 1 || end > 1|| contain > 1){
            if(!lista.contains("Nepravilan Where")){
                lista.add("Nepravilan Where");
            }
        }
        if((whereIn == 0 && where == 0 && between == 0 && start == 0 && end == 0 && contain == 0) && (orWhere == 1 || andWhere == 1)){
            if(!lista.contains("Nepravilan Where")){
                lista.add("Nepravilan Where");
            }
        }
        if(select == 1 && (avg > 0 || max > 0 || min > 0 || count > 0) && groupBy == 0){
            if(!lista.contains("Nepravilan HavingGriupBy")){
                lista.add("Nepravilan HavingGroupBy");
            }
        }
        if(having == 1 && groupBy==0){
            if(!lista.contains("Nepravilan HavingGriupBy")){
                lista.add("Nepravilan HavingGroupBy");
            }
        }
        if((andHaving == 1 && having == 0) || (orHaving == 1 && having == 0)){
            if(!lista.contains("Nepravilan Having")){
                lista.add("Nepravilan Having");
            }
        }
        for(int h = 0; h<a.length;h++){
            if(a[h].contains("Having(") || a[h].contains("OrHaving(") || a[h].contains("AndHaving(")){
                if(a[h].contains("avg(") || a[h].contains("min(") || a[h].contains("max(") || a[h].contains("count(") || a[h].contains("Avg(") || a[h].contains("Min(") || a[h].contains("Max(") || a[h].contains("Count(")){
                }
                else {
                    if(!lista.contains("Nepravilan HavingAgregacija")){
                        lista.add("Nepravilan HavingAgregacija");
                    }
                }
            }
        }
        if(select > 1){
            lista.add("Select");
        }
        return lista;
    }

}
