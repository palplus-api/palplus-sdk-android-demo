package me.palplus.demo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import me.palplus.sdk.PalPlus;

public class MainActivity extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    final String boardId = "cd1ec670-ab5a-11e4-9e3a-25191cafc7c9";
    findViewById(R.id.goToBoardButton).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (PalPlus.getForum().canResolveOpenBoardIntent(boardId)) {
          startActivity(PalPlus.getForum().createOpenBoardIntent(boardId));
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
        if (PalPlus.getForum().canResolveCreateArticleIntent(boardId)) {
          startActivity(PalPlus.getForum().createCreateArticleIntent(boardId, null, null));
        } else {
          Toast.makeText(MainActivity.this, "Please install Pal+ first!", Toast.LENGTH_SHORT)
              .show();
        }
      }
    });
    findViewById(R.id.createArticleWithPictureButton).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (PalPlus.getForum().canResolveCreateArticleIntent(boardId)) {
          startActivity(PalPlus.getForum().createCreateArticleIntent(boardId, pictureUri, null));
        } else {
          Toast.makeText(MainActivity.this, "Please install Pal+ first!", Toast.LENGTH_SHORT)
              .show();
        }
      }
    });
    findViewById(R.id.createArticleWithPictureAndTextButton).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (PalPlus.getForum().canResolveCreateArticleIntent(boardId)) {
          startActivity(PalPlus.getForum()
              .createCreateArticleIntent(boardId, pictureUri, "hello world"));
        } else {
          Toast.makeText(MainActivity.this, "Please install Pal+ first!", Toast.LENGTH_SHORT)
              .show();
        }
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
