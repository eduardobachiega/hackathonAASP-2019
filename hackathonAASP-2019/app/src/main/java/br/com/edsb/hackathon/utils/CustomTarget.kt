package br.com.edsb.hackathon.utils

import android.content.res.Resources
import android.graphics.Point
import android.util.Log
import android.view.View
import com.github.amlcurran.showcaseview.targets.Target

class CustomTarget: Target {
    enum class Position {
        LEFT,
        CENTER,
        RIGHT
    }

    private var view: View? = null
    private var position: Position? = null
    private var target: Int = 0

    constructor(view: View, target: Int, position: Position) {
        this.view = view
        this.target = target
        this.position = position
    }

    constructor(view: View, position: Position) {
        this.view = view
        this.target = 0
        this.position = position
    }

    override fun getPoint(): Point {
        val location = IntArray(2)
        view!!.getLocationInWindow(location)

        var x = 0
        var y = 0

        when (position) {
            Position.LEFT -> {
                x = if (target != 0)
                    location[0] + view!!.x.toInt() + dpToPx(50) / 2
                else
                    location[0] + dpToPx(50) / 2

                y = location[1] + view!!.height / 2
            }

            Position.CENTER -> {
                x = location[0] + view!!.width / 2
                y = location[1] + view!!.height / 2
            }

            Position.RIGHT -> {
                Log.e("LOC", ""+location[0])
                x = view!!.width + dpToPx(50) / 2
                y = location[1] + view!!.height / 2
            }
        }


        return Point(x, y)
    }

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }
}