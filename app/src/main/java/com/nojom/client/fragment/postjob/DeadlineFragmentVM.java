package com.nojom.client.fragment.postjob;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.databinding.FragmentDeadlineBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.ui.clientprofile.PostJobActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Utils;

import java.sql.Time;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.FormatFlagsConversionMismatchException;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

class DeadlineFragmentVM extends AndroidViewModel implements View.OnClickListener {
    private final FragmentDeadlineBinding binding;
    private final BaseFragment fragment;
    private String moServiceID, moSkilIDs, moSkillNames, moBudget, moClientRate, payType, lawyerService;
    private int moClientRateId;
    private String selectedDeadline;
    private boolean isEdit;
    private int mHour;
    private int mMinute;
    private Calendar c;

    DeadlineFragmentVM(Application application, FragmentDeadlineBinding fragmentDeadlineBinding, BaseFragment deadlineFragment) {
        super(application);
        binding = fragmentDeadlineBinding;
        fragment = deadlineFragment;
        initData();
    }

    private void initData() {
        learnMoreClick();

        binding.tvTime.setOnClickListener(this);

        if (fragment.getArguments() != null) {
            isEdit = fragment.getArguments().getBoolean(Constants.IS_EDIT);
            moServiceID = fragment.getArguments().getString(Constants.PLATFORM_ID);
            moSkilIDs = fragment.getArguments().getString(Constants.SKILL_IDS);
            moSkillNames = fragment.getArguments().getString(Constants.SKILL_NAMES);
            moClientRateId = fragment.getArguments().getInt(Constants.CLIENT_RATE_ID);
            moClientRate = fragment.getArguments().getString(Constants.CLIENT_RATE);
            moBudget = fragment.getArguments().getString(Constants.BUDGET);
            selectedDeadline = fragment.getArguments().getString("deadline");

            if (!TextUtils.isEmpty(selectedDeadline)) {
                setDeadLine(selectedDeadline);
            }

            payType = fragment.getArguments().getString(Constants.PAY_TYPE);
            lawyerService = fragment.getArguments().getString(Constants.PLATFORM_NAME);

        }

        c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        Utils.trackAppsFlayerEvent(fragment.activity, "Deadline_screen");
        binding.tvTitle.setText(Utils.getColorString(fragment.activity, fragment.getString(R.string.what_s_your_deadline),
                fragment.getString(R.string.deadline).toLowerCase(), R.color.colorPrimary));

        binding.btnLastStep.setOnClickListener(view -> {
            onClickNext();
        });

        ((PostJobActivity) fragment.activity).getBackView().setOnClickListener(view -> {
            goBack();
        });
    }

    private void learnMoreClick() {
        AtomicBoolean isExpand = new AtomicBoolean(true);
        binding.txtLearnMore.setOnClickListener(view -> {
            if (isExpand.get()) {
                isExpand.set(false);
                binding.imgLearnMore.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                binding.imgLearnMore.setRotation(360);
                binding.txtLearnMoreDesc.setVisibility(View.VISIBLE);
            } else {
                isExpand.set(true);
                binding.imgLearnMore.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                binding.imgLearnMore.setRotation(180);
                binding.txtLearnMoreDesc.setVisibility(View.GONE);
            }
        });
    }

    void onResumeMethod() {
        ((PostJobActivity) fragment.activity).hideNextView();
        if (!isEdit) {
            ((PostJobActivity) fragment.activity).setProgressView(0.70f);
        } else {
            ((PostJobActivity) fragment.activity).hideProgressView();
        }
    }

    @Override
    public void onClick(View view) {
        Utils.hideSoftKeyboard(fragment.activity);
        switch (view.getId()) {
            case R.id.img_back:
                fragment.goBackTo();
                break;
            case R.id.tv_time:
                showDateDialog();
                break;
            case R.id.tv_next:
                onClickNext();
                break;
        }

    }

