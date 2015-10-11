package com.peirra.tvdialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.v17.leanback.app.GuidedStepFragment;

/**
 * Created by kurt on 1/28/14 - 4:48 PM.
 */
public abstract class BaseGuidedStepFragment<T> extends GuidedStepFragment {
    protected Activity activity;
    protected boolean visible;
    private T contract;

    @SuppressWarnings("unchecked")
    @Override
    public void onAttach(Activity activity) {
        this.activity = getActivity();
        try {
            contract = (T) activity;
        } catch (ClassCastException e) {
            throw new IllegalStateException(activity.getClass().getSimpleName()
                    + " does not implement " + getClass().getSimpleName() + "'s contract interface.", e);
        }
        super.onAttach(activity);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        visible = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        visible = false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        contract = null;
    }

    public final T getContract() {
        if (contract == null) {
            contract = (T) activity;
        }
        return contract;
    }
}