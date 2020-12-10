package bg.sofia.uni.fmi.mjt.virtualwallet.core.card;

public abstract class Card 
{
	private String name;
	private double amount;
	
    public Card(String name) 
    {
    	if (name == null)
    	{
    		return;
    	}
    	
    	this.name = name;
    }
	
    public abstract boolean executePayment(double cost);

    public String getName() 
    {
    	return name;
    }
	
    public double getAmount() 
    {
    	return amount;
    }
    
    public void setName(String name)
    {
    	if (name == null)
    	{
    		return;
    	}
    	this.name = name;
    }
    
    public void setAmount(double amount)
    {
    	this.amount = amount;
    }
}