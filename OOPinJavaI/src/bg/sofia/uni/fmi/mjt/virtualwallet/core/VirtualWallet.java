package bg.sofia.uni.fmi.mjt.virtualwallet.core;

import java.util.Iterator;
import java.util.Vector;
//import java.time.LocalDateTime;

import bg.sofia.uni.fmi.mjt.virtualwallet.core.card.Card;
import bg.sofia.uni.fmi.mjt.virtualwallet.core.payment.PaymentInfo;

public class VirtualWallet implements VirtualWalletAPI {

	Vector<Card> cards;
	
    public VirtualWallet()
    {
    	cards = new Vector<Card>(5);
    }

	@Override
	public boolean registerCard(Card card)
	{
		if (card == null)
		{
			return false;
		}
		
		if (cards.size() == 5)
		{
			return false;
		}
		
		if (card.getName() == null)
		{
			return false;
		}

		Iterator<Card> iterator = cards.iterator();
		
		while(iterator.hasNext())
		{
			if (card.getName().equals(iterator.next().getName()))
			{
				return false;
			}
		}
		
		cards.add(card);
		
		return true;
	}

	@Override
	public boolean executePayment(Card card, PaymentInfo paymentInfo) 
	{
		if (paymentInfo == null || card == null)
		{
			return false;
		}
		return card.executePayment(paymentInfo.getCost());
	
	}

	@Override
	public boolean feed(Card card, double amount)
	{
		if (card == null)
		{
			return false;
		}
		
		if (amount < 0.0)
		{
			return false;
		}
		
		Iterator<Card> iterator = cards.iterator();
		
		while(iterator.hasNext())
		{
			if (card.getName().equals(iterator.next().getName()))
			{
				card.setAmount(amount);
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Card getCardByName(String name)
	{
		if (name == null)
		{
			return null;
		}
		
		Iterator<Card> iterator = cards.iterator();
		int i = 0;
		while(iterator.hasNext())
		{
		
			if (name.equals(iterator.next().getName()))
			{
				return cards.elementAt(i);
			}
			i++;
		}
		
		return null;
	}

	@Override
	public int getTotalNumberOfCards() 
	{
		return cards.size();
	}
}