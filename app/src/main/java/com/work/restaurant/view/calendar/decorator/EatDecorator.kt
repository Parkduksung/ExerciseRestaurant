package com.work.restaurant.view.calendar.decorator


import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.LineBackgroundSpan
import androidx.core.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.work.restaurant.R
import com.work.restaurant.util.App
import java.util.*

class EatDecorator(
    private val dates: HashSet<CalendarDay>
) : DayViewDecorator {


    override fun shouldDecorate(day: CalendarDay?): Boolean =
        dates.contains(day)


    override fun decorate(view: DayViewFacade?) {
        view?.areDaysDisabled().let {
            view?.addSpan(
                CustomEatSpan(
                    10f,
                    ContextCompat.getColor(App.instance.context(), R.color.colorLowPurple)
                )
            )
        }
    }
}


class CustomEatSpan(private val radius: Float, private var color: Int) :
    LineBackgroundSpan {

    override fun drawBackground(
        canvas: Canvas,
        paint: Paint,
        left: Int,
        right: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        text: CharSequence,
        start: Int,
        end: Int,
        lineNumber: Int
    ) {


        val leftMost = -12
        val oldColor = paint.color
        paint.color = color

        canvas.drawCircle(
            ((left + right) / 2 - leftMost).toFloat(),
            bottom + radius,
            radius,
            paint
        )

        paint.color = oldColor

    }


}
