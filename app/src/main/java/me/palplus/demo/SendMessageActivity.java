package me.palplus.demo;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import me.palplus.sdk.PalPlus;
import me.palplus.sdk.messenger.view.MessengerBaseActivity;
import me.palplus.sdk.messenger.view.MessengerHelper;
import me.palplus.sdk.messenger.exception.MessengerException;
import me.palplus.sdk.messenger.model.MessengerCallback;
import me.palplus.sdk.messenger.model.PpMessage;
import me.palplus.sdk.messenger.model.PpMessageActionParams;
import me.palplus.sdk.messenger.model.PpMessageBuilder;
import me.palplus.sdk.messenger.model.PpSendMessageResult;
import me.palplus.sdk.messenger.model.PpUser;

public class SendMessageActivity extends MessengerBaseActivity {

  enum RequestCode {
    SELECT_FRIENDS, //
    ;
  }

  private final static String TAG = SendMessageActivity.class.getSimpleName();
  private CheckBox textCheckBox;
  private CheckBox imageUrlCheckBox;
  private CheckBox linkTextCheckBox;
  private CheckBox buttonTextCheckBox;
  private EditText notificationEditText;
  private EditText textEditText;
  private EditText imageUrlEditText;
  private EditText linkTextEditText;
  private EditText linkExecuteParamsEditText;
  private EditText linkMarketParamsEditText;
  private EditText buttonTextEditText;
  private EditText buttonExecuteParamsEditText;
  private EditText buttonMarketParamsEditText;

  private PpMessage buildMessage() {
    final PpMessageBuilder builder = new PpMessageBuilder();

    builder.setNotification(notificationEditText.getText().toString());

    if (textCheckBox.isChecked()) {
      builder.setText(textEditText.getText().toString());
    }

    if (imageUrlCheckBox.isChecked()) {
      builder.setImage(imageUrlEditText.getText().toString());
    }

    if (linkTextCheckBox.isChecked()) {
      builder.setAppLink(linkTextEditText.getText().toString(),
          new PpMessageActionParams().withExecuteParam(linkExecuteParamsEditText.getText()
              .toString()).withMarketParam(linkMarketParamsEditText.getText().toString()));
    }

    if (buttonTextCheckBox.isChecked()) {
      builder.setAppButton(buttonTextEditText.getText().toString(),
          new PpMessageActionParams().withExecuteParam(buttonExecuteParamsEditText.getText()
              .toString()).withMarketParam(buttonMarketParamsEditText.getText().toString()));
    }
    return builder.build();
  }

  ;

  private void consumeExecuteParams() {
    final String click = PpMessageActionParams.resolveExecuteParams(getIntent(), "click");
    if (click == null || click.length() == 0) {
      return;
    }
    Toast.makeText(getActivity(), "you clicked a " + click, Toast.LENGTH_SHORT).show();
  }

  private Activity getActivity() {
    return this;
  }

  private void goToMainActivity() {
    startActivity(new Intent(this, ConnectActivity.class));
    finish();
  }

