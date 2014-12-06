package org.kleemann.autoscore

import org.scaloid.common._

class Solution extends SActivity  {

  onCreate {

    val iv = new SImageView().
      adjustViewBounds(true)
    if (Global.solution != null) iv.imageBitmap(Global.solution)

    contentView = new SFrameLayout {
      this += iv
    }

  }
}
