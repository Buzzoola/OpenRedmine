package jp.redmine.redmineclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.j256.ormlite.android.apptools.OrmLiteFragmentActivity;

import jp.redmine.redmineclient.activity.handler.AttachmentActionHandler;
import jp.redmine.redmineclient.activity.handler.AttachmentActionInterface;
import jp.redmine.redmineclient.activity.handler.ConnectionActionInterface;
import jp.redmine.redmineclient.activity.handler.ConnectionListHandler;
import jp.redmine.redmineclient.activity.handler.Core;
import jp.redmine.redmineclient.activity.handler.IssueActionInterface;
import jp.redmine.redmineclient.activity.handler.IssueViewHandler;
import jp.redmine.redmineclient.activity.handler.TimeEntryHandler;
import jp.redmine.redmineclient.activity.handler.TimeentryActionInterface;
import jp.redmine.redmineclient.activity.handler.WebviewActionInterface;
import jp.redmine.redmineclient.activity.helper.ActivityHelper;
import jp.redmine.redmineclient.db.cache.DatabaseCacheHelper;
import jp.redmine.redmineclient.fragment.ActivityInterface;
import jp.redmine.redmineclient.fragment.IssueList;
import jp.redmine.redmineclient.param.FilterArgument;

public class IssueFilterActivity extends OrmLiteFragmentActivity<DatabaseCacheHelper>
	implements ActivityInterface {
	private static final String TAG = IssueFilterActivity.class.getSimpleName();
	public IssueFilterActivity(){
		super();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		ActivityHelper.setupTheme(this);
		super.onCreate(savedInstanceState);
		getSupportActionBar();

		/**
		 * Add fragment on first view only
		 * On rotate, this method would be called with savedInstanceState.
		 */
		if(savedInstanceState != null)
			return;
		FilterArgument intent = new FilterArgument();
		intent.setIntent(getIntent());

		FilterArgument arg = new FilterArgument();
		arg.setArgument();
		arg.setConnectionId(intent.getConnectionId());
		arg.setProjectId(intent.getProjectId());
		arg.setFilterId(intent.getFilterId());

		getSupportFragmentManager().beginTransaction()
				.replace(android.R.id.content, IssueList.newInstance(arg))
				.commit();
	}

	@SuppressWarnings("unchecked")
	public <T> T getHandler(Class<T> cls){
		Core.ActivityRegistry registry = new Core.ActivityRegistry(){

			@Override
			public FragmentManager getFragment() {
				return getSupportFragmentManager();
			}

			@Override
			public Intent getIntent(Class<?> activity) {
				return new Intent(getApplicationContext(),activity);
			}

			@Override
			public void kickActivity(Intent intent) {
				startActivity(intent);
			}

		};
		if(cls.equals(ConnectionActionInterface.class))
			return (T) new ConnectionListHandler(registry);
		if(cls.equals(WebviewActionInterface.class))
			return (T) new IssueViewHandler(registry);
		if(cls.equals(IssueActionInterface.class))
			return (T) new IssueViewHandler(registry);
		if(cls.equals(TimeentryActionInterface.class))
			return (T) new TimeEntryHandler(registry);
		if(cls.equals(AttachmentActionInterface.class))
			return (T) new AttachmentActionHandler(registry);
		return null;
	}
}
