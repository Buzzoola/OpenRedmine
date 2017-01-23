package jp.redmine.redmineclient.url;

import android.net.Uri;

/**
 * Created by SSvistunov on 18.01.17.
 */
public class RemoteUrlCustomFields extends RemoteUrl {

	@Override
	public Uri.Builder getUrl(String baseurl) {
		Uri.Builder url = convertUrl(baseurl);
		url.appendEncodedPath("custom_fields." + getExtention());
		return url;
	}
}