package party.sicef.borderless.controller;

import android.app.Activity;
import android.app.ProgressDialog;

import party.sicef.borderless.R;

/**
 * Created by ahuskano on 14.11.2015..
 */
public abstract class DialogController extends BaseController{

    private Activity activity;
    private ProgressDialog dialog;

    public DialogController(Activity activity){
        this.activity=activity;
    }
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * Method used to show dialog
     */
    public void showDialog(){
        if(dialog==null)
            setUpDialog();
        dialog.show();
    }


    /**
     * Method used to set up dialog
     *
     */
    private void setUpDialog(){
        dialog=new ProgressDialog(getActivity(), R.style.DialogTheme);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(android.R.style.Widget_Holo_ProgressBar_Large);
    }

    /**
     * Method used to dismiss showing dialog
     */
    public void dismissDialog(){
        if(dialog!=null && dialog.isShowing())
            dialog.dismiss();
    }
}
