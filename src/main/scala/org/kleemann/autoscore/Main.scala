package org.kleemann.autoscore

import org.scaloid.common._

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

import android.content.Intent
import android.net.Uri
import android.graphics.Color
import android.os.{Bundle, Environment}
import android.provider.MediaStore
import android.util.Log
import android.view.{View, ViewGroup, Menu, MenuItem, Gravity}
import android.widget.{FrameLayout, Toast}

class Main extends SActivity  {

  onCreate {
    contentView = new SFrameLayout {

      this += new SVerticalLayout {

        SButton(R.string.take_picture)
	    .<<.wrap.Gravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL).>>
            .onClick { takeAPicture }

	val hasFiles = Main.mediaStorageDir.listFiles.size != 0

        SButton(R.string.select_picture)
	    .<<.wrap.Gravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL).>>
	    .enabled(hasFiles)
            .onClick { toast("TODO: Select a Photo") }

        SButton(R.string.previous_picture)
	    .<<.wrap.Gravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL).>>
	    .enabled(hasFiles)
            .onClick { usePreviousPicture }

      }

    } padding 20.dip
  }

  override def onCreateOptionsMenu(menu: Menu) = {
    getMenuInflater.inflate(R.menu.actions, menu)
    true
  }

  // Used by takeAPicture and onActivity Result since this data cannot 
  // be communicated through the camera intent.  This activity could 
  // be destroyed while camera intent is active so we need to save this
  // data.
  var fileUri: Uri = Uri.EMPTY

  def takeAPicture {
    // use an intent to have the user create a picture
    val intent: Intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    fileUri = Uri.fromFile(Main.getOutputMediaFile(Main.MEDIA_TYPE_IMAGE)) // create a file to save the image
    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
    startActivityForResult(intent, Main.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
  }

  override def onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    // returned data is null if MediaStore.EXTRA_OUTPUT is set
    if (requestCode == Main.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
      if (resultCode == android.app.Activity.RESULT_OK) {
	if (fileUri == null) {
          // Image captured and saved to fileUri specified in the Intent
          //Toast.makeText(this, "fileUri is null", Toast.LENGTH_LONG).show()
	} else nextActivity(fileUri)
      } else if (resultCode == android.app.Activity.RESULT_CANCELED) {
        // User cancelled the image capture
      } else {
        // Image capture failed, advise user
      }
    }
  }

  override def onSaveInstanceState(savedInstanceState: Bundle) {
    super.onSaveInstanceState(savedInstanceState)
    savedInstanceState.putString("fileUri", fileUri.toString)
  }

  override def onRestoreInstanceState(savedInstanceState: Bundle) {
    // Always call the superclass so it can restore the view hierarchy
    super.onRestoreInstanceState(savedInstanceState)
    fileUri = Uri.parse(savedInstanceState.getString("fileUri"))
  }

  def usePreviousPicture {
    val fs = Main.mediaStorageDir.listFiles.toList.sorted
    if (!fs.isEmpty) nextActivity(Uri.fromFile(fs.last))
  }

  /**
   * We have a picture; go to the next activity */
  def nextActivity(pictureUri: Uri) {
    val intent: Intent = new Intent(this, classOf[SelectBorder]);
    intent.setData(pictureUri)
    startActivity(intent);
  }

  def onUndo(item: MenuItem)  = toast("Undo")

  def onRestart(item: MenuItem) = takeAPicture

}

object Main {

  val MEDIA_TYPE_IMAGE = 1
  val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100

  // To be safe, you should check that the SDCard is mounted
  // using Environment.getExternalStorageState() before doing this.
  // This location works best if you want the created images to be shared
  // between applications and persist after your app has been uninstalled.
  val mediaStorageDir: File = new File(Environment.getExternalStoragePublicDirectory(
              Environment.DIRECTORY_PICTURES), "MyCameraApp")

  /** Create a File for saving an image or video.
   * Taken from android examples. Can probably do better */
  def getOutputMediaFile(type0: Int): File = {

    // Create the storage directory if it does not exist
    if (! mediaStorageDir.exists()) {
        if (! mediaStorageDir.mkdirs()) {
            Log.d("MyCameraApp", "failed to create directory")
            return null
        }
    }

    // Create a media file name
    val timeStamp: String = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    var mediaFile: File =
      if (type0 == MEDIA_TYPE_IMAGE) {
        new File(mediaStorageDir.getPath() + File.separator +
        "IMG_"+ timeStamp + ".jpg")
      } else null

    return mediaFile
  }

}