  private void loadProfile() {
    final TextView nameView = (TextView) findViewById(R.id.name);
    final ImageView iconView = (ImageView) findViewById(R.id.icon);

    PalPlus.getMessenger().requestMe(new MessengerCallback<PpUser>() {

      @Override
      public void done(PpUser user, MessengerException e) {
        if (e != null) {
          Log.e(TAG, "PalPlus.getMessenger().requestMe.onFailure", e);
        } else {
          Log.d(TAG, "PalPlus.getMessenger().requestMe.onSuccess:" + user);
          nameView.setText(user.getNickname());
          Picasso.with(getActivity()).load(user.getIconUrl()).into(iconView);
        }
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == RequestCode.SELECT_FRIENDS.ordinal() && resultCode == RESULT_OK) {
      final SelectFriendActivity.Result result = SelectFriendActivity.Result.createFromIntent(data);

      PalPlus.getMessenger()
          .sendMessage(result.getFriendUid(),
              buildMessage(),
              new MessengerCallback<PpSendMessageResult>() {
                @Override
                public void done(PpSendMessageResult sendAck, MessengerException e) {
                  if (e != null) {
                    Toast.makeText(getActivity(),
                        "fail to send message:" + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
                  } else {
                    Toast.makeText(getActivity(),
                        "message sent to " + result.getFriendName() + " successfully",
                        Toast.LENGTH_SHORT).show();
                  }
                }
              });
    }

  }

  @Override
  protected void onBaseCreate(final Bundle savedInstanceState) {
    setContentView(R.layout.activity_send_message);

    textCheckBox = (CheckBox) findViewById(R.id.text_checkbox);
    imageUrlCheckBox = (CheckBox) findViewById(R.id.image_url_checkbox);
    linkTextCheckBox = (CheckBox) findViewById(R.id.link_text_checkbox);
    buttonTextCheckBox = (CheckBox) findViewById(R.id.button_text_checkbox);

    notificationEditText = (EditText) findViewById(R.id.notification_edittext);
    textEditText = (EditText) findViewById(R.id.text_edittext);
    imageUrlEditText = (EditText) findViewById(R.id.image_url_edittext);
    linkTextEditText = (EditText) findViewById(R.id.link_text_edittext);
    linkExecuteParamsEditText = (EditText) findViewById(R.id.link_execute_params_edittext);
    linkMarketParamsEditText = (EditText) findViewById(R.id.link_market_params_edittext);
    buttonTextEditText = (EditText) findViewById(R.id.button_text_edittext);
    buttonExecuteParamsEditText = (EditText) findViewById(R.id.button_execute_params_edittext);
    buttonMarketParamsEditText = (EditText) findViewById(R.id.button_market_params_edittext);

    findViewById(R.id.friend_button).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(final View v) {
        final PpMessage PpMessage = buildMessage();

        if (PpMessage.isEmpty()) {
          Toast.makeText(getActivity(), "cannot send an empty message", Toast.LENGTH_LONG).show();
        } else {
          startActivityForResult(SelectFriendActivity.createIntent(getActivity()),
              RequestCode.SELECT_FRIENDS.ordinal());
        }
      }
    });

    consumeExecuteParams();
  }

  @Override
  public boolean onCreateOptionsMenu(final Menu menu) {
    final MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.activity_send_message, menu);
    return true;
  }

  @Override
  protected void onNewIntent(final Intent intent) {
    setIntent(intent);
    consumeExecuteParams();
  }

  @Override
  public boolean onOptionsItemSelected(final MenuItem item) {
    switch (item.getItemId()) {
      case R.id.make_access_token_need_to_refresh:
        MessengerHelper.getSession().testMakeAccessTokenNeedToRefresh();
        return true;
      case R.id.item_show_access_token:
        final String accessTokenString = "access token:"
            + MessengerHelper.getSession()
            .getAccessToken()
            + "\n\nexpire:"
            + MessengerHelper.getSession().getExpireTime()
            + " ("
            + DateUtils.getRelativeTimeSpanString(MessengerHelper.getSession()
            .getExpireTime()
            .getTime(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS)
            + ")";
        new AlertDialog.Builder(this).setMessage(accessTokenString).show();
        System.out.println(accessTokenString);
        return true;
      case R.id.item_invalidate_access_token:
        MessengerHelper.getSession().testInvalidAccessToken();
        Toast.makeText(getActivity(), "try to pause then resume", Toast.LENGTH_SHORT).show();
        return true;
      case R.id.item_logout:
        disconnect();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onSessionClose() {
    Log.d(TAG, "onSessionClose");
    goToMainActivity();
  }

  @Override
  public void onSessionOpen() {
    Log.d(TAG, "onSessionOpen");
    loadProfile();
  }
}
