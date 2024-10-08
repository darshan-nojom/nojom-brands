package com.nojom.client.ui.projects;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;

import androidx.databinding.DataBindingUtil;

import com.google.android.material.chip.Chip;
import com.nojom.client.R;
import com.nojom.client.adapter.SearchTagAdapter;
import com.nojom.client.databinding.ActivitySearchTagsBinding;
import com.nojom.client.model.SearchTagModel;
import com.nojom.client.model.ServicesModel;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;

import java.util.ArrayList;
import java.util.Objects;


public class SearchTagActivity extends BaseActivity implements Constants {
    private ActivitySearchTagsBinding binding;
    private ArrayList<SearchTagModel> selectedTags;
    private ArrayList<ServicesModel.Data> subCatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_tags);
        binding.toolbar.tvTitle.setText(getString(R.string.search_tags));
        initData();
    }

    private void initData() {
        if (getIntent() != null) {
            selectedTags = (ArrayList<SearchTagModel>) getIntent().getSerializableExtra("data");
            subCatList = (ArrayList<ServicesModel.Data>) getIntent().getSerializableExtra("list");
        }

        if (selectedTags == null) {
            selectedTags = new ArrayList<>();
        } else {
            addDefaultTag();
        }

        binding.tvAdd.setOnClickListener(v -> {
            if (selectedTags != null && selectedTags.size() > 4) {
                toastMessage(getString(R.string.you_can_add_max_5_tags));
                return;
            }
            addTag(getSearchTag(), 0);
        });

        binding.toolbar.imgBack.setOnClickListener(v -> onBackPressed());

        binding.tvSave.setOnClickListener(v -> {
            if (selectedTags == null || selectedTags.size() == 0) {
                toastMessage(getString(R.string.please_add_min_one_tag));
                return;
            }
            if (selectedTags.size() < 6) {
                Intent intent = new Intent();
                intent.putExtra("tags", selectedTags);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                toastMessage(getString(R.string.you_can_add_max_5_tags));
            }
        });

        try {
            SearchTagAdapter adapter = new SearchTagAdapter(this, R.layout.layout_textview, R.id.text, subCatList);
            binding.etSearchTags.setThreshold(1);
            binding.etSearchTags.setAdapter(adapter);
            binding.etSearchTags.setOnItemClickListener((adapterView, view, pos, id) -> {
                ServicesModel.Data selectedSkill = (ServicesModel.Data) adapterView.getItemAtPosition(pos);
                if (selectedTags != null && selectedTags.size() > 4) {
                    toastMessage(getString(R.string.you_can_add_max_5_tags));
                    return;
                }

                if (selectedTags != null) {
                    for (int i = 0; i < selectedTags.size(); i++) {
                        if (selectedTags.get(i).tagName.equalsIgnoreCase(selectedSkill.name)) {
                            toastMessage(selectedSkill.name + " " + getString(R.string.already_selected));
                            binding.etSearchTags.setText("");
                            return;
                        }
                    }
                }
                addTag(selectedSkill.name, selectedSkill.id);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getSearchTag() {
        return Objects.requireNonNull(binding.etSearchTags.getText()).toString().trim();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishToRight();
    }

    private void addTag(String tagName, int tagId) {
        if (TextUtils.isEmpty(tagName)) {
            return;
        }
        binding.etSearchTags.setText("");
        Chip chip = makeChipView(tagName);
        selectedTags.add(new SearchTagModel(0, tagId, 0, tagName));
        binding.tagGroup.addView(chip);
    }

    private Chip makeChipView(String tagName) {
        Chip chip = new Chip(this);
        int paddingDp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 10,
                getResources().getDisplayMetrics()
        );
        chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
        chip.setText(tagName);
        chip.setCloseIconResource(R.drawable.close_gray);
        chip.setCloseIconEnabled(true);
        chip.setOnCloseIconClickListener(v -> {
            for (SearchTagModel item : selectedTags) {
                if (item.tagName.equals(tagName)) {
                    selectedTags.remove(item);
                    break;
                }
            }
            binding.tagGroup.removeView(chip);
        });
        return chip;
    }

    private void addDefaultTag() {
        for (int i = 0; i < selectedTags.size(); i++) {
            Chip chip = makeChipView(selectedTags.get(i).tagName);
            binding.tagGroup.addView(chip);
        }
    }
}
