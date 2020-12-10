package bg.sofia.uni.fmi.mjt.virtualwallet.core.card;

public class StandardCard extends Card
{

	public StandardCard(String name) 
	{
		super(name);
	}

	@Override
	public boolean executePayment(double cost)
	{
		if (cost < 0.0)
		{
			return false;
		}
		
		double newAmount = getAmount() - cost;
		
		if (newAmount >= 0.0) // the payment is successful
		{
			setAmount(newAmount);
			return true;
		}
		
		return false; // not successful
	}

}
