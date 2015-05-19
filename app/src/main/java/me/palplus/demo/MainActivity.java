package me.palplus.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import me.palplus.sdk.PalPlus;

public class MainActivity extends AppCompatActivity {

  private final static String BOARD_ID = "cd1ec670-ab5a-11e4-9e3a-25191cafc7c9";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViewById(R.id.goToMessenger).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(MainActivity.this, ConnectActivity.class));
      }
    });
    findViewById(R.id.goToBoardButton).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (PalPlus.getForum().canResolveOpenBoardIntent(BOARD_ID)) {
          startActivity(PalPlus.getForum().createOpenBoardIntent(BOARD_ID));
        } else {
          Toast.makeText(MainActivity.this, "Please install Pal+ first!", Toast.LENGTH_SHORT)
              .show();
        }
      }
    });

    final Uri pictureUri = Uri.parse("android.resource://"
        + getPackageName()
        + "/"
        + R.drawable.sample);
    findViewById(R.id.createArticleButton).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (PalPlus.getForum().canResolveCreateArticleIntent(BOARD_ID)) {
          startActivity(PalPlus.getForum().createCreateArticleIntent(BOARD_ID, null, null));
        } else {
          Toast.makeText(MainActivity.this, "Please install Pal+ first!", Toast.LENGTH_SHORT)
              .show();
        }
      }
    });
    findViewById(R.id.createArticleWithPictureButton).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (PalPlus.getForum().canResolveCreateArticleIntent(BOARD_ID)) {
          startActivity(PalPlus.getForum().createCreateArticleIntent(BOARD_ID, pictureUri, null));
        } else {
          Toast.makeText(MainActivity.this, "Please install Pal+ first!", Toast.LENGTH_SHORT)
              .show();
        }
      }
    });
    findViewById(R.id.createArticleWithPictureAndTextButton).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (PalPlus.getForum().canResolveCreateArticleIntent(BOARD_ID)) {
          startActivity(PalPlus.getForum()
              .createCreateArticleIntent(BOARD_ID, pictureUri, "hello world"));
        } else {
          Toast.makeText(MainActivity.this, "Please install Pal+ first!", Toast.LENGTH_SHORT)
              .show();
        }
      }
    });
  }
}
