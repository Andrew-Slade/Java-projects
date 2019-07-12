
import java.io.Serializable;
import java.sql.ResultSet;

class Query
 implements Serializable{

  public static final long serialVersionUID = 1L;
  public String type = null;
  public String ssn = null;
  public String name = null;
  public String zip = null;
  public String address = null;
  public String[] results = null;
  public String success = null;

  
  /**
  * Constructor
  **/
  public Query(){}

  public Query(String t, String s, String n,
                String z, String a){
 
    type = t;
    ssn = s;
    name = n;
    zip = z;
    address = a;
  }

  /**
  * mutators
  **/
  public void setResults(String[] r){
    results = r;
  }
  public void setSuccess(String ss){
    success = ss;
  }

  /**
  * Accessor Methods
  **/
  public String getType(){
    return type;
  }
  public String getSsn(){
    return ssn;
  }
  public String getName(){
    return name;
  }
  public String getZip(){
    return zip;
  }
  public String getAddress(){
    return address;
  }
  public String[] getResults(){
    return results;
  }
  public String getSuccess(){
    return success;
  }
}
