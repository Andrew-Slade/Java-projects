
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
/*
  Below is a copy of the license from the student's
  code that I repurposed.


**************************************************************
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
****************************************************************
*/

