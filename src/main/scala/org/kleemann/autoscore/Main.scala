package org.kleemann.autoscore

import org.scaloid.common._
import android.graphics.Color

import android.view.{Menu, MenuItem, Gravity}

class Main extends SActivity  {

  onCreate {
    contentView = new SFrameLayout {
      style {
        case b: SButton => b.textColor(Color.RED).onClick(toast("Bang!"))
        case t: STextView => t textSize 10.dip
        case e: SEditText => e.backgroundColor(Color.YELLOW)
      }
      SEditText("Yellow input field fills the space").fill
      SButton(R.string.red).<<.Gravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL).>>
    } padding 20.dip
  }

  override def onCreateOptionsMenu(menu: Menu) = {
    getMenuInflater.inflate(R.menu.actions, menu)
    true
  }

  def onUndo(item: MenuItem)  = toast("Undo")
  def onRestart(item: MenuItem)  = toast("Restart")

}
