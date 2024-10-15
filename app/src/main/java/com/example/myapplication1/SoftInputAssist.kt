package com.example.myapplication1

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout

class SoftInputAssist(activity: Activity) {

    private var rootView: View? = null
    private var contentContainer: ViewGroup? = null
    private var viewTreeObserver: ViewTreeObserver? = null
    private val contentAreaOfWindowBounds = Rect()
    private var rootViewLayoutParams: FrameLayout.LayoutParams? = null
    private var usableHeightPrevious = 0

    private val listener = ViewTreeObserver.OnGlobalLayoutListener {
        possiblyResizeChildOfContent()
    }

    init {
        contentContainer = activity.findViewById(android.R.id.content)
        rootView = contentContainer?.getChildAt(0)
        rootViewLayoutParams = rootView?.layoutParams as? FrameLayout.LayoutParams
    }

    fun onResume() {
        if (viewTreeObserver == null || !(viewTreeObserver?.isAlive == true)) {
            viewTreeObserver = rootView?.viewTreeObserver
        }
        viewTreeObserver?.addOnGlobalLayoutListener(listener)
    }

    fun onPause() {
        if (viewTreeObserver?.isAlive == true) {
            viewTreeObserver?.removeOnGlobalLayoutListener(listener)
        }
    }

    fun onDestroy() {
        rootView = null
        contentContainer = null
        viewTreeObserver = null
    }

    private fun possiblyResizeChildOfContent() {
        contentContainer?.getWindowVisibleDisplayFrame(contentAreaOfWindowBounds)
        val usableHeightNow = contentAreaOfWindowBounds.height()

        if (usableHeightNow != usableHeightPrevious) {
            rootViewLayoutParams?.height = usableHeightNow
            rootView?.layout(
                contentAreaOfWindowBounds.left,
                contentAreaOfWindowBounds.top,
                contentAreaOfWindowBounds.right,
                contentAreaOfWindowBounds.bottom
            )
            rootView?.requestLayout()
            usableHeightPrevious = usableHeightNow
        }
    }
}
