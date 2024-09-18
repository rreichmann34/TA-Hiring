///////////////////////////////////////////////////////////////////////////////
//
// Title: The HiringTesting Class creates test cases to ensure that the methods defined in Hiring
// work as intended.
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

import java.util.Random;
import java.util.ArrayList;

public class HiringTesting {

  /**
   * Tests greedyHiring() when no recursive calls are made.
   * 
   * @return true if all tests pass, false otherwise.
   */
  public static boolean greedyHiringBaseTest() {
    // Test 1 actual and expected Values
    CandidateList expectedTest1 = new CandidateList();
    CandidateList test1 = Hiring.greedyHiring(new CandidateList(), new CandidateList(), 3);
    // Test 2 actual and expected values
    CandidateList expectedTest2 = new CandidateList();
    CandidateList list = new CandidateList();
    list.add(new Candidate(new boolean[] {true}));
    CandidateList test2 = Hiring.greedyHiring(list, new CandidateList(), 0);

    return expectedTest1.equals(test1) && expectedTest2.equals(test2);
  }

  /**
   * Tests greedyHiring() when there are recursive calls made
   * 
   * @return true if all tests pass, false otherwise.
   */
  public static boolean greedyHiringRecursiveTest() {
    // Random test Candidate(s)
    Candidate candidate1 = new Candidate(new boolean[] {true, true, false, false, false});
    Candidate candidate2 = new Candidate(new boolean[] {true, false, false, false, true});
    Candidate candidate3 = new Candidate(new boolean[] {true, false, false, true, true});
    CandidateList list = new CandidateList();
    list.add(candidate1);
    list.add(candidate2);
    list.add(candidate3);

    // Expected list should contain candidates 1 and 3
    CandidateList expectedList = new CandidateList();
    expectedList.add(candidate3);
    expectedList.add(candidate1);

    // Actual list is obtained from running greedyHiring
    CandidateList actualList = Hiring.greedyHiring(list, new CandidateList(), 2);

    return expectedList.equals(actualList);
  }

  /**
   * Tests optimalHiring() when no recursive calls are made.
   * 
   * @return true if all tests pass, false otherwise.
   */
  public static boolean optimalHiringBaseTest() {
    // Test 1
    // The actual list should end up being empty since there are no candidates
    CandidateList expectedTest1 = new CandidateList();
    CandidateList actual1 = Hiring.optimalHiring(new CandidateList(), new CandidateList(), 3);
    // Test 2
    CandidateList expectedTest2 = new CandidateList();
    CandidateList list = new CandidateList();
    list.add(new Candidate(new boolean[] {true}));
    // The actual list should end up being empty since there are no candidates
    CandidateList actual2 = Hiring.optimalHiring(list, new CandidateList(), 0);

    return expectedTest1.equals(actual1) && expectedTest2.equals(actual2);
  }

  /**
   * Tests optimalHiring() when recursive calls are made.
   * 
   * @return true if all tests pass, false otherwise.
   */
  public static boolean optimalHiringRecursiveTest() {
    // Random Candidate(s) to test optimalHiring() with
    Candidate candidate1 = new Candidate(new boolean[] {true, false, false, true, true, true});
    Candidate candidate2 = new Candidate(new boolean[] {true, true, false, false, true, false});
    Candidate candidate3 = new Candidate(new boolean[] {false, false, true, true, false, true});
    CandidateList list = new CandidateList();
    list.add(candidate1);
    list.add(candidate2);
    list.add(candidate3);

    // Expected list should contain candidates 1 and 3
    CandidateList expectedList = new CandidateList();
    expectedList.add(candidate2);
    expectedList.add(candidate3);

    CandidateList actualList = Hiring.optimalHiring(list, new CandidateList(), 2);

    return actualList.equals(expectedList);
  }

  /**
   * Tests minCoverageHiring() when no recursive calls are made.
   * 
   * @return true if all tests pass, false otherwise.
   */
  public static boolean minCoverageHiringBaseTest() {
    // Test 1
    CandidateList expectedTest1 = new CandidateList();
    // Actual list should contain an empty CandidateList
    CandidateList actualTest1 =
        Hiring.minCoverageHiring(new CandidateList(), new CandidateList(), 3);
    // Test 2
    CandidateList expectedTest2 = new CandidateList();
    CandidateList list = new CandidateList();
    list.add(new Candidate(new boolean[] {true}));
    // Actual list should contain an empty CandidateList
    CandidateList actualTest2 = Hiring.minCoverageHiring(list, new CandidateList(), 0);

    return expectedTest1.equals(actualTest1) && expectedTest2.equals(actualTest2);
  }

