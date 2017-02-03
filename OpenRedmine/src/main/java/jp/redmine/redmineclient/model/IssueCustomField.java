package jp.redmine.redmineclient.model;

import java.util.ArrayList;
import java.util.List;

import jp.redmine.redmineclient.entity.RedmineCustomField;

/**
 * Created by SSvistunov on 31.01.17.
 */

public class IssueCustomField {

	private int customFieldId;
	private RedmineCustomField description;
	private String name;
	private ArrayList<String> values;

	public ArrayList<String> getValues() {
		return values;
	}

	public void addValue(String value) {
		if (this.values == null)
			this.values = new ArrayList<>();

		this.values.add(value);
	}

	public IssueCustomField(int idx, String name) {
		this.customFieldId = idx;
		this.name = name;

		description = CustomFieldsModel.getInstance().getDescription(idx);
	}

	public int getCustomFieldId() {
		return customFieldId;
	}

	public RedmineCustomField getDescription() {
		if (description == null)
			description = CustomFieldsModel.getInstance().getDescription(customFieldId);
		return description;
	}

	public String getName() {
		return name;
	}

	public void setValues(ArrayList<String> values) {
		this.values = new ArrayList<>(values);
	}

	public void setValues(List<Integer> values) {
		ArrayList<String> newList = new ArrayList<>();
		for(Integer i : values) {
			newList.add(i.toString());
		}
		this.values = newList;
	}
}
