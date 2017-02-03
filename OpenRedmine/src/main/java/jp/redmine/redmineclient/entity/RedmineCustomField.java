package jp.redmine.redmineclient.entity;

import com.j256.ormlite.field.DatabaseField;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by SSvistunov on 18.01.17.
 */

public class RedmineCustomField implements IConnectionRecord
		,IMasterRecord
{
	public final static String ID = "id";
	public final static String NAME = "name";
	public final static String CONNECTION = RedmineConnection.CONNECTION_ID;

	public final static String CUSTOMFIELD_ID = "customized_field_id";
	public final static String CUSTOMIZED_TYPE = "customized_type";
	public final static String FIELD_FORMAT = "field_format";
	public final static String REGEXP = "regexp";
	public final static String MIN_LENGTH = "min_length";
	public final static String MAX_LENGTH = "max_length";
	public final static String IS_REQUIRE = "is_required";
	public final static String IS_FILTER = "is_filter";
	public final static String SEARCHABLE = "searchable";
	public final static String MILTIPLE = "multiple";
	public final static String DEFAULT_VALUE = "default_value";
	public final static String VISIBLE = "visible";
	public final static String POSSIBLE_VALUES = "possible_values";


	@DatabaseField(generatedId = true)
	private Long id;
	@DatabaseField(uniqueIndexName="custom_field_id", columnName = RedmineConnection.CONNECTION_ID)
	private Integer connection_id;
	@DatabaseField(uniqueIndexName="custom_field_id")
	private int custom_field_id;
	@DatabaseField
	private String name;
	@DatabaseField
	private String customized_type;
	@DatabaseField
	private String field_format;
	@DatabaseField
	private String regexp;
	@DatabaseField
	private int min_length;
	@DatabaseField
	private int max_length;
	@DatabaseField
	private boolean is_required;
	@DatabaseField
	private boolean is_filter;
	@DatabaseField
	private boolean searchable;
	@DatabaseField
	private boolean multiple;
	@DatabaseField
	private String default_value;
	@DatabaseField
	private boolean visible;
	@DatabaseField
	private Date created;
	@DatabaseField
	private Date modified;

	private TreeMap<Integer, String> possible_values;

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	@Override
	public String toString(){
		return name;
	}


	public Integer getCustomFieldId() {
		return custom_field_id;
	}

	public void setCustomFieldId(int custom_field_id) {
		this.custom_field_id = custom_field_id;
	}

	@Override
	public Long getId() {
		return id;

	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public String getCustomizedType() {
		return customized_type;
	}

	public void setCustomizedType(String customized_type) {
		this.customized_type = customized_type;
	}

	public String getFieldFormat() {
		return field_format;
	}

	public void setFieldFormat(String field_format) {
		this.field_format = field_format;
	}

	public String getRegexp() {
		return regexp;
	}

	public void setRegexp(String regexp) {
		this.regexp = regexp;
	}

	public int getMinLength() {
		return min_length;
	}

	public void setMinLength(int min_length) {
		this.min_length = min_length;
	}

	public int getMaxLength() {
		return max_length;
	}

	public void setMaxLength(int max_length) {
		this.max_length = max_length;
	}

	public boolean isRequired() {
		return is_required;
	}

	public void setIsRequired(boolean is_required) {
		this.is_required = is_required;
	}

	public boolean isFilter() {
		return is_filter;
	}

	public void setIsFilter(boolean is_filter) {
		this.is_filter = is_filter;
	}

	public boolean isSearchable() {
		return searchable;
	}

	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public String getDefaultValue() {
		return default_value;
	}

	public void setDefaultValue(String default_value) {
		this.default_value = default_value;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public TreeMap<Integer, String> getPossibleValues() {
		return possible_values;
	}


	public void addPossibleValue(int idx, String value) {
		if (this.possible_values == null) {
			this.possible_values = new TreeMap<>();
		}

		this.possible_values.put(idx, value);
	}

	public String getPossibleValue(int idx) {
		if (possible_values == null) return null;
		return possible_values.get(idx);
	}

	@Override
	public void setRedmineConnection(RedmineConnection connection) {
		this.setConnectionId(connection.getId());
	}

	@Override
	public void setConnectionId(Integer connection_id) {
		this.connection_id = connection_id;
	}
	@Override
	public Integer getConnectionId() {
		return connection_id;
	}


	@Override
	public void setRemoteId(Long id) {
		if(id == null)
			return;
		setCustomFieldId(id.intValue());
	}

	@Override
	public Long getRemoteId() {
		return (getCustomFieldId() == null) ? null : (long)getCustomFieldId();
	}
}
