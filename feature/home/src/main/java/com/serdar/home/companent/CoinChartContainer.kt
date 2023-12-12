package com.serdar.home.companent

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView

class CoinChartContainer @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(
    context, attributeSet, defStyleAttr
) {
    private var indicatorView: View? = null

    private val viewRectF = RectF()

    private var coinTradeView: CoinTradeView? = null

    private var layoutWidth: Float = 0f
    private var layoutHeight: Float = 36.dp().toFloat()
    private var tradeLayoutHeight: Float = 196.dp().toFloat()

    private var itemWidth: Float = 0f
    private var itemHeight: Float = 0f

    private var onItemSelectedListener: ((OptionEnum) -> Unit)? = null

    private var tabInitialSelectedIndex = 0

    private var currentSelectedTextView: TextView? = null

    private var indicatorAnimator: ValueAnimator? = ValueAnimator.ofFloat(0f, 0f).apply {
        duration = ANIMATION_DURATION
        addUpdateListener { animation ->
            val marginLeft = animation.animatedValue as Float
            val marginParam: FrameLayout.LayoutParams =
                indicatorView?.layoutParams as FrameLayout.LayoutParams
            marginParam.setMargins(
                marginLeft.toInt(),
                marginParam.topMargin,
                marginParam.rightMargin,
                marginParam.bottomMargin
            )
            indicatorView?.layoutParams = marginParam
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewRectF.set(0f, 0f, w.toFloat(), h.toFloat())
        layoutWidth = viewRectF.width()
        itemWidth = viewRectF.width() / OptionEnum.values().size
        itemHeight = layoutHeight

        post {
            drawOptionItems()
        }
    }

    init {
        orientation = VERTICAL
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, tradeLayoutHeight.toInt())
        layoutParams.setMargins(0, 0, 0, 16.dp())
        coinTradeView = CoinTradeView(context, attributeSet, defStyleAttr).apply {
            this.layoutParams = layoutParams
        }
        addView(coinTradeView)
    }

    fun setOnItemSelectedListener(onItemSelectedListener: ((OptionEnum) -> Unit)?) {
        this.onItemSelectedListener = onItemSelectedListener
    }

    private fun drawOptionItems() {
        val optionContainer = FrameLayout(context).apply {
            layoutParams = LayoutParams(layoutWidth.toInt(), layoutHeight.toInt())
        }

        val indicatorWidth = itemWidth * 3 / 4f

        indicatorView = View(context).apply {
            val indicatorLayoutParams =
                FrameLayout.LayoutParams((indicatorWidth).toInt(), layoutHeight.toInt())
            indicatorLayoutParams.setMargins(
                (itemWidth / 8f).toInt(),
                0,
                0,
                0
            )
            layoutParams = indicatorLayoutParams

        }
        optionContainer.addView(indicatorView)

        drawItems(optionContainer)

        invalidate()
    }

    private fun drawItems(optionContainer: FrameLayout) {
        val optionLayout = LinearLayout(context).apply {
            layoutParams = LayoutParams(layoutWidth.toInt(), layoutHeight.toInt())
            orientation = HORIZONTAL
            tag = TAG_CONTAINER
        }
        OptionEnum.values().forEachIndexed { index, option ->
            optionLayout.addView(TextView(context).apply {
                textAlignment = View.TEXT_ALIGNMENT_CENTER
                gravity = Gravity.CENTER
                layoutParams = LayoutParams(itemWidth.toInt(), layoutHeight.toInt())
                text = option.getName(context)

                if (index == tabInitialSelectedIndex) {
                    currentSelectedTextView = this
                    onItemSelectedListener?.invoke(OptionEnum.values()[index])
                }

                setOnClickListener {
                    if (it != currentSelectedTextView) {
                        currentSelectedTextView = it as TextView
                        onSelected(index)
                        coinTradeView?.intervalChanged()
                    }
                }
            })
        }
        optionContainer.addView(optionLayout)
        addView(optionContainer)
    }

    private fun onSelected(index: Int) {
        animateIndicator(index)

        onItemSelectedListener?.invoke(OptionEnum.values()[index])
    }

    private fun animateIndicator(currentItemIndex: Int) {
        val previousMargin: Float =
            (indicatorView?.layoutParams as? FrameLayout.LayoutParams)?.leftMargin?.toFloat()
                ?: 0F
        val currentMargin: Float = itemWidth / 8f + currentItemIndex * itemWidth
        indicatorAnimator?.setFloatValues(previousMargin, currentMargin)
        indicatorAnimator?.start()
    }

    fun updateCoinItems(chartDataViewState: CoinChartDataViewState) {
        coinTradeView?.post {
            coinTradeView?.updateCoinItems(chartDataViewState)
        }
    }

    companion object {
        const val TAG_CONTAINER = "TAG_CONTAINER"
        const val ANIMATION_DURATION = 300L
    }

    fun Int.dp(): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }
}