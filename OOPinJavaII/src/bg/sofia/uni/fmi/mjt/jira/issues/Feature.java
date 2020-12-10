package bg.sofia.uni.fmi.mjt.jira.issues;

import java.time.LocalDateTime;


import bg.sofia.uni.fmi.mjt.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;

public class Feature extends Issue
{

	
	public Feature(IssuePriority priority, Component component, String description)
	{
		super(priority, component, description);
	}

	@Override
	public void resolve(IssueResolution resolution) 
	{
		boolean design = false;
		boolean implementation = false;
		boolean tests = false;
		
		if (resolution == null)
		{
			return;
		}
		
		if (getActionLog() == null)
		{
			return;
		}
		
		for (int i = 0; i < currentActionsSize; i++)
		{
			if (super.getActionLog()[i].contains("design"))
			{
				design = true;
			}
			if (super.getActionLog()[i].contains("implementation"))
			{
				implementation = true;
			}
			if (super.getActionLog()[i].contains("tests"))
			{
				tests = true;
			}
		}
		
		if (design == false || implementation == false || tests == false)
		{
			throw new RuntimeException();
		}
		else
		{
			this.setResolution(resolution);
			this.setLastModifiedOn(LocalDateTime.now());
		}
		
	}

}
