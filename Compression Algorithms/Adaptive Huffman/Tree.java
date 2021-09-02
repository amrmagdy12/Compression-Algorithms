package com.company;

import java.time.temporal.ChronoUnit;
import java.util.*;

public class Tree {
    public int NODE_NUMBER = 100 ;

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    private  Node root ;
    private Node NYT  ; // Current NYT node

    // da 34an a2dr a access 3al nodes asr3 3ala 7asab el symbol bt3ha
    private Map<Character ,Node> seenNodes = new HashMap<>() ;

    // da 34an el trteb el nodes 3ala 7asab el weight bt3ha
    private static Map<Character ,String> shortcodes = new HashMap<>() ;

    //  private ArrayList<Node> order = new ArrayList<>() ;

    // for ENCODED TREEE
    public Tree(String message) {
        root = new Node(NODE_NUMBER);
        NYT = root ;

        fillShortCode(message) ;
    }

    // for decoded TREEE
    public Tree (String message , Map<String ,Character> shortcde){
        root = new Node(--NODE_NUMBER);
        NYT = root ;
    }

    public void insertinto (char symbol){
        if(seenNodes.containsKey(symbol)){
            Node nsymbol = seenNodes.get(symbol);
            updatetree(nsymbol) ;
        }else {
            Node parent = givebirth(symbol);
            updatetree(parent) ;
        }
    }

    public boolean contains(char symbol){
        if (seenNodes.containsKey(symbol)){
            return true ;
        }
        else{
            return false ;
        }

    }

    public  Node getNode(String code){
        Node curr = root ;
        for (int i = 0 ; i < code.length() ; i++){
            if (code.charAt(i) == '0'){
                curr = curr.leftNode ;
            }
            else
                curr = curr.rightNode ;

        }
        return curr ;
    }

    private void updatetree(Node node) {
        while (node != root ){
            // here handling swap {to be continued }
            node.increment();

            Node tobeswapped = getNodetobeswapped(node);
            if (tobeswapped != null) {
                swap(node ,tobeswapped);
            }

            node = node.getParent() ;

        }
        root.setWeight(root.rightNode.getWeight() + root.leftNode.getWeight());
}

    private void swap(Node node , Node tobeswapped) {
        // hat el parent bta3 kol wa7ed fehom
        Node nodeParent = node.getParent() ;
        Node tobeswappedParent = tobeswapped.getParent() ;

        // need to know if nodes were left or right child
        boolean nodewasOnRight = false  , tobeswappedwasOnRight = false ;

        if (nodeParent.rightNode == node){
            nodewasOnRight = true ;
        }
        if (tobeswappedParent.rightNode == tobeswapped){
            tobeswappedwasOnRight = true ;
        }

        if (nodewasOnRight){
            nodeParent.rightNode = tobeswapped ;
        }
        else {
            nodeParent.leftNode = tobeswapped ;
        }

        if (tobeswappedwasOnRight){
             tobeswappedParent.rightNode = node ;
        }
        else{
             tobeswappedParent.leftNode = node ;
        }

        // update parent pointers

        node.parent = tobeswappedParent ;
        tobeswapped.parent = nodeParent ;

        int nodenumber = node.getNodenumber();
        int swappednodenumber = tobeswapped.getNodenumber() ;

        // brg3 el node number b3d el swap
        tobeswapped.nodenumber = nodenumber ;
        node.nodenumber = swappednodenumber ;


    }

