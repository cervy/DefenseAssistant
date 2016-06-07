package com.szzaq.jointdefence.activity.ke;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.szzaq.jointdefence.R;

import com.szzaq.jointdefence.A_Base;
import com.szzaq.jointdefence.App;
import com.szzaq.jointdefence.model.Enterprise;
import com.szzaq.jointdefence.utils.DensityUtil;

/**
 * 重点单位详情界面
 *
 * @author jiajiaUser
 * @date 2015/07/14
 */
public class ZhongdianDetailsActivity extends A_Base implements OnClickListener {

    //设备清单
    private TextView zhongdian_details_equipmentlist, name, zerenren, guanliren, guanxidanwei, jianduyuan, xieguanyuan;
    //返回键
    private ImageView zhongdian_details_back;
    //场所详情列表，由于是动态角色，所以用列表动态获取展示
    //private ListView Zhongdian_details_listview;
    //详情数据集合
    //private List<HashMap<String, Object>> list;
    private Enterprise list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_zhongdian_details);
        App.getInstance().addActivity(this);
        zhongdian_details_equipmentlist = (TextView) this.findViewById(R.id.zhongdian_details_equipmentlist);
        // Zhongdian_details_listview = (ListView) this.findViewById(R.id.Zhongdian_details_listview);
        zhongdian_details_back = (ImageView) this.findViewById(R.id.zhongdian_details_back);
        //获取右箭头
        Drawable dra = getResources().getDrawable(R.drawable.arrow);
        assert dra != null;
        dra.setBounds(0, 0, DensityUtil.dip2px(this, 10), DensityUtil.dip2px(this, 15));
        zhongdian_details_equipmentlist.setCompoundDrawables(null, null, dra, null);
        //绑定事件
        zhongdian_details_back.setOnClickListener(this);
        //获取上一个界面的详情数据
        list = getIntent().getParcelableExtra("list");
        //Zhongdian_details_listview.setAdapter(new ActivityZhongdianDetailsAdapter(list, this, 0));
        //跳转至设备清单界面
        zhongdian_details_equipmentlist.setOnClickListener(this);
        zerenren = (TextView) findViewById(R.id.zerenren);
        guanliren = (TextView) findViewById(R.id.guanliren);
        guanxidanwei = (TextView) findViewById(R.id.guanxiadanwei);
        jianduyuan = (TextView) findViewById(R.id.jianduyuan);
        xieguanyuan = (TextView) findViewById(R.id.xieguanyuan);
        name = (TextView) findViewById(R.id.name);
        name.setText(list.getName());

        zerenren.setText(list.getIncharge_name());
        jianduyuan.setText(list.getSupervisor_assistant_name());
        guanxidanwei.setText(list.getSupervisor_org_name());
        guanliren.setText(list.getSupervisor_name());

        xieguanyuan.setText(list.getSupervisor_assistant_name());

        initListeners(R.id.jianduyuan, R.id.guanliren,R.id.zerenren, R.id.xieguanyuan);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = null;
        switch (id) {
            case R.id.zhongdian_details_back:
                this.finish();
                break;
            case R.id.zhongdian_details_equipmentlist:// TODO: 2016/4/28 0028 设备清单
                intent = new Intent(this, EquipmentListActivity.class);
                break;
            case R.id.jianduyuan:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + list.getIncharge_mobile()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                break;
            case R.id.xieguanyuan:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + list.getIncharge_mobile()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                break;
            case R.id.zerenren:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + list.getIncharge_mobile()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                break;
            case R.id.guanliren:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + list.getIncharge_mobile()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                break;
            default:
                break;
        }
        if (intent != null) startActivity(intent);

    }


}
