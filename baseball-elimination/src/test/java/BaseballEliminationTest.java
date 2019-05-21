import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author <insert your name hre>
 *
 */
public class BaseballEliminationTest {
  BaseballElimination baseballEliminationTeams4;
  BaseballElimination baseballEliminationTeams5;
  BaseballElimination baseballEliminationTeams8;
  BaseballElimination baseballEliminationTeams12;
  BaseballElimination baseballEliminationTeams24;

  @Before
  public void setUp() throws Exception {
    baseballEliminationTeams4 = new BaseballElimination("baseball-testing-files/teams4.txt");
    baseballEliminationTeams5 = new BaseballElimination("baseball-testing-files/teams5.txt");
    baseballEliminationTeams8 = new BaseballElimination("baseball-testing-files/teams8.txt");
    baseballEliminationTeams12 = new BaseballElimination("baseball-testing-files/teams12.txt");
    baseballEliminationTeams24 = new BaseballElimination("baseball-testing-files/teams24.txt");
  }


  @Test
  public void testNumberOfTeams() {
    assertEquals(4, baseballEliminationTeams4.numberOfTeams());
  }

  @Test
  public void testNumberOfTeams5() {
    assertEquals(5, baseballEliminationTeams5.numberOfTeams());
  }

  @Test
  public void testNumberOfTeams8() {
    assertEquals(8, baseballEliminationTeams8.numberOfTeams());
  }

  @Test
  public void testTeams() {
    String[] teamsExpected = {"Atlanta", "Philadelphia", "New_York", "Montreal"};
    ArrayList<String> teamsExpectedList = new ArrayList<String>();
    for (String team: teamsExpected) {
      teamsExpectedList.add(team);
    }
    for (String actualTeam: baseballEliminationTeams4.teams()) {
      assertTrue(actualTeam + " is not in the expected list",teamsExpectedList.contains(actualTeam));
    }
  }

  @Test
  public void testWinsTeams4() {
    String[] teamsExpected = {"Atlanta", "Philadelphia", "New_York", "Montreal"};
    int[] teamsExpectedWins = {83, 80, 78, 77};
    for (int i = 0; i < teamsExpectedWins.length; i++) {
      assertEquals(teamsExpectedWins[i], baseballEliminationTeams4.wins(teamsExpected[i]));
    }
  }

  @Test
  public void testWinsTeams5() {
    String[] teamsExpected = {"New_York", "Baltimore", "Boston", "Toronto", "Detroit"};
    int[] teamsExpectedWins = {75, 71, 69, 63, 49};
    for (int i = 0; i < teamsExpectedWins.length; i++) {
      assertEquals(teamsExpectedWins[i], baseballEliminationTeams5.wins(teamsExpected[i]));
    }
  }

  @Test
  public void testWinsTeams8() {
    String[] teamsExpected = {"Brown", "Columbia", "Cornell", "Dartmouth", "Penn", "Harvard", "Yale", "Princeton"};
    int[] teamsExpectedWins = {44, 44, 44, 44, 44, 43, 43, 0};
    for (int i = 0; i < teamsExpectedWins.length; i++) {
      assertEquals(teamsExpectedWins[i], baseballEliminationTeams8.wins(teamsExpected[i]));
    }
  }

  @Test
  public void testLosses() {
    String[] teamsExpected = {"Atlanta", "Philadelphia", "New_York", "Montreal"};
    int[] teamsExpectedLosses = {71, 79, 78, 82};
    for (int i = 0; i < teamsExpected.length; i++) {
      assertEquals(teamsExpectedLosses[i], baseballEliminationTeams4.losses(teamsExpected[i]));
    }
  }

  @Test
  public void testLosses5() {
    String[] teamsExpected = {"New_York", "Baltimore", "Boston", "Toronto", "Detroit"};
    int[] teamsExpectedLosses = {59, 63, 66, 72, 86};
    for (int i = 0; i < teamsExpected.length; i++) {
      assertEquals(teamsExpectedLosses[i], baseballEliminationTeams5.losses(teamsExpected[i]));
    }
  }

