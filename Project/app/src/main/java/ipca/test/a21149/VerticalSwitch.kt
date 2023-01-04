package ipca.test.a21149

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat

class VerticalSwitch : View, GestureDetector.OnGestureListener{

    private var onStateChanged : ((Boolean) ->Unit)? = null
    fun setOnStateChanged(onStateChanged : (Boolean) ->Unit) {
        this.onStateChanged = onStateChanged
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    private val swipeThreshold = 100
    private val swipeVelocityThreshold = 100
    private var gestureDetector: GestureDetectorCompat = GestureDetectorCompat(context, this)
    var isOn = false



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val paint = Paint()
        paint.color = Color.BLUE
        canvas?.drawRect(Rect(0, 0, width, height), paint)

        val margin = 20
        paint.color = Color.RED

        if (isOn){
            // cima
            canvas?.drawRect(Rect(margin, margin, width-margin, height/2), paint)
        } else {
            // baixo
            canvas?.drawRect(Rect(margin, height/2, width-margin, height-margin), paint)
        }


        paint.color = Color.BLACK
        paint.textSize = 60f
        paint.textAlign = Paint.Align.CENTER
        canvas?.drawText("ON", width/ 2f, 80f, paint)
        canvas?.drawText("OFF", width/ 2f, height - 80f + margin, paint)


    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (gestureDetector.onTouchEvent(event)) {
            true
        }
        else {
            super.onTouchEvent(event)
        }
    }


    override fun onDown(p0: MotionEvent?): Boolean {
        return true
    }

    override fun onShowPress(p0: MotionEvent?) {
        return
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return true
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return true
    }

    override fun onLongPress(p0: MotionEvent?) {
        return
    }

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        try {
            val diffY = e2.y - e1.y
            val diffX = e2.x - e1.x
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > swipeThreshold && Math.abs(velocityX) > swipeVelocityThreshold) {
                    if (diffX > 0) {
                        Toast.makeText(context, "Left to Right swipe gesture", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(context, "Right to Left swipe gesture", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                // movimento horizontal
                if (Math.abs(diffY) > swipeThreshold && Math.abs(velocityY) > swipeVelocityThreshold) {
                    isOn = diffY <= 0
                    onStateChanged?.invoke(isOn)
                    invalidate()
                }
            }
        }
        catch (exception: Exception) {
            exception.printStackTrace()
        }
        return true
    }
}