package com.nojom.client.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.textview.CustomTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.model.HireList;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.MainActivity;
import com.nojom.client.ui.settings.GetDiscountActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class HirePagerAdapter extends PagerAdapter implements Constants {
    private final BaseActivity activity;
    private final List<HireList> arrHireList;
    private final LayoutInflater layoutInflater;

    public HirePagerAdapter(BaseActivity activity, List<HireList> arrHireList) {
        this.activity = activity;
        this.arrHireList = arrHireList;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrHireList.size();
    }

    @Override
    public boolean isViewFromObject(@NotNull View view, @NotNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NotNull
    @Override
    public Object instantiateItem(@NotNull ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.item_hire, container, false);

        CustomTextView txtHire = itemView.findViewById(R.id.txtHire);
        CustomTextView txtHireDetail = itemView.findViewById(R.id.txtHireDetail);
        LinearLayout loutHireDetail = itemView.findViewById(R.id.loutHireDetail);

        loutHireDetail.getBackground().setColorFilter(!arrHireList.get(position).categoryColor.equalsIgnoreCase("#000000") ? Color.parseColor(arrHireList.get(position).categoryColor) : activity.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);

        switch (position) {
            case 0:
                txtHire.setText(Utils.getColorString(activity, activity.getString(R.string.find_and_hire) + "\n" + activity.getString(R.string.influencers_) + " " + activity.getString(R.string.in_mins)
                        , activity.getString(R.string.influencers_) + " " + activity.getString(R.string.in_mins),
                        !arrHireList.get(position).categoryColor.equalsIgnoreCase("#000000") ? arrHireList.get(position).categoryColor : "#FFFFFF"));
                /*txtHire.setText(Utils.getColorString(activity, String.format(activity.getString(R.string.find_and_hire), arrHireList.get(position).categoryName.replaceAll(activity.getString(R.string.influencers), activity.getString(R.string.all)) + " " + activity.getString(R.string.influencers)),
                        arrHireList.get(position).categoryName.replaceAll(activity.getString(R.string.influencers), activity.getString(R.string.all)) + " " + activity.getString(R.string.influencers), !arrHireList.get(position).categoryColor.equalsIgnoreCase("#000000") ? arrHireList.get(position).categoryColor : "#FFFFFF"));*/
                txtHireDetail.setText(activity.getString(R.string.post_a_job));
                txtHireDetail.setTag("Post a job");
                break;
            case 1:

                txtHire.setText(Utils.getColorString(activity, arrHireList.get(position).categoryName
                        , arrHireList.get(position).formattedWord,
                        !arrHireList.get(position).categoryColor.equalsIgnoreCase("#000000") ? arrHireList.get(position).categoryColor : "#FFFFFF"));

                if (activity.isLogin()) {
                    txtHireDetail.setText(activity.getString(R.string.see_details));
                    txtHireDetail.setTag("See Details");
                } else {
                    txtHireDetail.setText(activity.getString(R.string.copy_code));
                    txtHireDetail.setTag("Copy code");
                }
            case 2:
                txtHire.setText(Utils.getColorString(activity, arrHireList.get(position).categoryName
                        , arrHireList.get(position).formattedWord,
                        !arrHireList.get(position).categoryColor.equalsIgnoreCase("#000000") ? arrHireList.get(position).categoryColor : "#FFFFFF"));
                txtHireDetail.setText(activity.getString(R.string.see_details));
                txtHireDetail.setTag("See Details");
                break;
        }

        txtHireDetail.setOnClickListener(view -> {
            try {
                switch (txtHireDetail.getTag().toString()) {
                    case "Post a job":
                        if (activity.isLogin()) {
                            Intent i = new Intent(activity, MainActivity.class);
                            i.putExtra(Constants.SCREEN_NAME, Constants.TAB_POST_JOB);
                            if (arrHireList.get(position).categoryID != 0) {
                                i.putExtra(Constants.PLATFORM_ID, String.valueOf(arrHireList.get(position).categoryID));
                                i.putExtra(PLATFORM_NAME, arrHireList.get(position).categoryName);
                                i.putExtra(Constants.LAWYER_CASE, true);
                                if (!arrHireList.get(position).categoryName.contains(Utils.laywer)) {
                                    i.putExtra(Constants.FROM_HOME, true);
                                }
                            } else {
                                i.putExtra("allcategory", true);
                            }
                            activity.startActivity(i);
                            activity.finish();
                        } else {
                            activity.openLoginDialog();
                        }
                        break;
                    case "See Details":
                        if (activity.isLogin()) {
                            if (position == 1) {
                                activity.redirectActivity(GetDiscountActivity.class);
                            } else {
                                Intent i = new Intent(activity, GetDiscountActivity.class);
                                i.putExtra(Constants.TAB_BALANCE, 2);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                activity.startActivity(i);
                            }
                        } else {
                            activity.openLoginDialog();
                        }
                        break;
                    case "Copy code":
                        if (!TextUtils.isEmpty(Task24Application.getInstance().promoCode)) {
                            copyMsg(Task24Application.getInstance().promoCode);
                        } else {
                            copyMsg("WELCOME10");
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        container.addView(itemView);

        return itemView;
    }

    private void copyMsg(String msg) {
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied", msg);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            activity.toastMessage("Copied");
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NotNull Object object) {
        container.removeView((LinearLayout) object);
    }
}