  @Test
  public void testLosses8() {
    String[] teamsExpected = {"Brown", "Columbia", "Cornell", "Dartmouth", "Penn", "Harvard", "Yale", "Princeton"};
    int[] teamsExpectedLosses = {51, 51, 51, 51, 51, 60, 60, 59};
    for (int i = 0; i < teamsExpected.length; i++) {
      assertEquals(teamsExpectedLosses[i], baseballEliminationTeams8.losses(teamsExpected[i]));
    }
  }

  @Test
  public void testRemaining() {
    String[] teamsExpected = {"Atlanta", "Philadelphia", "New_York", "Montreal"};
    int[] teamsExpectedRemaining = {8, 3, 6, 3};
    for (int i = 0; i < teamsExpected.length; i++) {
      assertEquals(teamsExpectedRemaining[i], baseballEliminationTeams4.remaining(teamsExpected[i]));
    }
  }

  @Test
  public void testRemaining5() {
    String[] teamsExpected = {"New_York", "Baltimore", "Boston", "Toronto", "Detroit"};
    int[] teamsExpectedLosses = {28, 28, 27, 27, 27};
    for (int i = 0; i < teamsExpected.length; i++) {
      assertEquals(teamsExpectedLosses[i], baseballEliminationTeams5.remaining(teamsExpected[i]));
    }
  }

  @Test
  public void testRemaining8() {
    String[] teamsExpected = {"Brown", "Columbia", "Cornell", "Dartmouth", "Penn", "Harvard", "Yale", "Princeton"};
    int[] teamsExpectedLosses = {9, 9, 9, 9, 9, 1, 1, 45};
    for (int i = 0; i < teamsExpected.length; i++) {
      assertEquals(teamsExpectedLosses[i], baseballEliminationTeams8.remaining(teamsExpected[i]));
    }
  }

  @Test
  public void testAgainstTeams4() {
    String[] teamsExpected = {"Atlanta", "Philadelphia", "New_York", "Montreal"};
    int[] teamsExpectedAgainstAtlanta = {0, 1, 6, 1};
    int[] teamsExpectedAgainstMontreal = {1, 2, 0, 0};
    for (int i = 0; i < teamsExpected.length; i++) {
      assertEquals(teamsExpectedAgainstAtlanta[i], baseballEliminationTeams4.against("Atlanta", teamsExpected[i]));
      assertEquals(teamsExpectedAgainstMontreal[i], baseballEliminationTeams4.against("Montreal", teamsExpected[i]));
    }
  }

  @Test
  public void testAgainst5() {
    String[] teamsExpected = {"New_York", "Baltimore", "Boston", "Toronto", "Detroit"};
    int[] teams4AgainstNewYork = {0, 3, 8, 7, 3};
    int[] teams4AgainstBaltimore = {3, 0, 2, 7, 7};
    for (int i = 0; i < teamsExpected.length; i++) {
      assertEquals(teams4AgainstNewYork[i], baseballEliminationTeams5.against(teamsExpected[i], "New_York"));
      assertEquals(teams4AgainstBaltimore[i], baseballEliminationTeams5.against(teamsExpected[i], "Baltimore"));
    }
  }

  @Test
  public void testAgainst8() {
    String[] teamsExpected = {"Brown", "Columbia", "Cornell", "Dartmouth", "Penn", "Harvard", "Yale", "Princeton"};
    int[] teams4AgainstBrown = {0, 0, 0, 0, 0, 0, 0, 9};
    int[] teams4AgainstCornell = {0, 0, 0, 0, 0, 0, 0, 9};
    for (int i = 0; i < teamsExpected.length; i++) {
      assertEquals(teams4AgainstBrown[i], baseballEliminationTeams8.against(teamsExpected[i], "Brown"));
      assertEquals(teams4AgainstCornell[i], baseballEliminationTeams8.against(teamsExpected[i], "Cornell"));
    }
  }

  @Test
  public void testIsEliminatedTeams4() {
    assertFalse(baseballEliminationTeams4.isEliminated("Atlanta"));
    assertTrue(baseballEliminationTeams4.isEliminated("Philadelphia"));
    assertFalse(baseballEliminationTeams4.isEliminated("New_York"));
    assertTrue(baseballEliminationTeams4.isEliminated("Montreal"));
  }

