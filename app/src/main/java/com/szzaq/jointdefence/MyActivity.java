package com.szzaq.jointdefence;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.szzaq.jointdefence.utils.DensityUtil;

/**
 * @author jiajiaUser
 * @date 2015/07/07
 */
public class MyActivity extends A_Base {

    //控件
    private TextView my_sanxiao, my_zhongdian, my_set, my_help, tvChangePwd;//my_message,my_message_t,my_sanxiao_t, my_zhongdian_t, my_set_t,
    private TableRow my_help_t, changePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addView(R.layout.activity_my, R.string.my_title, 0);
        App.getInstance().addActivity(this);

      /*  my_sanxiao = (TextView) this.findViewById(R.id.my_sanxiao);
        my_zhongdian = (TextView) this.findViewById(R.id.my_zhongdian);*/
        //my_message = (TextView) this.findViewById(R.id.my_message);
       /* my_set = (TextView) this.findViewById(R.id.my_set);*/
        my_help = (TextView) this.findViewById(R.id.my_help);
        //this.findViewById(R.id.my_sanxiao_t).setOnClickListener(this);
      /*   my_zhongdian_t = (TableRow) this.findViewById(R.id.my_zhongdian_t);*/
        //my_message_t = (TableRow) this.findViewById(R.id.my_message_t);
        //my_set_t = (TableRow) this.findViewById(R.id.my_set_t);
        my_help_t = (TableRow) this.findViewById(R.id.my_help_t);
        changePwd = (TableRow) this.findViewById(R.id.changePwd);
        tvChangePwd = (TextView) this.findViewById(R.id.tvChangePwd);
        //获取箭头
        Drawable dra = getResources().getDrawable(R.drawable.arrow);
        assert dra != null;
        dra.setBounds(0, 0, DensityUtil.dip2px(this, 10), DensityUtil.dip2px(this, 15));
        //绑定箭头
       /* my_sanxiao.setCompoundDrawables(null, null, dra, null);
        my_zhongdian.setCompoundDrawables(null, null, dra, null);*/
        // my_message.setCompoundDrawables(null, null, dra, null);
        //my_set.setCompoundDrawables(null, null, dra, null);
        my_help.setCompoundDrawables(null, null, dra, null);
        tvChangePwd.setCompoundDrawables(null, null, dra, null);
        //绑定返回键点击事件
        //绑定跳转事件
        // my_sanxiao_t.setOnClickListener(this);
        // my_zhongdian_t.setOnClickListener(this);
        //my_message_t.setOnClickListener(this);
        //my_set_t.setOnClickListener(this);
        my_help_t.setOnClickListener(this);
        changePwd.setOnClickListener(this);
        this.findViewById(R.id.trExit).setOnClickListener(this);

    }

    @Override
    public void click(int id) {
        super.click(id);
        Intent intent;
        switch (id) {
            case R.id.back:
                startActivity(new Intent(this, MainActivity.class));

                break;

            //跳转三小
          /*   case R.id.my_sanxiao_t:
                intent = new Intent(this, InfoActivity.class);
                intent.putExtra("place_type", 1);
                this.startActivity(intent);
                break;
            //跳转重点
            case R.id.my_zhongdian_t:
                intent = new Intent(this, InfoActivity.class);
                intent.putExtra("place_type", 0);
                this.startActivity(intent);
                break;*/
           /* //跳转消息
            case R.id.my_message_t:
                intent = new Intent(this, MessageActivity.class);
                this.startActivity(intent);
                break;*/
            //跳转设置
          /*  case R.id.my_set_t:
                Intent setIntent = new Intent(this, HjhConfigActivity.class);
                startActivity(setIntent);
                break;*/
            //跳转帮助
            case R.id.my_help_t:
                Toast.makeText(getApplicationContext(), "欢迎您的使用", Toast.LENGTH_SHORT).show();
                break;
            //修改密码
            case R.id.changePwd:
                intent = new Intent(this, ChangePwdActivity.class);
                startActivity(intent);
                break;
            case R.id.trExit://注销登陆
                MyActivity.this.getSharedPreferences("fpplus", MODE_PRIVATE)
                        .edit().putString("token", "")
                        .commit();

                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }
}
