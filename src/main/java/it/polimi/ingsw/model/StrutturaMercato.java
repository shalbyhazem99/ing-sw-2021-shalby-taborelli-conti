package it.polimi.ingsw.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class StrutturaMercato {
    private static final int COLUMNNUMBER = 4;
    private static final int ROWNUMBER = 3;

    private Biglia[][] matriceMercato; //[row][column]
    private Biglia bigliaAggiuntiva;
    private ConverterAbstract converter;
    /*
    @requires (row==null)<-->(column=!null) && (column==null)<-->(row!=null) && (0<=column<=3) && (0<=row<=2)
    @ensures /result = set of resources mapped from set of marbles contained in the selected row/column
     */
    public StrutturaMercato()
    {
        //Marbles : 4 white, 2 blue, 2 grey, 2 yellow, 2 purple, 1 red
        matriceMercato = new Biglia[ROWNUMBER][COLUMNNUMBER];
        matriceMercato[0][0] = new Biglia(ColoreBiglia.BIANCHE);
        matriceMercato[0][1] = new Biglia(ColoreBiglia.BIANCHE);
        matriceMercato[0][2] = new Biglia(ColoreBiglia.BIANCHE);
        matriceMercato[0][3] = new Biglia(ColoreBiglia.BIANCHE);
        matriceMercato[1][0] = new Biglia(ColoreBiglia.BLU);
        matriceMercato[1][1] = new Biglia(ColoreBiglia.BLU);
        matriceMercato[1][2] = new Biglia(ColoreBiglia.GRIGIE);
        matriceMercato[1][3] = new Biglia(ColoreBiglia.GRIGIE);
        matriceMercato[2][0] = new Biglia(ColoreBiglia.GIALLE);
        matriceMercato[2][1] = new Biglia(ColoreBiglia.GIALLE);
        matriceMercato[2][2] = new Biglia(ColoreBiglia.VIOLA);
        matriceMercato[2][3] = new Biglia(ColoreBiglia.VIOLA);
        bigliaAggiuntiva = new Biglia(ColoreBiglia.ROSSA);
        converter = new ConvertToNothing();
        shuffle();
    }
    public ArrayList<Risorsa> getResources(Integer row,Integer column)
    {
        ArrayList<Risorsa> temp;
        if(row!=null)
        {
            System.out.println("Select row n: "+row);
            temp = converter.convertMarbleToResources(getRow(row));
            slideRow(row);
        }
        else
        {
            System.out.println("Select row n: "+column);
            slideColumn(column);
            temp = converter.convertMarbleToResources(getColumn(column));
        }
        return temp;
    }
    /*
        @requires 0<=column<=3
        @ensures /result = selected column
     */
    public ArrayList<Biglia> getColumn(int column)
    {
        ArrayList<Biglia> temp = new ArrayList<>();
        for (int r=0;r<ROWNUMBER;r++)
        {
            temp.add(matriceMercato[r][column]);
        }
        return temp;
    }
    /*
        @requires 0<=row<=2
        @ensures /result = selected row
     */
    public ArrayList<Biglia> getRow(int row)
    {
        ArrayList<Biglia> temp = new ArrayList<>();
        for (int c=0;c<COLUMNNUMBER;c++)
        {
            temp.add(matriceMercato[row][c]);
        }
        return temp;
    }
    //@ensures provide a shuffled version of the previous array (considering array composed of the matrix and the additional murble
    public void shuffle()
    {
        ArrayList<Biglia> temp = new ArrayList<>();
        for(int r=0;r<ROWNUMBER;r++)
        {
            for(int c=0;c<COLUMNNUMBER;c++)
            {
                temp.add(matriceMercato[r][c]);
            }
        }
        temp.add(bigliaAggiuntiva);
        Collections.shuffle(temp);
        int i=0;
        for(int r=0;r<ROWNUMBER;r++)
        {
            for(int c=0;c<COLUMNNUMBER;c++)
            {
                matriceMercato[r][c] = temp.get(i);
                i++;
            }
        }
        bigliaAggiuntiva = temp.get(i);
    }

    public void print()
    {
        for(int r=0;r<ROWNUMBER;r++)
        {
            for(int c=0;c<COLUMNNUMBER;c++)
            {
                System.out.print("|"+matriceMercato[r][c].toString()+"|");
            }
            System.out.println();
        }
        System.out.println(bigliaAggiuntiva.toString());
        System.out.println("----------------------------------------------");
    }
    /*
    @requires 0<=row<=2
     */
    private void slideRow(int row)
    {
        Biglia oldAdditionalMurble = bigliaAggiuntiva;
        bigliaAggiuntiva = matriceMercato[row][0]; //the murble in the left position of the row will be the next additionalmurble
        matriceMercato[row][0] = matriceMercato[row][1]; //slide to left
        matriceMercato[row][1] = matriceMercato[row][2]; //slide to left
        matriceMercato[row][2] = matriceMercato[row][3]; //slide to left
        matriceMercato[row][3] = oldAdditionalMurble; //the old additional murble will be the murble in the right position of the row
    }
    /*
    @requires 0<=column<=3
     */
    private void slideColumn(int column)
    {
        Biglia oldAdditionalMurble = bigliaAggiuntiva;
        bigliaAggiuntiva = matriceMercato[0][column]; //the murble in the top position of the column will be the next additionalmurble
        matriceMercato[0][column] = matriceMercato[1][column]; //slide top
        matriceMercato[1][column] = matriceMercato[2][column]; //slide top
        matriceMercato[2][column] = oldAdditionalMurble;  //the old additional murble will be the murble in the bottom position of the column
    }
    public static void main(String [] args)
    {
        StrutturaMercato s = new StrutturaMercato();
        s.print();
        ArrayList<Risorsa> a = s.getResources(1,null);
        s.print();
        a = s.getResources(null,2);
        s.print();
    }

}
