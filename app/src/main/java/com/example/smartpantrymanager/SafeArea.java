package com.example.smartpantrymanager;

import android.app.Activity;
import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public final class SafeArea {

    private SafeArea() {
        // Utility class
    }

    public static void apply(Activity activity) {
        View content = activity.findViewById(android.R.id.content);

        ViewCompat.setOnApplyWindowInsetsListener(content, (view, windowInsets) -> {
            Insets insets = windowInsets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
            );

            view.setPadding(
                    insets.left,
                    insets.top,
                    insets.right,
                    insets.bottom
            );

            return windowInsets;
        });

        ViewCompat.requestApplyInsets(content);
    }
}
