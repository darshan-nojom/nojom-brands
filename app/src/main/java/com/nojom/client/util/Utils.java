package com.nojom.client.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.model.Profile;
import com.nojom.client.model.SortByFilterModel;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.MaintenanceActivity;
import com.nojom.client.ui.SplashActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import io.branch.referral.util.BranchEvent;

import static com.nojom.client.util.Constants.FOLLOWER_1;
import static com.nojom.client.util.Constants.FOLLOWER_2;
import static com.nojom.client.util.Constants.FOLLOWER_3;
import static com.nojom.client.util.Constants.FOLLOWER_4;
import static com.nojom.client.util.Constants.FOLLOWER_5;
import static com.nojom.client.util.Constants.FOLLOWER_6;
import static com.nojom.client.util.Constants.FOLLOWER_7;
import static com.nojom.client.util.Constants.SELECT_FOLLOWER;
import static com.nojom.client.util.FilePath.getDataColumn;

public class Utils {

    private static final String ENCODING_UTF_8 = "UTF-8";
    private static final String BYTES = "Bytes";
    private static final String MEGABYTES = "MB";
    private static final String KILOBYTES = "kB";
    private static final String GIGABYTES = "GB";
    private static final long KILO = 1024;
    private static final long MEGA = KILO * 1024;
    private static final long GIGA = MEGA * 1024;
    public static String laywer = "Lawyer";
    public static String local = "Local";

