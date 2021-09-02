package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AdaptiveEncoder {

    private static  Scanner scanner = new Scanner(System.in) ;

    private static Map<String , Character> decodeShortcode  = new HashMap<>();



    AdaptiveEncoder() { }

    public static void main(String [] args) {
        AdaptiveEncoder Encoder = new AdaptiveEncoder();
        System.out.println("Enter message :");
        String s = scanner.nextLine();
        decodeShortcode.put("00" ,'A') ;
        decodeShortcode.put("01" ,'B') ;
        decodeShortcode.put("10" ,'C') ;

        Tree tree = new Tree(s ,decodeShortcode) ;
        System.out.println(Encoder.startDecode(s, decodeShortcode));

      /*  int numchoice, choice;
        String message = "";

        System.out.println("1) start Encode \n 2) start Decode ");
        System.out.println(" choice: ");
        numchoice = scanner.nextInt();


        if (numchoice == 1) {


            System.out.println(" 1) Input from a file and output on file \n 2)read from the console and output on console ");
            System.out.println(" choice : ");
            choice = scanner.nextInt();

            if (choice == 1) {

                File file = new File("ReadFile.txt");
                if (!file.exists()) {
                    System.out.println("Error : file not found ");
                    return;
                }
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(file));
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                String st;
                try {
                    while ((st = br.readLine()) != null)
                        message += st;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                Tree tree = new Tree(message);
                String Encoded = Encoder.startEncode(tree, message);
                //----------------------------------------------------------------------
                // Outputing on file
                File file1 = new File("Output.txt");

                BufferedWriter writer = null;
                if (!file1.exists()) {
                    try {
                        file1.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {

                    writer = new BufferedWriter(new FileWriter(file1));
                    writer.write(Encoded);
                    writer.newLine();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // read from console an output on the console
                message = scanner.nextLine();
                Tree tt = new Tree(message);

                System.out.println(Encoder.startEncode(tt, message));

            }

        } else {
            // start decode
            Tree Dtree = new Tree(message, decodeShortcode);

            System.out.println(" 1) Input from a file and output on file \n 2)read from the console and output on console ");
            System.out.println(" choice : ");
            choice = scanner.nextInt();

            if (choice == 1) {

                File file = new File("ReadFile.txt");
                if (!file.exists()) {
                    System.out.println("Error : file not found ");
                    return;
                }
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(file));
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                String st;
                try {
                    while ((st = br.readLine()) != null)
                        message += st;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                Tree tree = new Tree(message);
                String Encoded = Encoder.startDecode(message, decodeShortcode);

                //--------------------------------------------------------

                // Outputing on file
                File file1 = new File("Output.txt");

                BufferedWriter writer = null;
                if (!file1.exists()) {
                    try {
                        file1.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {

                    writer = new BufferedWriter(new FileWriter(file1));
                    writer.write(Encoded);
                    writer.newLine();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // read from console and output on the console (Decode)
                System.out.println("Enter message to be decoded: ");

                String tobedecoded = scanner.nextLine();
                System.out.println(Encoder.startDecode(tobedecoded, decodeShortcode));
            }


        }
*/

        }
    //----------------------------------------------------------


    public String startEncode(Tree tree ,String message) {
        ArrayList<Integer> buffer ;
        String encodedmessage = "" ;
        for (int i = 0; i < message.length(); i++) {
            buffer = new ArrayList<>() ;
            if (tree.contains(message.charAt(i))){
                 int len = tree.getCode(message.charAt(i) ,true ,buffer);

                 // loop from the end
                for (len = len -1  ; len >= 0 ; len-- ){
                      encodedmessage+=buffer.get(len) ;
                }
                tree.insertinto(message.charAt(i));
            }
            else{

                String code = tree.getCode(message.charAt(i));
                for (int k = 0  ;k  < code.length() ; k++){
                    encodedmessage+=code.charAt(k) ;
                }
                tree.insertinto(message.charAt(i));
            }

        }
        decodeShortcode = fillDecodeshortCode(message);

        return encodedmessage ;
    }

    private Map<String, Character> fillDecodeshortCode(String message) {
        Map<String , Character> decodeShortcode  = new HashMap<>();
        ArrayList<Character>dic=new ArrayList<>();
        for (int index = 0; index < message.length(); index++) {
            if(!dic.contains(message.charAt(index)))
                dic.add(message.charAt(index));
        }

        int resultLength=(int)Math.ceil(Math.log(dic.size()) / Math.log(2));

        for (int i = 0 ; i < dic.size() ; i++){
            String res = "";
            res+= Integer.toBinaryString(i);
            while (res.length() < resultLength){
                res = "0"+res ;
            }
            decodeShortcode.put(res,dic.get(i));
        }

        return decodeShortcode;
    }

    public String startDecode(String decodedMessage , Map<String , Character> decodeShortcode) {
        Tree decTree = new Tree(decodedMessage , decodeShortcode) ;

        // to get length of decodeshortcode
        int keylength=(int)Math.ceil((Math.log(decodeShortcode.size()) / Math.log(2)));

        String res = "" ;
        for (int i = 0; i < decodedMessage.length(); i++) {
            if (i == 0){
               String temp = decodedMessage.substring(i,i+keylength) ;
                Character symb = decodeShortcode.get(temp);
                decTree.insertinto(symb);
                res+=symb;
                i+=keylength-1;
            }
            else {
               String code=decodedMessage.charAt(i)+"";
               Node check = decTree.getNode(code) ;
               while (!(check.isNYT() || check.isLeaf())){
                   i++;
                  code+= decodedMessage.charAt(i);
                  check= decTree.getNode(code);
               }
               if(check.isNYT()){// yb2a ele b3de shortcode
                   String x = decodedMessage.substring(i+1,i+keylength+1) ;
                   Character symb= decodeShortcode.get(x);
                   decTree.insertinto(symb);
                   res+=symb;
                   i+=keylength;
               }
               else if(check.isLeaf()){//yb2a 7rf ad5lo fe el message
                   decTree.insertinto(check.getNodesymb());
                   res+=check.getNodesymb();
               }


            }
        }
        return res;
    }
}
