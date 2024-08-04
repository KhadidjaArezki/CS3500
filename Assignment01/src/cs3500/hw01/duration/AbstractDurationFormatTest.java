package cs3500.hw01.duration;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/** Tests for the format method of {@link Duration}s. 
    Add your tests to this class to assure that your format 
    method works properly
*/
public abstract class AbstractDurationFormatTest {
  Duration wdhDuration1 = wdh(0, 4, 9);
  Duration hoursDuration1 = hours(wdhDuration1.inHours());
  Duration wdhDuration2 = wdh(5, 5, 17);
  Duration hoursDuration2 = hours(wdhDuration2.inHours());
  
  @Test
  public void formatWdhExample1() {
    assertEquals("4 days, 0 weeks, and 9 hours",
                    wdhDuration1.format("%d days, %w weeks, and %h hours"));
  }
  
  @Test
  public void formatHoursExample1() {
    assertEquals("4 days, 0 weeks, and 9 hours",
                    hoursDuration1.format("%d days, %w weeks, and %h hours"));
  }

  @Test
  public void formatWdhExample2() {
    assertEquals("5:05:17",
                  wdhDuration2.format("%w:%D:%H"));
  }

  @Test
  public void formatHoursExample2() {
    assertEquals("05:05:17",
                  hoursDuration2.format("%W:%D:%H"));
  }
  
  // ADD MORE TESTS HERE
  // Your tests must only use wdh(...) and hours(...) to construct new Durations
  // and must *not* directly say "new CompactDuration(...)" or
  // "new WdhDuration(...)"
  @Test
  public void formatEmptyTemplate() {
    assertEquals("", wdhDuration1.format(""));
  }
  
  @Test
  public void formatNoSpecifiers() {
    assertEquals("WDDHH", wdhDuration1.format("WDDHH"));
  }

  @Test
  public void formatOverlapSpecifiers() {
    assertEquals("%t", wdhDuration1.format("%%t"));
  }
  
  @Test
  public void formatBadTemplate() {
    assertThrows(IllegalArgumentException.class, () -> wdhDuration1.format("%S:%D:%H"));
  }
  
  @Test
  public void formatInhoursExample1() {
    assertEquals("hours: " + hoursDuration1.inHours(),
        wdhDuration1.format("hours: %t"));
  }
  
  @Test
  public void formatInhoursExample2() {
    assertEquals("hours: " + hoursDuration1.inHours(),
        hoursDuration1.format("hours: %t"));
  }

  
  //=================================================================================//
  /*
    Leave this section alone: It contains two abstract methods to
    create Durations, and concrete implementations of this testing class
    will supply particular implementations of Duration to be used within 
    your tests.
   */
  /**
   * Constructs an instance of the class under test representing the duration
   * given in weeks, days and hours
   *
   * @param weeks the weeks in the duration
   * @param days the days in the duration
   * @param hours the hours in the duration
   * @return an instance of the class under test
   */
  protected abstract Duration wdh(int weeks, int days, int hours);

  /**
   * Constructs an instance of the class under test representing the duration
   * given in hours.
   *
   * @param inHours the total hours in the duration
   * @return an instance of the class under test
   */
  protected abstract Duration hours(long inHours);

  /** 
   * A nested testing factory class, that uses {@link WdhDuration} for
   * all of its test cases.
   */
  public static final class WdhDurationTest extends AbstractDurationFormatTest {
    @Override
    protected Duration wdh(int weeks, int days, int hours) {
      return new WdhDuration(weeks, days, hours);
    }

    @Override
    protected Duration hours(long inHours) {
      return new WdhDuration(inHours);
    }
  }

  /** 
   * A nested testing factory class, that uses {@link CompactDuration} for
   * all of its test cases.
   */  
  public static final class CompactDurationTest extends AbstractDurationFormatTest {
    @Override
    protected Duration wdh(int weeks, int days, int hours) {
      return new CompactDuration(weeks, days, hours);
    }

    @Override
    protected Duration hours(long inHours) {
      return new CompactDuration(inHours);
    }
  }
}
