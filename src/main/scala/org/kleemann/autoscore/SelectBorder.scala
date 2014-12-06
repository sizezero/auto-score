package org.kleemann.autoscore

import org.scaloid.common._

import android.content.Intent
import android.graphics.{Bitmap, BitmapFactory, Color}
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.{Gravity, Menu, MenuItem, MotionEvent, View, ViewGroup}
import android.widget.FrameLayout

class SelectBorder extends SActivity  {

  var dotOverlay: DotOverlay = null

  onCreate {

    val intent: Intent = getIntent
    val fileUri: Uri = intent.getData
    val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
    val iv = new SImageView().
      adjustViewBounds(true).
      imageBitmap(bitmap)

    contentView = new SFrameLayout {

      this += iv.<<.wrap.Gravity(Gravity.TOP | Gravity.LEFT).>>.onTouch( (v, me) => {
	if (me.getAction == MotionEvent.ACTION_UP) {
	  dotOverlay.addPoint(me.getX, me.getY)
	  if (dotOverlay.circles.size == 3) {
	    // transition to the solution activity
	    // TODO: this should be done in a background thread
	    Global.solution = Transform.all(bitmap)
	    val intent: Intent = new Intent(SelectBorder.this, classOf[Solution]);
	    startActivity(intent);
	  }
	}
	true
      })

      dotOverlay = new DotOverlay(context)
      dotOverlay.setLayoutParams(new FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.FILL_PARENT,
        ViewGroup.LayoutParams.FILL_PARENT,
        Gravity.FILL))
      dotOverlay.setAlpha(0.3f)
      this += dotOverlay

      this += new SVerticalLayout {
	STextView("SelectBorder Activity")
	STextView(fileUri.toString)
      }.enabled(false)
    }
  }

  override def onCreateOptionsMenu(menu: Menu) = {
    getMenuInflater.inflate(R.menu.actions, menu)
    true
  }

  def onRestart(item: MenuItem) = startFromBeginning

  def onUndo(item: MenuItem) = {}

  def startFromBeginning() {
    val intent: Intent = new Intent(this, classOf[Main]);
    startActivity(intent);
  }
}
