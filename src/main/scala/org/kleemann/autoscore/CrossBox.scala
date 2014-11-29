package org.kleemann.autoscore

import org.scaloid.common._

import android.content.Context
import android.graphics.{Canvas, Color, Paint}
import android.view.{View, ViewGroup, Gravity}

/**
 * Write the dumb version of this first.
 * See how to incorporate this into the Scaloid framework later
 */
class CrossBox(context: Context) extends View(context) {

  var backgroundColor = Color.BLUE

  override def onDraw(canvas: Canvas) {
    // should cache this paint object
    val pBackground: Paint = new Paint()
    pBackground.setColor(backgroundColor)
    canvas.drawPaint(pBackground)

    val pStroke: Paint = new Paint()
    pStroke.setStrokeWidth(5.0f)
    pStroke.setStyle(Paint.Style.STROKE)
    pStroke.setColor(Color.WHITE)

    val w: Float = getWidth()
    val h: Float = getHeight()
    val z: Float = 0.0f
    // border
    canvas.drawLine(z, z, w, z, pStroke)
    canvas.drawLine(w, z, w, h, pStroke)
    canvas.drawLine(w, h, z, h, pStroke)
    canvas.drawLine(z, h, z, z, pStroke)
    // cross
    canvas.drawLine(z, z, w, h, pStroke)
    canvas.drawLine(w, z, z, h, pStroke)
  }

  override def setBackgroundColor(color: Int) {
    super.setBackgroundColor(color)
    this.backgroundColor = color
  }
}

