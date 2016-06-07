package com.szzaq.jointdefence;

import android.os.Bundle;

/**
 * Created by DOS on 2016/5/12 0012.
 */
public class A_ChangeMicroStationDetail extends A_Base {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addView(R.layout.a_changemicrostationdetail, "修改信息", 0);
        right.setText(R.string.complete);
        right.setOnClickListener(this);
    }

    @Override
    public void click(int id) {
        super.click(id);
        switch (id) {
            case R.id.right:
                // TODO: 2016/5/12 0012                   showToast("修改成功");


                break;
        }
    }
}
