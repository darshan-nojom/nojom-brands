package android.radiobox;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;

import com.nojom.client.R;


public class CustomRadioButton extends AppCompatRadioButton {

    public CustomRadioButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        style(context, attrs);
    }

    public CustomRadioButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        style(context, attrs);
    }

    private void style(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView);
            String fontName = a.getString(R.styleable.CustomTextView_font_name);
            try {
                if (fontName != null) {
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "" + fontName);
                    setTypeface(myTypeface);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            a.recycle();
        }
    }
}