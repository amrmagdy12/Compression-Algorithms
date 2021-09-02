package com.company;

import javax.swing.plaf.synth.SynthMenuBarUI;
import java.net.SecureCacheResponse;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static ArrayList<String> dictionary = new ArrayList<>();
    private static Scanner scan = new Scanner(System.in);

    private static ArrayList<String> picked  = new ArrayList<>();
    public static void main(String[] args) {
        // write your code here

        for (int i = 0; i < 128; i++) {
            dictionary.add((char) i + "");
        }
        ArrayList<Integer> tobedecopressed = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            tobedecopressed.add(scan.nextInt());
        }
        System.out.println("original data :" +decompress(tobedecopressed));

    }

    private static String decompress(ArrayList<Integer> tobedecopressed) {
         int start  ;
        String result  = "";
        for (int i = 0; i < tobedecopressed.size(); i++) {
           if(i == 0 ){
               Integer index= tobedecopressed.get(i);
               String prevsymbol = dictionary.get(index);
               result+=prevsymbol ;
               picked.add(prevsymbol) ;

           }
           else{

               Integer ind = tobedecopressed.get(i);
               String symb = dictionary.get(ind) ;
               picked.add(symb);
               char firstsymb= symb.charAt(0);
               dictionary.add(picked.get(i-1)+firstsymb+"");
               result+=symb ;

           }

        }
        return result ;
    }
}
