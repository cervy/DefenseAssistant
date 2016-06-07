package com.szzaq.jointdefence.activity.ke;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.szzaq.jointdefence.App;
import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.SearchMaintenanceActivity;

import java.util.HashMap;

/**
 * 编辑设备界面
 *
 * @author jiajiaUser
 * @date 2015/07/09
 */
public class EquipmentEditActivity extends Activity implements OnClickListener {

    private TextView edit_maintenance, edit_equipment_save;
    private HashMap<String, Object> map;
    private EditText edit_equipment_name, edit_equipment_number,
            edit_equipment_location, edit_maintenance_person;
    private ImageView edit_equipment_back;
    //本界面类型，0为修改，1为新增
    private int equipment_type;
    //维保单位回传值判断是否成功
    public static int RESULT_SUCCESS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_editequipment_);
        App.getInstance().addActivity(this);
        equipment_type = getIntent().getIntExtra("equipment_type", 0);
        initView();
    }

    /**
     * 初始化控件
     */
    public void initView() {
        edit_maintenance = (TextView) this.findViewById(R.id.edit_maintenance);

        edit_equipment_name = (EditText) this
                .findViewById(R.id.edit_equipment_name);
        edit_equipment_number = (EditText) this
                .findViewById(R.id.edit_equipment_number);
        edit_equipment_location = (EditText) this
                .findViewById(R.id.edit_equipment_location);
        edit_maintenance_person = (EditText) this
                .findViewById(R.id.edit_maintenance_person);
        edit_equipment_back = (ImageView) this
                .findViewById(R.id.edit_equipment_back);
        edit_equipment_save = (TextView) this
                .findViewById(R.id.edit_equipment_save);
        //修改时，获取上一个界面传过来的信息
        if (equipment_type == 0) {
            map = (HashMap<String, Object>) getIntent().getSerializableExtra("map");
            edit_equipment_name.setText(map.get("equipment_name").toString());
            edit_equipment_number.setText(map.get("equipment_number").toString());
            edit_equipment_location.setText(map.get("equipment_location").toString());
            edit_maintenance_person.setText(map.get("maintenance_person").toString());
            edit_maintenance.setText(map.get("maintenance").toString());
        }
        edit_equipment_back.setOnClickListener(this);
        edit_equipment_save.setOnClickListener(this);
        edit_maintenance.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.edit_equipment_back:
                this.finish();
                break;
            case R.id.edit_equipment_save:

                break;
            case R.id.edit_maintenance:
                Intent intent = new Intent(this, SearchMaintenanceActivity.class);
                this.startActivityForResult(intent, RESULT_SUCCESS);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_SUCCESS) {
            String name = data.getStringExtra("name");
            edit_maintenance.setText(name);
        }
    }

}
