package org.kleemann.autoscore

import org.scaloid.common._

import android.content.Context
import android.graphics.{Canvas, Color, Paint, PointF}
import android.view.{View, ViewGroup, Gravity}

/**
 * Write the dumb version of this first.
 * See how to incorporate this into the Scaloid framework later
 */
class DotOverlay(context: Context) extends View(context) {

  var circles = new scala.collection.mutable.ArrayBuffer[PointF]

  override def onDraw(canvas: Canvas) {

    if (circles.size == 0) return

    // should cache this paint object
    val p: Paint = new Paint()
    p.setStrokeWidth(5.0f)
    p.setStyle(Paint.Style.FILL_AND_STROKE)
    p.setColor(Color.GREEN)

    var last: PointF = null
    for (pt <- circles) {
      canvas.drawCircle(pt.x, pt.y, 20f, p)
      if (last != null) canvas.drawLine(last.x, last.y, pt.x, pt.y, p)
      last = pt
    }
  }

  def addPoint(x: Float, y: Float) {
    circles.append(new PointF(x, y))
    invalidate
  }
}

