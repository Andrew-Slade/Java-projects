  import java.util.*; //import all because 
  import java.io.*;   //simplicity 
  import java.lang.*;

  
public class MilageRedemptionApp{

  public static void main(String[] args){
    String divider = "-----------------------";
    Scanner s = new Scanner(System.in);
    String conf = "y";


    //break if argument list is too long or short
    if(args.length != 1) {
      System.out.println("Error: Invalid number of"
                          + " arguments.");
                         return;
    }
    //end argument check
 
    
    while(conf.equals("y") || conf.equals("Y")){
      //a little trick from the stdlib
     
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
        MilesRedeemer mrService;
        mrService = new MilesRedeemer();


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
 

 
        // print block for city names
        System.out.println(divider); //divider

        for(String i : output){
          System.out.println(i);//print city names
        }

        System.out.println(divider); //divider 
        // end print block


      
        try{ 
          //while block for user input verification
          while(userMiles < 0){ //depends on the set default above
            System.out.print("\nPlease input your total"
                             + " accumulated miles: ");
            userMiles = stdi.nextInt(); // grab user miles
          }
    
          while(userMonth < 1 || userMonth > 12){ //also depends on default above
            System.out.print("\nPlease input your month"
                             + " of departure(1-12): ");
            userMonth = stdi.nextInt();//grab user month
          }
          inputFlag = true; //set flag to continue with output
        }
        //catch and try from partner's code
        //general io exception
        catch(Exception ie){
          System.out.println("Invalid input type: invalid numeric or non-numeric");
          inputFlag = false; //set flag to break output and go to end
        }
        //user input verification done



        if(inputFlag){ //if there are no user input errors
          //call redeem miles class
          mrService.redeemMiles(userMiles,userMonth);
 
          //output for tickets and class 
          System.out.println("\nYour accumulated miles can be used" 
                           + " to redeem the following tickets:\n");

          
          //redeem the tickets and the upgrades
          ArrayList<String> tsTempAL = mrService.redeemMiles(userMiles, userMonth);
          ArrayList<String> upTempAL =  mrService.getUpgrades();


          if(tsTempAL.size() > 0){ //if there are tickets 
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
          }
          else{//if there are no tickets
            System.out.println("There are no tickets near  " 
                               +  userMiles + " miles");
          }


          //get remaining miles and output the value
          remainingM = mrService.getRemainingMiles();
          System.out.println("\nYour remaining miles " + remainingM);
        }
      }
      catch(FileNotFoundException fileE){
        //for files not found
        System.out.println(fileE);
        return;
      }


      //User input to continue the loop
      System.out.print("\nDo you want to continue (y/n)?");
      conf = s.next(); 
      s.nextLine();
    }
  }
}
