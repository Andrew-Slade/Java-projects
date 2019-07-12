public class Destination {
	
	//Class member variables
	private	String destName;
	private	int normMiles;
	private	int upMiles;
	private	int superMiles;
	private int startMonth;
	private int endMonth;
	
	//Class constructor
	public Destination(String newName, int normalMiles,
                           int saverMiles, int upgradeMiles,
                              int saverStart, int saverEnd)
        {
		setName(newName);
		setMiles(normalMiles);
		setUpgdeMiles(upgradeMiles);
		setSaverMiles(saverMiles);
		setStartMonth(saverStart);
		setEndMonth(saverEnd);
	}
	
	//Accessor and mutator methods for the destination class
	public String getName() {
		return destName;
	}
	
	public void setName(String newDest) {
		destName = newDest;
	}
	
	public int getMiles() {
		return normMiles;
	}
	
	public void setMiles(int newMiles) {
		normMiles = newMiles;
	}
	
	public int getUpgdeMiles() {
		return upMiles;
	}
	
	public void setUpgdeMiles(int newMiles) {
		upMiles = newMiles;
	}
	
	public int getSaverMiles() {
		return superMiles;
	}
	
	public void setSaverMiles(int newMiles) {
		superMiles = newMiles;
	}
	
	public int getStartMonth() {
		return startMonth;
	}
	
	public void setStartMonth(int newStart) {
		startMonth = newStart;
	}
	
	public int getEndMonth() {
		return endMonth;
	}
	
	public void setEndMonth(int newEnd) {
		endMonth = newEnd;
	}

}
