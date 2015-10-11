package com.peirra.tvdialog;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v17.leanback.app.GuidedStepFragment;
import android.support.v17.leanback.widget.GuidanceStylist;
import android.support.v17.leanback.widget.GuidedAction;
import android.text.Html;


import java.util.List;

/**
 * Created by kurt on 16 08 2015 .
 */
public class TVDialogActivity extends Activity implements DialogContract {
    private static final int YES = 0;
    private static final int NO = 1;

    public static final String ARG_TITLE_RES = "arg_title";
    public static final String ARG_POSITIVE_RES = "arg_yes";
    public static final String ARG_NEGATIVE_RES = "arg_no";
    public static final String ARG_ICON_RES = "arg_icon";
    public static final String ARG_DESC_RES = "arg_desc";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getIntent().getExtras();
        int iconRes = args.getInt(ARG_ICON_RES);
        int yesRes = args.getInt(ARG_POSITIVE_RES);
        int noRes = args.getInt(ARG_NEGATIVE_RES);
        int descRes = args.getInt(ARG_DESC_RES);
        int titleRes = args.getInt(ARG_TITLE_RES);
        GuidedStepFragment.add(getFragmentManager(), AlertSelectionFragment.newInstance(titleRes, iconRes, descRes, yesRes, noRes));
    }

    private static void addAction(List<GuidedAction> actions, long id, String title, String desc) {
        actions.add(new GuidedAction.Builder()
                .id(id)
                .title(title)
                .description(desc)
                .build());
    }

    @Override
    public void onSelection(boolean positive) {
        if(positive){
            setResult(RESULT_OK);
        }else{
            setResult(RESULT_CANCELED);
        }
        finish();
    }


    public static class AlertSelectionFragment extends BaseGuidedStepFragment<DialogContract> {

        public static AlertSelectionFragment newInstance(int titleRes,int iconRes,int descRes,int yesRes,int noRes) {
            Bundle args = new Bundle();
            args.putInt(ARG_TITLE_RES,titleRes);
            args.putInt(ARG_ICON_RES,iconRes);
            args.putInt(ARG_NEGATIVE_RES,noRes);
            args.putInt(ARG_POSITIVE_RES,yesRes);
            args.putInt(ARG_DESC_RES,descRes);
            AlertSelectionFragment fragment = new AlertSelectionFragment();
            fragment.setArguments(args);
            return fragment;
        }
//        @Override
//        public int onProvideTheme() {
//            return R.style.PeirrTheme_Tv_GuidedStep_Connect;
//        }


        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {
            String title = getString(getArguments().getInt(ARG_TITLE_RES));
            String breadcrumb = "";
            String description = Html.fromHtml(getString(getArguments().getInt(ARG_DESC_RES))).toString();
            Drawable icon = getActivity().getDrawable(getArguments().getInt(ARG_ICON_RES));
            return new GuidanceStylist.Guidance(title, description, breadcrumb, icon);
        }

        @Override
        public void onCreateActions(List<GuidedAction> actions, Bundle savedInstanceState) {
            addAction(actions, YES,
                    getResources().getString(getArguments().getInt(ARG_POSITIVE_RES)),"");
            addAction(actions, NO,
                    getResources().getString(getArguments().getInt(ARG_NEGATIVE_RES)),"");
        }

        @Override
        public void onGuidedActionClicked(GuidedAction action) {
            if (action.getId() == YES) {
                getContract().onSelection(true);
            } else {
                getContract().onSelection(false);
            }
        }
    }
}
