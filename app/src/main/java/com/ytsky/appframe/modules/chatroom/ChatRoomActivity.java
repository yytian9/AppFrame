package com.ytsky.appframe.modules.chatroom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.ChatRoomServiceObserver;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomData;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomResultData;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.ytsky.appframe.R;
import com.ytsky.appframe.util.GsonUtils;
import com.ytsky.appframe.util.Logger;

import java.util.List;
import java.util.Map;

public class ChatRoomActivity extends AppCompatActivity {

    private static final String TAG = ChatRoomActivity.class.getSimpleName();
    private TextView mTvMsg;
    private String roomId = "4032965";
    private StringBuilder textMsg = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);


        //        login();

        joinChatRoom();
        initView();
        initData();
    }

    private void login() {
        LoginInfo info = new LoginInfo("57513da18a204ca3961db7a0365b8517", "3b689525c651400d979042cdb64a2d31"); // config...
        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo loginInfo) {

                    }

                    @Override
                    public void onFailed(int i) {

                    }

                    @Override
                    public void onException(Throwable throwable) {

                    }
                    // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                };
        NIMClient.getService(AuthService.class).login(info)
                .setCallback(callback);
    }

    //加入房间
    private void joinChatRoom() {
        EnterChatRoomData data = new EnterChatRoomData(roomId);
        NIMClient.getService(ChatRoomService.class).enterChatRoom(data).setCallback(new RequestCallback<EnterChatRoomResultData>() {
                    @Override
                    public void onSuccess(EnterChatRoomResultData enterChatRoomResultData) {
                        Log.i(TAG, "进入房间成功---->getRoomId = " + enterChatRoomResultData.getRoomId());
                    }

                    @Override
                    public void onFailed(int i) {
                        Log.i(TAG, "进入房间失败---->i = " + i);

                    }

                    @Override
                    public void onException(Throwable throwable) {
                        Log.i(TAG, "进入房间失败---->throwable = " + throwable.toString());
                    }
                });
    }

    private void initView() {
        mTvMsg = (TextView) findViewById(R.id.tv_msg);
    }

    private void initData() {
        //消息内容监听
        NIMClient.getService(ChatRoomServiceObserver.class).observeReceiveMessage(incomingChatRoomMsg, true);
    }

    //消息内容的监听器
    Observer<List<ChatRoomMessage>> incomingChatRoomMsg = new Observer<List<ChatRoomMessage>>() {
        @Override
        public void onEvent(List<ChatRoomMessage> messages) {
            // 处理新收到的消息
            for (ChatRoomMessage message : messages) {
                if (message != null) {
                    final String sessionId = message.getSessionId();
                    if (!TextUtils.isEmpty(sessionId)) {
                        try {
                            if (message.getMsgType() == MsgTypeEnum.text) {
                                //下面是拿到房间信息的方法
                                Map<String, Object> map = message.getRemoteExtension();
                                String jsonStr = GsonUtils.toJson(map);

                                Logger.i(jsonStr);

                                //拿到文字内容的方法
                                String content = message.getContent();

                                textMsg.append(content);
                                mTvMsg.setText(textMsg);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    };

                /*Map<String, Object> map = message.getRemoteExtension();
                String result = GsonUtils.toJson(map);
                textMsg.append(result);
                mTvMsg.setText(textMsg);*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销相关消息内容监听
        NIMClient.getService(ChatRoomServiceObserver.class)
                .observeReceiveMessage(incomingChatRoomMsg, false);
        //退出房间
        NIMClient.getService(ChatRoomService.class).exitChatRoom(roomId);
    }
}
