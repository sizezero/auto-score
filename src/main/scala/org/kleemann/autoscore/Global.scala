package org.kleemann.autoscore

import android.graphics.{Bitmap, BitmapFactory, Color}

/**
 * Android recommends global static data as a way to pass large data between
 * activities.  */
object Global {

  var camera: Bitmap = null
  var solution: Bitmap = null

}
