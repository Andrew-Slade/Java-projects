
  import java.util.*; //import all because 
  import java.io.*;   //simplicity 
  import java.lang.*;

  
public class MilageRedemptionApp{

  public init(String[] args){
    Scanner s = new Scanner(System.in);


    //break if argument list is too long or short
    if(args.length != 1) {
      System.out.println("Error: Invalid number of"
                          + " arguments.");
                         return;
    }
    //end argument check
     
    try{
     
        //variable block
        Scanner scan =
         new Scanner(new File(args[0])).useDelimiter(";|-");

        Scanner stdi = new Scanner(System.in);
        //input scanner for stdin
        int userMiles = -1;//default -1 to force user input
        int userMonth = 0; //default 0 to force user input
        int remainingM = 0; //default 0 
        boolean inputFlag = true;
        //end variable block


        //instantiate Miles Redeemer class
        MilesRedeemer mrService = new MilesRedeemer();


        //pass scanner to read destinations method
        try{
          mrService.readDestinations(scan);
        }
        //catch the general exception
        catch(Exception ex){
          //catch general exception
          System.out.println(ex.getMessage());
          return;
        }

        //return city names
        String[] output = mrService.getCityNames(); 
        //pass the city names to gui
        


          //call redeem miles class
          mrService.redeemMiles(userMiles,userMonth);
 
          //output for tickets and class 

          
          //redeem the tickets and the upgrades
          ArrayList<String> tsTempAL = mrService.redeemMiles(userMiles, userMonth);
          ArrayList<String> upTempAL =  mrService.getUpgrades();


          if(tsTempAL.size() > 0){ //if there are tickets 
            /*
            //this particular for loop iterates through the tickets
            //checks for upgrades, then prints the results
            for(String p : tsTempAL){ //for p = Destination list
              if(upTempAL.size() > 0){ //only if there are upgrades
                if(upTempAL.contains(p)){ //if the ticket is upgraded
                  System.out.println("* A trip to " + 
                                      p + ", first class");  
                }  
                else{ //otherwise
                  System.out.println ("* A trip to " + p 
                                     + ", economy class");
                }
              }
              else{ //if there are no upgrades
                System.out.println ("* A trip to " + p 
                                    + ", economy class");
              } 
            }
           */
          }
          else{//if there are no tickets
            /*
            System.out.println("There are no tickets near  " 
                               +  userMiles + " miles");
            */
          }


          //get remaining miles and output the value
        //  remainingM = mrService.getRemainingMiles();
          //System.out.println("\nYour remaining miles " + remainingM);
      }
      catch(FileNotFoundException fileE){
        //for files not found
        System.out.println(fileE);
        return;
    }
  }
}
