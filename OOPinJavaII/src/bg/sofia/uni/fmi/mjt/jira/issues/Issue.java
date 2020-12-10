package bg.sofia.uni.fmi.mjt.jira.issues;

import java.time.LocalDateTime;

import bg.sofia.uni.fmi.mjt.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.mjt.jira.enums.WorkAction;

public abstract class Issue
{
	final int MAX_LOG = 20;
	static int uniqueNumber = 0;
	
	private String issueID;
	private String description;
	private IssuePriority priority;
	private IssueResolution resolution;
	private IssueStatus status;
	private Component component;
	
	protected String[] actionLog;
	
	protected  int currentActionsSize = 0; 
	
	// time stamp
	private LocalDateTime createdOn;
	private LocalDateTime lastModifiedOn;
	
	public Issue(IssuePriority priority, Component component, String description)
	{
		// ID
		issueID = component.getShortName() + '-' + uniqueNumber;
		uniqueNumber++;
		
		this.resolution = IssueResolution.UNRESOLVED;
		this.status = IssueStatus.OPEN;
		this.priority = priority;
		this.component = component;
		this.description = description;
		
		actionLog = new String[MAX_LOG];
		
		this.createdOn = LocalDateTime.now();
		this.lastModifiedOn = createdOn;
		
		
	}
	
	public String getIssueID()
	{ 
		return issueID;
	}
	
	public String getDescription() 
	{ 
		return description; 
	}
	
	public IssuePriority getPriority() 
	{ 
		return priority; 
	}
	
	public IssueResolution getResolution()
	{ 
		return resolution; 
	}
	
	public IssueStatus getStatus() 
	{ 
		return status;
	}
	
	public Component getComponent()
	{
		return component; 
	}
	
	public LocalDateTime getCreatedOn() 
	{ 
		return createdOn;
	}
	
	public LocalDateTime getLastModifiedOn() 
	{
		return lastModifiedOn;
	}
	
	public String[] getActionLog()
	{
		return actionLog; 
	}

	public void setStatus(IssueStatus status) 
	{
		this.status = status;
		this.setLastModifiedOn(LocalDateTime.now());
	}
	
	public void setIssueID(String issueID) {
		this.issueID = issueID;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPriority(IssuePriority priority) {
		this.priority = priority;
	}

	public void setResolution(IssueResolution resolution) {
		this.resolution = resolution;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public void setActionLog(String[] actionLog) {
		this.actionLog = actionLog;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public void setLastModifiedOn(LocalDateTime lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}
	
	public void addAction(WorkAction action, String description)
	{	
		if (action == null || description == null)
		{
			return;
		}
		
		if (currentActionsSize == MAX_LOG)
		{
			throw new RuntimeException();
		}
		else
		{
			actionLog[currentActionsSize] = action.getAction() + ": " + description;
			currentActionsSize++;
			this.setLastModifiedOn(LocalDateTime.now());
		}
		
	}

	public abstract void resolve(IssueResolution resolution);
}
