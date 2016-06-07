package com.szzaq.jointdefence;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.model.ZoneDefense;
import com.szzaq.jointdefence.net.PostRequest;
import com.szzaq.jointdefence.net.UploadUtil;
import com.szzaq.jointdefence.utils.ImageCompression;
import com.szzaq.jointdefence.utils.ImageloaderUtils;
import com.szzaq.jointdefence.utils.PathUtils;
import com.szzaq.jointdefence.utils.Resources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by DOS on 2016/5/13 0013.
 */
public class A_ZoneDefense extends A_Base {
    private ZoneDefense mZoneDefense;

    private String imgUrls;
    private String imgNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addView(R.layout.a_zonedefense, R.string.task, 0);
        mZoneDefense = getIntent().getParcelableExtra("list");

        imageCompression = new ImageCompression();
        imageButton = new ImageView[3];
        imageButton[0] = (ImageView) findViewById(R.id.imageButton);
        imageButton[1] = (ImageView) findViewById(R.id.imageButton2);
        imageButton[2] = (ImageView) findViewById(R.id.imageButton3);

        tasktype = (TextView) findViewById(R.id.tasktype);
        mEditText = (EditText) findViewById(R.id.editText);
        taskfeedback = (EditText) findViewById(R.id.taskfeedback);

        zerenren = (TextView) findViewById(R.id.zerenren);
        deadline = (TextView) findViewById(R.id.deadline);
        assigner = (TextView) findViewById(R.id.assigner);
        assigntime = (TextView) findViewById(R.id.assigntime);
        finishstate = (TextView) findViewById(R.id.finishstate);
        taskdescription = (TextView) findViewById(R.id.taskdescription);

        if (mZoneDefense.finish_time != null) {
            finishstate.setText("已完成");
            finishstate.setEnabled(false);
            taskfeedback.setText(mZoneDefense.report);
            taskfeedback.setBackgroundResource(0);
            taskfeedback.setEnabled(false);
            mEditText.setText(mZoneDefense.finish_time);
            mEditText.setEnabled(false);
            mEditText.setBackgroundResource(0);
            if (mZoneDefense.attachment_urls != null && mZoneDefense.attachment_urls.length > 0) {
                for (ImageView image : imageButton) {
                    image.setVisibility(View.GONE);
                }
                for (int i = 0; i < mZoneDefense.attachment_urls.length && i < 3; i++) {
                    imageButton[i].setVisibility(View.VISIBLE);
                    if (!mZoneDefense.attachment_urls[i].equals("")) {
                        String imgUrl = Resources.URL_ROOT + mZoneDefense.attachment_urls[i];

                        ImageloaderUtils.loaderimage(imgUrl, imageButton[i]);


                    }


                    if (imgUrls == null) {
                        imgUrls = Resources.URL_ROOT + mZoneDefense.attachment_urls[i];
                        imgNames = " ";

                    } else {
                        imgUrls += "--=--" + Resources.URL_ROOT + mZoneDefense.attachment_urls[i];
                        imgNames += "--=--" + " ";
                    }
                }

                initListeners(R.id.viewphotos);
            } else {
                for (ImageView image : imageButton) {
                    image.setVisibility(View.GONE);
                }
            }

        } else {
            finishstate.setText("上报数据");
            initListeners(finishstate, imageButton[0], imageButton[1], imageButton[2]);
            mEditText.setHint("2016-05-15");

        }

        zerenren.setText("责任人：" + mZoneDefense.person_incharge_name);
        assigntime.setText("指派时间：" + mZoneDefense.created);
        deadline.setText("任务时限：" + mZoneDefense.expired_date);
        assigner.setText("指派人：" + mZoneDefense.assigner);
        taskdescription.setText(mZoneDefense.task_desc);


