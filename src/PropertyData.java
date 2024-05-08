public class PropertyData {

  private String propertyType;
  private String price; 
  private String description;
  private String propID;

  public String getPropID() {
    return propID;
  }
  public void setPropID(String propID) {
    this.propID = propID;
  }
  public void setPropertyType(String propertyType) {
    this.propertyType = propertyType;
  }
  public void setPrice(String price) {
    this.price = price;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public String getPropertyType() {
    return propertyType;
  }
  public String getPrice() {
    return price;
  }
  public String getDescription() {
    return description;
  }
  
}
