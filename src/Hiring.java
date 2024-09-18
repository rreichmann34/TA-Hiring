///////////////////////////////////////////////////////////////////////////////
//
// Title: The Hiring Class conatins 3 methods for hiring TA's: the greedy hiring approach, the
// optimal approach, and the best approach when factoring in salary.
//
// Course: CS 300 Fall 2023
//
// Author: Remington Reichmann
// Email: rreichmann@wisc.edu
// Lecturer: Mouna Kacem
//
///////////////////////////////////////////////////////////////////////////////
//
// Persons: NONE
// Online Sources: NONE
//
///////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;

public class Hiring {

  public Hiring() {
  }

  /**
   * Helper method for greedyHiring() method. This method is intended to find a candidate that can
   * cover the most hours that have not already been covered
   * 
   * @param candidates          a list containing candidates that may be the best candidate
   * @param currentHoursCovered the hours that are already covered by the TA's who have already been
   *                            hired
   * 
   * @return the best candidate based on the parameters inputed. Returns null if the candidates list
   *         is empty
   */
  private static Candidate bestCandidate(CandidateList candidates, boolean[] currentHoursCovered) {
    int hours; // Placeholder for how many extra hours this TA would contribute
    int mostHours = 0; // The TA with the most extra hours to contribute ends up having this value
    Candidate returnCandidate = null;
    for (int i = 0; i < candidates.size(); i++) { // Loop through candidates list
      hours = 0; // Rest the hours for every new candidate
      for (int j = 0; j < candidates.get(i).getAvailability().length; j++) { // Loop through this
                                                                             // candidates
                                                                             // availability
        if (candidates.get(i).getAvailability()[j] == true && currentHoursCovered[j] == false) {
          hours++;
        }
      }
      // Sets the new best candidate if it has more hours than all of the previous TA's
      if (hours > mostHours) {
        returnCandidate = candidates.get(i);
        mostHours = mostHours(returnCandidate, currentHoursCovered);
      }
    }
    return returnCandidate;
  }

  /**
   * Helper method for the bestCandidate() method. This method calculates the amount of extra hours
   * that this candidate adds .
   * 
   * @param candidate the candidate to consider
   * 
   * @return the amount of extra hours this candidate provides
   */
  private static int mostHours(Candidate candidate, boolean[] currentHoursCovered) {
    int hours = 0; // Placeholder variable for how many hours this candidate contributes
    for (int i = 0; i < candidate.getAvailability().length; i++) { // Loop through this candidate's
                                                                   // availability
      if (candidate.getAvailability()[i] == true && currentHoursCovered[i] == false) {
        hours++;
      }
    }
    return hours;
  }

  /**
   * Helper method for greedyHiring() method and minCoverageHiring() method. It is intended to
   * retrieve the current hours that are already taken by another hired TA.
   * 
   * @param hired the group of TA's to loop through and see which hours they cover
   * @param size  the amount of hours that need to be covered throughout the day
   * 
   * @return a boolean array containing false wherever a new hired TA would be needed.
   */
  private static boolean[] getCurrentCandidateListHours(CandidateList hired, int size) {
    // Prevents errors by making sure the hired list actually contains TA's
    if (hired.size() == 0) {
      return new boolean[size];
    }


    boolean[] returnArray = new boolean[size]; // Initially a set of all false values
    for (int i = 0; i < hired.size(); i++) { // Loop through the hired TA's
      for (int j = 0; j < hired.get(i).getAvailability().length; j++) { // Loop through the
                                                                        // availability of this TA
        if (hired.get(i).getAvailability()[j] == true) {
          returnArray[j] = true;
        }
      }
    }
    return returnArray;
  }

