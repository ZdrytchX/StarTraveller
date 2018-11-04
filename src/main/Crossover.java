/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import java.util.*;

/**
 *
 * @author ZdrytchX
 */
public class Crossover {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //DEMO: REMOVE BEFORE FLIGHT >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //long sample for practical case
        int DemoChrome1[] = {1,2,4,5,3,8,9,6,16,7,17,18,19,20,14,15,13,11,12,10,1};
        int DemoChrome2[] = {1,5,12,13,7,6,9,11,16,17,8,4,3,18,19,20,14,2,15,10,1};

        //int DemoChrome1[] = {1,2,3,4,5,1};
        //int DemoChrome2[] = {1,3,5,2,4,1};
        //small sample for visibility, you need to modify SETTINGS to accomadate
        /*
        int DemoChrome1[] = {1,2,4,5,3};
        int DemoChrome2[] = {3,2,5,1,4};
        */
        //DEMO: REMOVE BEFORE FLIGHT <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        
        
                ///////==============///////
                ///////MAIN CROSSOVER///////
                ///////==============///////

        //SETTINGS
        //Substitute these values as needed ->->->->->->->->->->->->->->->->->->
        int child1[] =     DemoChrome1;//Import Parent 1 here
        int[] genePool1 = new int[DemoChrome1.length];
        System.arraycopy(child1, 0, genePool1, 0, DemoChrome1.length);
        
        int child2[] =     DemoChrome2;//Import Parent 2 here
        int[] genePool2 = new int[DemoChrome2.length];
        System.arraycopy(child2, 0, genePool2, 0, genePool2.length);
            
        //Does not wrap around e.g. swapping gene slot 0 and last slot
        double swapWidthMax = 0.4;//0.1     Must be < 1.0
        double swapWidthMin = 0.1;//0.05   Must be <= swapWidthMax
        
        //Probability of swapping a random gene
        double mutationThreshold = 0.05;//typical value 0.001 - 0.01
        
        //Mutation PROBABILITY condition is executed X times per parent/child cycle
        //typical value = 1(?) but doesn't scale with size. Assign it to chrome
        //size and you get per-gene equivalent-ish probability of mutation, even 
        //though it can mutate the same gene twice
        int mutationAttempts = (int)(genePool1.length * 1.0);//scale on size, per-gene probability
        //int mutationAttempts = 1;//per-cycle single-gene probability
        
        int numSwaps = 1;//Minimum swaps per parent/child cycle
        double extraSwaps = 1.5;//Random number of extra swaps per cycle, >1.0 to do anything due to rounding
                //e.g. value 1.5 = 33.3% chance of adding 1 extra swap
                //can act as extra mutation
        numSwaps += (int)(extraSwaps * Math.random() );
        ////////////////////////////////////////////////////////////////////////
        /////END OF SETTINGS/////
        //<-<-<-<-<-<-<-<-<-<-Do not modify any numbers below here unless necessary
        //"demo: remove before flight" print statements are not necessary
        
        int chromeSize = genePool1.length;//cut down on processing time
        
        //print imported chromosomes
        System.out.println("Parent 1 Imported: " + Arrays.toString(child1));
        System.out.println("Parent 2 Imported: " + Arrays.toString(child2));
        //Swap function only swaps once, repeat times for multiple segment swaps in different locations
        for(int i=0; i < numSwaps; i++)
        {
            swapGenes(child1, child2, swapWidthMin, swapWidthMax, chromeSize - 2);
        }
        
        //Random Mutation
        System.out.println("\n>Child1 selected for mutation: "+Arrays.toString(child1));
        mutateGene(child1, genePool1, mutationThreshold, mutationAttempts, chromeSize - 2);
        System.out.println("\n>Child2 selected for mutation: "+Arrays.toString(child2));
        mutateGene(child2, genePool2, mutationThreshold, mutationAttempts, chromeSize - 2);
        
        //DEMO: REMOVE BEFORE FLIGHT >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        System.out.println("\nChild1 Before Fix: "+Arrays.toString(child1));
        System.out.println("\nChild2 Before Fix: "+Arrays.toString(child2));
        //DEMO: REMOVE BEFORE FLIGHT <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        
        //checkGenes fixes duplicate genes
        //This may also be used to fix other chromosomes in contexts as well
        checkGenes(child1, genePool1, chromeSize - 2);
        System.out.println("Child1 after fix: "+Arrays.toString(child1));
        
        System.out.println("\n>Child2 after mutation: "+Arrays.toString(child2));
        /*
        System.out.println(Arrays.toString(child2));
        for (int i=0; i < child2.length; i++)
        {
            int j = child2[i];
            System.out.println(j);
        }*/
        checkGenes(child2, genePool2, chromeSize - 2);
        
        
        //DEMO: REMOVE BEFORE FLIGHT >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        System.out.println("Child2 after fix: "+Arrays.toString(child2));
        /*
        for (int i=0; i < child2.length; i++)
        {
            int j = child2[i];
            System.out.println(j);
        }*/
        //DEMO: REMOVE BEFORE FLIGHT <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    }
    
