package com.company;

import java.util.*;

public class VectorQuantization
{   private int vectorHeight;
    private int vectorWidth;
    private int numOfBlocks;
    private String path;

    Map<int[][] , ArrayList<int[][]>> map = new HashMap<>() ;
    ArrayList<int[][]> VectorBlocks = new ArrayList<>();
    ArrayList<int[][]> Ceil_floor_list ;

    // final blocks
    Queue<double[][]> CodeBook = new LinkedList<>();

    public VectorQuantization(int vectorHeight, int vectorWidth, int numOfBlocks, String path) {
        this.vectorHeight = vectorHeight;
        this.vectorWidth = vectorWidth;
        this.numOfBlocks = numOfBlocks;
        this.path = path;
    }

    public void compress(){
        int [][]compressedImage= new int [4][4];
        //int [][] orignalImage=ImageRW.readImage(path);
        int [][]orignalImage={{1,2,7,9,4,11},
                {3,4,6,6,12,12},
                {4,9,15,14,9,9},
                {10,10,20,18,8,8},
                {4,3,17,16,1,4},
                {4,5,18,18,5,6}};
        ArrayList<int[][]> blocks= getBlocks(orignalImage);
        double[][] avg = getAVG(blocks);
        ArrayList<ArrayList<int[][]>> lastBlocks=new ArrayList<>();
        int counter=0;
        ArrayList<ArrayList<int[][]>> codeBook = split(lastBlocks, avg, blocks);
     int x= 0 ;

        // here is code


        /*return compressedImage;*/
    }
    private double[][] getAVG( ArrayList<int[][]> blocks){
        double [][] avg=new double[vectorHeight][vectorWidth];
        int numOfBlocks= blocks.size();
        for (int i = 0; i <vectorHeight ; i++) {
            for (int j = 0; j < vectorWidth; j++) {
                for (int k = 0; k < numOfBlocks; k++) {
                    avg[i][j]+=blocks.get(k)[i][j];

                }
                avg[i][j]/=numOfBlocks;
            }

        }
        return avg;
    }


    private  ArrayList<int[][]>  getBlocks(int [][] orignalImage){

        int rowCounter=0,colCounter=0;
        int numOfBlocks=(int)Math.ceil(orignalImage.length*orignalImage[0].length/(vectorWidth*vectorHeight));
        int[][][] blocks=new int [numOfBlocks][vectorHeight][vectorWidth];

        for (int i = 0; i <numOfBlocks ; i++) {
            for (int j = 0; j < vectorHeight; j++) {
                for (int k = 0; k <vectorWidth ; k++) {
                    blocks[i][j][k]=orignalImage[rowCounter+j][colCounter*vectorWidth+k];// hna hzaoed el image btol el block bl 3ard kol ma a3ml block gded
                    if(colCounter*vectorWidth+k == orignalImage[j].length-1 && j == vectorHeight-1 ){// hna hnzl le ta7t lma 2osel le taraf el sora
                        rowCounter+=vectorHeight;
                        colCounter=-1;
                    }
                }
            }
            colCounter++;
            VectorBlocks.add(blocks[i]) ;
        }
        return  VectorBlocks;
    }

    public ArrayList<ArrayList<int[][]>> split(ArrayList<ArrayList<int[][]>> ARR , double[][] avg , ArrayList<int[][]> blocks){
        ArrayList<int[][]> ceil_block_list = new ArrayList<>( );
        ArrayList<int[][]> floor_block_list = new ArrayList<>( );
        int [][] ceiledAvgBlock = ceil(avg);
        int [][] flooredAvgBlock = floor(avg) ;
        int value1  ;
        int value2 ;
        for (int i = 0 ; i < blocks.size() ;i++){
            value1 = 0 ;
            value2 = 0 ;
            for (int j = 0 ; j < vectorHeight ; j++){
                for (int k = 0; k <  vectorWidth ; k++) {
                    value1 +=Math.abs(blocks.get(i)[j][k] - ceiledAvgBlock[j][k]);
                    value2 +=Math.abs(blocks.get(i)[j][k] - flooredAvgBlock[j][k]);
                }
            }
            double Euclidean_Distance_for_ceiled = Math.sqrt((double) value1);
            double Euclidean_Distance_for_floored = Math.sqrt((double) value2);

            if(Euclidean_Distance_for_ceiled < Euclidean_Distance_for_floored){
                ceil_block_list.add(blocks.get(i));
            }
            else{
                floor_block_list.add(blocks.get(i)) ;
            }

        }

        if(ceil_block_list.size()+floor_block_list.size()==numOfBlocks/2){
            ArrayList<int[][]> last=new ArrayList<>();
            last.addAll(ceil_block_list);
            last.addAll(floor_block_list);
            ARR.add(last);
            return ARR;
        }
        else if (ARR.size() >= numOfBlocks)
        {
            return ARR;
        }
        else{

            ARR = split(ARR,getAVG(ceil_block_list),ceil_block_list);
            ARR = split(ARR,getAVG(floor_block_list),floor_block_list);
            return ARR;
        }
    }

    private int[][] floor(double[][] avg) {
        int [][] res = new int [vectorHeight][vectorWidth];
        for (int i = 0; i < vectorHeight ; i++) {
            for (int j = 0; j < vectorWidth ; j++) {
                res[i][j] =(int) (avg[i][j]-1);
            }
        }
        return res ;
    }

    private int [][] ceil(double [][] avg) {
        int [][] res = new int [vectorHeight][vectorWidth];
        for (int i = 0; i < vectorHeight ; i++) {
            for (int j = 0; j < vectorWidth ; j++) {
                res[i][j] = (int) (avg[i][j]+1);
            }
        }
        return res ;
    }

    public boolean isSame(ArrayList<int[][]> A1, ArrayList<int[][]> A2)
    {
        for (int i = 0; i < A1.size(); i++) {
            for (int j = 0; j < A1.get(i).length; j++) {
                for (int k = 0; k < A1.get(i)[j].length; k++) {
                    if (A1.get(i)[j][k] != A2.get(i)[j][k])
                        return false;
                }
            }
        }
        return true;
    }
}