  /**
   * Return a set of hires using a greedy strategy that always chooses whichever candidate will
   * cover the most hours.
   * 
   * @param candidates the list of all possible TA's who could be hired
   * @param hired      a list of TA's who have already been hired
   * @param hiresLeft  the amount of TA's we can hire
   * 
   * @return a list containing the greedy solution
   */
  public static CandidateList greedyHiring(CandidateList candidates, CandidateList hired,
      int hiresLeft) {

    // base case
    if (candidates.size() <= 0) {
      return hired;
    }

    // base case
    if (hiresLeft == 0) {
      return hired;
    }

    // Gets the hours already covered by the hired list
    boolean[] currentHoursCovered = new boolean[candidates.get(0).getAvailability().length];
    currentHoursCovered = getCurrentCandidateListHours(hired, currentHoursCovered.length);

    // Choose the best candidate in the candidates list. Add it to the hired list. Remove it from
    // the candidates list. Decrease hiresLeft.
    Candidate bestCandidate = bestCandidate(candidates, currentHoursCovered);
    hired.add(bestCandidate);
    candidates.remove(bestCandidate);
    hiresLeft--;
    return greedyHiring(candidates, hired, hiresLeft);
  }

  /**
   * Helper method for optimalHiring() method. Takes an arraylist of CandidateList(s) and returns
   * whichever CandidateList covers the most amount of hours
   * 
   * @param candidates the list of CandidateList(s)
   * 
   * @return the CandidateList with the most amount of hours covered
   */
  private static CandidateList mostHoursCovered(ArrayList<CandidateList> candidates) {
    // This shouldn't ever happen, but just in case
    if (candidates.size() == 0) {
      return new CandidateList();
    }

    int indexToSave = 0; // The CandidateList with the most amount of hours covered end up at this
                         // index
    for (int i = 1; i < candidates.size(); i++) { // Loop through candidates ArrayList
      if (candidates.get(i).numCoveredHours() > candidates.get(indexToSave).numCoveredHours()) {
        indexToSave = i;
      }
    }

    return candidates.get(indexToSave);
  }

  /**
   * Intended to return the best possible solution of candidates given a certain number of hires.
   * 
   * @param candidates the list of all possible TA's that could be hired
   * @param hired      the list of hired TA's
   * @param hiresLeft  the amount of TA's we can hire
   * 
   * @return a completely optimal list of candidates that covers the most amount of hours
   */
  public static CandidateList optimalHiring(CandidateList candidates, CandidateList hired,
      int hiresLeft) {
    // Base Case
    if (hiresLeft == 0 || candidates.size() == 0) {
      return hired;
    }

    // Keeps a record of the hired CandidateList(s) we want to consider
    ArrayList<CandidateList> possibilities = new ArrayList<CandidateList>();

    // Deep copy of all parameters
    int hires = 0;
    CandidateList deepCopyCandidates = null;
    CandidateList deepCopyHired = null;
    for (int i = 0; i < candidates.size(); i++) { // Loop through candidates list
      // Every time the loop runs, it means a recursive call has been made, so the deep copies
      // should all be set back to what they were before the loop.
      hires = hiresLeft;
      deepCopyCandidates = candidates.deepCopy();
      deepCopyHired = hired.deepCopy();
      deepCopyHired.add(deepCopyCandidates.get(i)); // Add this TA to deep copy of hired
      deepCopyCandidates.remove(i); // Remove this TA from deep copy of candidates
      hires--;
      possibilities.add(optimalHiring(deepCopyCandidates, deepCopyHired, hires));

    }

    // Return the element in possibilities that covers the most hours
    if (possibilities.size() > 1) {
      CandidateList elementToReturn = mostHoursCovered(possibilities);
      return elementToReturn;
    }
    // If there is only one element in possibilities 2D list, return that element
    else if (possibilities.size() == 1) {
      return possibilities.get(0);
    }
    // Return null if there are no CandidateList(s) in possibilities
    return null;
  }

