import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Replacement {
    static int frames;
    public static void main(String[] args){
        //TODO: 1: take in command line arguments and assign them to variables
        //      2: finish readFile method
        //      3: finish project methods
        //System.out.println("hello world");
        //frames = Integer.parseInt(args[1]);
        int counter = 0, FIFO_sum = 0, LRU_sum = 0, RAND_sum = 0, OPT_sum = 0;

        String testS = "1 3 0 3 5 6 3";
        int[] arr = stringToIntArray(testS);
        //for(int i : arr) System.out.println(i);
        frames = 3;
        System.out.println("Testing: " + testS + ", " + frames + " frames");
        FIFO(arr);


        /*try(BufferedReader br = new BufferedReader(new FileReader(args[0]))){
            String line;
            while((line = br.readLine()) != null){
                //TODO: create a method that turns the string into an array of ints
                FIFO_sum += FIFO(line);
                LRU_sum += LRU(line);
                RAND_sum += Random(line);
                OPT_sum += Optimized(line);
                counter++;
            }
        }
        catch (Exception e){}

        System.out.println("Here are the average page faults for each method:" +
                "\nFIFO: " + (FIFO_sum/counter) +
                "\nLRU: " + (LRU_sum/counter) +
                "\nRandom: " + (RAND_sum/counter) +
                "\nOptimized: " + (OPT_sum/counter));*/
    }
    // TODO: loop for every number in the line

    public static void FIFO(int[] arr) {
        System.out.println("FIFO START");
        int[] storage = new int[frames]; //page frames holding page references
        int faultCount = 0;
        int replace = 0; //index of page replacement

        for(int i = 0; i < storage.length; i++) storage[i] = -1;

        for (int num : arr) { //for every number in the string
            boolean miss = true;
            //System.out.println("COMPARE: " + num);
            for (int f = 0; f < storage.length; f++) { //compare to each frame
                int page = storage[f];
                if(num == page) { //HIT
                    miss = false;
                    //System.out.println("HIT (" + num + ")");
                    break; //stop traversal
                }
            } //END OF storage traversal

            if(miss) {
                faultCount++;
                //System.out.println("FAULT #" + faultCount + ", Replace " + storage[replace] + " with " + num);
                storage[replace] = num;
                replace = (replace + 1) % storage.length;
            }
        } //END OF INPUT ARRAY

        System.out.println("FIFO FAULTS: " + faultCount);

        return;
    }

    public static void LRU(int[] arr){ //Least Recently Used
        System.out.println("LRU START");
        int[] storage = new int[frames]; //page frames holding page references
        int faultCount = 0;

        for (int i = 0; i < storage.length; i++) storage[i] = -1; //negative used as "empty" for initial storage
        for (int num : arr) { //for every number in the string
            boolean miss = true;
            //System.out.println("COMPARE: " + num);
            for (int frame = 0; frame < storage.length; frame++) { //compare to each frame
                int page = storage[frame];
                if(num == page) { //HIT
                    miss = false;
                    //System.out.println("HIT at Frame #" + frame);
                    for (int i = frame; i > 0; i--) { //all other numbers moved back in array
                        storage[i] = storage[i-1];
                    }
                    storage[0] = num; //first in array is now most recently used
                    break; //stop traversal
                }
            } //END OF storage

            if(miss) {
                faultCount++;
                //if(storage[storage.length-1] == -1) System.out.println("FAULT #" + faultCount); //initial faults
                //else System.out.println("FAULT #" + faultCount + ", Removing: " + storage[storage.length-1]);
                for(int i = storage.length-1; i > 0; i--) {
                    storage[i] = storage[i-1]; //all numbers moved back, least recently used is removed
                }
                storage[0] = num;
            }
        } //END OF INPUT ARRAY

        System.out.println("LRU FAULTS: " + faultCount);

        return;
    }

    public static int Random(String s){
        int[] storage = new int[frames];
        return 0;
    }

    public static void Optimized(int[] arr){
        int[][] storage = new int[frames][2]; //2D Array, for page ref and iterations until next use
        int faultCount = 0;
        for(int i = 0; i < storage.length; i++) {
            for(int j = 0; j < storage[i].length; j++) {
                storage[i][j] = 999; //initially large value that is always replaced
            }
        }

        System.out.println("OPTIMIZED START");

        for (int i = 0; i < arr.length; i++) { //for every number in the string
            int num = arr[i];
            boolean miss = true;
            for (int f = 0; f < storage.length; f++) { //compare to each frame
                int page = storage[f][0];
                if(num == page) { //HIT
                    miss = false;
                    System.out.println("HIT at Frame #" + f);
                    break;
                }
            }

            if(miss) {
                faultCount++;
                int greatest = -1;
                for(int f = 0; f < storage.length; f++) { //find page with greatest amount of time before next use
                    if(storage[f][1] > greatest) greatest = storage[f][1];
                }

                for(int f = 0; f < storage.length; f++) {
                    if(storage[f][1] == greatest) { //find and replace optimal page
                        System.out.print(storage[f][0] + " used next in " + storage[f][1] + " --> ");
                        System.out.println("FAULT #" + faultCount + ", Replace with: " + num);
                        storage[f][0] = num; //place page reference in storage
                        int nextUse = 1;
                        for(int j = i+1; j < arr.length; j++) { //For new num, count until next use
                            if(num == arr[j]) break;
                            nextUse++;
                        }
                        storage[f][1] = nextUse;
                        break;
                    }
                }
            } //END OF miss CASE
        } //END OF INPUT ARRAY

        System.out.println("OPTIMIZED FAULTS: " + faultCount);
        return;
    }

    boolean search(int[] A, int x){
        int p = 0, r = A.length-1;
        int q;
        while(p <= r){
            q = (p + r)/2;
            if(A[q] == x) return true;
            else if(A[q] < x) p = q+1;
            else r = q-1;
        }
        return false;
    }

    static int[] stringToIntArray(String s){
        ArrayList arr = new ArrayList();
        String num = "";
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == 32){
                arr.add(Integer.parseInt(num));
                num = "";
            }
            else if(i == s.length()-1){
                arr.add(Integer.parseInt(num + "" + s.charAt(i)));
            }
            else num += "" + s.charAt(i);
        }
        int[] intArr = new int[arr.size()];
        for(int i = 0; i < arr.size(); i++){
            intArr[i] = (int)arr.get(i);
        }
        return intArr;
    }


    public int[] readFile(){
        // TODO: use arrayList to copy lines into an array
        return null;
    }
}
