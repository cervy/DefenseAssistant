package com.szzaq.jointdefence.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.fragment.F_SendAlert;
import com.szzaq.jointdefence.model.Message;
import com.szzaq.jointdefence.utils.ImageloaderUtils;
import com.szzaq.jointdefence.utils.Resources;
import com.szzaq.jointdefence.widget.CircleImageView;

import java.util.List;

public class ActivityMessageAdapter extends BaseAdapter {

    private List<Message> list;
    private Context context;
    private static MediaPlayer mPlayer;


    public ActivityMessageAdapter(List<Message> list,
                                  Context context) {
        super();
        this.list = list;
        this.context = context;
    }


    @Override
    public int getCount() {
        if (list == null) return 0;
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        if (list == null) return null;

        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {

        ViewHolder holder = null;
        if (holder == null) {
            arg1 = LayoutInflater.from(context).inflate(R.layout.item_alert, null);
            holder = new ViewHolder();

            holder.tvTime = (TextView) arg1.findViewById(R.id.tvTime);
            holder.tvUser = (TextView) arg1.findViewById(R.id.tvUser);
            holder.tvMsg = (TextView) arg1.findViewById(R.id.tvMsg);
            holder.ivPortrait = (CircleImageView) arg1.findViewById(R.id.portrait);
            holder.warnType = (ImageView) arg1.findViewById(R.id.warntype);

            arg1.setTag(holder);
        } else {
            holder = (ViewHolder) arg1.getTag();
        }
        Message map = list.get(list.size() - 1 - arg0);
        String contentType = map.contenttype;

        String userName = map.username;
        holder.tvUser.setText(userName);
        holder.tvTime.setText(map.time);
        String portraitUrl = map.portrait;
        if (portraitUrl != null) {
            ImageloaderUtils.loaderimage(Resources.URL_ROOT + portraitUrl, holder.ivPortrait);
        }
        if (map.type.equals(F_SendAlert.blowup))
            holder.warnType.setImageResource(R.drawable.ic_compare_black_48dp);
        if (map.type.equals(F_SendAlert.leak))
            holder.warnType.setImageResource(R.drawable.ic_collections_bookmark_black_48dp);
        if (map.type.equals(F_SendAlert.collapse))
            holder.warnType.setImageResource(R.drawable.ic_color_lens_black_48d);
        if (map.type.equals(F_SendAlert.nearFire))
            holder.warnType.setImageResource(R.drawable.ic_compare_black_48dp);
        if (map.type.equals(F_SendAlert.thisUnitFire))
            holder.warnType.setImageResource(R.drawable.ic_compare_black_48dp);
        if ("text".equals(contentType) || "alert".equals(contentType)) {
            holder.tvMsg.setText(map.msg);
        } else if ("audio".equals(contentType)) {
            holder.tvMsg.setText(R.string.presslisten);
            holder.tvMsg.setClickable(true);
            holder.warnType.setImageResource(0);

            final String audioUrl = map.url;

            if (audioUrl != null) {
                holder.tvMsg.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        playVoice(audioUrl);

                    }

                });
            }
        }
        if ("sys".equals(userName)) {
            holder.ivPortrait.setImageResource(R.drawable.ic_launcher);
            holder.tvUser.setText("智安全");
        }


        return arg1;

    }

    static class ViewHolder {
        TextView tvMsg, tvUser, tvTime;
        public ImageView ivPortrait, warnType;
    }


    private void playVoice(String path) {
        mPlayer = new MediaPlayer();
        if (mPlayer.isPlaying()) {
            if (TextUtils.isEmpty(path)) {//语音资源为空或在播放同条时
                stopPlay();
                return;
            }
            stopPlay();
        }

        try {
            mPlayer.setDataSource(path);
            mPlayer.prepare();
            //  mPlayer.prepareAsync();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mPlayer.release();
//                    mPlayer = null;
                    //stopPlay();
                }

            });
            mPlayer.start();
        } catch (Exception ignored) {
        }
    }

    private void stopPlay() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();

        }

    }


}
