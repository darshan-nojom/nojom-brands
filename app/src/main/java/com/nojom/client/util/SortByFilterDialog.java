package com.nojom.client.util;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.textview.CustomTextView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemSinglemultiselectionBinding;
import com.nojom.client.model.SortByFilterModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SortByFilterDialog {
    private final List<SortByFilterModel> arrList;
    private final Map<Integer, Boolean> mapSelect = new HashMap<>();
    private final CustomTextView selectValue;
    private final ArrayList<SortByFilterModel> arrSelectedList = new ArrayList<>();

    public SortByFilterDialog(Activity activity, List<SortByFilterModel> arrList, CustomTextView selectValue, Utils.OnSheetDialogClickInterface onSheetDialogClickInterface) {
        this.arrList = arrList;
        this.selectValue = selectValue;

        final Dialog dialog = new Dialog(activity, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_sort_by_filter);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        RecyclerView rvSortByFilter = dialog.findViewById(R.id.rv_sort_by_filter);
        CustomTextView txtDone = dialog.findViewById(R.id.txt_done);

        LinearLayoutManager linearLayoutManagerCustomDesigner = new LinearLayoutManager(activity);
        rvSortByFilter.setLayoutManager(linearLayoutManagerCustomDesigner);
        rvSortByFilter.setNestedScrollingEnabled(false);

        CustomOptionGigAdapter customOptionGigAdapter = new CustomOptionGigAdapter(activity, arrList, mapSelect);
        rvSortByFilter.setAdapter(customOptionGigAdapter);

        if (selectValue.getTag() != null) {
            if (!TextUtils.isEmpty(selectValue.getTag().toString())) {
                List<String> arrSelectedTempList = Arrays.asList(selectValue.getTag().toString().split(","));
                for (int i = 0; i < arrList.size(); i++) {
                    for (int j = 0; j < arrSelectedTempList.size(); j++) {
                        if (arrSelectedTempList.get(j).equalsIgnoreCase(String.valueOf(arrList.get(i).filterID))) {
                            mapSelect.put(i, true);
                            arrSelectedList.add(arrList.get(i));
                            break;
                        }
                    }
                }
                rvSortByFilter.invalidate();
            }
        }

        txtDone.setOnClickListener(v -> {
            arrSelectedList.clear();
            arrSelectedList.addAll(getArrSelectedList());
            dialog.dismiss();
            onSheetDialogClickInterface.onDone(arrSelectedList);
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }


    private List<SortByFilterModel> getArrSelectedList() {

        List<SortByFilterModel> arrSelectedList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < arrList.size(); i++) {
            if (mapSelect.containsKey(i) && mapSelect.get(i)) {
                arrSelectedList.add(arrList.get(i));
                builder.append(",").append(arrList.get(i).filterID);
            }
        }

        selectValue.setTag(!TextUtils.isEmpty(builder.toString()) ? builder.toString().trim().substring(1) : "");

        return arrSelectedList;
    }

    public static class CustomOptionGigAdapter extends RecyclerView.Adapter<CustomOptionGigAdapter.SimpleViewHolder> {
        private final List<SortByFilterModel> arrList;
        private final Map<Integer, Boolean> mapSelect;
        LayoutInflater layoutInflater;

        public CustomOptionGigAdapter(Activity context, List<SortByFilterModel> arrList, Map<Integer, Boolean> mapSelect) {
            this.arrList = arrList;
            this.mapSelect = mapSelect;
            layoutInflater = context.getLayoutInflater();
        }

        @NonNull
        @Override
        public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemSinglemultiselectionBinding popularBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_singlemultiselection, parent, false);
            return new SimpleViewHolder(popularBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {
            final int pos = position;
            SortByFilterModel item = arrList.get(pos);

            holder.binding.txtName.setText(item.filterName);

            holder.binding.loutMain.setOnClickListener(v -> {
                mapSelect.clear();
                mapSelect.put(pos, true);
                notifyDataSetChanged();
            });

            if (mapSelect.containsKey(pos) && mapSelect.get(pos)) {
                mapSelect.put(pos, true);
                holder.binding.imgChecked.setChecked(true);
            } else {
                mapSelect.remove(pos);
                holder.binding.imgChecked.setChecked(false);
            }
        }

        @Override
        public int getItemCount() {
            return arrList.size();
        }

        static class SimpleViewHolder extends RecyclerView.ViewHolder {
            ItemSinglemultiselectionBinding binding;

            public SimpleViewHolder(ItemSinglemultiselectionBinding itemView) {
                super(itemView.getRoot());
                binding = itemView;
            }
        }
    }
}
