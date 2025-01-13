package com.nojom.client.ui.home;

import static com.nojom.client.util.Constants.AGENT_PROFILE_DATA;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.adapter.SelectedStarsAdapter;
import com.nojom.client.adapter.StarsAdapter;
import com.nojom.client.databinding.ActivityAddStarBinding;
import com.nojom.client.databinding.DialogStarsBinding;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.AgentService;
import com.nojom.client.model.Agents;
import com.nojom.client.model.InfServices;
import com.nojom.client.model.Serv;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.projects.AddStarActivityVM;
import com.nojom.client.ui.projects.CampDataActivity;
import com.nojom.client.util.EndlessRecyclerViewScrollListener;
import com.nojom.client.util.Utils;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddMoreStarActivity extends BaseActivity implements StarsAdapter.StarClickListener, SelectedStarsAdapter.StarDeletedListener {
    private LawyerHomeActivityVM lawyerHomeActivityVM;
    ActivityAddStarBinding binding;
    private AddStarActivityVM addStarActivityVM;
    private List<Agents> agentList;
    private List<Agents> selectedAgentList;
    private int pageNo = 1;
    private EndlessRecyclerViewScrollListener scrollListener;
    private InfServices selectedService;
    private AgentProfile agentData;
    private String notes;
    double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_star);

        selectedService = (InfServices) getIntent().getSerializableExtra("service");
        agentData = (AgentProfile) getIntent().getSerializableExtra(AGENT_PROFILE_DATA);
        notes = getIntent().getStringExtra("notes");
        total = getIntent().getDoubleExtra("total", 0.0);

        addStarActivityVM = ViewModelProviders.of(this).get(AddStarActivityVM.class);
        addStarActivityVM.init(this);
        agentList = new ArrayList<>();
        pageNo = 1;
        addStarActivityVM.getAgentServiceFilter(pageNo, 10, binding.etSearch.getText().toString().trim());

        binding.imgBack.setOnClickListener(view -> onBackPressed());

        addStarActivityVM.agentMutableData.observe(this, servData -> {

            if (servData != null && servData.agents != null && servData.agents.size() > 0) {
                if (agentData != null && selectedService != null && selectedService.services != null && selectedService.services.size() > 0) {
                    for (Agents agents : servData.agents) {
                        if (agents.id.equals(agentData.id)) {
                            for (AgentService sa : agents.services) {
                                for (Serv selS : selectedService.services) {
                                    if (selS.id == sa.socialPlatformId) {
                                        sa.isChecked = selS.isChecked;
                                        break;
                                    }
                                }
                            }
                            selectedService = null;
                            break;
                        }
                    }
                }
                agentList.addAll(servData.agents);
            }

            double subTotalPrice = 0;
            for (Agents agents : agentList) {
                if (agents.getPrice(agents.services) > 0) {
                    subTotalPrice = subTotalPrice + agents.getPrice(agents.services);
                }
            }
            calculateDialogPrice(null, subTotalPrice);

            setAdapter();
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rvBestInf.setLayoutManager(linearLayoutManager);
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (totalItemsCount > 9) {
                    pageNo = page;
                    addStarActivityVM.getAgentServiceFilter(pageNo, 10, binding.etSearch.getText().toString().trim());
                }
            }
        };

        addStarActivityVM.getIsShowProgress().observe(this, aBoolean -> {
            if (!aBoolean) {
                runOnUiThread(() -> {
                    if (adapter != null && selPos != -1) {
                        adapter.getData().get(selPos).isShowProgress = false;
                        adapter.notifyItemChanged(selPos);
                        selPos = -1;
                    }
                });
            }
        });

        binding.etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                resetCall();
                return true;
            }
            return false;
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    resetCall();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.imgPreview.setOnClickListener(view -> {
            showDialog();
        });

        binding.btnContinuePrice.setOnClickListener(view -> {
            Intent intent = new Intent(this, CampDataActivity.class);
            intent.putExtra("selData", (Serializable) agentList);
            intent.putExtra(AGENT_PROFILE_DATA, agentData);
            intent.putExtra("total", total);
//                intent.putExtra("social", connectedMediaList);
            startActivity(intent);
        });
    }

    private void resetCall() {
        agentList = new ArrayList<>();
        pageNo = 1;
        addStarActivityVM.getAgentServiceFilter(pageNo, 10, binding.etSearch.getText().toString().trim());
        adapter = null;
        binding.relContinue.setVisibility(View.GONE);
    }

    private StarsAdapter adapter;

    private void setAdapter() {
        if (adapter == null) {
            adapter = new StarsAdapter(this, agentList, this);
            binding.rvBestInf.setAdapter(adapter);
        } else {
            adapter.addData(agentList);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (scrollListener != null) binding.rvBestInf.addOnScrollListener(scrollListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (scrollListener != null) binding.rvBestInf.removeOnScrollListener(scrollListener);
    }

    private int selPos = -1;

    @Override
    public void onClickProfile(int pos, Agents agents) {
        selPos = pos;
        runOnUiThread(() -> {
            if (adapter != null) {
                adapter.getData().get(pos).isShowProgress = true;
                adapter.notifyItemChanged(pos);
            }
        });
        addStarActivityVM.getAgentProfile(agents.id);
    }

    @Override
    public void getPrice(double price) {
        DecimalFormat decimalFormat = new DecimalFormat("0.######"); // Adjust the format as needed
        String fullNumber = decimalFormat.format(price);
        String formattedNumber = Utils.getDecimalFormat(fullNumber);
        binding.btnContinuePrice.setText(getString(R.string.continue_) + " (" + formattedNumber + " " + getString(R.string.sar) + ")");
        if (price > 0) {
            binding.relContinue.setVisibility(View.VISIBLE);
        } else {
            binding.relContinue.setVisibility(View.GONE);
        }
    }

    private SelectedStarsAdapter selectedStarsAdapter;
    private DialogStarsBinding bindingDialog;

    private void showDialog() {
        final Dialog dialog = new Dialog(this, R.style.Theme_AppCompat_Dialog);
        dialog.setTitle(null);
        bindingDialog = DialogStarsBinding.inflate(LayoutInflater.from(this));
        dialog.setContentView(bindingDialog.getRoot());
        dialog.setCancelable(true);

        double subTotalPrice = 0;
        selectedAgentList = new ArrayList<>();
        for (Agents agents : agentList) {
            if (agents.getPrice(agents.services) > 0) {
                subTotalPrice = subTotalPrice + agents.getPrice(agents.services);
                selectedAgentList.add(agents);
            }
        }

        selectedStarsAdapter = new SelectedStarsAdapter(this, selectedAgentList, this);
        bindingDialog.rvPlatform.setAdapter(selectedStarsAdapter);

        calculateDialogPrice(bindingDialog, subTotalPrice);

        bindingDialog.btnContinuePrice.setOnClickListener(view -> {
            dialog.dismiss();
            Intent intent = new Intent(this, CampDataActivity.class);
            intent.putExtra("selData", (Serializable) agentList);
            intent.putExtra(AGENT_PROFILE_DATA, agentData);
            startActivity(intent);
        });


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        lp.height = (int) (displayMetrics.heightPixels * 0.95f);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    private void calculateDialogPrice(DialogStarsBinding bindingDialog, double subTotalPrice) {
        String fSubtotal = Utils.getDecimalFormat(Utils.formatValue(subTotalPrice));
        if (bindingDialog != null) {
            bindingDialog.txtSubTotal.setText(fSubtotal + "");
        }

        double per = (subTotalPrice * 5) / 100;
        String fees = Utils.getDecimalFormat(Utils.formatValue(per));
        if (bindingDialog != null) {
            bindingDialog.txtFees.setText(fees + "");
        }

        double total = subTotalPrice + per;
        DecimalFormat decimalFormat = new DecimalFormat("0.######"); // Adjust the format as needed
        String fullNumber = decimalFormat.format(total);
        String formattedNumber = Utils.getDecimalFormat(fullNumber);
        if (bindingDialog != null) {
            bindingDialog.txtTotal.setText(formattedNumber + " " + getString(R.string.sar));
        }
        if (bindingDialog != null) {
            bindingDialog.btnContinuePrice.setText(getString(R.string.continue_) + " (" + formattedNumber + " " + getString(R.string.sar) + ")");
        }
        if (total > 0) {
            binding.relContinue.setVisibility(View.VISIBLE);
            if (bindingDialog != null) {
                bindingDialog.relContinue.setVisibility(View.VISIBLE);
            }
        } else {
            if (bindingDialog != null) {
                bindingDialog.relContinue.setVisibility(View.GONE);
            }
            binding.relContinue.setVisibility(View.GONE);
        }
        binding.btnContinuePrice.setText(getString(R.string.continue_) + " (" + formattedNumber + " " + getString(R.string.sar) + ")");
    }

    @Override
    public void onClickDeleteStar(int pos, Agents agents) {
        if (selectedStarsAdapter != null) {
            selectedStarsAdapter.removeData(pos);
            //re calculate price
            double subTotalPrice = 0;
            for (Agents ag : selectedStarsAdapter.getData()) {
                if (ag.getPrice(ag.services) > 0) {
                    subTotalPrice = subTotalPrice + ag.getPrice(ag.services);
                }
            }
            calculateDialogPrice(bindingDialog, subTotalPrice);
        }

        //uncheck from main list and reset total
        for (Agents aD : agentList) {
            if (aD.id.equals(agents.id)) {
                for (AgentService aS : aD.services) {
                    aS.isChecked = false;
                }
                if (adapter != null) {
                    adapter.addData(agentList);
                }
                break;
            }
        }
    }
}
