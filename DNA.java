import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;
////////////////////////////

public class DNA{
     public static String random_dna(int n) { // random dna sequence of length n
        if (n<1) return "";
        char[] C = new char[n];
        char[] DNA = {'A','C','G','T'};
        for(int i=0;i<n;i++)
            C[i] = DNA[ (int)(Math.random()*4) ];
        return new String(C); // converts char[] to String
    }//random_dna

    //load dna string from file
    public static Optional<String> load_dna(String filename) { 
        Optional<String> answer = Optional.empty();
	try {
            Scanner br = new Scanner(new File(filename));
            answer = Optional.of(br.nextLine());
            br.close();
        } // IOExceptions MUST be try-caught
        catch (IOException ie) {}
	return answer;
    }//load_dna

    // for debugging only!  only call on small strings
    public static void printmatrix(String A, String B, int[][] M) {
        if (A==null || B==null || M==null || M[0]==null) return;
        if (A.length() != M.length || B.length() != M[0].length) return;
        int rows = A.length();
        int cols = B.length();
        System.out.print("    ");
        for(int i=0;i<cols;i++) System.out.printf(" %2s ",B.charAt(i));
        System.out.println();
        for(int i=0;i<rows;i++) {
            System.out.print("   "+A.charAt(i));
            for(int k=0;k<cols;k++) {
                System.out.printf(" %2d ",M[i][k]);
            }//for k
            System.out.println();
        }//for i
    }//printmatrix

    public static void main(String[] args){
        //If sequences are in files:
        // Paths to the two files
        // String file1Path = "arman.dna"; // Replace with actual file name
        // String file2Path = "mother.dna"; // Replace with actual file name
        // Load sequences from the two files
        // Optional<String> dnaSequence1 = load_dna(file1Path);
        // Optional<String> dnaSequence2 = load_dna(file2Path);

        //If manually putting sequences:
        String file1Path = "s1";
        String file2Path = "s2";
        Optional<String> dnaSequence1 = Optional.of("ACGT");
        Optional<String> dnaSequence2 = Optional.of("CG");

        if (dnaSequence1.isPresent() && dnaSequence2.isPresent()) {
                // Get the sequences
                String sequence1 = dnaSequence1.get();
                String sequence2 = dnaSequence2.get();

                //System.out.println("Loaded Sequence 1: " + sequence1);
                //System.out.println("Loaded Sequence 2: " + sequence2);

                // Instantiate NeedlemanWunsch object (e.g., using scheme1)
                //Can use scheme1, scheme2, or scheme3
                NeedlemanWunsch nw = new scheme1(sequence1, sequence2);

                // Compute the alignment score
                int alignmentScore = nw.D();
                System.out.println("Alignment Score: " + alignmentScore);

                // Perform traceback (prints aligned sequences)
                nw.traceback(); //Comment out if handling long sequences :)

               printmatrix(sequence1, sequence2, nw.tracebackMatrix);
        } else {
                if (dnaSequence1.isEmpty()) {
                    System.out.println("Failed to load DNA sequence from: " + file1Path);
                }
                if (dnaSequence2.isEmpty()) {
                    System.out.println("Failed to load DNA sequence from: " + file2Path);
                }
    }
    }//MAIN
}//DNA