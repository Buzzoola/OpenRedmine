package jp.redmine.redmineclient.parser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;

import jp.redmine.redmineclient.entity.RedmineConnection;
import jp.redmine.redmineclient.entity.RedmineCustomField;
import jp.redmine.redmineclient.entity.RedmineProjectVersion;
import jp.redmine.redmineclient.entity.TypeConverter;

/**
 * Created by SSvistunov on 18.01.17.
 */

public class ParserCustomFields extends BaseParserInternal<RedmineConnection,RedmineCustomField> {

	@Override
	protected String getProveTagName() {
		return "custom_fileds";
	}

	@Override
	protected RedmineCustomField getNewProveTagItem() {
		return new RedmineCustomField();
	}

	@Override
	protected void parseInternal(RedmineConnection con, RedmineCustomField item)
			throws XmlPullParserException, IOException, SQLException {
		if(xml.getDepth() <= 1)
			return;
		if("id".equalsIgnoreCase(xml.getName())){
			String work = getNextText();
			if("".equals(work))	return;
			item.setCustomFieldId(TypeConverter.parseInteger(work));
		} else if("name".equalsIgnoreCase(xml.getName())){
			item.setName(getNextText());
		} else if("customized_type".equalsIgnoreCase(xml.getName())){
			item.setCustomizedType(getNextText());
		} else if("field_format".equalsIgnoreCase(xml.getName())){
			item.setFieldFormat(getNextText());
		} else if("regexp".equalsIgnoreCase(xml.getName())){
			item.setRegexp(getNextText());
		} else if("min_length".equalsIgnoreCase(xml.getName())){
			item.setMinLength(TypeConverter.parseInteger(getNextText()));
		} else if("max_length".equalsIgnoreCase(xml.getName())){
			item.setMaxLength(TypeConverter.parseInteger(getNextText()));
		} else if("is_required".equalsIgnoreCase(xml.getName())){
			item.setIsRequired(TypeConverter.parseBoolean(getNextText()));
		} else if("is_filter".equalsIgnoreCase(xml.getName())){
			item.setIsFilter(TypeConverter.parseBoolean(getNextText()));
		} else if("searchable".equalsIgnoreCase(xml.getName())){
			item.setSearchable(TypeConverter.parseBoolean(getNextText()));
		} else if("multiple".equalsIgnoreCase(xml.getName())){
			item.setMultiple(TypeConverter.parseBoolean(getNextText()));
		} else if("default_value".equalsIgnoreCase(xml.getName())){
			item.setDefaultValue(getNextText());
		} else if("visible".equalsIgnoreCase(xml.getName())){
			item.setVisible(TypeConverter.parseBoolean(getNextText()));
		} else if("possible_values".equalsIgnoreCase(xml.getName())){
			item.setVisible(TypeConverter.parseBoolean(getNextText()));
		}

	}
}