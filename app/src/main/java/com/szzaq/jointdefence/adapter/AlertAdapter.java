package com.szzaq.jointdefence.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.szzaq.jointdefence.Adialog.AlertActivity;
import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.fragment.F_SendAlert;
import com.szzaq.jointdefence.utils.ImageloaderUtils;
import com.szzaq.jointdefence.utils.Resources;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class AlertAdapter extends BaseAdapter {

    private List<HashMap<String, Object>> list;
    private Context context;
    private MediaPlayer mPlayer;

    public AlertAdapter(List<HashMap<String, Object>> list, Context context) {
        super();
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list != null ? list.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.item_alert, null);
        TextView tvTime, tvUser, tvMsg;
        ImageView ivPortrait, warnType;
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvUser = (TextView) view.findViewById(R.id.tvUser);
        tvMsg = (TextView) view.findViewById(R.id.tvMsg);
        ivPortrait = (ImageView) view.findViewById(R.id.portrait);
        warnType = (ImageView) view.findViewById(R.id.warntype);

        HashMap<String, Object> map = list.get(position);
        String contentType = map.get("contentType").toString();
        String extras = map.get("extras").toString();
        try {
            JSONObject extrasJson = new JSONObject(extras);
            String userName = extrasJson.optString("userName");
            tvUser.setText(userName);
            tvTime.setText(extrasJson.optString("time", ""));

            String portraitUrl = extrasJson.optString("portrait", null);
            if (portraitUrl != null) {
                ImageloaderUtils.loaderimage(Resources.URL_ROOT + portraitUrl, ivPortrait);
            }
            /*String typeimg = extrasJson.optString("", null);
            if (portraitUrl != null) {
                ImageloaderUtils.loaderimage(Resources.URL_ROOT + portraitUrl, ivPortrait);//
            }*/
            if (extrasJson.optString(AlertActivity.TYPE).equals(F_SendAlert.blowup))
                warnType.setImageResource(R.drawable.ic_compare_black_48dp);
            if (extrasJson.optString(AlertActivity.TYPE).equals(F_SendAlert.leak))
                warnType.setImageResource(R.drawable.ic_collections_bookmark_black_48dp);
            if (extrasJson.optString(AlertActivity.TYPE).equals(F_SendAlert.collapse))
                warnType.setImageResource(R.drawable.ic_color_lens_black_48d);
            if (extrasJson.optString(AlertActivity.TYPE).equals(F_SendAlert.nearFire))
                warnType.setImageResource(R.drawable.ic_compare_black_48dp);
            if (extrasJson.optString(AlertActivity.TYPE).equals(F_SendAlert.thisUnitFire))
                warnType.setImageResource(R.drawable.ic_compare_black_48dp);

            if ("text".equals(contentType) || "alert".equals(contentType)) {
                tvMsg.setText(map.get("msg").toString());
            } else if ("audio".equals(contentType)) {
                tvMsg.setText(R.string.presslisten);
                warnType.setImageResource(0);

                tvMsg.setClickable(true);
                final String audioUrl = extrasJson.optString("url", null);

                if (audioUrl != null) {
                    tvMsg.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            playVoice(audioUrl);

                        }

                    });
                }
            }
            if ("sys".equals(userName)) {
                ivPortrait.setImageResource(R.drawable.ic_launcher);
                tvUser.setText("智安全");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return view;
    }


    public void playVoice(String audioUrl) {

        try {
            if (mPlayer != null) {
                mPlayer.release();
                mPlayer = null;
            }
            mPlayer = new MediaPlayer();
            try {
                mPlayer.setDataSource(audioUrl);

                mPlayer.setOnCompletionListener(new OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mPlayer.release();

                    }

                });

                mPlayer.prepare();
                mPlayer.start();
            } catch (Exception e) {
                Log.e("Media", "播放失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
