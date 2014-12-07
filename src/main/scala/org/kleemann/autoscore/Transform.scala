package org.kleemann.autoscore

import android.graphics.{Bitmap, BitmapFactory, Canvas, Color, Paint, PointF}

object Transform {

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




    val working = newBitmap(green.getWidth + dx*2, green.getHeight + dy*2)
    val cw = new Canvas(working)
    // red edges for now
    // should cache this paint object
    p.setColor(Color.RED)
    cw.drawRect(0f, 0f, working.getWidth.toFloat, working.getHeight.toFloat, p)
    cw.drawBitmap(green, dx.toFloat, dy.toFloat, p)

    // apparently ImageView cannot display a texture larger than 4096x4096
    if (working.getWidth > 4096 || working.getHeight > 4096) {
      val resize = newBitmap(math.min(working.getWidth, 4096), math.min(working.getHeight, 4096))
      val cr = new Canvas(resize)
      cr.drawBitmap(working, 0f, 0f, p)
      resize
    } else {
      working
    }
  }

  def newBitmap(w: Int, h: Int): Bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)

}
