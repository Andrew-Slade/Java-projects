
import java.util.*;
import java.io.*;
import java.lang.*;




public class MilesRedeemer{
  
  //private variables below
  private ArrayList<Destination> destL = new ArrayList<>(); //destination list private
  private int remainingMiles = 0; //remaining miles private variable
  private ArrayList<String> uTempAL = new ArrayList<>(); //upgrade list private
  //end private variables




  /*
   *****
      Read Destinations Method
      Needs: A scanner
      Returns: Nothing
      Notes: throws back an exception if broken by input or file
   *****
  */
  public void readDestinations(Scanner scaner)
  throws FileNotFoundException, Exception
  {
    try{

      //'s with a couple of repurposes
      while(scaner.hasNextLine()) {
        String nextLine = scaner.nextLine();

        //Split stings into tokens 
        String[] tokens = nextLine.split(";");

        String name = tokens[0]; //tokens go here
        int nMiles = Integer.parseInt(tokens[1]); //first token = normal miles
        int sMiles = Integer.parseInt(tokens[2]); //second token = saver miles
        int uMiles = Integer.parseInt(tokens[3]); //third token = upgrade miles

        String[] sMonths = tokens[4].split("-"); //4 token needs splitting into months
        int month1 = Integer.parseInt(sMonths[0]);//4 token part 1 = first month
        int month2 = Integer.parseInt(sMonths[1]);//4 token part 2 = second month

        //call constructor to create object
        destL.add(new Destination(name, nMiles, sMiles, uMiles, month1, month2));
      }


      //sort list by normal miles
      destL.sort((Destination d1, Destination d2)
                 ->{return(d2.getMiles() - d1.getMiles());}); 
      // lambda vs comparitor, I used a lambda


      scaner.close(); //close file once done
    }
   catch(Exception ioex){
      //catch input exceptions here
      Exception ioe = new Exception("Parsing error concerning"
                                   +"I/O file");
      
      throw ioe;
   } 
  }





  /*
   *****
      Get City Names Method
      Needs: Nothing
      Returns: An array of City Names
      Notes: 
   *****
  */
  public String[] getCityNames(){
    ArrayList<String> temp = new ArrayList<>(); //list
    String tempName = ""; //temp for adding to list


    for(Destination t: destL){ 
      tempName = t.getName(); //grab city names push to temp
      temp.add(tempName); //push to temp ArrayList
    }


    //turn array list into array
    String cities[] = temp.toArray(new String[temp.size()]);
    Arrays.sort(cities); //sort array alphabetically

   return cities;	           
  }




  /*
   *****
      Redeem Miles Method
      Needs: int for miles and int for months
      Returns: An array list of strings that is 
               the list of tickets
      Notes: also finds upgrade list which is
             put into a private variable for later
   *****
  */
  public ArrayList<String> redeemMiles(int miles, int month){
    //variable block
    int userMile = miles;//user miles temp variable
    //arraylists
    ArrayList<String> sTempAL = new ArrayList<>();//string ArrayList to be returned
    ArrayList<Destination> upgrade = new ArrayList<>();//upgrade list
    //end variable block
  

    //temp array list of tickets
    ArrayList<Destination> ticketList = new ArrayList<>();


    for(Destination l: destL){ //iterate throught list
      if(!(month >= l.getStartMonth() && month <= l.getEndMonth())){ 
        //if not in super months
        if(userMile >= l.getMiles()){//if possible
          //add destination to the array
          userMile -= l.getMiles(); //subtract normal miles from user's miles
          sTempAL.add(l.getName()); //add name to list of tickets
          ticketList.add(l); //add destination to upgrade check list
        }
      }
      else{
        //if in super months
        if(userMile >= l.getSaverMiles()){//if possible
          userMile -= l.getSaverMiles(); //subtract from user miles
          sTempAL.add(l.getName()); //add to ticket name list
          ticketList.add(l); //add to upgrade check list
        }
      }
    }     

    
    for(Destination itterator: ticketList){
      //transfer destination list
      //to a malleable list 
      //to preserve main
      upgrade.add(itterator);
    }

      
    for(Destination iterator : upgrade){ //check for upgrades
      if(userMile >= iterator.getUpgdeMiles()){ //if possible
        //subtract from available user miles
        userMile -= iterator.getUpgdeMiles();
        uTempAL.add(iterator.getName()); //list of upgraded tickets
      }
    } 

       
    remainingMiles = userMile; //pass remaining miles to variable

   return sTempAL;
  }



   
  /*
   *****
      Get Remaining Miles Method
      Needs: private remaining miles variable in this class
      Returns: An integer representing remaining miles
      Notes: Accessor
   *****
  */
  public int getRemainingMiles(){
   return remainingMiles;    
  }




  /*
   *****
      Get Upgrades Method
      Needs: private arraylist of upgrades
      Returns: An ArrayList with type string of upgraded tickets
      Notes: Accessor
   *****
  */
  public ArrayList<String> getUpgrades(){
   return uTempAL;
  }
}

/*
  Below is a copy of the license from the student's
  code that I repurposed.

*****************************************************************

MIT License

Copyright (c) 2018 Mason Greig

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

*******************************************************************
*/


