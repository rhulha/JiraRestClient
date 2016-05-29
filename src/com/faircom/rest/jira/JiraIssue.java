package com.faircom.rest.jira;

import java.text.ParseException;
import java.util.*;

public class JiraIssue {
	public final long id;
	public final String url;
	public final String key;
	public final String summary;
	
	public final Date updated;

	public final Map<String, Object> fields;
	public final List<WorkLog> worklogs = new ArrayList<WorkLog>();

	@SuppressWarnings("unchecked")
	@Deprecated // this can loose entries due to maxResults
	public JiraIssue(Map<String, Object> issueJson) {
		this(issueJson, (Map<String, Object>) ((Map<String, Object>) issueJson.get("fields")).get("worklog"));
	}

	@SuppressWarnings("unchecked")
	public JiraIssue(Map<String, Object> issueJson, Map<String, Object> worklogsJsonMap) {
		id= Long.parseLong( (String) issueJson.get("id"));
		url=(String) issueJson.get("self");
		key=(String) issueJson.get("key");
		fields = (Map<String, Object>) issueJson.get("fields");
		summary=(String) fields.get("summary");
		
		try {
			//created = jiraJsonDateFormat.parse((String) worklogJsonMap.get("created"));
			updated = JiraRestClient.jiraJsonDateFormat.parse((String) fields.get("updated"));
			//started = jiraJsonDateFormat.parse((String) worklogJsonMap.get("started"));
		} catch (ParseException pe) {
			throw new RuntimeException(pe);
		}
		
		if(!worklogsJsonMap.get("total").equals(worklogsJsonMap.get("maxResults"))) {
			System.err.println("Warning: total!=maxResults");
		}
		
		List<Map<String, Object>> worklogsJsonList = (List<Map<String, Object>>) worklogsJsonMap.get("worklogs");
		for (Map<String, Object> worklogJsonMap : worklogsJsonList) {
			worklogs.add(new WorkLog(worklogJsonMap));
		}
	}

	@Override
	public String toString() {
		return "JiraIssue [id=" + id + ", url=" + url + ", key=" + key + ", summary=" + summary + "]";
	}

	public String toShortString() {
		return summary + " (" + key + ')';
	}
	
	

}
