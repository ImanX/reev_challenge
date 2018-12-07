package challenge.reev.imanx.github.com.reevchallenge.views.surfaceView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import challenge.reev.imanx.github.com.reevchallenge.models.Reev;

/**
 * Created by ImanX.
 * ReevChallenge | Copyrights 2018 ZarinPal Crop.
 */
public class ReevSurfaceView extends LinearLayout implements IReevCommand, IReevMark {

    private static final char          MARK_WEIR = '#';
    private static final char[]        ARROWS    = {'↑', '→', '←'};
    private              Reev.Position lastArrowPosition;

    @Nullable
    private OnDoCommandListener listener;

    public ReevSurfaceView(Context context) {
        super(context);

    }

    public ReevSurfaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ReevSurfaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ReevSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOrientation(VERTICAL);

        for (int i = 0; i < 20; i++) {
            addView(new Row(getContext()));
        }
    }


    public void mark(Reev.Position position, char c) {


        if (position.y > 19 || position.x > 9) {
            return;
        }


        try {
            Row    row    = (Row) this.getChildAt((19 - position.y));
            Button button = (Button) row.getChildAt(position.x);

            if (button.getText().equals(String.valueOf(MARK_WEIR))) {
                if (listener != null) listener.onCrash(position);
                return;
            }

            if (listener != null) {
                listener.onMove(c, position);
            }


            button.setTextSize(20);
            button.setText(String.valueOf(c));
        } catch (Exception ex) {
            // Log.e("TAG", "mark: y: " + y + " x: " + x);
            ex.printStackTrace();
        }


    }


    public void mark(Reev.Position position) {
        mark(position, MARK_WEIR);
    }

    @Override
    public void start(Reev.Position startPosition, List<Reev.Position> weirs, OnDoCommandListener listener) {
        this.lastArrowPosition = startPosition;
        mark(startPosition, ARROWS[0]);
        for (Reev.Position p : weirs) {
            mark(p);
        }
        this.listener = listener;
    }

    @Override
    public void clean(Reev.Position position) {
        mark(position, ' ');
    }


    public void doCommands(final char[] chars) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (final char c : chars) {
                        Thread.sleep(1000);
                        post(new Runnable() {
                            @Override
                            public void run() {
                                doCommand(c);
                            }
                        });

                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void doCommand(char c) {
        switch (c) {
            case 'M':
                move();
                break;
            case 'R':
                moveToRight();
                break;
            case 'L':
                moveToLeft();
                break;
        }
    }


    @Override
    public void move() {
        clean(lastArrowPosition);
        lastArrowPosition.y += 1;
        mark(lastArrowPosition, ARROWS[0]);
    }

    @Override
    public void moveToRight() {
        clean(lastArrowPosition);
        lastArrowPosition.x += 1;
        mark(lastArrowPosition, ARROWS[1]);
    }

    @Override
    public void moveToLeft() {
        clean(lastArrowPosition);
        lastArrowPosition.x -= 1;
        mark(lastArrowPosition, ARROWS[2]);
    }


}
