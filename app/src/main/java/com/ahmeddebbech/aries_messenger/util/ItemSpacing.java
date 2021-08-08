package com.ahmeddebbech.aries_messenger.util;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemSpacing extends RecyclerView.ItemDecoration {
    private int vertSpace;

    public ItemSpacing(int vertHeight){
        this.vertSpace = vertHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = vertSpace;
    }
}
