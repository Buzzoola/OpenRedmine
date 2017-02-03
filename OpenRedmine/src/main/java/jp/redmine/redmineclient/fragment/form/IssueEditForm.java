package jp.redmine.redmineclient.fragment.form;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.andreabaccega.widget.FormEditText;

import jp.redmine.redmineclient.MultiSpinner;
import jp.redmine.redmineclient.R;
import jp.redmine.redmineclient.adapter.FilterListAdapter;
import jp.redmine.redmineclient.db.cache.DatabaseCacheHelper;
import jp.redmine.redmineclient.db.cache.RedmineCategoryModel;
import jp.redmine.redmineclient.db.cache.RedmineCustomFieldModel;
import jp.redmine.redmineclient.db.cache.RedminePriorityModel;
import jp.redmine.redmineclient.db.cache.RedmineStatusModel;
import jp.redmine.redmineclient.db.cache.RedmineTrackerModel;
import jp.redmine.redmineclient.db.cache.RedmineUserModel;
import jp.redmine.redmineclient.db.cache.RedmineVersionModel;
import jp.redmine.redmineclient.entity.DummySelection;
import jp.redmine.redmineclient.entity.IMasterRecord;
import jp.redmine.redmineclient.entity.RedmineIssue;
import jp.redmine.redmineclient.entity.RedmineJournal;
import jp.redmine.redmineclient.entity.RedminePriority;
import jp.redmine.redmineclient.entity.RedmineProjectCategory;
import jp.redmine.redmineclient.entity.RedmineProjectVersion;
import jp.redmine.redmineclient.entity.RedmineStatus;
import jp.redmine.redmineclient.entity.RedmineTracker;
import jp.redmine.redmineclient.entity.RedmineUser;
import jp.redmine.redmineclient.entity.TypeConverter;
import jp.redmine.redmineclient.form.helper.FormHelper;
import jp.redmine.redmineclient.model.CustomFieldsModel;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class IssueEditForm extends FormHelper {
	public Spinner spinnerStatus;
	public Spinner spinnerTracker;
	public Spinner spinnerCategory;
	public Spinner spinnerPriority;
	public Spinner spinnerVersion;
	public Spinner spinnerAssigned;
	public MultiSpinner spinnerTags;
	public SeekBar progressIssue;

	public FormEditText textTitle;
	public FormEditText textDescription;
	public FormEditText textComment;
	public FormEditText textDateStart;
	public FormEditText textDateDue;
	public EditText textBranchName;
	public ImageButton imageCalendarStart;
	public ImageButton imageCalendarDue;
	public FormEditText textTime;
	public TableRow rowCreated;
	public TableRow rowModified;
	public TextView textCreated;
	public TextView textModified;
	public TextView textProgress;
	public Button buttonOK;
	public DatePickerDialog dialogDatePicker;
	public LinearLayout layoutComment;
	protected FilterListAdapter adapterStatus;
	protected FilterListAdapter adapterTracker;
	protected FilterListAdapter adapterCategory;
	protected FilterListAdapter adapterPriority;
	protected FilterListAdapter adapterUser;
	protected FilterListAdapter adapterVersion;

	public IssueEditForm(View activity){
		this.setup(activity);
		this.setupEvents();
	}


	public void setup(View view){
		spinnerStatus = (Spinner)view.findViewById(R.id.spinnerStatus);
		spinnerTracker = (Spinner)view.findViewById(R.id.spinnerTracker);
		spinnerCategory = (Spinner)view.findViewById(R.id.spinnerCategory);
		spinnerPriority = (Spinner)view.findViewById(R.id.spinnerPriority);
		spinnerVersion = (Spinner)view.findViewById(R.id.spinnerVersion);
		spinnerAssigned = (Spinner)view.findViewById(R.id.spinnerAssigned);
		spinnerTags = (MultiSpinner)view.findViewById(R.id.spinnerTags);
		progressIssue = (SeekBar)view.findViewById(R.id.progressIssue);

		textTitle = (FormEditText)view.findViewById(R.id.textTitle);
		textDescription = (FormEditText)view.findViewById(R.id.textDescription);
		textComment = (FormEditText)view.findViewById(R.id.textComment);
		textDateStart = (FormEditText)view.findViewById(R.id.textDateStart);
		textDateDue = (FormEditText)view.findViewById(R.id.textDateDue);
		textBranchName = (EditText)view.findViewById(R.id.textBranchName);
		imageCalendarStart = (ImageButton)view.findViewById(R.id.imageCalendarStart);
		imageCalendarDue = (ImageButton)view.findViewById(R.id.imageCalendarDue);
		textTime = (FormEditText)view.findViewById(R.id.textTime);

		layoutComment = (LinearLayout)view.findViewById(R.id.layoutComment);
		rowCreated = (TableRow)view.findViewById(R.id.rowCreated);
		rowModified = (TableRow)view.findViewById(R.id.rowModified);
		textCreated = (TextView)view.findViewById(R.id.textCreated);
		textModified = (TextView)view.findViewById(R.id.textModified);
		textProgress = (TextView)view.findViewById(R.id.textProgress);
		textCreated.setVisibility(View.GONE);
		rowModified.setVisibility(View.GONE);
	}

	@Override
	public void setupEvents() {
		setupDateSelector(imageCalendarStart, textDateStart);
		setupDateSelector(imageCalendarDue, textDateDue);

		progressIssue.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
				if(fromUser){
					int current = Math.round(((float)progress)/10)*10;
					if(current != seekBar.getProgress()){
						seekBar.setProgress(current);
					}
					progress = current;
				}
				textProgress.setText(textProgress.getContext().getString(R.string.format_progress, progress));
			}
		});
	}

	protected void setupDateSelector(ImageButton button, final FormEditText text){
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Calendar date = Calendar.getInstance();
				if(!TextUtils.isEmpty(text.getText()))
					date.setTime(TypeConverter.parseDate(text.getText().toString()));
				dialogDatePicker = new DatePickerDialog(v.getContext(), new OnDateSetListener() {

					@SuppressLint("SimpleDateFormat")
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
						Calendar selected = Calendar.getInstance();
						selected.set(year, monthOfYear, dayOfMonth);
						text.setText(TypeConverter.getDateString(selected.getTime()));

					}
				}, date.get(Calendar.YEAR),  date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
				dialogDatePicker.show();
			}
		});

	}

	public void setupDatabase(DatabaseCacheHelper helper){
		adapterStatus = new FilterListAdapter(new RedmineStatusModel(helper));
		adapterTracker = new FilterListAdapter(new RedmineTrackerModel(helper));
		adapterCategory = new FilterListAdapter(new RedmineCategoryModel(helper));
		adapterPriority = new FilterListAdapter(new RedminePriorityModel(helper));
		adapterUser = new FilterListAdapter(new RedmineUserModel(helper));
		adapterVersion = new FilterListAdapter(new RedmineVersionModel(helper));

	}

	public void setupParameter(int connection, long project){
		setupParameter(spinnerStatus, adapterStatus, connection, project, true);
		setupParameter(spinnerTracker, adapterTracker, connection, project, true);
		setupParameter(spinnerCategory, adapterCategory, connection, project, true);
		setupParameter(spinnerPriority, adapterPriority, connection, project, true);
		setupParameter(spinnerAssigned, adapterUser, connection, project, true);
		setupParameter(spinnerVersion, adapterVersion, connection, project, true);

	}

	protected void setupParameter(Spinner spinner, FilterListAdapter adapter
			,int connection, long project, boolean isAdd){
		adapter.setupDummyItem(spinner.getContext());
		adapter.setupParameter(connection, project, isAdd);
		spinner.setAdapter(adapter);

		adapter.notifyDataSetInvalidated();
		adapter.notifyDataSetChanged();
	}

	public void setValue(RedmineIssue data){
		setDate(textDateStart, data.getDateStart());
		setDate(textDateDue, data.getDateDue());
		textTitle.setText(data.getSubject());
		textDescription.setText(data.getDescription());
		textTime.setText(data.getEstimatedHours() == null ? "" : String.valueOf(data.getEstimatedHours()));

		setSpinnerItem(spinnerStatus,adapterStatus,data.getStatus());
		setSpinnerItem(spinnerTracker,adapterTracker,data.getTracker());
		setSpinnerItem(spinnerCategory,adapterCategory,data.getCategory());
		setSpinnerItem(spinnerPriority,adapterPriority,data.getPriority());
		setSpinnerItem(spinnerAssigned,adapterUser,data.getAssigned());
		setSpinnerItem(spinnerVersion,adapterVersion,data.getVersion());
		setSpinnerItem(spinnerTags,adapterVersion,data.getVersion());

		progressIssue.setProgress(data.getProgressRate() == null ? 0 : data.getProgressRate());
		textCreated.setVisibility(data.getCreated() == null ? View.GONE : View.VISIBLE);
		rowModified.setVisibility(data.getModified() == null ? View.GONE : View.VISIBLE);
		layoutComment.setVisibility(data.getIssueId() == null ? View.GONE : View.VISIBLE);
		setDateTime(textCreated, data.getCreated());
		setDateTime(textModified, data.getModified());

		//Branch name
		if (data.getIssueId() != null) {
			List<String> currentValues = CustomFieldsModel.getInstance().get(data.getIssueId(), "branch name").getValues();
			if (currentValues != null && currentValues.size() > 0) {
				textBranchName.setText(currentValues.get(0));
			}
		}

		//Tags
		List<String> items = CustomFieldsModel.getInstance().getTagValues();

		List<Integer> currentIntValues = null;

		if (data.getIssueId() != null) {
			List<String> currentValues = CustomFieldsModel.getInstance().get(data.getIssueId(), "tag").getValues();
			currentIntValues = convertFromStringList(currentValues);
		}


		spinnerTags.setItems(items, currentIntValues, new MultiSpinner.MultiSpinnerListener() {
			@Override
			public void onItemsSelected(boolean[] selected) {

			}
		});

	}

	private List<Integer> convertFromStringList(List<String> strings) {
		List<Integer> returnValue = new ArrayList<>();
		for(String item : strings) {
			returnValue.add(Integer.parseInt(item)-1);
		}
		return returnValue;
	}

	protected void setSpinnerItem(Spinner spinner, FilterListAdapter adapter, IMasterRecord record){
		if(record == null){
			spinner.setSelection(0);
		} else {
			for(int i = 0; i < adapter.getCount(); i++){
				@SuppressWarnings("deprecation")
				IMasterRecord activity = (IMasterRecord) adapter.getItem(i);
				if (activity != null && record != null) {
					if (activity.getId() == record.getId()) {
						spinner.setSelection(i);
						break;
					}
				}
			}
		}
	}
	@SuppressWarnings("unchecked")
	protected <T> T getSpinnerItem(Spinner spinner){
		if(spinner == null || spinner.getSelectedItem() == null)
			return null;
		if(spinner.getSelectedItem() instanceof DummySelection)
			return null;
		return (T)spinner.getSelectedItem();
	}

	public void getValue(RedmineIssue data){
		data.setDateStart(getDate(textDateStart));
		data.setDateDue(getDate(textDateDue));
		data.setSubject(textTitle.getText().toString());
		data.setDescription(textDescription.getText().toString());
		data.setEstimatedHours(TextUtils.isEmpty(textTime.getText()) ? null : TypeConverter.parseBigDecimal(textTime.getText().toString()).doubleValue());

		data.setStatus(this.<RedmineStatus>getSpinnerItem(spinnerStatus));
		data.setTracker(this.<RedmineTracker>getSpinnerItem(spinnerTracker));
		data.setCategory(this.<RedmineProjectCategory>getSpinnerItem(spinnerCategory));
		data.setPriority(this.<RedminePriority>getSpinnerItem(spinnerPriority));
		data.setAssigned(this.<RedmineUser>getSpinnerItem(spinnerAssigned));
		data.setVersion(this.<RedmineProjectVersion>getSpinnerItem(spinnerVersion));

		data.setDoneRate((short)progressIssue.getProgress());

		if(!TextUtils.isEmpty(textComment.getText())){
			RedmineJournal journal = data.getJournal();
			if(journal == null){
				journal = new RedmineJournal();
				data.setJournal(journal);
			}
			journal.setNotes(textComment.getText().toString());
		}

		Integer tempIssueId = data.getIssueId();
		if (tempIssueId == null) {
			tempIssueId = -1;
			CustomFieldsModel.getInstance().createCustomFieldsIfNeed(tempIssueId);
		}

		CustomFieldsModel.getInstance().putTagList(tempIssueId, spinnerTags.getSelectedIdx());
		CustomFieldsModel.getInstance().setBranchName(tempIssueId, textBranchName.getText().toString());

	}

	@Override
	public boolean Validate(){
		StringBuilder sb = new StringBuilder();
		Context context = spinnerStatus.getContext();
		if(spinnerStatus.getSelectedItem() == null || ! (spinnerStatus.getSelectedItem() instanceof RedmineStatus)){
			sb.append(context.getString(R.string.input_error_select,context.getString(R.string.ticket_status)));
			sb.append("\n");
		}
		if(spinnerPriority.getSelectedItem() == null || ! (spinnerPriority.getSelectedItem() instanceof RedminePriority)){
			sb.append(context.getString(R.string.input_error_select,context.getString(R.string.ticket_priority)));
			sb.append("\n");
		}
		if(spinnerTracker.getSelectedItem() == null || ! (spinnerTracker.getSelectedItem() instanceof RedmineTracker)){
			sb.append(context.getString(R.string.input_error_select,context.getString(R.string.ticket_tracker)));
			sb.append("\n");
		}
		boolean valid = true;
		//Validate spinners
		if(sb.length() > 0){
			Toast.makeText(spinnerStatus.getContext(), sb.toString(), Toast.LENGTH_LONG).show();
			valid  = false;
		}
		//Validate forms
		if(!ValidateForms(
				textDateStart
				, textDateDue
				, textTitle
				, textTime
				))
			valid = false;

		//TODO check tags and branch name


		return valid;
	}

}