  /**
   * Helper method for minCoverageHiring() method. Takes in an ArrayList of CandidateList objects
   * and returns whichever object costs the least.
   * 
   * @param candidates the ArrayList of CandidateList(s) to look through
   * 
   * @return the CandidateList that costs the least
   */
  private static CandidateList bestPriceList(ArrayList<CandidateList> candidates) {
    // This shouldn't ever happen, but just in case
    if (candidates.size() == 0) {
      return new CandidateList();
    }

    int indexToSave = 0; // The CandidateList that costs the least will be stored at this index
    for (int i = 1; i < candidates.size(); i++) { // Loop through candidates
      if (candidates.get(i).totalCost() < candidates.get(indexToSave).totalCost()) {
        indexToSave = i;
      }
    }

    return candidates.get(indexToSave);
  }

  /**
   * Helper method for minCoverageHiring() method. It is intended to get the amount of hours that
   * this candidate has to contribute.
   * 
   * @param candidate the candidate to get their number of hours.
   * 
   * @return the number of additional hours this candidate has to contribute
   */
  private static int getNumHours(Candidate candidate, boolean[] currentHoursCovered) {
    int hours = 0; // The amount of hours this candidate will end up having
    for (int i = 0; i < candidate.getAvailability().length; i++) { // Loop through the candidate's
                                                                   // availability
      if (candidate.getAvailability()[i] == true && currentHoursCovered[i] == false) {
        hours++;
      }
    }
    return hours;
  }

  /**
   * Creates a completely optimal set of TA's that minimizes cost to achieve a minimum amount of
   * hours.
   * 
   * @param candidates the list of all possible TA's
   * @param hired      the list of all of the hired TA's
   * @param minHours   the minimum amount of hours we need to hire for
   * 
   * @return an optimized list of TA's or null if the list of TA's won't be able to cover the min
   *         amount of hours
   */
  public static CandidateList minCoverageHiring(CandidateList candidates, CandidateList hired,
      int minHours) {
    // Base Case
    if (minHours <= 0 || candidates.size() == 0) {
      return hired;
    }

    // Takes care of the case where the candidates list doesn't achieve minHours
    if (candidates.numCoveredHours() < minHours) {
      return null;
    }

    // Keeps a record of the CandidateList(s) we want to consider
    ArrayList<CandidateList> possibilities = new ArrayList<CandidateList>();

    // Create a deep copy of all parameters
    int hoursLeft = 0;
    CandidateList deepCopyCandidates = null;
    CandidateList deepCopyHired = null;
    for (int i = 0; i < candidates.size(); i++) {
      // Every time this loop finishes, it means a recursive call has been made, so reset all deep
      // copy values to what they were before the loop initiated.
      hoursLeft = minHours;
      deepCopyCandidates = candidates.deepCopy();
      deepCopyHired = hired.deepCopy();

      // Gets the hours already covered by the hired list
      boolean[] currentHoursCovered = new boolean[candidates.get(0).getAvailability().length];
      currentHoursCovered = getCurrentCandidateListHours(deepCopyHired, currentHoursCovered.length);

      hoursLeft -= getNumHours(deepCopyCandidates.get(i), currentHoursCovered); // Decrement the
                                                                                // hours this
                                                                                // candidate
                                                                                // contributes
      deepCopyHired.add(deepCopyCandidates.get(i)); // Add this candidate to the hired list
      deepCopyCandidates.remove(i); // Remove this candidate from the candidates list
      possibilities.add(minCoverageHiring(deepCopyCandidates, deepCopyHired, hoursLeft));
    }

    // Return the CandidateList in possibilities that costs the less while meeting minHours
    // requirement
    if (possibilities.size() > 1) {
      CandidateList elementToReturn = bestPriceList(possibilities);
      return elementToReturn;
    }
    // If there is only one element in possibilities 2D list, return that element
    else if (possibilities.size() == 1) {
      return possibilities.get(0);
    }
    // Return null if there are no CandidateList(s) in possibilities
    return null;
  }
}
