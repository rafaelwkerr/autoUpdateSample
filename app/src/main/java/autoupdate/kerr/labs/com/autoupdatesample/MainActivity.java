package autoupdate.kerr.labs.com.autoupdatesample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.app.AbstractUpdateListener;
import com.github.snowdream.android.app.DownloadTask;
import com.github.snowdream.android.app.UpdateFormat;
import com.github.snowdream.android.app.UpdateInfo;
import com.github.snowdream.android.app.UpdateManager;
import com.github.snowdream.android.app.UpdateOptions;
import com.github.snowdream.android.app.UpdatePeriod;


public class MainActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkVersion();

    }

    private void checkVersion() {
        UpdateManager manager = new UpdateManager(this);
        UpdateOptions options = new UpdateOptions.Builder(this)
                .checkUrl("https://api.myjson.com/bins/4hcwo")
                .updateFormat(UpdateFormat.JSON)
                .updatePeriod(new UpdatePeriod(UpdatePeriod.EACH_TIME))
                .checkPackageName(false)
                .build();

        //manager.check(this, options);

      manager.check(this, options, new AbstractUpdateListener() {

            AlertDialog alertDialog = null;

            /**
              *Exit the app here
              */
            @Override
            public void ExitApp() {
                System.out.print("ExitApp");
            }

            /**
             * show the update dialog
             *
             * @param info the info for the new app
             */
            @Override
            public void onShowUpdateUI(UpdateInfo info) {
                System.out.print("onShowUpdateUI");
            }

            /**
             * It's the latest app,or there is no need to update.
             */
            @Override
            public void onShowNoUpdateUI() {
                System.out.print("onShowNoUpdateUI");
            }

            /**
             * show the progress when downloading the new app
             *
             * @param info
             * @param task
             * @param progress
             */
            @Override
            public void onShowUpdateProgressUI(UpdateInfo info, DownloadTask task, int progress) {
                System.out.print("Download status:" + progress);
                if(progress==100){
                    managerLoading(View.VISIBLE, View.GONE);
                }
            }

            /**
             * show the checking dialog
             */
            @Override
            public void onStart() {
                //super.onStart();
                Context context = this.getContext();
                if(context != null) {
                    alertDialog = (new android.app.AlertDialog.Builder(context)).setMessage(context.getText(R.string.msg_checking_update)).setCancelable(false).create();
                    alertDialog.show();
                }

            }

            /**
             * hide the checking dialog
             */
            @Override
            public void onFinish() {
                //super.onFinish();
                if(this.alertDialog != null) {
                    this.alertDialog.dismiss();
                }
                managerLoading(View.GONE, View.VISIBLE);
            }
        });

    }

    private void managerLoading(int textView, int containerLoadingView) {
        TextView version = (TextView) findViewById(R.id.txt);
        version.setVisibility(textView);
        LinearLayout containerLoading = (LinearLayout) findViewById(R.id.container_loading);
        containerLoading.setVisibility(containerLoadingView);
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
