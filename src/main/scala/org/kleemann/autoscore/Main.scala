package org.kleemann.autoscore

import org.scaloid.common._
import android.graphics.Color

import android.view.{View, ViewGroup, Menu, MenuItem, Gravity}
import android.widget.FrameLayout

class Main extends SActivity  {

  onCreate {
    contentView = new SFrameLayout {
      style {
        case b: SButton => b.textColor(Color.RED).onClick(toast("Bang!"))
        case t: STextView => t textSize 10.dip
        case e: SEditText => e.backgroundColor(Color.YELLOW)
      }

      // adding non-Scaloid components is really ugly
      val cb1 = new CrossBox(context)
      cb1.setLayoutParams(new FrameLayout.LayoutParams(
	ViewGroup.LayoutParams.FILL_PARENT,
	ViewGroup.LayoutParams.FILL_PARENT,
	Gravity.FILL))
      cb1.setBackgroundColor(Color.BLUE)
      this += cb1

      val cb2 = new CrossBox(context)
      cb2.setLayoutParams(new FrameLayout.LayoutParams(
	40,
	40,
	Gravity.RIGHT | Gravity.TOP))
      cb2.setBackgroundColor(Color.RED)
      cb2.setAlpha(0.5f)
      this += cb2

      val cb3 = new CrossBox(context)
      cb3.setLayoutParams(new FrameLayout.LayoutParams(
	80,
	80,
	Gravity.LEFT | Gravity.CENTER_VERTICAL))
      cb3.setBackgroundColor(Color.GREEN)
      cb3.setAlpha(0.5f)
      this += cb3

      //SButton(R.string.red).<<.Gravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL).>>
    } padding 20.dip
  }

  override def onCreateOptionsMenu(menu: Menu) = {
    getMenuInflater.inflate(R.menu.actions, menu)
    true
  }

  def onUndo(item: MenuItem)  = toast("Undo")
  def onRestart(item: MenuItem)  = toast("Restart")

}