    private void onClickNext() {
        if (TextUtils.isEmpty(selectedDeadline)) {
            fragment.activity.toastMessage(fragment.activity.getString(R.string.please_select_your_deadline));
            return;
        }
        if (isEdit) {
            Intent intent = new Intent(fragment.activity, PostJobFragment.class);
            intent.putExtra("deadline", selectedDeadline);
            Objects.requireNonNull(fragment.getTargetFragment()).onActivityResult(fragment.getTargetRequestCode(), RESULT_OK, intent);
            fragment.activity.getSupportFragmentManager().popBackStack();
        } else {
            Fragment fragmentA = new DescribeFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.PLATFORM_ID, moServiceID + "");
            bundle.putString(Constants.SKILL_IDS, moSkilIDs + "");
            bundle.putString(Constants.SKILL_NAMES, moSkillNames + "");

            bundle.putString(Constants.BUDGET, moBudget);
            bundle.putInt(Constants.CLIENT_RATE_ID, moClientRateId);
            bundle.putString(Constants.CLIENT_RATE, moClientRate);
            bundle.putString("deadline", selectedDeadline);
            bundle.putString(Constants.PAY_TYPE, payType);
            bundle.putString(Constants.PLATFORM_NAME, lawyerService);
            String desc = null;
            if (fragment.getArguments() != null) {
                desc = fragment.getArguments().getString(Constants.DESCRIBE);
            }
            bundle.putString(Constants.DESCRIBE, desc);
            fragmentA.setArguments(bundle);
            fragment.activity.replaceFragment(fragmentA);
        }
    }

    private void showDateDialog() {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(fragment.activity, (view, year, month, dayOfMonth) -> {
            String _year = String.valueOf(year);
            String _month = (month + 1) < 10 ? "0" + (month + 1) : String.valueOf(month + 1);
            String _date = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
            String _pickedDate = year + "-" + _month + "-" + _date;
            showTimeDialog(_pickedDate);
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dialog.show();
    }

    private void showTimeDialog(String selectedDate) {
        // Get Current Time
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(fragment.activity,
                (view, hourOfDay, minute) -> {

                    Calendar datetime = Calendar.getInstance();
                    Calendar c = Calendar.getInstance();
                    datetime.set(Calendar.YEAR, Integer.parseInt(selectedDate.split("-")[0]));
                    datetime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(selectedDate.split("-")[2]));
                    datetime.set(Calendar.MONTH, Integer.parseInt(selectedDate.split("-")[1]));
                    datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    datetime.set(Calendar.MINUTE, minute);
                    if (datetime.getTimeInMillis() > c.getTimeInMillis()) {

                        String time = getTime(hourOfDay, minute);
                        String timeEng = getTimeEnglish(hourOfDay, minute);
                        String outputFormat = "EEEE, dd MMMM yyyy";
                        String inputFormat = "yyyy-MM-dd";
                        try {
                            String formattedDate = Utils.TimeStampConverter_Ar(inputFormat, selectedDate, outputFormat);
                            String formattedDateTime = formattedDate + "  " + fragment.getString(R.string.at) + " " + time;
                            binding.tvTime.setText(formattedDateTime);
                            binding.tvDateTime.setText(String.format(fragment.getString(R.string.job_will_be_ready_before_s), formattedDateTime));
                            binding.tvDateTime.setVisibility(View.VISIBLE);
                            if (printDifference(selectedDate, timeEng, hourOfDay, minute) < 0) {
                                invalidTime();
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (FormatFlagsConversionMismatchException e) {
                            e.printStackTrace();
                        }
                    } else {
                        invalidTime();
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private void invalidTime() {
        selectedDeadline = "";
        binding.tvTime.setText(fragment.getString(R.string.select_deadline));
        binding.tvLeft.setText("");
        binding.tvDateTime.setText(String.format(fragment.getString(R.string.job_will_be_ready_before_s), ""));
        binding.tvDateTime.setVisibility(View.INVISIBLE);
        fragment.activity.toastMessage(fragment.getString(R.string.please_select_future_date_within_maximum_duration_999_days));
    }

    private String getTime(int hr, int min) {
        Time tme = new Time(hr, min, 0);//seconds by default set to zero
        Format formatter;
        formatter = new SimpleDateFormat("hh:mm a");
        return formatter.format(tme);
    }
    private String getTimeEnglish(int hr, int min) {
        Time tme = new Time(hr, min, 0);//seconds by default set to zero
        Format formatter;
        formatter = new SimpleDateFormat("hh:mm a",Locale.ENGLISH);
        return formatter.format(tme);
    }


    private long printDifference(String selectedDate, String time, int hours, int mins) {
        //milliseconds
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.ENGLISH);
        Date startDate = null;
        try {
            startDate = formatter.parse(selectedDate + " " + time);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

        long different = 0;
        if (startDate != null) {
            different = startDate.getTime() - new Date().getTime();
        }

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        if (elapsedDays > 999) {
            return -1;
        }

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;
        String left_s = "";
        String left_e = "";
        String hoursString = fragment.activity.getString(R.string.hours).toLowerCase();
        String minuteString = fragment.activity.getString(R.string.minutes);
        String dayString = fragment.activity.getString(R.string.days);

        if (fragment.activity.getLanguage().equals("ar")) {
            left_s = fragment.activity.getString(R.string.left);
            minuteString = Utils.getMinutes(elapsedMinutes);
            hoursString = Utils.getHours(elapsedHours);
            dayString = Utils.getDays(elapsedDays);
        } else {
            left_e = fragment.activity.getString(R.string.left);
        }


        if (elapsedHours > 0) {
            if (elapsedDays == 0) {
                String leftTime = left_s + " " + elapsedHours + " " + hoursString + " " + fragment.activity.getString(R.string.and) + " " + elapsedMinutes + " " + minuteString + " " + left_e;
                binding.tvLeft.setText(Utils.getColorString(fragment.activity, leftTime,
                        elapsedHours + " " + hoursString + " " + fragment.activity.getString(R.string.and) + " " + elapsedMinutes + " " + minuteString, R.color.red));
            } else {
                String leftTime = left_s + " " + elapsedDays + " " + dayString + " " + fragment.activity.getString(R.string.and)
                        + " " + elapsedHours + " " + hoursString + " " + fragment.activity.getString(R.string.and) + " " + elapsedMinutes + " " + minuteString + " " + left_e;
                binding.tvLeft.setText(Utils.getColorString(fragment.activity, leftTime,
                        elapsedDays + " " + dayString
                                + " " + fragment.activity.getString(R.string.and) + " " + elapsedHours + " " + hoursString
                                + " " + fragment.activity.getString(R.string.and) + " " + elapsedMinutes + " " + minuteString
                        , R.color.red));
            }
        } else {
            String leftTime = left_s + " " + elapsedDays + dayString + " " + fragment.activity.getString(R.string.and) + " " + elapsedMinutes + " " + minuteString + " " + left_e;
            binding.tvLeft.setText(Utils.getColorString(fragment.activity, leftTime,
                    elapsedDays + dayString + " " + fragment.activity.getString(R.string.and) + " " + elapsedMinutes + " " + minuteString, R.color.red));
        }

        selectedDeadline = selectedDate + " " + hours + ":" + mins;
        return different;
    }

    private void setDeadLine(String deadline) {
        try {
            String[] deadlineText = deadline.split(" ");
            String date = deadlineText[0];
            String time = deadlineText[1];
            String outputFormat = "EEEE, dd MMMM yyyy";
            String outputFormatTime = "hh:mm a";
            String inputFormat = "yyyy-MM-dd";
            String inputFormatTime = "HH:mm";

            String formattedDate = Utils.TimeStampConverter(inputFormat, date, outputFormat);
            String formattedTime = Utils.TimeStampConverter(inputFormatTime, time, outputFormatTime);
            binding.tvTime.setText(String.format("%s %s %s", formattedDate,fragment.getString(R.string.at), formattedTime));
            binding.tvDateTime.setText(String.format(fragment.getString(R.string.job_will_be_ready_before_s), String.format("%s %s %s", formattedDate,fragment.getString(R.string.at), formattedTime)));
            binding.tvDateTime.setVisibility(View.VISIBLE);
            if (printDifference(date, formattedTime, Integer.parseInt(time.split(":")[0]), Integer.parseInt(time.split(":")[1])) < 0) {
                invalidTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void goBack() {
        Intent intent = new Intent();
        intent.putExtra(Constants.PLATFORM_ID, moServiceID + "");
        intent.putExtra(Constants.SKILL_IDS, moSkilIDs + "");
        intent.putExtra(Constants.SKILL_NAMES, moSkillNames + "");
        intent.putExtra(Constants.BUDGET, moBudget);
        intent.putExtra(Constants.CLIENT_RATE_ID, moClientRateId);
        intent.putExtra(Constants.CLIENT_RATE, moClientRate);
        intent.putExtra("deadline", selectedDeadline);
        intent.putExtra(Constants.PAY_TYPE, payType);
        intent.putExtra(Constants.PLATFORM_NAME, lawyerService);
        if (isEdit) {
            fragment.goBackTo();
        } else {
            fragment.activity.setResult(RESULT_OK, intent);
            fragment.activity.finish();
        }
    }
}
