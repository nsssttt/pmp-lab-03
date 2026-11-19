package org.student.mobile.lab3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private View linearStage;
    private View frameStage;
    private Button switchButton;
    private TextView modeLabel;
    private boolean showingFrame;

    private void applySystemBarInsets(View root) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false);
            final int left = root.getPaddingLeft();
            final int top = root.getPaddingTop();
            final int right = root.getPaddingRight();
            final int bottom = root.getPaddingBottom();
            root.setOnApplyWindowInsetsListener((view, insets) -> {
                android.graphics.Insets bars = insets.getInsets(WindowInsets.Type.systemBars());
                view.setPadding(left + bars.left, top + bars.top,
                        right + bars.right, bottom + bars.bottom);
                return insets;
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        applySystemBarInsets(findViewById(R.id.root_container));
        linearStage = findViewById(R.id.linear_stage);
        frameStage = findViewById(R.id.frame_stage);
        switchButton = findViewById(R.id.switch_layout_button);
        modeLabel = findViewById(R.id.mode_label);
        switchButton.setOnClickListener(view -> showStage(!showingFrame));
        showStage(false);
    }

    private void showStage(boolean showFrame) {
        showingFrame = showFrame;
        linearStage.setVisibility(showFrame ? View.GONE : View.VISIBLE);
        frameStage.setVisibility(showFrame ? View.VISIBLE : View.GONE);
        modeLabel.setText(showFrame ? R.string.frame_mode : R.string.linear_mode);
        switchButton.setText(showFrame ? R.string.show_linear : R.string.show_frame);
    }
}
