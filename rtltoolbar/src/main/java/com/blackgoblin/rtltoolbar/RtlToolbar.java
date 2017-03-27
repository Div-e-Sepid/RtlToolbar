package com.blackgoblin.rtltoolbar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

public class RtlToolbar extends Toolbar {

    public RtlToolbar(Context context) {
        super(context);
        init(context);
    }

    public RtlToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RtlToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setRotationY(180f);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof TextView && ((TextView) child).getText().toString().equals(title)) {
                ((TextView) child).setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.toolbarTitleSize));
            }
        }
    }

    @Override
    public void onViewAdded(View child) {
        if (!(child instanceof ActionMenuView)) {
            child.setRotationY(180f);
        } else {
            ((ActionMenuView) child).setOnHierarchyChangeListener(new OnHierarchyChangeListener() {
                @Override
                public void onChildViewAdded(View parent, View child) {
                    child.setRotationY(180f);
                }

                @Override
                public void onChildViewRemoved(View view, View view1) {

                }
            });
        }

        // rotates searchView back button
        if (child.getContentDescription() != null && child.getContentDescription().equals(getContext().getString(android.support.v7.appcompat.R.string.abc_toolbar_collapse_description))) {
            child.setRotationY(0);
        }

        // rotates home button
        if (child.getContentDescription() != null && child.getContentDescription().equals(getNavigationContentDescription())) {
            child.setRotationY(0);
        }

        // rotates searchView
        if (child instanceof SearchView) {
            child.setRotationY(0);
            child.findViewById(android.support.v7.appcompat.R.id.search_src_text).setRotationY(180f);
        }
        super.onViewAdded(child);
    }
}