  /**
   * Tests minCoverageHiring() when recursive calls are made.
   * 
   * @return true if all tests pass, false otherwise.
   */
  public static boolean minCoverageHiringRecursiveTest() {
    // Random Candidate(s) to tests minCoverageHiring() with
    Candidate candidate1 = new Candidate(new boolean[] {true, false, false, true, true, true}, 4);
    Candidate candidate2 = new Candidate(new boolean[] {true, true, false, false, true, false}, 2);
    Candidate candidate3 = new Candidate(new boolean[] {false, false, true, true, false, true}, 3);
    CandidateList list = new CandidateList();
    list.add(candidate1);
    list.add(candidate2);
    list.add(candidate3);

    CandidateList expectedList = new CandidateList();
    expectedList.add(candidate1);

    // Actual list should contain only candidate 1
    CandidateList actualList = Hiring.minCoverageHiring(list, new CandidateList(), 4);

    return actualList.equals(expectedList);
  }

  /**
   * Tests minCoverageHiring() with a random large number of test cases.
   * 
   * @return true if all tests pass, false otherwise .
   */
  public static boolean minCoverageHiringFuzzTest() {
    Random randGen = new Random();
    boolean allTestsPassed = true; // Set to false if any tests fail
    int numOfTests = randGen.nextInt(101) + 100; // Random number of tests to run between 100-200
    for (int i = 0; i < numOfTests; i++) { // Loop for numOfTests times
      int randomHours = randGen.nextInt(5) + 1; // Random number of hours between 1-5
      int randomNumOfCandidates = randGen.nextInt(10) + 1; // Random number of candidates between
                                                           // 1-10
      int randomNumOfHours = randGen.nextInt(randomHours) + 1; // Random number of hours between 1-5
      int randomPayRate = (randGen.nextInt(randomNumOfCandidates) / 2) + 1; // Random pay rate
                                                                            // between 1-5
      // Creates random list of Candidate(s)
      CandidateList candidates = HiringTestingUtilities.generateRandomInput(randomHours,
          randomNumOfCandidates, randomPayRate);

      CandidateList actualList =
          Hiring.minCoverageHiring(candidates, new CandidateList(), randomNumOfHours);
      ArrayList<CandidateList> allSolutions =
          HiringTestingUtilities.allMinCoverageSolutions(candidates, randomNumOfHours);

      // Test to make sure the actualList is found somewhere in all of the possible solutions
      if (!(HiringTestingUtilities.compareCandidateLists(allSolutions, actualList))) {
        allTestsPassed = false;
        break; // If any test fails, there is no need to test the situations that follow, so break.
      }
    }
    return allTestsPassed;
  }

  /**
   * Tests optimalHiring() with a random large number of test cases.
   * 
   * @return true if all tests pass, false otherwise.
   */
  public static boolean optimalHiringFuzzTest() {
    Random randGen = new Random();
    boolean allTestsPassed = true; // Set to false if any tests fail
    int numOfTests = randGen.nextInt(101) + 100; // Random number of tests to run between 100-200
    for (int i = 0; i < numOfTests; i++) { // Loop for numOfTests times
      int randomHours = randGen.nextInt(5) + 1; // Random number of hours between 1-5
      int randomNumOfCandidates = randGen.nextInt(10) + 1; // Random number of candidates between
                                                           // 1-10
      int randomNumOfHires = randGen.nextInt(randomNumOfCandidates) + 1; // Random number of hires
                                                                         // between 1-10
      // Create a list of randomly generated Candidate(s)
      CandidateList candidates =
          HiringTestingUtilities.generateRandomInput(randomHours, randomNumOfCandidates);

      CandidateList actualList =
          Hiring.optimalHiring(candidates, new CandidateList(), randomNumOfHires);
      ArrayList<CandidateList> allSolutions =
          HiringTestingUtilities.allOptimalSolutions(candidates, randomNumOfHires);

      // Testing to make sure the actualList is found somewhere in all of the possible solutions
      if (!(HiringTestingUtilities.compareCandidateLists(allSolutions, actualList))) {
        allTestsPassed = false;
        break; // If any test fails, there is no need to test the situations that follow, so break.
      }
    }
    return allTestsPassed;
  }

  public static void main(String[] args) {
    System.out.print("Greedy Hiring Tests: ");
    if (greedyHiringBaseTest() && greedyHiringRecursiveTest()) {
      System.out.println("PASSED");
    }

    System.out.print("Optimal Hiring Tests: ");
    if (optimalHiringBaseTest() && optimalHiringRecursiveTest()) {
      System.out.println("PASSED");
    }

    System.out.print("Min Coverage Hiring Tests: ");
    if (minCoverageHiringBaseTest() && minCoverageHiringRecursiveTest()) {
      System.out.println("PASSED");
    }

    System.out.print("Optimal Hiring Fuzz Test: ");
    if (optimalHiringFuzzTest()) {
      System.out.println("PASSED");
    }
    System.out.print("Min Coverage Hiring Fuzz Test: ");
    if (minCoverageHiringFuzzTest()) {
      System.out.println("PASSED");
    }
  }
}
