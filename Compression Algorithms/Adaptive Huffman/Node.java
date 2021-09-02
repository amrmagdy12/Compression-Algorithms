package com.company;

import java.util.List;

public class Node {

    protected Node leftNode = null;
    protected Node rightNode = null;
    protected Node parent = null ;
    protected boolean isNYT = false ;
    protected boolean isLeaf = false ;

    protected int nodenumber ;
    private int weight ;
    private char nodesymb ;

    public Node (int Node_numb){
        this.parent = null ;
        this.weight = 0 ;
        this.nodenumber = Node_numb ;
    }

    // Internal node constructor
    public Node(Node parent ,Node left , Node right , char nodesymbol , int weight ,int Node_numb ) {
       this.parent  = parent ;
       this.leftNode = left ;
       this.rightNode = right ;
       this.nodesymb = nodesymbol ;
       this.weight =weight ;
       this.nodenumber = Node_numb;
    }

    // Nyt node constructor
    public Node(Node parent ,int Node_numb) {
        this.parent  = parent ;
        this.weight = 0 ;
        this.nodenumber = Node_numb;
        this.isNYT = true;
    }
    // leaf node constructor
    public Node(Node parent , char symb ,int Node_numb) {
        this.parent  = parent ;
        this.nodenumber =  Node_numb ;
        this.nodesymb = symb ;
        this.weight = 1;
        this.isLeaf = true ;
    }

    void increment() {this.weight++ ;}

    public boolean isLeaf() {

        return this.isLeaf;

    }

    public boolean isNYT() {

        return this.isNYT;

    }

    //*****************************//
    /* Setters and Getters*/
    //**************************//
    public Node getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }

    public int getNodenumber() {
        return nodenumber;
    }

    public void setNodenumber(int nodenumber) {
        this.nodenumber = nodenumber;
    }

    public char getNodesymb() {
        return nodesymb;
    }

    public void setNodesymb(char nodesymb) {
        this.nodesymb = nodesymb;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        if (this.isLeaf){
            return " Node number : "+this.nodenumber + " weight: "+this.weight +" nodesymb: "+this.nodesymb+ " AM LEAF " ;
        }
        else if(this.isNYT){
            return " Node number : "+this.nodenumber+" weight: "+this.weight+" AM NYT";
        }
        else {
            return " Node number : "+this.nodenumber+" weight: "+this.weight+" AM INTERNAL" ;
        }
    }
}