    //works only on adjacent genes e.g. index 2 and 3 but not 2 and 5.
    public static void swapGenes(int[] chromeA, int[] chromeB, double swapWidthMin, double swapWidthMax, int chromeSize)
    {
        //This ensures swapGenes() remains within the array size. 
        int randomNumChanges;
        randomNumChanges = (int)( chromeSize * (
                (swapWidthMax - swapWidthMin) * Math.random()  + swapWidthMin ) );
        //Remainder is used to randomly pick an index to swap
        int randomIndex = (int)( Math.random() * (chromeSize - randomNumChanges ) ) + 1;
        
        //DEMO: REMOVE BEFORE FLIGHT - Indicator for multiple swaps
        System.out.println("Swap @ index "+randomIndex+", "+randomNumChanges+" changes:");
        
        //ignore first gene
        for(int i = 0; i < randomNumChanges; i++)
        {
            int j = randomIndex + i;//alternatively you could use a random function for increased spacing
            int SwapeeVar = chromeA[j];
            chromeA[j] = chromeB[j];
            chromeB[j] = SwapeeVar;
            System.out.println(" "+SwapeeVar + " swapped with " + chromeA[j] + " @ " + j);
        }
    }
        
    //changes a single random gene of a random index
    public static void mutateGene(int[] chrome, int[] genePool, double mutationThreshold, int mutationAttempts, int chromeSize)
    {
        //Calculate how many mutations to do based on threshold probability for this cycle
        int numMutations = 0;
        for(int i = 0; i < mutationAttempts; i++)
            if( Math.random() < mutationThreshold)
            {
                numMutations++;
            }
        
        //ignore first gene
        for(int i = 0; i < numMutations; i++)
        {
            //weird +/- shit avoids first/last star problem, assuming first star
            //is always 0
            int randomIndex = (int)( Math.random() * (chromeSize) ) + 1;
            int randomGene = (int)( Math.random() * (chromeSize) ) + 1;
            chrome[randomIndex] = genePool[randomGene];
            System.out.println("MUTATED @ Index "+randomIndex +" giving "+ genePool[randomGene]);//DEMO: REMOVE BEFORE FLIGHT
        }
    }
    
    //Finds duplicate genes and fixes them in a single chromosome
    //chromeSize has last star subtracted
    //genesAvail is the parent chromosome
    public static void checkGenes(int[] chrome, int[] genesAvail, int chromeSize)
    {
        int[] duplicatesCheck = new int[chromeSize];
        /*
        int[] tempChrome = new int[chromeSize];
        int[] tempGeneBank = new int[chromeSize];
        //Snip arrays to make them easier to deal with
        for(int i = 1;i < chromeSize + 1; i++)
        {
            tempChrome[i - 1] = chrome[i];
            tempGeneBank[i - 1] = genesAvail[i];
        }*/
            
    System.out.println("parent gene:");
    System.out.println(Arrays.toString(genesAvail));
    
    //Counts duplicate Genes in a chromosome
        for(int i = 0;i < chromeSize; i++)
        {
            int count = 0;
            int gene = genesAvail[i + 1];
            //ArrayList<Integer> seenGene = new ArrayList<>();
            //seenGene.add(0);
            //Compare the gene from the gene library (genesAvail) to the chrome genes
            //System.out.println("i = "+i);
            for(int k = 0;k < chromeSize; k++)
            {
                if(chrome[k + 1] == gene/* && genesAvail[i + 1] != seenGene.get(i)*/)
                {
                    count++;
                    //seenGene.add(genesAvail[i + 1]);
                    //System.out.println(" gene match " + chrome[k + 1]);
                }
            }
            duplicatesCheck[i] = count;
        }
        
        System.out.println("duplicatesCheck list:");
        System.out.println(Arrays.toString(duplicatesCheck));
        
        //missingGene list is flexible - needed for custom array size for optimisation
        ArrayList<Integer> missingGene = new ArrayList<>();

        for(int k = 0; k < chromeSize;k++)
        {
            if(duplicatesCheck[k] < 1){
                missingGene.add(genesAvail[k+1]);
                System.out.println(" Missing Gene added: " + genesAvail[k+1]);
            }
        }
        
        System.out.println("Starting main genefixer loop ");
        
    //Main Loop: Corrects duplicated chromosomes
        for(int i=0;i<chromeSize;i++)
        {
            int gene = chrome[i+1];
            //System.out.println("Checking Chromosome Gene " + chrome[i+1]);        
            //compare against gene library, j is used to ID which gene to replace duplicate
            for(int j = 1; j < chromeSize+1; j++)
            {
                //System.out.println("...Scanning Chromosome Gene " + genesAvail[j]);  
                //Only deal with duplicate genes
                if(gene == genesAvail[j] && duplicatesCheck[j-1] > 1)
                {
 
                    //Variation ensured by picking random missing gene.
                    int pickAGene = 0;//random removed to preserve parent relationship
                    int useGene = missingGene.get(pickAGene);
                    
                    System.out.println(" Chromosome Gene Replaced "+chrome[i+1] +" with "+ useGene);  
                    chrome[i+1] = useGene;//genesAvail[useGene];//Substitute duplicate with missing gene
                                        
                    missingGene.remove(pickAGene);//remove used gene from list
                    duplicatesCheck[j-1]--;//We fixed one, make sure to remove it
                    //duplicatesCheck[useGene]++;
                }
            }
        }
    }
}