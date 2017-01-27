package com.ytsky.appframe.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.ytsky.appframe.R;
import com.ytsky.appframe.util.ToastUtils;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private static final String CATEGORY_SAMPLES = MainActivity.class.getPackage().getName();
    private static final String TAG_CLASS_NAME = "className";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_INTENT = "intent";

    private static final Comparator<Map<String, Object>> DISPLAY_NAME_COMPARATOR = new Comparator<Map<String, Object>>() {
        private final Collator collator = Collator.getInstance();

        @Override
        public int compare(Map<String, Object> lhs, Map<String, Object> rhs) {
            return collator.compare(lhs.get("className"), rhs.get("className"));
        }
    };
    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView listView;

    // Quickly navigate through the examples.
    enum Filter {
        All,
        GridView,
        RecyclerView,
        ScrollView,
        ListView,
        WebView,
        Toolbar,
        ActionBar,
        FlexibleSpace,
        Parallax,
        ViewPager,
    }

    Filter currentFilter = Filter.All;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(android.R.id.list);
        listView.setOnItemClickListener(this);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_toolbar);
        spinner.setAdapter(new FilterAdapter(this));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentFilter = Filter.values()[position];
                refreshData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        login();
    }

    private void login() {
        LoginInfo info = new LoginInfo("57513da18a204ca3961db7a0365b8517","3b689525c651400d979042cdb64a2d31"); // config...
        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo loginInfo) {
                        Log.i(TAG, "登录成功*****************************");
                    }

                    @Override
                    public void onFailed(int i) {
                        Log.i(TAG, "登录失败------------------------->"+i);

                    }

                    @Override
                    public void onException(Throwable throwable) {
                        Log.i(TAG, "登录失败"+throwable.toString());

                    }
                    // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                };
        NIMClient.getService(AuthService.class).login(info)
                .setCallback(callback);
    }

    private void refreshData() {
        listView.setAdapter(new SimpleAdapter(this, getData(),
                R.layout.list_item_main,
                new String[]{TAG_CLASS_NAME, TAG_DESCRIPTION,},
                new int[]{R.id.className, R.id.description,}));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem menu) {
        int id = menu.getItemId();
        if (id == R.id.menu_about) {
//            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
            ToastUtils.openToast("点击了关于界面",ToastUtils.TYPE_NORMAL);
            return true;
        }
        return false;
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> data = new ArrayList<>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.setPackage(getApplicationContext().getPackageName());
        mainIntent.addCategory(CATEGORY_SAMPLES);

        PackageManager pm = getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

        if (list == null) {
            return data;
        }

        for (ResolveInfo info : list) {
            CharSequence labelSeq = info.loadLabel(pm);
            String label = labelSeq != null
                    ? labelSeq.toString()
                    : info.activityInfo.name;

            String[] labelPath = label.split("/");

            String nextLabel = labelPath[0];

            if (labelPath.length == 1) {
                String nameLabel = info.activityInfo.name.replace(info.activityInfo.packageName + "", "");
                // Remove package and get simple class name
                if (nameLabel.contains(".")) {
                    nameLabel = nameLabel.replaceAll("[^.]*\\.", "");
                }

                // Filter logic.
                if (currentFilter == Filter.All || nameLabel.contains(currentFilter.name())) {
                    addItem(data,
                            nameLabel,
                            nextLabel,
                            activityIntent(
                                    info.activityInfo.applicationInfo.packageName,
                                    info.activityInfo.name));
                }
            }
        }

        Collections.sort(data, DISPLAY_NAME_COMPARATOR);

        return data;
    }

    protected Intent activityIntent(String pkg, String componentName) {
        Intent result = new Intent();
        result.setClassName(pkg, componentName);
        return result;
    }

    protected void addItem(List<Map<String, Object>> data, String className, String description,
                           Intent intent) {
        Map<String, Object> temp = new HashMap<>();
        temp.put(TAG_CLASS_NAME, className);
        temp.put(TAG_DESCRIPTION, description);
        temp.put(TAG_INTENT, intent);
        data.add(temp);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);

        Intent intent = (Intent) map.get(TAG_INTENT);
        startActivity(intent);
    }

    private class FilterAdapter extends ArrayAdapter<Filter> {
        public FilterAdapter(Context context) {
            super(context, android.R.layout.simple_spinner_item, Filter.values());
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }
    }
}
