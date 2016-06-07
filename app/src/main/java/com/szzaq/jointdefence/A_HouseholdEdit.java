package com.szzaq.jointdefence;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.model.Household;
import com.szzaq.jointdefence.net.GetRequest;
import com.szzaq.jointdefence.net.PostRequest;
import com.szzaq.jointdefence.utils.Resources;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by DOS on 2016/5/17 0017.
 */
public class A_HouseholdEdit extends A_Base {
    private Household e;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addView(R.layout.a_householdedit, R.string.household, 0);

        //    ctl00_MainContent_txtZJZXFGLRDH = (EditText) findViewById(R.id.ctl00_MainContent_txtZJZXFGLRDH);
        e = getIntent().getParcelableExtra("account");
        ctl00_MainContent_txtAQZRRSFZ = (EditText) findViewById(R.id.ctl00_MainContent_txtAQZRRSFZ);
        addr = (EditText) findViewById(R.id.addr);
        ctl00_MainContent_txtPLDWN = (EditText) findViewById(R.id.ctl00_MainContent_txtPLDWN);
        ctl00_MainContent_txtAQGLRSFZ = (EditText) findViewById(R.id.ctl00_MainContent_txtAQGLRSFZ);
        name = (EditText) findViewById(R.id.unitname);
        ctl00_MainContent_txtBNCWZ = (EditText) findViewById(R.id.ctl00_MainContent_txtBNCWZ);
        //   ctl00_MainContent_txtZJZXFGLRSFZ = (EditText) findViewById(R.id.ctl00_MainContent_txtZJZXFGLRSFZ);
        ctl00_MainContent_txtZZJGDM = (EditText) findViewById(R.id.ctl00_MainContent_txtZZJGDM);
        ctl00_MainContent_txtBNCS = (EditText) findViewById(R.id.ctl00_MainContent_txtBNCS);
        ctl00_MainContent_txtZDMJ = (EditText) findViewById(R.id.ctl00_MainContent_txtZDMJ);
        ctl00_MainContent_txtGDZC = (EditText) findViewById(R.id.ctl00_MainContent_txtGDZC);
        usename = (EditText) findViewById(R.id.usename);
        ctl00_MainContent_txtJZMJ = (EditText) findViewById(R.id.ctl00_MainContent_txtJZMJ);
        fax = (EditText) findViewById(R.id.fax);
        ctl00_MainContent_dropRQLX = (Spinner) findViewById(R.id.ctl00_MainContent_dropRQLX);
        ctl00_MainContent_txtSSDTS = (EditText) findViewById(R.id.ctl00_MainContent_txtSSDTS);
        ctl00_MainContent_txtBNCMJ = (EditText) findViewById(R.id.ctl00_MainContent_txtBNCMJ);
        ctl00_MainContent_dropJJSYZ = (Spinner) findViewById(R.id.ctl00_MainContent_dropJJSYZ);
        ctl00_MainContent_txtFRDBSFZ = (EditText) findViewById(R.id.ctl00_MainContent_txtFRDBSFZ);
        ctl00_MainContent_txtPLDWX = (EditText) findViewById(R.id.ctl00_MainContent_txtPLDWX);
        email = (EditText) findViewById(R.id.email);
        ctl00_MainContent_txtDWCLSJ = (EditText) findViewById(R.id.ctl00_MainContent_txtDWCLSJ);
        ctl00_MainContent_txtDLWZ = (EditText) findViewById(R.id.ctl00_MainContent_txtDLWZ);
        ctl00_MainContent_lblXZQY = (EditText) findViewById(R.id.ctl00_MainContent_lblXZQY);
        ctl00_MainContent_txtYZBM = (EditText) findViewById(R.id.ctl00_MainContent_txtYZBM);
        phone = (EditText) findViewById(R.id.unitphone);
        ctl00_MainContent_txtZGRS = (EditText) findViewById(R.id.ctl00_MainContent_txtZGRS);
        guanliren = (EditText) findViewById(R.id.guanliren);
        ctl00_MainContent_txtAQCKS = (EditText) findViewById(R.id.ctl00_MainContent_txtAQCKS);
        ctl00_MainContent_txtPLDWB = (EditText) findViewById(R.id.ctl00_MainContent_txtPLDWB);
        guanxiadanwei = (EditText) findViewById(R.id.guanxiadanwei);
        //  guanlirenn = (EditText) findViewById(R.id.guanlirenn);
        ctl00_MainContent_txtYWXFYS = (EditText) findViewById(R.id.ctl00_MainContent_txtYWXFYS);
        ctl00_MainContent_txtXFCDS = (EditText) findViewById(R.id.ctl00_MainContent_txtXFCDS);
        firedangerproperity = (EditText) findViewById(R.id.firedangerproperties);
        unittype = (EditText) findViewById(R.id.unittype);
        ctl00_MainContent_txtXFDTS = (EditText) findViewById(R.id.ctl00_MainContent_txtXFDTS);
        elseprofile = (EditText) findViewById(R.id.elseprofile);
        ctl00_MainContent_txtXFCDWZ = (EditText) findViewById(R.id.ctl00_MainContent_txtXFCDWZ);
        upunit = (EditText) findViewById(R.id.upunit);
        //  ctl00_MainContent_lblDWCSX = (EditText) findViewById(R.id.ctl00_MainContent_lblDWCSX);
        max = (EditText) findViewById(R.id.max);
        //  ctl00_MainContent_lblDWZSX = (EditText) findViewById(R.id.ctl00_MainContent_lblDWZSX);
        layer = (EditText) findViewById(R.id.layer);
        ctl00_MainContent_txtBZ = (EditText) findViewById(R.id.ctl00_MainContent_txtBZ);
        zerenren = (EditText) findViewById(R.id.zerenren);
        ctl00_MainContent_txtPLDWD = (EditText) findViewById(R.id.ctl00_MainContent_txtPLDWD);
        ctl00_MainContent_rblZDXFSS = (Spinner) findViewById(R.id.ctl00_MainContent_rblZDXFSS);
        //ctl00_MainContent_txtDWBH = (EditText) findViewById(R.id.ctl00_MainContent_txtDWBH);
        ctl00_MainContent_txtAQGLRDH = (EditText) findViewById(R.id.ctl00_MainContent_txtAQGLRDH);
        ctl00_MainContent_txtAQZRRDH = (EditText) findViewById(R.id.ctl00_MainContent_txtAQZRRDH);
        ctl00_MainContent_txtFRDBDH = (EditText) findViewById(R.id.ctl00_MainContent_txtFRDBDH);


