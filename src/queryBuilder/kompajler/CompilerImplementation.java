package queryBuilder.kompajler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CompilerImplementation implements Compiler{
    @Override
    public String compaile(String ss) {

        String select="select *",from="",where="",orderBy="",groupBy="",having="",andHaving="",orHaving="",min="",max="",avg="",count=""
                ,whereContains="",whereEndsWith="",whereStartsWith="",or="",and="",between="",tabela=""
                ,whereIn="",ob="",ob2="",join="",on="",whereInQ="",podString="";
        int counter =0,fromIndex = 0;
        String[] a = new String[0];
        String[] b;
        String[] c;
        String zaSplit = "";
        while ((fromIndex = ss.indexOf("var", fromIndex)) != -1 ){

            counter++;
            fromIndex++;

        }

        if(counter>1){
            String[] s = ss.split("\\r?\\n");
            b = s[0].split("[.]");
            c = s[1].split("[.]");
            for(int i = 0; i<b.length; i++){
                if(b[i].contains("WhereInQ")){
                    zaSplit = s[1];
                    a = b;
                    break;
                }
            }
            for(int i = 0; i<c.length; i++){
                if(c[i].contains("WhereInQ")){
                    zaSplit = s[0];
                    a=c;
                    break;
                }
            }
            
            System.out.println(s[0] + " s0");
            System.out.println(s[1] + " s1");
            String unos = compaile(s[1]);
            System.out.println(unos + " unos");
        }
        else{
            a = ss.split("[.]");
            System.out.println(ss + " ss");
        }




        int brojac = 0;
        for(int i = 0; i<a.length; i++){
            if(a[i].contains("OrderBy")){
                brojac++;
            }
        }

        for(int i = 0; i<a.length; i++){
            if(a[i].contains("Query")){
                from = " from " + Query(a[i]);
            }
            if(a[i].contains("Where")){
                if(a[i].contains("WhereEndsWith")){
                    whereEndsWith =" where " + WhereEndsWith(a[i]);
                }else if(a[i].contains("WhereStartsWith")){
                    whereStartsWith = " WHERE "+ WhereStartsWith(a[i]);
                }else if(a[i].contains("WhereContains")){
                    whereContains = " WHERE " + WhereContains(a[i]);
                }
                else if(a[i].contains("WhereBetween")){
                    between = " WHERE " + WhereBetween(a[i]);
                }
                else if(a[i].contains("OrWhere")){
                    or = " OR " + Where(a[i]);
                }
                else if(a[i].contains("AndWhere")){
                    and = " AND " + Where(a[i]);
                }
                else if(a[i].contains("WhereIn")){
                    if(a[i].contains("WhereInQ")){
                        System.out.println("Usao u where in q");
                        boolean flag1 = false;
                        String gotov = "";
                        for(int l = 0; l< a[i].length(); l++){
                            if(flag1 && a[i].charAt(i) == ')'){
                                break;
                            }
                            if(flag1 && a[i].charAt(i) != '"'){
                                gotov = gotov + a[i].charAt(i);
                            }

                            if(a[i].charAt(i) == '('){
                                flag1 = true;
                            }

                        }
                        whereInQ = WhereInQ(a[i], compaile(zaSplit));

                    } else{
                        whereIn = " where " + Query(a[i]) + " IN ";
                        boolean cek = false;
                        String string = a[i + 1];
                        System.out.println(string);
                        char[] niz = string.toCharArray();
                        string.toCharArray();
                        for (int k = 0; k < string.length(); k++) {
                            if (cek) {
                                if (niz[k] != '"') {
                                    whereIn = whereIn + niz[k];
                                }
                            }
                            if (niz[k] == '(') {
                                cek = true;
                                whereIn = whereIn + '(';
                            }
                        }
                    }
                    
                } else {
                    where = " WHERE " + Where(a[i]);
                }

            }
            if(a[i].contains("Select")){
                select = "SELECT "+ Select(a[i]);
            }
            if(a[i].contains("Avg")){
                avg = Avg(a[i]);
            }
            if(brojac == 1){
                if(a[i].contains("OrderBy")){
                    orderBy =" ORDER BY " + OrderBy(a[i]);

                }
                if(a[i].contains("OrderByDesc")){
                    orderBy = " ORDER BY " + OrderByDesc(a[i]);
                }
            }
            else if(brojac > 1){
                for(int j = 0; j<a.length; j++){
                    if(a[i].contains("OrderBy")){
                         ob = OrderBy(a[i]);
                    }
                    if(a[i].contains("OrderByDesc")){
                         ob2 = OrderByDesc(a[i]);
                    }
                }
                orderBy = " ORDER BY " + ob + ","+ ob2;
            }
            if(a[i].contains("GroupBy")){
                groupBy = " GROUP BY " + Select(a[i]);
            }
            if(a[i].contains("Having")){
                    having = " HAVING " + Having(a[i]);
            }
            if(a[i].contains("OrHaving")){
                    orHaving = " OR " + Having(a[i]);
            }
            if(a[i].contains("AndHaving")){
                andHaving = " AND " + Having(a[i]);
            }
            if(a[i].contains("Join")){
                join = " JOIN " + Join(a[i]);
            }
            if(a[i].contains("On")){
                on = " ON " + On(a[i]);
                String str = a[i+1];
                String gotovina="";
                for(int u =0 ; u< str.length() ; u++){
                    if( str.charAt(u) != '"' && str.charAt(u) != ',' && str.charAt(u) != ')'){
                        gotovina = gotovina + str.charAt(u);
                    }
                }
                String str2 = a[i+2];
                String gotovina2="";
                for(int z =0 ; z< str2.length() ; z++){
                    if( str2.charAt(z) != '"' && str2.charAt(z) != ',' && str2.charAt(z) != ')'){
                        gotovina2 = gotovina2 + str2.charAt(z);
                    }
                }
                on = on + "." + gotovina + "." + gotovina2;
            }
            if(a[i].contains("Max")){
                max = Max(a[i]);
            }
            if(a[i].contains("Min")){
                min = Min(a[i]);
            }
            if(a[i].contains("Count")){
                count = Count(a[i]);
            }
        }
        if(select != "select *" && avg.length() != 0){
            if(select.contains(",")){
                String[] str = select.split(",");
                select = str[0] + "," + avg;

            }
            else select = select + "," + avg;
        }
        else if(select == "select *" && avg.length() != 0){
            select = "select" + avg;
        }

        if(select != "select *" && max.length() != 0){
            if(select.contains(",")){
                String[] str = select.split(",");
                select = str[0] + "," + max;

            }
            else select = select + "," + max;
        }
        else if(select == "select *" && max.length() != 0){
            select = "select" + max;
        }

        if(select != "select *" && min.length() != 0){
            if(select.contains(",")){
                String[] str = select.split(",");
                select = str[0] + "," + min;

            }
            else select = select + "," + min;
        }
        else if(select == "select *" && min.length() != 0){
            select = "select" + min;
        }

        if(select != "select *" && count.length() != 0){
            if(select.contains(",")){
                String[] str = select.split(",");
                select = str[0] + "," + count;

            }
            else select = select + "," + count;
        }
        else if(select == "select *" && count.length() != 0){
            select = "select" + count;
        }
        tabela = select + from + join + on + where + whereInQ + whereEndsWith + whereStartsWith + whereContains
                + between +whereIn+ or + and +groupBy + having + andHaving + orHaving + orderBy;
        System.out.println(tabela + " tabela");
        return tabela;
    }

    private String WhereInQ(String s , String s1) {
        boolean flag = false;
        String gotov = "";
        for(int i = 0; i< s.length(); i++){
            if(flag && s.charAt(i) == ')'){
                break;
            }
            if(flag && s.charAt(i) != '"'){
                gotov = gotov + s.charAt(i);
            }

            if(s.charAt(i) == '('){
                flag = true;
            }

        }
        String finalni = "";
        String[] array = gotov.split(",");
        finalni =  " WHERE " + array[0] + " IN (" + s1 + ")";

        return finalni;
    }

    private String Join(String s) {
        boolean flag = false;
        String gotov = "";
        for(int i = 0; i< s.length(); i++){
            if(flag && s.charAt(i) == ')'){
                break;
            }
            if(flag && s.charAt(i) != '"'){
                gotov = gotov + s.charAt(i);
            }

            if(s.charAt(i) == '('){
                flag = true;
            }

        }
        String finalni = "";
        String[] array = gotov.split(",");

        finalni =  array[0];

        return finalni;
    }

    private String On(String s) {
        boolean flag = false;
        String gotov = "";
        for(int i = 0; i< s.length(); i++){
            if(flag && s.charAt(i) == ')'){
                break;
            }
            if(flag && s.charAt(i) != '"'){
                gotov = gotov + s.charAt(i);
            }

            if(s.charAt(i) == '('){
                flag = true;
            }

        }
        String finalni = "";
        String[] array = gotov.split(",");

        for(String a: array){
            finalni = finalni + a;
        }
        return finalni;
    }


    private String Having(String s) {
        boolean flag = false;
        String gotov = "";
        for(int i = 0; i< s.length(); i++){

            if(flag && s.charAt(i) != '"' && s.charAt(i) != ')'){
                gotov = gotov + s.charAt(i);
            }

            if(s.charAt(i) == '('){
                flag = true;
            }

        }
        String finalni = "";
        String[] array = gotov.split(",");
        array[0] = array[0]+")";
        for(String a: array){
            finalni = finalni + a;
        }

        return finalni;
    }

    private String Count(String s) {
        boolean flag = false;
        String gotov = "";
        for(int i = 0; i< s.length(); i++){
            if(flag && s.charAt(i) == ')'){
                break;
            }
            if(flag && s.charAt(i) != '"'){
                gotov = gotov + s.charAt(i);
            }

            if(s.charAt(i) == '('){
                flag = true;
            }

        }
        String finalni = "";
        String[] array = gotov.split(",");
        if(array.length > 1){
            finalni = " COUNT(" + array[0] + ")" + " AS \"" + array[1] + "\"";
        }
        else finalni = " COUNT(" + array[0] + ")";
        return finalni;
    }

    private String Min(String s) {
        boolean flag = false;
        String gotov = "";
        for(int i = 0; i< s.length(); i++){
            if(flag && s.charAt(i) == ')'){
                break;
            }
            if(flag && s.charAt(i) != '"'){
                gotov = gotov + s.charAt(i);
            }

            if(s.charAt(i) == '('){
                flag = true;
            }

        }
        String finalni = "";
        String[] array = gotov.split(",");
        if(array.length > 1){
            finalni = " MIN(" + array[0] + ")" + " AS \"" + array[1] + "\"";
        }
        else finalni = " MIN(" + array[0] + ")";
        return finalni;
    }

    private String Max(String s) {
        boolean flag = false;
        String gotov = "";
        for(int i = 0; i< s.length(); i++){
            if(flag && s.charAt(i) == ')'){
                break;
            }
            if(flag && s.charAt(i) != '"'){
                gotov = gotov + s.charAt(i);
            }

            if(s.charAt(i) == '('){
                flag = true;
            }

        }
        String finalni = "";
        String[] array = gotov.split(",");
        if(array.length > 1){
            finalni = " MAX(" + array[0] + ")" + " AS \"" + array[1] + "\"";
        }
        else finalni = " MAX(" + array[0] + ")";
        return finalni;
    }


    private String OrderByDesc(String s) {
        boolean flag = false;
        String gotov = "";
        for(int i = 0; i< s.length(); i++){
            if(flag && s.charAt(i) == ')'){
                break;
            }
            if(flag && s.charAt(i) != '"'){
                gotov = gotov + s.charAt(i);
            }

            if(s.charAt(i) == '('){
                flag = true;
            }

        }
        String finalni = "";
        String[] array = gotov.split(",");
        for(String a : array){
            System.out.println(a);
        }
        if(array.length > 1){

            for(int brr = 0; brr<array.length;brr++){
                if(brr == 0)
                {
                    finalni = array[0] + " DESC";
                }
                else finalni =  finalni+ ","  + array[brr] + " DESC";
            }
        }
        else finalni = array[0] + " DESC";
        return finalni;
    }

    private String OrderBy(String s) {
        boolean flag = false;
        String gotov = "";
        for(int i = 0; i< s.length(); i++){
            if(flag && s.charAt(i) == ')'){
                break;
            }
            if(flag && s.charAt(i) != '"'){
                gotov = gotov + s.charAt(i);
            }

            if(s.charAt(i) == '('){
                flag = true;
            }

        }
        String finalni = "";
        String[] array = gotov.split(",");
        for(String a : array){
            System.out.println(a);
        }
        if(array.length > 1){

                for(int brr = 0; brr<array.length;brr++){
                    if(brr == 0)
                    {
                        finalni = array[0] + " ASC";
                    }
                    else finalni =  finalni+ ","  + array[brr] + " ASC";
                }
        }
        else finalni = array[0] + " ASC";
        return finalni;
    }


    private String WhereBetween(String s) {
        boolean flag = false;
        String gotov = "";
        for(int i = 0; i< s.length(); i++){
            if(flag && s.charAt(i) == ')'){
                break;
            }
            if(flag && s.charAt(i) != '"'){
                gotov = gotov + s.charAt(i);
            }

            if(s.charAt(i) == '('){
                flag = true;
            }

        }
        String finalni = "";
        String[] array = gotov.split(",");
        for(String a : array){
            System.out.println(a);
        }
        finalni = array[0] + " BETWEEN " + array[1] + " AND " + array[2];
        return finalni;
    }

    private String Query(String s){
        boolean flag = false;
        String gotov = "";
        for(int i = 0; i< s.length(); i++){
            if(flag && s.charAt(i) == '"'){
                break;
            }
            if(flag){
                gotov = gotov + s.charAt(i);
            }

            if(s.charAt(i) == '"'){
                flag = true;
            }

        }
        return gotov;
    }

    private String Where(String s){
        boolean flag = false;
        String gotov = "";
        for(int i = 0; i< s.length(); i++){
            if(flag && s.charAt(i) == ')'){
                break;
            }
            if(flag && s.charAt(i) != '"'){
                gotov = gotov + s.charAt(i);
            }

            if(s.charAt(i) == '('){
                flag = true;
            }

        }
        String finalni = "";
        String[] array = gotov.split(",");
        if(array[1].equals("like")){
            finalni = array[0] + " " + array[1]+ " " + "'" +array[2] + "'";
        }else {
            for (String a : array) {
                finalni = finalni + " " + a;
            }
        }
        return finalni;
    }

    private String Avg(String s){
        boolean flag = false;
        String gotov = "";
        for(int i = 0; i< s.length(); i++){
            if(flag && s.charAt(i) == ')'){
                break;
            }
            if(flag && s.charAt(i) != '"'){
                gotov = gotov + s.charAt(i);
            }

            if(s.charAt(i) == '('){
                flag = true;
            }

        }
        String finalni = "";
        String[] array = gotov.split(",");
        if(array.length > 1){
            finalni = " AVG(" + array[0] + ")" + " AS \"" + array[1] + "\"";
        }
        else finalni = " AVG(" + array[0] + ")";
        return finalni;
    }

    private String Select(String s){
        boolean flag = false;
        String gotov = "";
        for(int i = 0; i< s.length(); i++){
            if(flag && s.charAt(i) == ')'){
                break;
            }
            if(flag && s.charAt(i) != '"'){
                gotov = gotov + s.charAt(i);
            }

            if(s.charAt(i) == '('){
                flag = true;
            }

        }
        String finalni = "";
        String[] array = gotov.split(",");
        if(array.length > 1){
            for(int brr = 0; brr<array.length;brr++){
                if(brr == 0){
                    finalni = finalni + array[brr];
                }
                else{
                    finalni =  finalni+ ","  + array[brr];
                }
            }

        }
        else finalni = array[0];
        return finalni;
    }

    private String WhereEndsWith(String s){
        boolean flag = false;
        String gotov = "";
        for(int i = 0; i< s.length(); i++){
            if(flag && s.charAt(i) == ')'){
                break;
            }
            if(flag && s.charAt(i) != '"'){
                gotov = gotov + s.charAt(i);
            }

            if(s.charAt(i) == '('){
                flag = true;
            }

        }
        String finalni = "";
        String[] array = gotov.split(",");
        for(String a : array){
            System.out.println(a);
        }
        finalni = array[0] + " like " + "\'%" + array[1] + "\'";
        return finalni;
    }

    private String WhereStartsWith(String s){
        boolean flag = false;
        String gotov = "";
        for(int i = 0; i< s.length(); i++){
            if(flag && s.charAt(i) == ')'){
                break;
            }
            if(flag && s.charAt(i) != '"'){
                gotov = gotov + s.charAt(i);
            }

            if(s.charAt(i) == '('){
                flag = true;
            }

        }
        String finalni = "";
        String[] array = gotov.split(",");
        for(String a : array){
            System.out.println(a);
        }
        finalni = array[0] + " LIKE " + "\'" + array[1] + "%\'";
        return finalni;
    }

    private String WhereContains(String s){
        boolean flag = false;
        String gotov = "";
        for(int i = 0; i< s.length(); i++){
            if(flag && s.charAt(i) == ')'){
                break;
            }
            if(flag && s.charAt(i) != '"'){
                gotov = gotov + s.charAt(i);
            }

            if(s.charAt(i) == '('){
                flag = true;
            }

        }
        String finalni = "";
        String[] array = gotov.split(",");
        for(String a : array){
            System.out.println(a);
        }
        finalni = array[0] + " LIKE " + "\'%" + array[1] + "%\'";
        return finalni;
    }

}
