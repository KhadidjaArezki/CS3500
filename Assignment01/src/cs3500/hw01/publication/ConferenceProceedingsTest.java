package cs3500.hw01.publication;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class ConferenceProceedingsTest {
  Publication designComm = new ConferenceProceedings("Huang", "Su",
          "Proceedings of the 24th annual ACM international conference on the design of communication",
          "Stockholm", 2006, "ACM Digital Library");

  @Test
  public  void testCiteApa() {
    assertEquals("Huang,S. (Eds.). (2006). Proceedings of the 24th annual ACM international conference on the design of communication. ACM Digital Library.",
            designComm.citeApa());
  }

  @Test
  public  void testCiteMla() {
    assertEquals("Huang,Su.Proceedings of the 24th annual ACM international conference on the design of communication,Stockholm,ACM Digital Library,2006.",
            designComm.citeMla());
  }
}