        if (mZoneDefense.task_type.equals(String.valueOf(1))) {
            tasktype.setText("任务类型：联合演练");

        } else tasktype.setText("任务类型：交叉检查");

    }


    private TextView tasktype, zerenren, deadline, assigner, assigntime, finishstate, taskdescription;
    private EditText taskfeedback;
    private EditText mEditText;
    private ImageView[] imageButton;

    @Override
    public void click(int id) {
        super.click(id);
        switch (id) {
            case R.id.back:
                startActivity(new Intent(this, A_ZoneDefenses.class));
                finish();
                break;
            case R.id.finishstate:
                /*if (checkDate.getText().toString().isEmpty() || checkerr.getText().toString().isEmpty() || checker.getText().toString().isEmpty() || manager.getText().toString().isEmpty() || checkTimes.getText().toString().isEmpty() || profile.getText().toString().isEmpty() || remark.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "需填全数据", Toast.LENGTH_SHORT).show();
                    return;
                }*/
                if (taskfeedback.getText().toString().isEmpty())// || mEditText.getText().toString().isEmpty())
                    return;
                PostRequest request = new PostRequest();
                request.setCallback(new HttpCallback() {
                    @Override
                    public void handleData(String s) {
// TODO: 2016/5/16 0016 上传成功 if ()

                        finishstate.setText(" 已完成");
                        finishstate.setBackgroundColor(Color.GRAY);
                        taskfeedback.setEnabled(false);
                        // mEditText.setEnabled(false);
                      /*  for (ImageView button : imageButton) {
                            button.setEnabled(false);
                        }*/

                        finishstate.setEnabled(false);
                        showToast("操作成功");

                    }
                });

                String params = "";
                JSONStringer js = new JSONStringer();
                try {
                    js.object();
                    js.key("attachments").array();
                    for (int i = 0; i < images.length(); i++) {
                        js.value(images.getString(i));
                    }
                    for (String url : mZoneDefense.attachments) {
                        js.value(url);
                    }
                    js.endArray()
                            .key("report").value(taskfeedback.getText().toString())
                            .key("finish_time").value(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date(System.currentTimeMillis())))//当前时间
                            .key("assigner").value(mZoneDefense.assigner)
                            .key("created").value(mZoneDefense.created)
                            .key("attachment_urls")
                            .key("expired_date").value(mZoneDefense.expired_date)
                            .key("id").value(mZoneDefense.id)
                            .key("person_incharge").value(mZoneDefense.person_incharge)
                            .key("person_incharge_name").value(mZoneDefense.person_incharge_name)
                            .key("task_desc").value(mZoneDefense.task_desc)
                            .key("task_type").value(mZoneDefense.task_type)
                            .key("url").value(mZoneDefense.url)
                            .key("_method").value("PUT")
                            .endObject();
                    params = js.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                    showToast(getString(R.string.dataerr));

                }
                request.execute(mZoneDefense.url, params, this);


                break;

            case R.id.imageButton:
            case R.id.imageButton2:
            case R.id.imageButton3:
                takePhoto(id);
                break;
            case R.id.viewphotos:
                Intent intent = new Intent(A_ZoneDefense.this, ImageActivity.class);
                intent.putExtra("imgUrls", imgUrls.split("--=--"));
                intent.putExtra("imgNames", imgNames.split("--=--"));
                startActivity(intent);
                break;

        }

    }

    private void takePhoto(int id) {
        String photoPath = PathUtils.getPicturePath(this);
        String fileName = (new Date()).getTime() + ""
                + (new Random()).nextInt(999999) + ".jpg";
        cameraFile = new File(photoPath, fileName);
        Uri uri = Uri.fromFile(cameraFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, id);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Bitmap portraitBitmap = null;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case R.id.imageButton:
                    afterTakephoto(0);
                    break;
                case R.id.imageButton2:
                    afterTakephoto(1);
                    break;
                case R.id.imageButton3://拍照回调
                    afterTakephoto(2);
                    break;
            }
        }
    }


    private final JSONArray images = new JSONArray();

    private File cameraFile;
    //private String photoFileName = null;
    private ImageCompression imageCompression;

    private void afterTakephoto(int re) {
        UploadUtil uploader = new UploadUtil();

        Bitmap bt = BitmapFactory.decodeFile(cameraFile.getAbsolutePath());
        if (bt != null) {
            bt = imageCompression.comp(bt);
            imageButton[re].setImageBitmap(bt);
            imageCompression.bitmap2file(bt, cameraFile.getAbsolutePath());
            File uploadFile = new File(cameraFile.getAbsolutePath());
            uploader.setCallback(new HttpCallback() {

                @Override
                public void handleData(String s) {
                    try {
                        JSONObject imageStore = new JSONObject(s);
                        images.put(imageStore.getString("url"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            });

            uploader.execute(Resources.IMAGE_STORE,
                    uploadFile);


        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, A_ZoneDefenses.class));
        super.onBackPressed();

    }
}