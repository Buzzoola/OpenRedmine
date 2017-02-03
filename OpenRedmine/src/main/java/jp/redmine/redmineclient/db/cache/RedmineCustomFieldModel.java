package jp.redmine.redmineclient.db.cache;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.redmine.redmineclient.entity.RedmineConnection;
import jp.redmine.redmineclient.entity.RedmineCustomField;
import jp.redmine.redmineclient.entity.RedmineStatus;

/**
 * Created by SSvistunov on 30.01.17.
 */


public class RedmineCustomFieldModel implements IMasterModel<RedmineCustomField> {
	private final static String TAG = RedmineRoleModel.class.getSimpleName();
	protected Dao<RedmineCustomField, Integer> dao;
	public RedmineCustomFieldModel(DatabaseCacheHelper helper) {
		try {
			dao = helper.getDao(RedmineCustomField.class);
		} catch (SQLException e) {
			Log.e(TAG,"getDao",e);
		}
	}

	public List<RedmineCustomField> fetchAll() throws SQLException{
		return dao.queryForAll();
	}

	public List<RedmineCustomField> fetchAll(int connection) throws SQLException{
		List<RedmineCustomField> item;
		item = dao.queryForEq(RedmineStatus.CONNECTION, connection);
		if(item == null){
			item = new ArrayList<>();
		}
		return item;
	}

	public RedmineCustomField fetchById(int connection, int statusId) throws SQLException{
		PreparedQuery<RedmineCustomField> query = dao.queryBuilder().where()
				.eq(RedmineCustomField.CONNECTION, connection)
				.and()
				.eq(RedmineCustomField.CUSTOMFIELD_ID, statusId)
				.prepare();
		Log.d(TAG,query.getStatement());
		RedmineCustomField item = dao.queryForFirst(query);
		if(item == null)
			item = new RedmineCustomField();
		return item;
	}

	public RedmineCustomField fetchById(int id) throws SQLException{
		RedmineCustomField item;
		item = dao.queryForId(id);
		if(item == null)
			item = new RedmineCustomField();
		return item;
	}


	@Override
	public long countByProject(int connection_id, long project_id) throws SQLException {
		QueryBuilder<RedmineCustomField, ?> builder = dao.queryBuilder();
		builder
				.setCountOf(true)
				.where()
				.eq(RedmineStatus.CONNECTION, connection_id)
		//.and()
		//.eq(RedmineStatus.PROJECT_ID, project_id)
		;
		return dao.countOf(builder.prepare());
	}

	@Override
	public RedmineCustomField fetchItemByProject(int connection_id,
	                                      long project_id, long offset, long limit) throws SQLException {
		QueryBuilder<RedmineCustomField, ?> builder = dao.queryBuilder();
		builder
				.limit(limit)
				.offset(offset)
				.orderBy(RedmineStatus.NAME, true)
				.where()
				.eq(RedmineStatus.CONNECTION, connection_id)
		//.and()
		//.eq(RedmineStatus.PROJECT_ID, project_id)
		;
		RedmineCustomField item = builder.queryForFirst();
		if(item == null)
			item = new RedmineCustomField();
		return item;
	}

	public int insert(RedmineCustomField item) throws SQLException{
		return dao.create(item);
	}

	public int update(RedmineCustomField item) throws SQLException{
		return dao.update(item);
	}
	public int delete(RedmineCustomField item) throws SQLException{
		return dao.delete(item);
	}
	public int delete(int id) throws SQLException{
		return dao.deleteById(id);
	}

	public RedmineCustomField refreshItem(RedmineConnection info, RedmineCustomField data) throws SQLException{
		return refreshItem(info.getId(),data);
	}
	public RedmineCustomField refreshItem(int connection_id, RedmineCustomField data) throws SQLException{
		if(data == null)
			return null;

		RedmineCustomField project = this.fetchById(connection_id, data.getCustomFieldId());
		data.setConnectionId(connection_id);
		if(project.getId() == null){
			this.insert(data);
			project = fetchById(connection_id, data.getCustomFieldId()  );
		} else {
			data.setId(project.getId());
			if(project.getModified() == null){
				project.setModified(new java.util.Date());
			}
			if(data.getModified() == null){
				data.setModified(new java.util.Date());
			}
			if(!project.getModified().before(data.getModified())){
				this.update(data);
			}
		}

		return project;
	}
}
