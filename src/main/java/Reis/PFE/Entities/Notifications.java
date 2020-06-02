package Reis.PFE.Entities;

public class Notifications {
private int count;

public Notifications(int count) {
	super();
	this.count = count;
}

public int getCount() {
	return count;
}

public void setCount(int count) {
	this.count = count;
}
public void increment()
{
	this.count++;
}
}
