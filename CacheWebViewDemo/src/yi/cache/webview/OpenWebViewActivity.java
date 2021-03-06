package yi.cache.webview;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

public class OpenWebViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open);

		StringBuilder log = new StringBuilder();
		String inPath = getInnerSDCardPath();
		log.append("内置SD卡路径：" + inPath + "\r\n");

		List<String> extPaths = getExtSDCardPath();
		for (String path : extPaths) {
			log.append("外置SD卡路径：" + path + "\r\n");
		}
		System.out.println(log.toString());

		Button open_webview = (Button) findViewById(R.id.open_webview);
		open_webview.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(OpenWebViewActivity.this, MainActivity.class));
			}
		});
	}

	/**
	 * 获取内置SD卡路径
	 * 
	 * @return
	 */
	public String getInnerSDCardPath() {
		return Environment.getExternalStorageDirectory().getPath();
	}

	/**
	 * 获取外置SD卡路径
	 * 
	 * @return 应该就一条记录或空
	 */
	public List<String> getExtSDCardPath() {
		List<String> lResult = new ArrayList<String>();
		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec("mount");
			InputStream is = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("extSdCard")) {
					String[] arr = line.split(" ");
					String path = arr[1];
					File file = new File(path);
					if (file.isDirectory()) {
						lResult.add(path);
					}
				}
			}
			isr.close();
		} catch (Exception e) {
		}
		return lResult;
	}
}
