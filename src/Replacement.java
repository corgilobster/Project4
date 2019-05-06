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
        System.out.println("hello world");
        //frames = Integer.parseInt(args[1]);
        int counter = 0, // used to count how many lines were processed for averaging
                FIFO_sum = 0, // sum of all fifo faults
                LRU_sum = 0, // sum of all LRU faults
                RAND_sum = 0, // sum of all random faults
                OPT_sum = 0; // sum of all optimized faults

        // test code
        String testS = "1 2 3 1 2 1 4 1";
        int[] arr = stringToIntArray(testS);
        for(int i : arr) System.out.println(i + 1);
        frames = 3;
        System.out.println("FIFO FAULTS: " + FIFO(testS));

        // end test code


        // commented all this out for testing
        // reads the file
        /*try(BufferedReader br = new BufferedReader(new FileReader(args[0]))){
            String line; // string used for each line to be passed into functions

            // loop for every line
            while((line = br.readLine()) != null){
                // sum all faults for every line together
                FIFO_sum += FIFO(line);
                LRU_sum += LRU(line);
                RAND_sum += Random(line);
                OPT_sum += Optimized(line);
                counter++; // add one to the line counter
            }
        }
        catch (Exception e){}
        // process data to find averages and prints them
        System.out.println("Here are the average page faults for each method:" +
                "\nFIFO: " + (FIFO_sum/counter) +
                "\nLRU: " + (LRU_sum/counter) +
                "\nRandom: " + (RAND_sum/counter) +
                "\nOptimized: " + (OPT_sum/counter));*/
    }
    // First In First Out method
    public static int FIFO(String s){
        int[] storage = new int[frames]; // main array to check for page faults
        Queue intQueue = new LinkedList(); // queue for checking which value needs replacement
        int[] intList = stringToIntArray(s); // convert string to int array
        int faultCount = 0; // counter for faults
        boolean isFound;

        for(int x : intList){
            isFound = false;
            for(int i = 0; i < storage.length; i++){
                if(x == storage[i]){
                    isFound = true;
                    break;
                }
            }
            if(isFound == false){
                faultCount++;
                try{
                    intQueue.add(x);
                } catch(Exception e){
                    int removed = (Integer)intQueue.remove();
                    for(int i = 0; i < storage.length; i++){
                        if(storage[i] == removed) storage[i] = x;
                        intQueue.add(x);
                    }
                }
            }
        }


        return 0;
    }

    public static int LRU(String s){
        int[] storage = new int[frames];
        return 0;
    }

    public static int Random(String s){
        int[] storage = new int[frames];
        return 0;
    }

    public static int Optimized(String s){
        int[] storage = new int[frames];
        return 0;
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
