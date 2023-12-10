package com.example.finalskillsync.customeView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.example.finalskillsync.R

class SendIconView : View {

    private val paint: Paint = Paint()
    private val path: Path = Path()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        paint.apply {
            color = resources.getColor(R.color.send_icon_color) // Set your desired color
            style = Paint.Style.FILL
            isAntiAlias = true
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = canvas.width.toFloat()
        val height = canvas.height.toFloat()

        path.reset()
        path.moveTo(0f, height / 2f)
        path.lineTo(width / 2f, 0f)
        path.lineTo(width, height / 2f)
        path.lineTo(width / 2f, height)
        path.close()

        canvas.drawPath(path, paint)
    }
}
