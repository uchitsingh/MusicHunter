package com.codepath.musichunter;

import android.view.View;

/**
 * This interface is implemented when a user needs a click event listener. .
 */

public interface ItemClickListener {
    void onClick(View view, int position);
}