    public static String formatFileSize(BaseActivity activity, String pBytes) {
        long bytes = Long.parseLong(pBytes);
        if (bytes < KILO) {
            return pBytes + BYTES;
        } else if (bytes < MEGA) {
            return (int) (0.5 + (bytes / (double) KILO)) + activity.getString(R.string.kb);
        } else if (bytes < GIGA) {
            return (int) (0.5 + (bytes / (double) MEGA)) + activity.getString(R.string.mb);
        } else {
            return (int) (0.5 + (bytes / (double) GIGA)) + activity.getString(R.string.gb);
        }
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(html);
        }
    }


    public static SpannableStringBuilder getBoldString(Context context, String s, String[] fonts, int[] colorList, String[] words) {
        if (TextUtils.isEmpty(s)) return null;

        SpannableStringBuilder ss = new SpannableStringBuilder(s);
        try {
            for (int i = 0; i < words.length; i++) {
                if (s.contains(words[i])) {
                    if (colorList != null) {
                        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, colorList[i])), s.indexOf(words[i]), s.indexOf(words[i]) + words[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }

                    if (fonts != null && fonts.length > i) {
                        Typeface font = Typeface.createFromAsset(context.getAssets(), fonts[i]);
                        ss.setSpan(new CustomTypefaceSpan("", font), s.indexOf(words[i]), s.indexOf(words[i]) + words[i].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ss;
    }

    public static SpannableStringBuilder getColorString(Context context, String s, String word, int color) {
        SpannableStringBuilder ss = new SpannableStringBuilder(s);
        int end = s.indexOf(word) + word.length();
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color)), s.indexOf(word), end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    public static SpannableStringBuilder getColorString(Context context, String s, String word, String color) {
        SpannableStringBuilder ss = new SpannableStringBuilder(s);
        int end = s.indexOf(word) + word.length();
        ss.setSpan(new ForegroundColorSpan(Color.parseColor(color)), s.indexOf(word), end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    public static SpannableStringBuilder getColorString(Context context, String s, String word, int color, boolean isBold) {
        SpannableStringBuilder ss = new SpannableStringBuilder(s);
        try {
            int end = s.indexOf(word) + word.length();
            ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color)), s.indexOf(word), end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (isBold) {
                ss.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), s.indexOf(word), end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ss;
    }

    public static void makeLinks(TextView textView, String[] links, ClickableSpan[] clickableSpans) {
        try {
            SpannableString spannableString = new SpannableString(textView.getText());
            for (int i = 0; i < links.length; i++) {
                ClickableSpan clickableSpan = clickableSpans[i];
                String link = links[i];

                int startIndexOfLink = textView.getText().toString().indexOf(link);
                spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            textView.setHighlightColor(Color.TRANSPARENT); // prevent TextView change background when highlight
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setText(spannableString, TextView.BufferType.SPANNABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void makeLinks(TextView textView, String[] links, MyClickableSpan[] clickableSpans) {
        try {
            SpannableString spannableString = new SpannableString(textView.getText());
            for (int i = 0; i < links.length; i++) {
                MyClickableSpan clickableSpan = clickableSpans[i];
                String link = links[i];

                int startIndexOfLink = textView.getText().toString().indexOf(link);
                spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            textView.setHighlightColor(Color.TRANSPARENT); // prevent TextView change background when highlight
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setText(spannableString, TextView.BufferType.SPANNABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static class MyClickableSpan extends ClickableSpan {// extend ClickableSpan

        public MyClickableSpan() {
            super();
        }

        public void onClick(View tv) {

        }

        public void updateDrawState(TextPaint ds) {// override updateDrawState
            ds.setColor(ds.linkColor);
            ds.setUnderlineText(false); // set to false to remove underline
        }
    }

    public static void makeLinks(CheckBox checkBox, String[] links, ClickableSpan[] clickableSpans) {
        try {
            SpannableString spannableString = new SpannableString(checkBox.getText());
            for (int i = 0; i < links.length; i++) {
                ClickableSpan clickableSpan = clickableSpans[i];
                String link = links[i];

                int startIndexOfLink = checkBox.getText().toString().indexOf(link);
                spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            checkBox.setHighlightColor(Color.TRANSPARENT); // prevent TextView change background when highlight
            checkBox.setMovementMethod(LinkMovementMethod.getInstance());
            checkBox.setText(spannableString, TextView.BufferType.SPANNABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        /*if (activity == null)
            return;

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }*/
    }


    public static String priceWith$(Object object, BaseActivity activity) {
        String price = String.valueOf(object);
        if (TextUtils.isEmpty(price) || price.equalsIgnoreCase("null")) {
            return activity.getString(R.string.dollar) + "0";
        }
        if (price.contains(activity.getString(R.string.dollar))) {
            price = price.replace(activity.getString(R.string.dollar), "");
        }
        return activity.getString(R.string.dollar) + price.trim();
    }

    public static String priceWithSAR(BaseActivity activity, Object object) {
        String price = String.valueOf(object);
        if (TextUtils.isEmpty(price) || price.equalsIgnoreCase("null")) {
            return "0 " + activity.getString(R.string.sar);
        }
        if (price.contains(activity.getString(R.string.sar))) {
            price = price.replace(activity.getString(R.string.sar), "");
        }
        return price.trim() + " " + activity.getString(R.string.sar);
    }

    public static String numberFormat2Places(Object number) {
        try {
            Double d = Double.parseDouble(String.valueOf(number));
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            NumberFormat nf = new DecimalFormat("0.00", symbols);
            return nf.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(number);
    }

    public static String priceWithout$(Object object) {
        String price = String.valueOf(object);
        if (TextUtils.isEmpty(price) || price.equalsIgnoreCase("null")) {
            return "0";
        }
        if (price.contains("$")) {
            price = price.replace("$", "");
        } else if (price.contains("ريال")) {
            price = price.replace("ريال", "");
        }
        return price.trim();
    }

    public static String priceWithoutSAR(BaseActivity activity, Object object) {
        String price = String.valueOf(object);
        if (TextUtils.isEmpty(price) || price.equalsIgnoreCase("null")) {
            return "0";
        }
        if (price.contains(activity.getString(R.string.sar))) {
            price = price.replace(activity.getString(R.string.sar), "");
        }
        return price.trim();
    }

    public static void openSoftKeyboard(Activity activity, View view) {
        if (activity == null) return;

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static String numberFormat(String number, int decimalPoint) {
        try {
            Double d = Double.parseDouble(number);
            StringBuilder pattern = new StringBuilder();
            pattern.append("0.");
            for (int i = 0; i < decimalPoint; i++) {
                pattern.append("0");
            }
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            NumberFormat nf = new DecimalFormat(pattern.toString(), symbols);
            return nf.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return number;
    }

    public static String numberFormat(double number, int decimalPoint) {
        try {
            StringBuilder pattern = new StringBuilder();
            pattern.append("0.");
            for (int i = 0; i < decimalPoint; i++) {
                pattern.append("0");
            }
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            NumberFormat nf = new DecimalFormat(pattern.toString(), symbols);
            return nf.format(number);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(number);
    }

    public static String numberFormat(float number, int decimalPoint) {
        try {
            StringBuilder pattern = new StringBuilder();
            pattern.append("0.");
            for (int i = 0; i < decimalPoint; i++) {
                pattern.append("0");
            }
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            NumberFormat nf = new DecimalFormat(pattern.toString(), symbols);
            return nf.format(number);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(number);
    }

    public static String numberFormat(String number) {
        try {
            Double d = Double.parseDouble(number);
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            NumberFormat nf = new DecimalFormat("#.####", symbols);
            return nf.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return number;
    }

    public static String removeTrailingZeros(double number) {
        // Convert the double to a string
        String str = String.valueOf(number);

        // Remove trailing zeros and decimal point if present
        str = str.indexOf(".") < 0 ? str : str.replaceAll("0*$", "").replaceAll("\\.$", "");

        return str;
    }

    public static String decimalFormat(String number) {
        try {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            Double d = Double.parseDouble(String.valueOf(number));
            DecimalFormat format = new DecimalFormat();
            format.setDecimalFormatSymbols(symbols);
            format.setDecimalSeparatorAlwaysShown(false);
            return format.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return number;
    }

    public static String doubleDigit(double number) {
        String s = numberFormat(String.valueOf(number));
        if (s.length() == 1) {
            s = "0" + s;
        } else if (s.length() > 3) {
            s = s.substring(0, 2) + "..";
        }
        return s;
    }

    public static String getExperienceLevel(int exp) {
        String level = "";
        switch (exp) {
            case Constants.LESS_THAN_1_ID:
                level = Constants.LESS_THAN_1;
                break;
            case Constants.YEAR_1_3_ID:
                level = Constants.YEAR_1_3;
                break;
            case Constants.YEAR_4_6_ID:
                level = Constants.YEAR_4_6;
                break;
            case Constants.YEAR_7_9_ID:
                level = Constants.YEAR_7_9;
                break;
            case Constants.YEAR_10_12_ID:
                level = Constants.YEAR_10_12;
                break;
            case Constants.YEAR_13_15_ID:
                level = Constants.YEAR_13_15;
                break;
            case Constants.YEAR_16_18_ID:
                level = Constants.YEAR_16_18;
                break;
            case Constants.YEAR_19_21_ID:
                level = Constants.YEAR_19_21;
                break;
            case Constants.YEAR_22_24_ID:
                level = Constants.YEAR_22_24;
                break;
            case Constants.YEAR_25_27_ID:
                level = Constants.YEAR_25_27;
                break;
            case Constants.YEAR_28_30_ID:
                level = Constants.YEAR_28_30;
                break;
            case Constants.YEAR_31_ID:
                level = Constants.YEAR_31_;
                break;
        }
        return level;
    }

    public static String getLanguageLevel(int levelId) {
        String level = "";
        switch (levelId) {
            case Constants.BASIC_ID:
                level = Constants.BASIC;
                break;
            case Constants.CONVERSATIONAL_ID:
                level = Constants.CONVERSATIONAL;
                break;
            case Constants.FLUENT_ID:
                level = Constants.FLUENT;
                break;
            case Constants.NATIVE_ID:
                level = Constants.NATIVE;
                break;
        }
        return level;
    }

    public static String getRatingLevel(int rating) {
        String level = "";
        switch (rating) {
            case Constants.Beginner_ID:
                level = Constants.Beginner;
                break;
            case Constants.Intermediate_ID:
                level = Constants.Intermediate;
                break;
            case Constants.Expert_ID:
                level = Constants.Expert;
                break;
        }
        return level;
    }

    public static String changeDateFormat(String source, String target, String dateString) {
        SimpleDateFormat input = new SimpleDateFormat(source, Locale.getDefault());
        SimpleDateFormat output = new SimpleDateFormat(target, Locale.getDefault());
        input.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = input.parse(dateString);
            if (date != null) {
                return output.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString;
    }


    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) return false;

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }

    public static Date changeDateFormat(String source, String dateString) {
        SimpleDateFormat input = new SimpleDateFormat(source);
        input.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return input.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String base64Decode(String string) {
        String decoded;
        try {
            byte[] bytes = Base64.decode(string, Base64.URL_SAFE | Base64.NO_WRAP | Base64.NO_PADDING);
            decoded = new String(bytes, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            throw new DecodeException("Received bytes didn't correspond to a valid Base64 encoded string.", e);
        }
        return decoded;
    }

    public static String decode(String token) {
        final String[] parts = splitToken(token);
        return base64Decode(parts[1]);
    }

    private static String[] splitToken(String token) {
        String[] parts = token.split("\\.");
        if (parts.length == 2 && token.endsWith(".")) {
            //Tokens with alg='none' have empty String as Signature.
            parts = new String[]{parts[0], parts[1], ""};
        }
        if (parts.length != 3) {
            throw new DecodeException(String.format(Locale.US, "The token was expected to have 3 parts, but got %s.", parts.length));
        }
        return parts;
    }

    public static void checkForMaintenance(Activity activity) {
        try {
            final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
            remoteConfig.fetchAndActivate().addOnCompleteListener(activity, task -> {
                if (!task.isSuccessful()) {
                    return;
                }
                Preferences.writeString(activity, Constants.ATTACH_LOCAL_FILE, "");
                if (remoteConfig.getString(Constants.KEY_FOR_MAINTENANCE).equalsIgnoreCase("true")) {
                    if (!isActivityRunning(MaintenanceActivity.class, activity, false)) {
                        Intent intent = new Intent(activity, MaintenanceActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
                    }
                } else {
                    isActivityRunning(MaintenanceActivity.class, activity, true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void checkForMaintenanceRetry(Activity activity) {
        try {
            final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
            remoteConfig.fetchAndActivate().addOnCompleteListener(activity, task -> {
                if (!task.isSuccessful()) {
                    return;
                }
                if (remoteConfig.getString(Constants.KEY_FOR_MAINTENANCE).equalsIgnoreCase("true")) {
                    toastMessage(activity, "Application is still under maintenance.");
                } else {
                    Intent i = new Intent(activity, SplashActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(i);
                    activity.finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void checkForFirebasePromoCode(Activity activity) {
        try {
            final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
            remoteConfig.fetchAndActivate().addOnCompleteListener(activity, task -> {
                if (!task.isSuccessful()) {
                    return;
                }

                Task24Application.getInstance().promoCode = remoteConfig.getString(Constants.KEY_FOR_APP_PROMO);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void checkForGigShow(Activity activity) {
        try {
            final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
            remoteConfig.fetchAndActivate().addOnCompleteListener(activity, task -> {
                if (!task.isSuccessful()) {
                    return;
                }

                Task24Application.getInstance().isGigShow = Boolean.parseBoolean(remoteConfig.getString(Constants.KEY_FOR_GIG_SHOW));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void toastMessage(Context context, String message) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        try {
            Toast toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_SHORT);

            View view = inflater.inflate(R.layout.custom_toast, null);
            TextView tvToast = view.findViewById(R.id.tvToast);
            tvToast.setText(message);
            toast.setGravity(Gravity.FILL_HORIZONTAL, 20, 20);
            toast.setView(view);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Boolean isActivityRunning(Class activityClass, Activity activity, boolean isFinish) {
        ActivityManager activityManager = (ActivityManager) activity.getBaseContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (activityClass.getCanonicalName().equalsIgnoreCase(task.baseActivity.getClassName())) {
                if (isFinish) {
                    activity.finish();
                }
                return true;
            }
        }
        return false;
    }

    public static String getFileNameFromUrl(String url) {
        try {
            URL resource = new URL(url);
            String urlString = resource.getFile();
            return urlString.substring(urlString.lastIndexOf('/') + 1).split("\\?")[0].split("#")[0];
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void trackAppsFlayerEvent(Context context, String eventName) {
        Profile userData = Preferences.getProfileData(context);
        try {
            Bundle bundle = new Bundle();
            bundle.putString("UserId", userData != null ? String.valueOf(userData.id) : "");
            bundle.putString("Screen", eventName + "-Client");
            if (!eventName.contains("Successfull")) {
                BranchEvent event = new BranchEvent(eventName + "_Android");
                event.logEvent(context);
            }
            Task24Application.getInstance().getFirebaseAnalytics().logEvent(eventName, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void trackBranchAppsEvent(Context context, String eventName) {
        try {
            BranchEvent event = new BranchEvent(eventName + "_Android");
            event.logEvent(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String currencyFormat(double number) {
        try {
            DecimalFormat nf = new DecimalFormat("0.00");
            Double d = Double.parseDouble(nf.format(number));
            NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(Locale.US);
            return defaultFormat.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(number);
    }

    public static String TimeStampConverter(final String inputFormat, String inputTimeStamp, final String outputFormat) throws ParseException {
        return new SimpleDateFormat(outputFormat).format(new SimpleDateFormat(inputFormat).parse(inputTimeStamp));
    }
    public static String TimeStampConverterEnglish(final String inputFormat, String inputTimeStamp, final String outputFormat) throws ParseException {
        return new SimpleDateFormat(outputFormat,Locale.ENGLISH).format(new SimpleDateFormat(inputFormat,Locale.ENGLISH).parse(inputTimeStamp));
    }

    public static String TimeStampConverter_Ar(final String inputFormat, String inputTimeStamp, final String outputFormat) throws ParseException {
        return new SimpleDateFormat(outputFormat).format(new SimpleDateFormat(inputFormat).parse(inputTimeStamp));
    }

    public static String setDeadLine(String deadline, BaseActivity activity) {
        try {
            String[] deadlineText = deadline.split(" ");
            String date = deadlineText[0];
            String time = deadlineText[1];
            String outputFormat = "EEEE, dd MMMM yyyy";
            String outputFormatTime = "hh:mm a";
            String inputFormat = "yyyy-MM-dd";
            String inputFormatTime = "HH:mm";

            String formattedDate = Utils.TimeStampConverter_Ar(inputFormat, date, outputFormat);
            String formattedTime = Utils.TimeStampConverter_Ar(inputFormatTime, time, outputFormatTime);
            return (String.format("%s %s %s", formattedDate, activity.getString(R.string.at_1), formattedTime));

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String get2DecimalPlaces(Object o) {
        if (o instanceof Double) return numberFormat((double) o, 2);
        else if (o instanceof Integer) return numberFormat((int) o, 2);
        else if (o instanceof Float) return numberFormat((float) o, 1);

        return numberFormat((String) o, 2);
    }


    public static String convertDate(String dateInMilliseconds, String dateFormat) {
        return DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static void setImage(ImageView view, String imageUrl, int height, int width) {
        setImage(view, imageUrl, height, width, true);
    }

    public static void setImage(ImageView view, String imageUrl, int height, int width, boolean isShowPlaceHolder) {
        try {
            if (isShowPlaceHolder) {
                Glide.with(view.getContext()).load(imageUrl).centerCrop().placeholder(R.mipmap.ic_launcher_round).into(view);
            } else {
                Glide.with(view.getContext()).load(imageUrl).centerCrop().placeholder(R.color.background).into(view);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnSheetDialogClickInterface {
        void onCancel();

        void onDone(ArrayList<SortByFilterModel> arrayList);
    }

    public static String getPlatformTxt(int platformId) {
        String level = "";
        switch (platformId) {
            case 0:
                level = SELECT_FOLLOWER;
                break;
            case 1:
                level = FOLLOWER_1;
                break;
            case 2:
                level = FOLLOWER_2;
                break;
            case 3:
                level = FOLLOWER_3;
                break;
            case 4:
                level = FOLLOWER_4;
                break;
            case 5:
                level = FOLLOWER_5;
                break;
            case 6:
                level = FOLLOWER_6;
                break;
            case 7:
                level = FOLLOWER_7;
                break;
        }
        return level;
    }

    @SuppressLint("NewApi")
    public static String getFilePath(BaseActivity context, Uri uri) throws URISyntaxException {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{split[1]};
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }
            if (isGoogleDriveUri(uri)) {
                return getDriveFilePath(context, uri);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                return copyFileToInternalStorage(context, uri, "InfluencerBird");
            } else {
                return getDataColumn(context, uri, null, null);
            }

        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    private static String getDriveFilePath(BaseActivity activity, Uri uri) {
        Uri returnUri = uri;
        Cursor returnCursor = activity.getContentResolver().query(returnUri, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         *     * move to the first row in the Cursor, get the data,
         *     * and display it.
         * */
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));
        File file = new File(activity.getCacheDir(), name);
        try {
            InputStream inputStream = activity.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1024 * 1024 * 1024 * 1024;
            int bytesAvailable = inputStream.available();

            //int bufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            Log.e("File Size", "Size " + file.length());
            inputStream.close();
            outputStream.close();
            Log.e("File Path", "Path " + file.getPath());
            Log.e("File Size", "Size " + file.length());
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return file.getPath();
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static boolean isGoogleDriveUri(Uri uri) {
        return "com.google.android.apps.docs.storage".equals(uri.getAuthority()) || "com.google.android.apps.docs.storage.legacy".equals(uri.getAuthority());
    }

    public static String getFileName(BaseActivity activity, Uri uri) throws IllegalArgumentException {
        // Obtain a cursor with information regarding this uri
        Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            throw new IllegalArgumentException("Can't obtain file name, cursor is empty");
        }

        cursor.moveToFirst();

        String fileName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));

        cursor.close();

        return fileName;
    }

    private static String copyFileToInternalStorage(BaseActivity activity, Uri uri, String newDirName) {
        Uri returnUri = uri;

        Cursor returnCursor = activity.getContentResolver().query(returnUri, new String[]{OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE}, null, null, null);


        /*
         * Get the column indexes of the data in the Cursor,
         *     * move to the first row in the Cursor, get the data,
         *     * and display it.
         * */
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));

        File output;
        if (!newDirName.equals("")) {
            File dir = new File(activity.getFilesDir() + "/" + newDirName);
            if (!dir.exists()) {
                dir.mkdir();
            }
            output = new File(activity.getFilesDir() + "/" + newDirName + "/" + name);
        } else {
            output = new File(activity.getFilesDir() + "/" + name);
        }
        try {
            InputStream inputStream = activity.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(output);
            int read = 0;
            int bufferSize = 1024;
            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }

            inputStream.close();
            outputStream.close();

        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }

        return output.getPath();
    }

    public static boolean isArabic(Activity activity) {
        String language = Preferences.readString(activity, Constants.SELECTED_LANGUAGE, "en");
        return language.equals("ar");
    }

    public static String getMinutes(long min) {
        if (min > 30) {
            return "دقيقة";
        } else {
            List<String> minList = new ArrayList<>();
            minList.add("دقيقة");
            minList.add("دقيقتان");
            minList.add("دقائق");
            minList.add("دقائق");
            minList.add("دقائق");
            minList.add("دقائق");
            minList.add("دقائق");
            minList.add("دقائق");
            minList.add("دقائق");
            minList.add("دقائق");
            minList.add("دقيقة");
            minList.add("دقيقة");
            minList.add("دقيقة");
            minList.add("دقيقة");
            minList.add("دقيقة");
            minList.add("دقيقة");
            minList.add("دقيقة");
            minList.add("دقيقة");
            minList.add("دقيقة");
            minList.add("دقيقة");
            minList.add("دقيقة");
            minList.add("دقيقة");
            minList.add("دقيقة");
            minList.add("دقيقة");
            minList.add("دقيقة");
            minList.add("دقيقة");
            minList.add("دقيقة");
            minList.add("دقيقة");
            minList.add("دقيقة");
            minList.add("دقيقة");
            minList.add("دقيقة");

            for (int i = 1; i <= 30; i++) {
                // Your code here
                if (i == min) {
                    return minList.get(i);
                }
            }
        }

        return "دقيقة";
    }

    public static String getHours(long hour) {
        if (hour > 30) {
            return "ساعة";
        } else {
            List<String> hourList = new ArrayList<>();
            hourList.add("ساعة");
            hourList.add("ساعات");
            hourList.add("ساعات");
            hourList.add("ساعات");
            hourList.add("ساعات");
            hourList.add("ساعات");
            hourList.add("ساعات");
            hourList.add("ساعات");
            hourList.add("ساعات");
            hourList.add("ساعات");
            hourList.add("ساعة");
            hourList.add("ساعة");
            hourList.add("ساعة");
            hourList.add("ساعة");
            hourList.add("ساعة");
            hourList.add("ساعة");
            hourList.add("ساعة");
            hourList.add("ساعة");
            hourList.add("ساعة");
            hourList.add("ساعة");
            hourList.add("ساعة");
            hourList.add("ساعة");
            hourList.add("ساعة");
            hourList.add("ساعة");
            hourList.add("ساعة");
            hourList.add("ساعة");
            hourList.add("ساعة");
            hourList.add("ساعة");
            hourList.add("ساعة");
            hourList.add("ساعة");
            hourList.add("ساعة");


            for (int i = 1; i <= 30; i++) {
                // Your code here
                if (i == hour) {
                    return hourList.get(i);
                }
            }
        }

        return "ساعة";
    }

    public static String getDays(long day) {
        if (day > 30) {
            return "يوم";
        } else {
            List<String> daysList = new ArrayList<>();
            daysList.add("يوم");
            daysList.add("يومان");
            daysList.add("أيام");
            daysList.add("أيام");
            daysList.add("أيام");
            daysList.add("أيام");
            daysList.add("أيام");
            daysList.add("أيام");
            daysList.add("أيام");
            daysList.add("أيام");
            daysList.add("يوم");
            daysList.add("يوم");
            daysList.add("يوم");
            daysList.add("يوم");
            daysList.add("يوم");
            daysList.add("يوم");
            daysList.add("يوم");
            daysList.add("يوم");
            daysList.add("يوم");
            daysList.add("يوم");
            daysList.add("يوم");
            daysList.add("يوم");
            daysList.add("يوم");
            daysList.add("يوم");
            daysList.add("يوم");
            daysList.add("يوم");
            daysList.add("يوم");
            daysList.add("يوم");
            daysList.add("يوم");
            daysList.add("يوم");
            daysList.add("يوم");

            for (int i = 1; i <= 30; i++) {
                // Your code here
                if (i == day) {
                    return daysList.get(i);
                }
            }
        }

        return "يوم";
    }

    public static int calculateAge(String birthdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            // Parse the birthdate string into a Date object
            Date birthDate = sdf.parse(birthdate);

            // Get the current date
            Calendar currentDate = Calendar.getInstance();

            // Get the birthdate year and month
            Calendar birthDateCal = Calendar.getInstance();
            if (birthDate != null) {
                birthDateCal.setTime(birthDate);
            }
            int birthYear = birthDateCal.get(Calendar.YEAR);
            int birthMonth = birthDateCal.get(Calendar.MONTH);
            int birthDay = birthDateCal.get(Calendar.DAY_OF_MONTH);

            // Get the current year, month, and day
            int currentYear = currentDate.get(Calendar.YEAR);
            int currentMonth = currentDate.get(Calendar.MONTH);
            int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);

            // Calculate the age
            int age = currentYear - birthYear;

            // Check if the birthday has occurred this year
            if (currentMonth < birthMonth || (currentMonth == birthMonth && currentDay < birthDay)) {
                age--;
            }

            return age;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1; // Return -1 if there's an error parsing the date
        }
    }
    public enum WindowScreen {
        NAME, BRAND_NAME, CONTACT_NAME, EMAIL, PHONE, CRN, ABOUT, VAT, UNAME
    }
}
