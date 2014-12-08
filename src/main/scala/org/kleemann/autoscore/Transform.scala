package org.kleemann.autoscore

import org.scaloid.common._

import android.graphics.{Bitmap, BitmapFactory, Canvas, Color, Matrix, Paint, PointF}

object Transform {

  implicit val tag = LoggerTag("MyAppTag")

  val DivideThreshold = 0.1f

  def all(src: Bitmap, a: PointF, b: PointF, c: PointF): Bitmap = {

    // make a bitmap with some padding and draw the source bimap in it
    val dx = 500
    val dy = 500

    // reuse paint object
    val p: Paint = new Paint()
    p.setStrokeWidth(5.0f)
    p.setStyle(Paint.Style.FILL_AND_STROKE)

    // draw green selections directly on image
    val green = newBitmap(src.getWidth, src.getHeight)
    val cg = new Canvas(green)
    cg.drawBitmap(src, 0f, 0f, p)
    p.setColor(Color.GREEN)
    var last: PointF = null
    for (pt <- List(a, b, c)) {
      cg.drawCircle(pt.x, pt.y, 20f, p)
      if (last != null) cg.drawLine(last.x, last.y, pt.x, pt.y, p)
      last = pt
    }

    // start matrix for transformation
    val m = new Matrix()
    m.postTranslate(-b.x, -b.y)
    // rotate A so that it is aligned with the y axis
    var ta = tp(a, m)
    if (ta.y > DivideThreshold)
      m.postRotate(math.toDegrees(math.tan(ta.x / ta.y)).toFloat)
    // skew C to X axis
    // meaning of skew arguments found by trial and error
    var tc = tp(c, m)
    if (tc.x > DivideThreshold)
      m.postSkew(0f, math.tan(- tc.y / tc.x).toFloat)
    // TODO: no scaling
    m.postTranslate(dx, dy)
    ta = tp(a, m)
    val tb = tp(b, m)
    tc = tp(c, m)
 
    // draw transformed bitmap onto large working bitmap
    val working = newBitmap(green.getWidth + dx*2, green.getHeight + dy*2)
    val cw = new Canvas(working)
    // red edges for now
    // should cache this paint object
    p.setColor(Color.RED)
    cw.drawRect(0f, 0f, working.getWidth.toFloat, working.getHeight.toFloat, p)
    cw.drawBitmap(green, m, p)

    // TODO: may want to create a new bitmap subset so the larger one can be GCed
    val cropped = Bitmap.createBitmap(
      working, 
      tb.x.toInt, tb.y.toInt, 
      (tc.x-tb.x).toInt, (ta.y-tb.y).toInt)

    // apparently ImageView cannot display a texture larger than 4096x4096
    if (cropped.getWidth > 4096 || cropped.getHeight > 4096) {
      val resize = newBitmap(math.min(cropped.getWidth, 4096), math.min(cropped.getHeight, 4096))
      val cr = new Canvas(resize)
      cr.drawBitmap(cropped, 0f, 0f, p)
      resize
    } else {
      cropped
    }
  }

  def newBitmap(w: Int, h: Int): Bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)

  def tp(src: PointF, m: Matrix): PointF = {
    val a = Array(src.x, src.y)
    m.mapPoints(a)
    new PointF(a(0), a(1))
  }

}
