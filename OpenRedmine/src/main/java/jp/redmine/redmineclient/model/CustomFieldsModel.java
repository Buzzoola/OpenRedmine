package jp.redmine.redmineclient.model;

import java.util.HashMap;

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

	private HashMap<Integer, RedmineCustomField> customFields = new HashMap<>();

	public void add(RedmineCustomField field) {
		customFields.put(field.getCustomFieldId(), field);
	}

	public void clear() {
		customFields.clear();
	}

	public RedmineCustomField get(int id) {
		return customFields.get(id);
	}


}