        ctl00_MainContent_txtXFCDWZ.setText(e.ctl00_MainContent_txtXFCDWZ);
        ctl00_MainContent_txtXFDTS.setText(e.ctl00_MainContent_txtXFDTS);
        ctl00_MainContent_txtBNCWZ.setText(e.ctl00_MainContent_txtBNCWZ);
        ctl00_MainContent_txtAQZRRSFZ.setText(e.ctl00_MainContent_txtAQZRRSFZ);
        ctl00_MainContent_txtPLDWN.setText(e.ctl00_MainContent_txtPLDWN);
        //  ctl00_MainContent_txtZJZXFGLRSFZ.setText(e.ctl00_MainContent_txtZJZXFGLRSFZ);
        ctl00_MainContent_txtFRDBSFZ.setText(e.ctl00_MainContent_txtFRDBSFZ);
        // ctl00_MainContent_txtDWBH.setText(e.ctl00_MainContent_txtDWBH);
        ctl00_MainContent_txtSSDTS.setText(e.ctl00_MainContent_txtSSDTS);
        ctl00_MainContent_txtPLDWX.setText(e.ctl00_MainContent_txtPLDWX);
        ctl00_MainContent_txtPLDWB.setText(e.ctl00_MainContent_txtPLDWB);
        ctl00_MainContent_txtZZJGDM.setText(e.ctl00_MainContent_txtZZJGDM);