    private Node getNodetobeswapped(Node node) {
            boolean found = false ;
            boolean inElse = false ;
            Node curr = root ;
            while (curr != null) {
                if(curr.leftNode != null || curr.rightNode != null){
                    if (curr.leftNode.getNodenumber() > node.getNodenumber() && node.getWeight() > curr.leftNode.getWeight() && curr.leftNode != node.getParent()){
                        found = true;
                        curr = curr.leftNode ;
                        break;
                    }
                    else if(curr.rightNode.getNodenumber() > node.getNodenumber() && node.getWeight() > curr.rightNode.getWeight() && curr.rightNode!=node.getParent()){
                        found= true ;
                        curr = curr.rightNode ;
                        break ;
                    }
                    else {
                        curr = curr.leftNode;
                    }
                }
                else{
                    if(curr.isLeaf || curr.isNYT){
                        if(curr.getNodenumber() > node.getNodenumber() && node.getWeight() > curr.getWeight() && curr!=node.getParent()){
                            found= true ;
                            break ;
                        }
                        else {
                            break ;
                        }
                    }
                }

            }
            // law mal2osh fel na7ya el shemal yro7 yshofo fl ymen b2a
            if (!found){
                curr = root ;
                while (curr != null){
                    if(curr.leftNode != null && curr.rightNode != null){
                        if (curr.leftNode.getNodenumber() > node.getNodenumber() && node.getWeight() > curr.leftNode.getWeight() && curr.leftNode != node.getParent()){
                            found = true;
                            curr = curr.leftNode ;
                            break;
                        }
                        else if(curr.rightNode.getNodenumber() > node.getNodenumber() && node.getWeight() > curr.rightNode.getWeight() && curr.rightNode!=node.getParent()){
                            found= true ;
                            curr = curr.rightNode ;
                            break ;
                        }
                        else {
                            curr = curr.rightNode;
                        }
                    }
                    else{
                        if(curr.isLeaf || curr.isNYT){
                            if(curr.getNodenumber() > node.getNodenumber() && node.getWeight() > curr.getWeight() && curr!=node.getParent()){
                                found= true ;
                                break ;
                            }
                            else {
                                curr = curr.rightNode;
                            }
                        }
                        else {
                            break;
                        }
                    }
                }

            }
            // detecting if it's not its parent
            if (found){
                Node parent = node.parent ;
                while (parent != root){
                    if(parent != curr){
                        parent = parent.parent ;
                    }
                    else {
                        return null;
                    }
                }
            }
            else{
                return null;
            }
        return curr ;
    }

    private Node givebirth(char symbol) {
        Node leaf = new Node (NYT ,symbol ,--NODE_NUMBER) ;
        Node newNyt = new Node (NYT ,--NODE_NUMBER) ;

        seenNodes.put(symbol ,leaf) ;

        // updating current NYT
        Node oldNYT = NYT ;
        NYT.isNYT =  false ;
        NYT.setLeftNode(newNyt);
        NYT.setRightNode(leaf);

        NYT = newNyt ;


        return oldNYT ;
    }

private int Generatecode(Node node ,ArrayList<Integer>buffer){
        int length = 0 ;
        Node parent ;
        while (node.getParent() !=null){
             parent = node.getParent() ;

             if(parent.leftNode == node){
                 buffer.add(0) ;
                 length++ ;
             }
             else {
                 buffer.add(1) ;
                 length++ ;
             }
             node = parent ;
       }
      return length  ;
}
private static void fillShortCode(String message) {
        // yalla ya kimo
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
            shortcodes.put(dic.get(i),res);
        }
    }

public int getCode(char symb ,boolean seen, ArrayList<Integer> buffer){
    int length = 0 ;
    if (!seen){
        if(NYT == root) {
            return length;
        }
        String code = shortcodes.get(symb);
        return code.length();
    }
    else {
        length = Generatecode(this.seenNodes.get(symb),buffer) ;
    }
    return length ;
}

// de law el symbol bta3k lesa awl mara yegi
public String getCode(char symb ){
        // lesa hytzbt .

    ArrayList<Integer> buffer = new ArrayList<>() ;
    String NYTcode = "" ;
    Node symbNode = seenNodes.get(symb) ;

    Node node  = NYT;
    while (node.getParent() != null){

        if (node.getParent().rightNode == node){
            buffer.add(1) ;
        }
        else{
            buffer.add(0) ;
        }
        node = node.getParent() ;
    }
    int len = buffer.size();
    for (len = len -1  ; len >= 0 ; len-- ){
        NYTcode+=buffer.get(len) ;
    }
    return NYTcode + shortcodes.get(symb);
}

public void printTreeBreadth (Node root){
    Queue<Node> queue = new LinkedList<>() ;
        if (root == null ){
            return ;
        }
        queue.clear();
        queue.add(root) ;

        while (!queue.isEmpty()){

            Node node = queue.remove();
            System.out.println(node);
            if(node.rightNode != null) queue.add(node.rightNode) ;
            if (node.leftNode != null) queue.add(node.leftNode) ;

        }
}

    public  Map<Character, String> getShortcodes() {
        return shortcodes;
    }

    public  void setShortcodes(Map<Character, String> shortcodes) {
        Tree.shortcodes = shortcodes;
    }

}