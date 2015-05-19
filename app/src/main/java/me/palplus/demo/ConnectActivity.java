package me.palplus.demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import me.palplus.sdk.messenger.view.MessengerBaseActivity;

public class ConnectActivity extends MessengerBaseActivity {

  private static final String TAG = ConnectActivity.class.getSimpleName();

  private void goToSendMessageActivity() {
    startActivity(new Intent(this, SendMessageActivity.class));
    finish();
  }

  @Override
  protected void onBaseCreate(final Bundle savedInstanceState) {
    setContentView(R.layout.activity_connect);
    findViewById(R.id.connectButton).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(final View v) {
        connect();
      }
    });
  }

  @Override
  public void onSessionClose() {
    Log.d(TAG, "onSessionClose");
  }

  @Override
  public void onSessionOpen() {
    Log.d(TAG, "onSessionOpen");
    goToSendMessageActivity();
  }
}
