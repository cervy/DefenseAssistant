package com.szzaq.jointdefence.activity.ke;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.szzaq.jointdefence.R;

import com.szzaq.jointdefence.A_Base;
import com.szzaq.jointdefence.App;
import com.szzaq.jointdefence.model.Device;

/**
 * 设备详情界面 listview实现其布局。
 *
 * @author jiajiaUser
 * @date 2015/07/20
 */
public class EquipmentDetailsActivity extends A_Base implements OnClickListener {
    private Device list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView( R.layout.activity_equipment_details);
        App.getInstance().addActivity(this);
        initView();
    }

    public void initView() {
        list = getIntent().getParcelableExtra("device");

        findViewById( R.id.equipment_details_back).setOnClickListener(this);
        TextView weibaodanwei = (TextView) findViewById( R.id.weibaodanwei);
        TextView weibaoyuan = (TextView) findViewById( R.id.weibaoyuan);
        TextView shebeimingcheng = (TextView) findViewById( R.id.shebeimingcheng);
        TextView shebeiweizhi = (TextView) findViewById( R.id.shebeiweizhi);
        TextView name = (TextView) findViewById( R.id.name);
        TextView zerenren = (TextView) findViewById( R.id.zerenren);
        TextView guanliren = (TextView) findViewById( R.id.guanliren);
        TextView guanxidanwei = (TextView) findViewById( R.id.guanxiadanwei);
        TextView jianduyuan = (TextView) findViewById( R.id.jianduyuan);
        TextView xieguanyuan = (TextView) findViewById(R.id.xieguanyuan);
        name.setText(list.getKey_enterprise_name());
        zerenren.setText(list.getIncharge_name());
        jianduyuan.setText(list.getSupervisor_assitant_name());
        guanxidanwei.setText(list.getSupervisor_org());
        guanliren.setText(list.getAdmin_name());
        xieguanyuan.setText(list.getSupervisor_name());
        weibaodanwei.setText(list.getFp_company_name());
        weibaoyuan.setText(list.getMaintainer_name());
        shebeimingcheng.setText(list.getName());
        shebeiweizhi.setText(list.getLocation());
        initListeners(zerenren, guanliren, weibaoyuan, jianduyuan, xieguanyuan);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = null;
        switch (id) {
            case R.id.equipment_details_back:
                this.finish();
                break;

            case R.id.jianduyuan:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + list.getSupervisor_assitant_tel()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                break;
            case R.id.xieguanyuan:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + list.getSupervisor_assitant_tel()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                break;
            case  R.id.zerenren:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + list.getIncharge_tel()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                break;
            case  R.id.guanliren:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + list.getAdmin_tel()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                break;
            case  R.id.weibaoyuan:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + list.getSupervisor_tel()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                break;
            default:
                break;
        }
        if (intent != null) startActivity(intent);


    }


}
