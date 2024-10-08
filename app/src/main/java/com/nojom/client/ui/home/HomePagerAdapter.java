package com.nojom.client.ui.home;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.nojom.client.R;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.intercom.android.sdk.Intercom;

public class HomePagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<HomePagerModel> arrayList;

    HomePagerAdapter(Context mContext, ArrayList<HomePagerModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NotNull
    @Override
    public Object instantiateItem(@NotNull ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.home_page_item, collection, false);

        ImageView imgIcon = v.findViewById(R.id.img_icon);
        TextView tvTitle = v.findViewById(R.id.tv_title);
        final TextView tvDetails = v.findViewById(R.id.tv_details);

        HomePagerModel items = arrayList.get(position);
        imgIcon.setImageDrawable(ContextCompat.getDrawable(mContext, items.icon));
        tvTitle.setText(items.title);
        tvDetails.setText(items.details);
        tvDetails.setPaintFlags(tvDetails.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        if (position == 0) {
            tvDetails.setVisibility(View.GONE);
        } else {
            tvDetails.setVisibility(View.VISIBLE);
        }

        tvDetails.setOnClickListener(v1 -> {
            if (tvDetails.getText().toString().equalsIgnoreCase(mContext.getString(R.string.offer_details))) {
                ((BaseActivity) mContext).redirectTab(Constants.TAB_FREE_TRIAL);
            } else if (tvDetails.getText().toString().equalsIgnoreCase(mContext.getString(R.string.chat_now))) {
//                ((BaseActivity) mContext).openWhatsappChat();
                Intercom.client().displayMessageComposer();
            } else if (tvDetails.getText().toString().equalsIgnoreCase(mContext.getString(R.string.read_more))) {
                ((BaseActivity) mContext).redirectUsingCustomTab(Constants.PRIVACY);
            }
        });
        collection.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, @NotNull Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NotNull View view, @NotNull Object object) {
        return view == object;
    }

    @Override
    public float getPageWidth(int position) {
        return 0.93f;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }
}
