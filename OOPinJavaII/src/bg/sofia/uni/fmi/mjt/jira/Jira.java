
package bg.sofia.uni.fmi.mjt.jira;


import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.mjt.jira.enums.WorkAction;
import bg.sofia.uni.fmi.mjt.jira.interfaces.Filter;
import bg.sofia.uni.fmi.mjt.jira.interfaces.Repository;
import bg.sofia.uni.fmi.mjt.jira.issues.Issue;

public class Jira implements Filter, Repository
{
	private final int MAX_ISSUES = 100;
	private int currentSize;
	
	Issue[] issues;
	
	public Jira()
	{
		issues = new Issue[MAX_ISSUES];
		currentSize = 0;
	}
	
	@Override
	public void addIssue(Issue issue) 
	{
		if (issue == null)
		{
			return;
		}
		
		if (currentSize == MAX_ISSUES)
		{
			throw new RuntimeException();
			
		}
		
		for (int i = 0; i < currentSize; i++)
		{
			if (issues[i].getIssueID().equals(issue.getIssueID()))
			{
				throw new RuntimeException();
			}
		}
		
		issues[currentSize] = issue;
		currentSize++;
		
		
	}

	@Override
	public Issue find(String issueID) 
	{
		if (issueID == null)
		{
			return null;
		}
		
		for (int i = 0; i < currentSize; i++)
		{
			if (issues[i].getIssueID().equals(issueID))
			{
				return issues[i];
			}
		}
		
		return null;
	}
	
	public void addActionToIssue(Issue issue, WorkAction action, String actionDescription)
	{
		if (issue == null || action == null || actionDescription == null)
		{
			return;
		}
		
		issue.addAction(action, actionDescription);
		issue.setStatus(IssueStatus.IN_PROGRESS);
		
	}
	public void resolveIssue(Issue issue, IssueResolution resolution)
	{
		if (issue == null || resolution == null)
		{
			return;
		}			
			
		issue.resolve(resolution);
		issue.setStatus(IssueStatus.RESOLVED);
		
	}



}
