package com.ytsky.appframe.modules.player;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ytsky.appframe.R;

public class LiveActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvLiveUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        initView();
    }

    private void initView() {
        mTvLiveUrl = (TextView) findViewById(R.id.tv_live_url);
        Button btnPlayVideo = (Button) findViewById(R.id.btn_play_video);
        btnPlayVideo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play_video:
                startVideo();
                break;
            default:
                break;
        }
    }

    private void startVideo() {
        String path = mTvLiveUrl.getText().toString().trim();
        Intent intent = new Intent(this, TextureVideoActivity.class);
        intent.putExtra("path", path);
        startActivity(intent);

    }
}
