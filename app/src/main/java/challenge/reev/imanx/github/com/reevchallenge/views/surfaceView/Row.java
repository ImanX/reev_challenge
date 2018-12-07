package challenge.reev.imanx.github.com.reevchallenge.views.surfaceView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by ImanX.
 * ReevChallenge | Copyrights 2018 ZarinPal Crop.
 */
public class Row extends LinearLayout {
    public Row(Context context) {
        super(context);
        setOrientation(HORIZONTAL);


        int index = 10;
        while (index != 0) {
            this.addView(buildIndex(index));
            index--;
        }
    }

    public Row(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Row(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Row(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    private View buildIndex(int id) {
        Button button = new Button(getContext());
        button.setId(id);
        LinearLayout.LayoutParams params = new LayoutParams(120, 120);
        button.setLayoutParams(params);
        return button;
    }
}
