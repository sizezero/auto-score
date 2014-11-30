package org.kleemann.autoscore

import org.scaloid.common._

import android.content.Intent
import android.graphics.{Bitmap, BitmapFactory}
import android.net.Uri
import android.os.Bundle
import android.view.{Menu, MenuItem}

class SelectBorder extends SActivity  {

  onCreate {

    val intent: Intent = getIntent
    val fileUri: Uri = intent.getData

    val iv = new SImageView()
    iv.setImageURI(fileUri)

    contentView = new SFrameLayout {

      this += iv

      this += new SVerticalLayout {
	STextView("SelectBorder Activity")
	STextView(fileUri.toString)
      }
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
