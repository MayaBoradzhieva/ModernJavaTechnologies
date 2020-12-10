package bg.sofia.uni.fmi.mjt.jira.issues;

import java.time.LocalDateTime;

import bg.sofia.uni.fmi.mjt.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;

public class Bug extends Issue
{

	public Bug(IssuePriority priority, Component component, String description) 
	{
		super(priority, component, description);
		
	}

	@Override
	public void resolve(IssueResolution resolution) 
	{
		if (resolution == null)
		{
			return;
		}
		if (actionLog == null)
		{
			return;
		}
		
		boolean fix = false;
		boolean tests = false;
		
		for (int i = 0; i < currentActionsSize; i++)
		{
			
			if (super.getActionLog()[i].contains("fix"))
			{
				fix = true;
			}
			if (super.getActionLog()[i].contains("tests"))
			{
				tests = true;
			}
		}
		
		if (fix == false || tests == false)
		{
			throw new RuntimeException();
		}
		else
		{
			setResolution(resolution);	
			setLastModifiedOn(LocalDateTime.now());
		}
		
	}

}
