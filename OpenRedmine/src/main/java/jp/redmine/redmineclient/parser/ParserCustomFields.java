package jp.redmine.redmineclient.parser;

import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.redmine.redmineclient.entity.RedmineConnection;
import jp.redmine.redmineclient.entity.RedmineCustomField;
import jp.redmine.redmineclient.entity.RedmineProjectVersion;
import jp.redmine.redmineclient.entity.TypeConverter;
import jp.redmine.redmineclient.model.CustomFieldsModel;

/**
 * Created by SSvistunov on 18.01.17.
 */

public class ParserCustomFields extends BaseParserInternal<RedmineConnection,RedmineCustomField> {

	private Integer possibleValueTemp = null;

	@Override
	protected String getProveTagName() {
		return "custom_field";
	}

	@Override
	protected RedmineCustomField getNewProveTagItem() {
		return new RedmineCustomField();
	}

	@Override
	protected void parseInternal(RedmineConnection con, RedmineCustomField item)
			throws XmlPullParserException, IOException, SQLException {
		Log.d("XML", "name=" + xml.getName());
		Log.d("XML", "attr_counts=" + xml.getAttributeCount());
		Log.d("XML", "text=" + xml.getText());

		if (xml.getDepth() <= 1)
			return;
		if ("id".equalsIgnoreCase(xml.getName())) {
			String work = getNextText();
			if ("".equals(work)) return;
			item.setCustomFieldId(TypeConverter.parseInteger(work));
		} else if ("name".equalsIgnoreCase(xml.getName())) {
			item.setName(getNextText());
		} else if ("customized_type".equalsIgnoreCase(xml.getName())) {
			item.setCustomizedType(getNextText());
		} else if ("field_format".equalsIgnoreCase(xml.getName())) {
			item.setFieldFormat(getNextText());
		} else if ("regexp".equalsIgnoreCase(xml.getName())) {
			item.setRegexp(getNextText());
		} else if ("min_length".equalsIgnoreCase(xml.getName())) {
			String work = getNextText();
			if ("".equals(work))
				item.setMinLength(0);
			else
				item.setMinLength(TypeConverter.parseInteger(getNextText()));
		} else if ("max_length".equalsIgnoreCase(xml.getName())) {
			String work = getNextText();
			if ("".equals(work))
				item.setMaxLength(0);
			else
				item.setMaxLength(TypeConverter.parseInteger(getNextText()));
		} else if ("is_required".equalsIgnoreCase(xml.getName())) {
			item.setIsRequired(TypeConverter.parseBoolean(getNextText()));
		} else if ("is_filter".equalsIgnoreCase(xml.getName())) {
			item.setIsFilter(TypeConverter.parseBoolean(getNextText()));
		} else if ("searchable".equalsIgnoreCase(xml.getName())) {
			item.setSearchable(TypeConverter.parseBoolean(getNextText()));
		} else if ("multiple".equalsIgnoreCase(xml.getName())) {
			item.setMultiple(TypeConverter.parseBoolean(getNextText()));
		} else if ("default_value".equalsIgnoreCase(xml.getName())) {
			item.setDefaultValue(getNextText());
		} else if ("visible".equalsIgnoreCase(xml.getName())) {
			item.setVisible(TypeConverter.parseBoolean(getNextText()));
		} else if ("possible_values".equalsIgnoreCase(xml.getName())) {
			String type = getAttributeString("type");
			if ("".equals(type)) return;
			//item.setPossibleValues(new ArrayList<String>());
		} else if ("possible_value".equalsIgnoreCase(xml.getName())) {
			String work = getNextText();
			if ("".equals(work)) return;
		} else if ("label".equalsIgnoreCase(xml.getName())) {
			String work = getNextText();
			if ("".equals(work)) return;
			if (possibleValueTemp != null) {
				item.addPossibleValue(possibleValueTemp, work);
				possibleValueTemp = null;
			}
		} // no name, just text, it means <value>XXX</value> in possible_value
		else if ("value".equalsIgnoreCase(xml.getName()) || xml.getName() == null) {
			String work = xml.getText();
			if (work != null) {
				possibleValueTemp = TypeConverter.parseInteger(work);
			}
		}
	}

	@Override
	protected void onTagEnd(RedmineConnection con)
			throws XmlPullParserException, IOException,SQLException {

		if(equalsTagName(getProveTagName()) && lastdepth == xml.getDepth()){
			CustomFieldsModel.getInstance().addDescription(item);
		}

		super.onTagEnd(con);
	}

}