package com.ytsky.appframe.modules.live;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ytsky.appframe.R;

public class LiveActivity extends AppCompatActivity {

    private TextView mTvLiveUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        mTvLiveUrl = (TextView) findViewById(R.id.tv_live_url);
        findViewById(R.id.btn_live).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = mTvLiveUrl.getText().toString().trim();
//                path ="rtmp://push.live.nagezan.net/vod/1cf456f2-c515-4131-80ab-d18973192deb";
//                path ="rtmp://push.live.nagezan.net/vod/48211601-613b-44f2-8b66-1b2edb2105add";
                //"/vod/65d7e475-5b1f-4acf-ab0e-db6e3ee3524b"

                path ="rtmp://push-ks.live.nagezan.net/live/dba06398-f135-4b28-b911-8287960f7204?vdoid=20161217113358";
                Intent intent = new Intent(LiveActivity.this, CameraActivity.class);
                startActivity(intent);

                CameraActivity.startActivity(getApplicationContext(), 0,
                        path, 15, 800,
                        48, 0, false, 3,
                        false, true);


            }
        });
    }
}