        actl00_MainContent_rblZDXFSS = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sctl00_MainContent_rblZDXFSS);
        actl00_MainContent_rblZDXFSS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ctl00_MainContent_rblZDXFSS.setAdapter(actl00_MainContent_rblZDXFSS);
        ctl00_MainContent_rblZDXFSS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ictl00_MainContent_rblZDXFSS = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ictl00_MainContent_rblZDXFSS = 0;
            }
        });
        actl00_MainContent_dropRQLX = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sctl00_MainContent_dropRQLX);
        actl00_MainContent_dropRQLX.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ctl00_MainContent_dropRQLX.setAdapter(actl00_MainContent_dropRQLX);
        ctl00_MainContent_dropRQLX.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ictl00_MainContent_dropRQLX = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ictl00_MainContent_dropRQLX = 1;
            }
        });
        actl00_MainContent_dropJJSYZ = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sctl00_MainContent_dropJJSYZ);
        actl00_MainContent_dropJJSYZ.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ctl00_MainContent_dropJJSYZ.setAdapter(actl00_MainContent_dropJJSYZ);
        ctl00_MainContent_dropJJSYZ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ictl00_MainContent_dropJJSYZ = position;
                if (position > 9) ictl00_MainContent_dropJJSYZ = 99;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ictl00_MainContent_dropJJSYZ = 0;
            }
        });


        //    ctl00_MainContent_txtZJZXFGLRDH.setText(e.ctl00_MainContent_txtZJZXFGLRDH);
        zerenren.setText(e.ctl00_MainContent_txtAQZRR);
        ctl00_MainContent_txtAQGLRSFZ.setText(e.ctl00_MainContent_txtAQGLRSFZ);
        ctl00_MainContent_txtBZ.setText(e.ctl00_MainContent_txtBZ);
        layer.setText(e.ctl00_MainContent_txtFRDB);
        ctl00_MainContent_txtDWCLSJ.setText(e.ctl00_MainContent_txtDWCLSJ);
        ctl00_MainContent_txtBNCMJ.setText(e.ctl00_MainContent_txtBNCMJ);

        max.setText(e.ctl00_MainContent_txtYYSZDRS);
        ctl00_MainContent_txtJZMJ.setText(e.ctl00_MainContent_txtJZMJ);
        // ctl00_MainContent_lblDWCSX.setText(e.ctl00_MainContent_lblDWCSX);
        upunit.setText(e.ctl00_MainContent_txtSJZGDW);
        ctl00_MainContent_txtZDMJ.setText(e.ctl00_MainContent_txtZDMJ);
        //    ctl00_MainContent_lblDWZSX.setText(e.ctl00_MainContent_lblDWZSX);
        ctl00_MainContent_txtYZBM.setText(e.ctl00_MainContent_txtYZBM);
        elseprofile.setText(e.ctl00_MainContent_lblDWQTQK);
        ctl00_MainContent_txtAQCKS.setText(e.ctl00_MainContent_txtAQCKS);
        unittype.setText(e.ctl00_MainContent_lblDWLX);
        ctl00_MainContent_txtZGRS.setText(e.ctl00_MainContent_txtZGRS);
        ctl00_MainContent_txtBNCS.setText(e.ctl00_MainContent_txtBNCS);
        ctl00_MainContent_txtYWXFYS.setText(e.ctl00_MainContent_txtYWXFYS);
        firedangerproperity.setText(e.ctl00_MainContent_lblZDDWLB);
        ctl00_MainContent_txtGDZC.setText(e.ctl00_MainContent_txtGDZC);
        ctl00_MainContent_txtDLWZ.setText(e.ctl00_MainContent_txtDLWZ);
        //  guanlirenn.setText(e.ctl00_MainContent_txtZJZXFGLR);
        guanxiadanwei.setText(e.ctl00_MainContent_txtXFGXDW);
        ctl00_MainContent_lblXZQY.setText(e.ctl00_MainContent_lblXZQY);
        guanliren.setText(e.ctl00_MainContent_txtAQGLR);
        phone.setText(e.ctl00_MainContent_txtDWDH);
        email.setText(e.ctl00_MainContent_txtDWDZYX);
        fax.setText(e.ctl00_MainContent_txtDWCZ);
        usename.setText(e.ctl00_MainContent_txtSYMC);
        ctl00_MainContent_txtXFCDS.setText(e.ctl00_MainContent_txtXFCDS);
        name.setText(e.ctl00_MainContent_txtDWMC);
        addr.setText(e.ctl00_MainContent_txtDWDZ);
        ctl00_MainContent_txtPLDWD.setText(e.ctl00_MainContent_txtPLDWD);
        ctl00_MainContent_txtAQGLRDH.setText(e.ctl00_MainContent_txtAQGLRDH);
        ctl00_MainContent_txtAQZRRDH.setText(e.ctl00_MainContent_txtAQZRRDH);
        ctl00_MainContent_txtFRDBDH.setText(e.ctl00_MainContent_txtFRDBDH);


        initListeners(R.id.btnSubmitt);
    }

    private EditText name, addr, usename, fax, email, phone, guanliren, guanxiadanwei, //guanlirenn,
            firedangerproperity, unittype, elseprofile, upunit, max, layer, zerenren;

    @Override
    public void click(int id) {
        super.click(id);
        switch (id) {
            case R.id.btnSubmitt:

                GetRequest request = new GetRequest();
                request.setCallback(new HttpCallback() {
                    @Override
                    public void handleData(String s) {
                        try {
                            JSONObject json = new JSONObject(s);

                            JSONArray objects = (json.getJSONArray(Resources.RESULTS));
                            //for (int i = 0; i < objects.length(); i++) {
                            JSONObject obj = objects.getJSONObject(0);
                            url = obj.optString("url");
                            obj.put("_method", "PUT");
                            obj.put("ctl00_MainContent_txtDWMC", name.getText().toString());
                            obj.put("ctl00_MainContent_txtSYMC", usename.getText().toString());
                            obj.put("ctl00_MainContent_txtDWDZ", addr.getText().toString());
                            obj.put("ctl00_MainContent_lblZDDWLB", firedangerproperity.getText().toString());
                            obj.put("ctl00_MainContent_lblDWLX", unittype.getText().toString());
                            obj.put("ctl00_MainContent_lblDWQTQK", elseprofile.getText().toString());
                            obj.put("ctl00_MainContent_txtSJZGDW", upunit.getText().toString());
                            obj.put("ctl00_MainContent_txtDWDH", phone.getText().toString());
                            obj.put("ctl00_MainContent_txtDWCZ", fax.getText().toString());
                            obj.put("ctl00_MainContent_txtDWDZYX", email.getText().toString());
                            obj.put("ctl00_MainContent_txtYYSZDRS", max.getText().toString());
                            obj.put("ctl00_MainContent_txtFRDB", layer.getText().toString());
                            obj.put("ctl00_MainContent_txtAQGLR", guanliren.getText().toString());// 安全管理人
                            //obj.put("ctl100_MainContent_txtAQZRR", zerenren.getText().toString());
                            // obj.put("ctl00_MainContent_txtZJZXFGLR", guanlirenn.getText().toString());
                            obj.put("account", e.account);
                            obj.put("pwd", e.pwd);

                            obj.put("ctl00_MainContent_txtXFGXDW", guanxiadanwei.getText().toString());


                            obj.put("ctl00_MainContent_txtZZJGDM", ctl00_MainContent_txtZZJGDM.getText().toString());// 组织机构代码：
                            obj.put("ctl00_MainContent_txtFRDBSFZ", ctl00_MainContent_txtFRDBSFZ.getText().toString());//法定代表人身份证:
                            obj.put("ctl00_MainContent_txtYZBM", ctl00_MainContent_txtYZBM.getText().toString());//邮政编码：
                            obj.put("ctl00_MainContent_txtAQGLRSFZ", ctl00_MainContent_txtAQGLRSFZ.getText().toString());//消防安全管理人身份证


                            //    obj.put("ctl00_MainContent_txtZJZXFGLRDH", ctl00_MainContent_txtZJZXFGLRDH.getText().toString());// 专兼职消防管理人电话
                            //obj.put("ctl00_MainContent_txtZJZXFGLRSFZ", ctl00_MainContent_txtZJZXFGLRSFZ.getText().toString());// 专兼职消防管理人身份证


                            obj.put("ctl00_MainContent_txtDWCLSJ", ctl00_MainContent_txtDWCLSJ.getText().toString());//单位成立时间：

                            obj.put("ctl00_MainContent_lblXZQY", ctl00_MainContent_lblXZQY.getText().toString());//行政区域：

                            obj.put("ctl00_MainContent_txtGDZC", ctl00_MainContent_txtGDZC.getText().toString());//固定资产：
                            obj.put("ctl00_MainContent_txtAQZRRSFZ", ctl00_MainContent_txtAQZRRSFZ.getText().toString());//消防安全责任人身份证
                            //    obj.put("ctl00_MainContent_txtZJZXFGLRDH", ctl00_MainContent_txtZJZXFGLRDH.getText().toString());//专兼职消防管理人 专兼职消防管理人电话
                            obj.put("ctl00_MainContent_txtZGRS", ctl00_MainContent_txtZGRS.getText().toString());//职工人数：

                            obj.put("ctl00_MainContent_txtZDMJ", ctl00_MainContent_txtZDMJ.getText().toString());//占地面积：

                            obj.put("ctl00_MainContent_txtJZMJ", ctl00_MainContent_txtJZMJ.getText().toString());//建筑面积：


                            obj.put("ctl00_MainContent_dropRQLX", ictl00_MainContent_dropRQLX);// 燃气类型：

                            obj.put("ctl00_MainContent_rblZDXFSS", ictl00_MainContent_rblZDXFSS);//自动消防设施// TODO:   0始
                            obj.put("ctl00_MainContent_dropJJSYZ", ictl00_MainContent_dropJJSYZ);//todo:经济所有制：


                            obj.put("ctl00_MainContent_txtYWXFYS", ctl00_MainContent_txtYWXFYS.getText().toString());//单位专职（志愿）消防员数：

                            obj.put("ctl00_MainContent_txtAQCKS", ctl00_MainContent_txtAQCKS.getText().toString());//安全出口数：

                            obj.put("ctl00_MainContent_txtSSDTS", ctl00_MainContent_txtSSDTS.getText().toString());//疏散楼梯数：

                            obj.put("ctl00_MainContent_txtXFCDS", ctl00_MainContent_txtXFCDS.getText().toString());//消防车道数：

                            obj.put("ctl00_MainContent_txtXFDTS", ctl00_MainContent_txtXFDTS.getText().toString());//消防电梯数：

                            obj.put("ctl00_MainContent_txtXFCDWZ", ctl00_MainContent_txtXFCDWZ.getText().toString());  //消防车道位置：

                            obj.put("ctl00_MainContent_txtBNCS", ctl00_MainContent_txtBNCS.getText().toString());//避难层数：

                            obj.put("ctl00_MainContent_txtBNCMJ", ctl00_MainContent_txtBNCMJ.getText().toString());//避难层总面积：
                            obj.put("ctl00_MainContent_txtBNCWZ", ctl00_MainContent_txtBNCWZ.getText().toString());//避难层位置：
                            obj.put("ctl00_MainContent_txtDLWZ", ctl00_MainContent_txtDLWZ.getText().toString());//单位毗邻情况：

                            obj.put("ctl00_MainContent_txtPLDWD", ctl00_MainContent_txtPLDWD.getText().toString());//毗邻单位东：
//                            json.put("ctl00_MainContent_txtDWBH", ctl00_MainContent_txtDWBH.getText().toString());//单位编号：
                            obj.put("ctl00_MainContent_txtPLDWX", ctl00_MainContent_txtPLDWX.getText().toString());//毗邻单位西：
                            obj.put("ctl00_MainContent_txtPLDWB", ctl00_MainContent_txtPLDWB.getText().toString());//毗邻单位北：
                            obj.put("ctl00_MainContent_txtBZ", ctl00_MainContent_txtBZ.getText().toString());// 备注：
                            //    json.put("ctl00_MainContent_lblDWZSX", ctl00_MainContent_lblDWZSX.getText().toString());//单位属性主类：
                            // json.put("ctl00_MainContent_lblDWCSX", ctl00_MainContent_lblDWCSX.getText().toString());//单位属性：子类：

                            obj.put("ctl00_MainContent_txtAQGLRDH", ctl00_MainContent_txtAQGLRDH.getText().toString());
                            obj.put("ctl00_MainContent_txtAQZRRDH", ctl00_MainContent_txtAQZRRDH.getText().toString());
                            obj.put("ctl00_MainContent_txtAQZRR", zerenren.getText().toString());
                            obj.put("ctl00_MainContent_txtFRDBDH", ctl00_MainContent_txtFRDBDH.getText().toString());
                            obj.put("ctl00_MainContent_txtPLDWN", ctl00_MainContent_txtPLDWN.getText().toString());
                            obj.put("updated", 1);
                            putData(obj.toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                request.execute(Resources.HOUSEHOLD);
                break;
        }
    }

    private EditText //  ctl00_MainContent_lblDWCSX,ctl00_MainContent_lblDWZSX,
            ctl00_MainContent_txtBZ, ctl00_MainContent_txtPLDWB, ctl00_MainContent_txtPLDWX, //ctl00_MainContent_txtDWBH,
            ctl00_MainContent_txtPLDWD,
            ctl00_MainContent_txtDLWZ, ctl00_MainContent_txtBNCMJ, ctl00_MainContent_txtBNCS,
            ctl00_MainContent_txtXFCDWZ,
            ctl00_MainContent_txtXFDTS, ctl00_MainContent_txtXFCDS, ctl00_MainContent_txtSSDTS, ctl00_MainContent_txtAQCKS, ctl00_MainContent_txtYWXFYS,

    ctl00_MainContent_txtJZMJ, ctl00_MainContent_txtZDMJ, ctl00_MainContent_txtZGRS, ctl00_MainContent_txtGDZC, ctl00_MainContent_lblXZQY, ctl00_MainContent_txtDWCLSJ,
            ctl00_MainContent_txtYZBM, ctl00_MainContent_txtZZJGDM, ctl00_MainContent_txtFRDBSFZ, ctl00_MainContent_txtAQGLRSFZ, ctl00_MainContent_txtAQZRRSFZ,
    //  ctl00_MainContent_txtZJZXFGLRDH, //ctl00_MainContent_txtZJZXFGLRSFZ,
    ctl00_MainContent_txtBNCWZ,
            ctl00_MainContent_txtAQGLRDH, ctl00_MainContent_txtAQZRRDH, ctl00_MainContent_txtFRDBDH, ctl00_MainContent_txtPLDWN;

    private String[] sctl00_MainContent_rblZDXFSS = {" 无 ", " 有 "}, sctl00_MainContent_dropJJSYZ = {"未设定",
            "国有企业", "集体企业", "民营企业", "个体工商户", "中外合资企业", "外商独资企业", "股份制企业", "事业单位", "国家机关", "其它"}, sctl00_MainContent_dropRQLX = {"天然气", "液化气", "煤气"};
    private Spinner ctl00_MainContent_dropRQLX, ctl00_MainContent_rblZDXFSS,
            ctl00_MainContent_dropJJSYZ;
    private int ictl00_MainContent_rblZDXFSS, ictl00_MainContent_dropRQLX, ictl00_MainContent_dropJJSYZ;
    private ArrayAdapter<String> actl00_MainContent_rblZDXFSS, actl00_MainContent_dropRQLX, actl00_MainContent_dropJJSYZ;

    private void putData(String data) {
        PostRequest request = new PostRequest();
        request.setCallback(new HttpCallback() {

            @Override
            public void handleData(String s) {
                try {
                    if (!s.isEmpty()) {
                        /*final Household household = new Household(name.getText().toString(), addr.getText().toString(), firedangerproperity.getText().toString(), elseprofile.getText().toString());


                        setResult(RESULT_OK, new Intent(A_HouseholdEdit.this, MainActivity.class).putExtra("e", household));*/

                        showToast("提交成功");
                        finish();

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        });
        request.execute(url, data);
    }

    private String url;
}
