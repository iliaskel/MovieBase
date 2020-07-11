package com.example.moviebase.view.widgets.controlsrecycler

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.view.NestedScrollingParent
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ControlsRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    RecyclerView(context, attrs, defStyleAttr), NestedScrollingParent {

    // region Constructor
    init {
        layoutManager = object : LinearLayoutManager(context, RecyclerView.VERTICAL, false) {
            override fun supportsPredictiveItemAnimations(): Boolean = true
        }

        itemAnimator = DefaultItemAnimator().apply {
            supportsChangeAnimations = true
            addDuration = 1500
            animate()
        }
        overScrollMode = View.OVER_SCROLL_NEVER
    }

    // endregion

    // region Fields

    // region Nested Scrolling enabled for recyclers inner scrollable items
    // code taken from: https://medium.com/widgetlabs-engineering/scrollable-nestedscrollviews-inside-recyclerview-ca65050d828a

    private var scrollTarget: View? = null
    private var scrollTargetDragging = false
    private var scrollTargetError = false
    private var disableOnInterceptTouchEvents = false

    // endregion

    // region Overrides

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val skipOnIntercept = scrollTarget != null
        if (skipOnIntercept) {
            disableOnInterceptTouchEvents = true
        }

        var dispatchHandled = super.dispatchTouchEvent(ev)

        if (skipOnIntercept) {
            disableOnInterceptTouchEvents = false

            if (!dispatchHandled || scrollTargetError) {
                dispatchHandled = super.dispatchTouchEvent(ev)
            }
        }

        return dispatchHandled
    }


    override fun onInterceptTouchEvent(
        e: MotionEvent
    ) = !disableOnInterceptTouchEvents && super.onInterceptTouchEvent(e)


    override fun onNestedScroll(
        target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int,
        dyUnconsumed: Int
    ) {
        if (target === scrollTarget && !scrollTargetDragging) {
            if (dyConsumed != 0) {
                scrollTargetDragging = true
                scrollTargetError = false
            } else if (dyConsumed == 0 && dyUnconsumed != 0) {
                scrollTargetError = true
                target.parent?.requestDisallowInterceptTouchEvent(false)
            }
        }
    }


    override fun onNestedScrollAccepted(child: View, target: View, axes: Int) {
        if (axes and View.SCROLL_AXIS_VERTICAL != 0) {
            scrollTarget = target
            scrollTargetDragging = false
            scrollTargetError = false
        }

        super.onNestedScrollAccepted(child, target, axes)
    }


    override fun onStartNestedScroll(
        child: View, target: View,
        nestedScrollAxes: Int
    ) = (nestedScrollAxes and View.SCROLL_AXIS_VERTICAL != 0)


    override fun onStopNestedScroll(child: View) {
        // The descendant finished scrolling. Clean up!
        scrollTarget = null
        scrollTargetDragging = false
        scrollTargetError = false
    }

    // endregion
}