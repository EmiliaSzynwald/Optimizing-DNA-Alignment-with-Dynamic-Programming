# DNA Sequence Alignment with Needleman-Wunsch Algorithm

## Overview
This project implements the **Needleman-Wunsch algorithm**, a dynamic programming approach to align DNA sequences optimally. The algorithm introduces gaps and calculates the best alignment score based on custom scoring and penalty functions.

---

## Features
- Align two DNA sequences and compute an **optimal alignment score**.
- Handle **edge gaps** and **internal gaps** with customizable penalties.
- Support multiple scoring schemes using an abstract superclass and subclasses.
- Load DNA sequences from `.dna` files or input sequences manually.
- Debug and visualize alignment matrices.

---

## Algorithm Description

### Key Concepts
1. **Alignment**: Insert gaps to align sequences  
The alignment score is based on matches, mismatches, and penalties.
2. **Recursive Function**:  
`D(i, k)` computes alignment scores for the first `i` characters of `seqA` and `k` characters of `seqB` using:  
D(i, k) = max( D(i-1, k-1) + score(i, k) [diagonal move], D(i-1, k) + penalty(isEdge) [up move], D(i, k-1) + penalty(isEdge) [left move] )
3. **Dynamic Programming Matrix**: Replaces exponential recursion with efficient bottom-up matrix computation.
4. **Traceback**: Reconstructs the alignment by backtracking through the matrix.

---

## Implementation Details

### Abstract Superclass
The `NeedlemanWunsch` class defines:
- **`score(int i, int k)`**: Abstract function for match/mismatch scores.
- **`penalty(boolean edge)`**: Abstract function for gap penalties.
- Additional utility methods for alignment and traceback.

### Scoring Schemes
Three scoring schemes are implemented as subclasses:
1. **Scheme 1**: Simple scoring, no gap penalties.
2. **Scheme 2**: Match: +1, Mismatch: -1, Gap penalty: -1.
3. **Scheme 3**: Case-insensitive scoring with edge-gap distinctions:
- Match: +3
- Mismatch: -1
- Edge Gap: 0
- Internal Gap: -2

---

## Usage

### Prerequisites
- Option 1: Input two .dna files (e.g., arman.dna, artyan.dna).
- Option 2: Enter sequences manually when prompted.
- Option 3: Change in DNA.java which Scheme (e.g., scheme1, scheme2).
- Option 4: Comment out traceback feature (prints aligned sequences); recommended to remove if handling long DNA sequences

### Setup
1. Compile the source files:

          javac NeedlemanWunsch.java DNA.java
3. Run the program:
   
          java DNA
