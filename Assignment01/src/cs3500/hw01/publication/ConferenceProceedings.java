package cs3500.hw01.publication;

/**
 * Represents bibliographic information for conferences
 */
public class ConferenceProceedings implements Publication{
  private final String editorLastName;
  private final String editorFirstName;
  private final String title;
  private final String location;
  private final int year;
  private final String publisher;
  /**
   * Constructs a {@code ConferenceProceedings} object
   *
   * @param editorLastName  the editor's last name
   * @param editorFirstName the editor's first name
   * @param title           the title of the conference
   * @param location        the location of the conference
   * @param year            the year of publication
   * @param publisher       the publisher of the conference
   */
  public ConferenceProceedings(String editorLastName, String editorFirstName, String title,
                               String location, int year, String publisher) {
    this.editorLastName = editorLastName;
    this.editorFirstName = editorFirstName;
    this.title = title;
    this.location = location;
    this.year = year;
    this.publisher = publisher;
  }

  @Override
  public String citeApa() {
    return editorLastName + "," + editorFirstName.charAt(0) + ". " + "(Eds.). " + "(" + year + "). " + title + ". " + publisher + ".";
  }

  @Override
  public String citeMla() {
    return editorLastName + "," + editorFirstName + "." + title + "," + location + "," + publisher + "," + year + ".";
  }
}