  @Test
  public void testIsEliminatedTeams5() {
      assertFalse(baseballEliminationTeams5.isEliminated("New_York"));
      assertFalse(baseballEliminationTeams5.isEliminated("Baltimore"));
      assertFalse(baseballEliminationTeams5.isEliminated("Boston"));
      assertFalse(baseballEliminationTeams5.isEliminated("Toronto"));
      assertTrue(baseballEliminationTeams5.isEliminated("Detroit"));
  }

  @Test
  public void testIsEliminatedTeams8() {
    assertFalse(baseballEliminationTeams8.isEliminated("Brown"));
    assertFalse(baseballEliminationTeams8.isEliminated("Princeton"));
    assertFalse(baseballEliminationTeams8.isEliminated("Columbia"));
    assertTrue(baseballEliminationTeams8.isEliminated("Yale"));
    assertFalse(baseballEliminationTeams8.isEliminated("Cornell"));
    assertTrue(baseballEliminationTeams8.isEliminated("Harvard"));
    assertFalse(baseballEliminationTeams8.isEliminated("Dartmouth"));
    assertFalse(baseballEliminationTeams8.isEliminated("Penn"));
  }

  @Test
  public void testCertificateOfEliminationTriviallyEliminatedTeams4() {
    String[] expectedEliminationCertificate = {"Atlanta"};
    int index = 0;

    for (String actualEliminationTeam:
      baseballEliminationTeams4.certificateOfElimination("Montreal")) {
      assertEquals(expectedEliminationCertificate[index], actualEliminationTeam);
      index++;
    }
  }

  @Test
  public void testCertificateOfEliminationTriviallyEliminatedTeams12() {
    String[] expectedEliminationCertificate = {"Poland", "Russia", "Brazil", "Iran"};
    int index = 0;

    for (String actualEliminationTeam:
      baseballEliminationTeams12.certificateOfElimination("China")) {
      assertEquals(expectedEliminationCertificate[index], actualEliminationTeam);
      index++;
    }
  }

  @Test
  public void testCertificateOfEliminationTriviallyEliminatedTeams24() {
    String[] expectedEliminationCertificate = {"Team14", "Team15", "Team20", "Team22"};
    int index = 0;

    for (String actualEliminationTeam:
      baseballEliminationTeams24.certificateOfElimination("Team7")) {
      assertEquals(expectedEliminationCertificate[index], actualEliminationTeam);
      index++;
    }
  }

  @Test
  public void testCertificateOfEliminationNonTriviallyEliminatedTeams4() {
    String[] expectedEliminationCertificate = {"Atlanta", "New_York"};
    ArrayList<String> teamsExpectedList = new ArrayList<String>();
    for (String team: expectedEliminationCertificate) {
      teamsExpectedList.add(team);
    }
    for (String actualEliminationTeam:
      baseballEliminationTeams4.certificateOfElimination("Philadelphia")) {
      assertTrue(actualEliminationTeam + " is not in the expected list",teamsExpectedList.contains(actualEliminationTeam));
    }

  }

  @Test
  public void testCertificateOfEliminationNonTriviallyEliminatedTeams12() {
    String[] expectedEliminationCertificate = {"Poland", "Russia", "Brazil", "Iran"};
    ArrayList<String> teamsExpectedList = new ArrayList<String>();
    for (String team: expectedEliminationCertificate) {
      teamsExpectedList.add(team);
    }
    for (String actualEliminationTeam:
      baseballEliminationTeams12.certificateOfElimination("Serbia")) {
      assertTrue(actualEliminationTeam + " is not in the expected list",teamsExpectedList.contains(actualEliminationTeam));

    }

  }

  @Test
  public void testCertificateOfEliminationNonTriviallyEliminatedTeams24() {
    String[] expectedEliminationCertificate = {"Team0", "Team2", "Team10", "Team14", "Team15", "Team17", "Team20", "Team21", "Team22"};
    ArrayList<String> teamsExpectedList = new ArrayList<String>();
    for (String team: expectedEliminationCertificate) {
      teamsExpectedList.add(team);
    }
    for (String actualEliminationTeam:
      baseballEliminationTeams24.certificateOfElimination("Team16")) {
      assertTrue(actualEliminationTeam + " is not in the expected list",teamsExpectedList.contains(actualEliminationTeam));

    }
  }

}
