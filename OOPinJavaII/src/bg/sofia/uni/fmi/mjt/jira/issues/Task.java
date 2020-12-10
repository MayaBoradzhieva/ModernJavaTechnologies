package bg.sofia.uni.fmi.mjt.jira.issues;

import java.time.LocalDateTime;

import bg.sofia.uni.fmi.mjt.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.mjt.jira.enums.WorkAction;

public class Task extends Issue
{

	public Task(IssuePriority priority, Component component, String description)
	{
		super(priority, component, description);
		
	}
	
	@Override
	public void addAction(WorkAction action, String description)
	{	
		if (action == null || description == null)
		{
			return;
		}
		
		if (action.getAction().equals("fix") || 
			action.getAction().equals("implementation") ||
			action.getAction().equals("tests"))
		{
			throw new RuntimeException();
		}
		else
		{
			super.addAction(action,  description);
			this.setLastModifiedOn(LocalDateTime.now());
		}
		
	}


	@Override
	public void resolve(IssueResolution resolution) 
	{
		
		
		if (resolution == null)
		{
			return;
		}
		
		
		setResolution(resolution);
		this.setLastModifiedOn(LocalDateTime.now());
	}
	

}
