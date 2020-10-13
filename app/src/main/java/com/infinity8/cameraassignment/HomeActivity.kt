package com.infinity8.cameraassignment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.infinity8.cameraassignment.databinding.ActivityHomeBinding
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_home.*
import java.io.File
import java.util.HashMap


class HomeActivity : AppCompatActivity() {

    var binding: ActivityHomeBinding? = null
    var camera: androidx.camera.core.Camera? = null
    var imageCapture: ImageCapture? = null
    var preview: Preview? = null
    var mDatabaseRef: DatabaseReference? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)
        mAuth = FirebaseAuth.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().reference.child("User")

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val rxPermissions = RxPermissions(this@HomeActivity)
        rxPermissions
            .request(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) // ask single or multiple permission once
            .subscribe { granted: Boolean ->
                if (granted) {
                    // All requested permissions are granted
                    startCamera()
                    //  Toast.makeText(this@HomeActivity, "Permission granted", Toast.LENGTH_SHORT).show()
                } else {
                    // At least one permission is denied
                    Toast.makeText(this@HomeActivity, "Failed", Toast.LENGTH_SHORT).show()

                }

            }

        binding!!.captureImage.setOnClickListener {
            takePhoto()
        }

        binding!!.orangeStar.setOnClickListener{

            mDatabaseRef!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val user = mAuth!!.currentUser!!
                    val name = user.displayName
                    val email = user.email
                    val photo = user.photoUrl.toString()
                    val map = HashMap<String, Any>()
                    map["email"] = email!!
                    map["name"] = name!!
                    map["image"] = photo
                    map["color"] = "orange"
                        FirebaseDatabase.getInstance().reference
                            .child("User")
                            .child(FirebaseAuth.getInstance().currentUser!!.uid)
                            .setValue(map)
                            .addOnSuccessListener { aVoid: Void? ->
                                Toast.makeText(
                                    this@HomeActivity,
                                    "Orange Stored",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()

                                finish()
                            }
                            .addOnFailureListener { e: Exception ->
                                Log.d("FailedData", "onFailure: " + e.message)
                                Toast.makeText(
                                    this@HomeActivity,
                                    "Failed" + e.message,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                }

                override fun onCancelled(error: DatabaseError) {}
            })

        }

        binding!!.greyStar.setOnClickListener {
            mDatabaseRef!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val user = mAuth!!.currentUser!!
                    val name = user.displayName
                    val email = user.email
                    val photo = user.photoUrl.toString()


                    val map = HashMap<String, Any>()
                    map["email"] = email!!
                    map["name"] = name!!
                    map["image"] = photo

                    map["color"] = "grey"
                    FirebaseDatabase.getInstance().reference
                        .child("User")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .setValue(map)
                        .addOnSuccessListener { aVoid: Void? ->
                            Toast.makeText(
                                this@HomeActivity,
                                "Grey Stored",
                                Toast.LENGTH_SHORT
                            )
                                .show()

                            finish()
                        }
                        .addOnFailureListener { e: Exception ->
                            Log.d("FailedData", "onFailure: " + e.message)
                            Toast.makeText(
                                this@HomeActivity,
                                "Failed" + e.message,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }

        binding!!.greenStar.setOnClickListener {
            mDatabaseRef!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val user = mAuth!!.currentUser!!
                    val name = user.displayName
                    val email = user.email
                    val photo = user.photoUrl.toString()


                    val map = HashMap<String, Any>()
                    map["email"] = email!!
                    map["name"] = name!!
                    map["image"] = photo
                    map["color"] = "green"
                    FirebaseDatabase.getInstance().reference
                        .child("User")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .setValue(map)
                        .addOnSuccessListener { aVoid: Void? ->
                            Toast.makeText(
                                this@HomeActivity,
                                "Green Stored",
                                Toast.LENGTH_SHORT
                            )
                                .show()

                            finish()
                        }
                        .addOnFailureListener { e: Exception ->
                            Log.d("FailedData", "onFailure: " + e.message)
                            Toast.makeText(
                                this@HomeActivity,
                                "Failed" + e.message,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }

        binding!!.yellowStar.setOnClickListener {
            mDatabaseRef!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val user = mAuth!!.currentUser!!
                    val name = user.displayName
                    val email = user.email
                    val photo = user.photoUrl.toString()


                    val map = HashMap<String, Any>()
                    map["email"] = email!!
                    map["name"] = name!!
                    map["image"] = photo
                    map["color"] = "yellow"
                    FirebaseDatabase.getInstance().reference
                        .child("User")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .setValue(map)
                        .addOnSuccessListener { aVoid: Void? ->
                            Toast.makeText(
                                this@HomeActivity,
                                "Yellow Stored",
                                Toast.LENGTH_SHORT
                            )
                                .show()

                            finish()
                        }
                        .addOnFailureListener { e: Exception ->
                            Log.d("FailedData", "onFailure: " + e.message)
                            Toast.makeText(
                                this@HomeActivity,
                                "Failed" + e.message,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }




    }

    private fun takePhoto() {

        val photoFile =
            File(externalMediaDirs.firstOrNull(), "CameraApp - ${System.currentTimeMillis()}.jpg")
        val output = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture?.takePicture(
            output,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Toast.makeText(this@HomeActivity, "Image Saved", Toast.LENGTH_SHORT).show()

                }

                override fun onError(exception: ImageCaptureException) {
                }
            })

    }

    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            preview = Preview.Builder().build()
            preview?.setSurfaceProvider(binding?.cameraView?.createSurfaceProvider(camera?.cameraInfo))
            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
            cameraProvider.unbindAll()
            camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
        }, ContextCompat.getMainExecutor(this))

    }

}