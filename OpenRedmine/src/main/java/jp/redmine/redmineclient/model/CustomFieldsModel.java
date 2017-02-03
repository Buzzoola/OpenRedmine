package jp.redmine.redmineclient.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import jp.redmine.redmineclient.entity.RedmineCustomField;

/**
 * Created by SSvistunov on 18.01.17.
 */

public class CustomFieldsModel {

	private static class InstanceHolder {
		private static CustomFieldsModel instance = new CustomFieldsModel();
	}

	public static CustomFieldsModel getInstance() {
		return InstanceHolder.instance;
	}

	private CustomFieldsModel(){
	}

	private HashMap<Integer, ArrayList<IssueCustomField>> customFields = new HashMap<>();
	private ArrayList<RedmineCustomField> description = new ArrayList<>();

	public void add(int issueId, IssueCustomField field) {
		ArrayList<IssueCustomField> list = customFields.get(issueId);
		if (list == null)
			list = new ArrayList<>();
		list.add(field);
		customFields.put(issueId, list);
	}

	public void createCustomFieldsIfNeed(int issueId) {
		if (customFields.get(issueId) == null) {
			for (RedmineCustomField desc : description) {
				IssueCustomField field = new IssueCustomField(desc.getCustomFieldId(), desc.getName());
				add(issueId, field);
			}
		}
	}

	public void putTagList(int issueId, List<Integer> tags) {
		ArrayList<IssueCustomField> list = customFields.get(issueId);
		if (list != null) {
			for (IssueCustomField field : list) {
				if (field.getName().equalsIgnoreCase("tag")) {
					field.setValues(tags);
				}
			}
		}
	}

	public void setBranchName(int issueId, String branchName) {
		ArrayList<IssueCustomField> list = customFields.get(issueId);
		if (list != null) {
			for (IssueCustomField field : list) {
				if (field.getName().equalsIgnoreCase("branch name")) {
					field.setValues(new ArrayList<String>(Arrays.asList(branchName)));
				}
			}
		}
	}

	public void addDescription(RedmineCustomField field) {
		description.add(field);
	}

	public RedmineCustomField getDescription(int idx) {
		for (RedmineCustomField field : description) {
			if (field.getCustomFieldId() == idx)
				return field;
		}
		return null;
	}

	public List<String> getTagValues() {
		RedmineCustomField tags = null;

		for (RedmineCustomField field : description) {
			if (field.getName().equalsIgnoreCase("tag")) {
				tags = field;
				break;
			}
		}

		ArrayList<String> returnValue = new ArrayList<>();

		if (tags != null) {

			List<Integer> sortedKeys=new ArrayList(tags.getPossibleValues().keySet());
			for (int i : sortedKeys) {
				returnValue.add(tags.getPossibleValues().get(i));
			}
		}

		return returnValue;
	}

	public void clear() {
		customFields.clear();
	}

	public void clearDescription() {
		description.clear();
	}

	public ArrayList<IssueCustomField> getListByIssueId(int issueId) {
		return customFields.get(issueId);
	}

	public IssueCustomField get(int issueId, String name) {
		ArrayList<IssueCustomField> list = customFields.get(issueId);
		if (list != null) {
			for (IssueCustomField field : list) {
				if (field.getName().equalsIgnoreCase(name))
					return field;
			}
		}

		return null;
	}

	public void clearForIssue(int issueId) {
		customFields.remove(issueId);
	}

}
