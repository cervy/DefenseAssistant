package com.szzaq.jointdefence.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
  
public class StatedFragment extends Fragment {
  
    Bundle savedState;
  
    public StatedFragment() {
        super();
    }
  
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Restore State Here
        if (!restoreStateFromArguments()) {
            // First Time, Initialize something here
           // onFirstTimeLaunched();
        }
    }
  

  
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save State Here
        saveStateToArguments();
    }
  
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Save State Here
        saveStateToArguments();
    }
  
    ////////////////////
    // Don't Touch !!
    ////////////////////
  
    private void saveStateToArguments() {
        if (getView() != null)
            savedState = saveState();
        if (savedState != null) {
            Bundle b = getArguments();
            b.putBundle("savedState", savedState);
        }
    }
  
    ////////////////////
    // Don't Touch !!
    ////////////////////
  
    private boolean restoreStateFromArguments() {
        Bundle b = getArguments();
        savedState = b.getBundle("savedState");
        if (savedState != null) {
            restoreState();
            return true;
        }
        return false;
    }
  
    /////////////////////////////////6
    // Restore Instance State Here
    /////////////////////////////////
  
    private void restoreState() {
        if (savedState != null) {
            // For Example
            //tv1.setText(savedState.getString(text));
          //  onRestoreState(savedState);
        }
    }
  

  
    //////////////////////////////
    // Save Instance State Here
    //////////////////////////////
  
    private Bundle saveState() {
        Bundle state = new Bundle();
        // For Example
        //state.putString(text, tv1.getText().toString());
     //   onSaveState(state);
        return state;
    }
  

}