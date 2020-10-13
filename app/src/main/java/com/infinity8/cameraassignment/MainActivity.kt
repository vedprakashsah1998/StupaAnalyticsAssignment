package com.infinity8.cameraassignment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*
import com.infinity8.cameraassignment.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity(){
    var SLoginType: String? = null
    private val RC_SIGN_IN = 9001
    private val TAG = "RegistrationActivity"
    private var mAuth: FirebaseAuth? = null
    var binding: ActivityMainBinding? = null
    var mDatabaseRef: DatabaseReference? = null
    private var mSignInClient: GoogleSignInClient? = null
    var currentuser: FirebaseUser? = null

    private var mGoogleApiClient: GoogleApiClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)
        mAuth = FirebaseAuth.getInstance()
        currentuser = mAuth!!.currentUser

        FirebaseApp.initializeApp(this@MainActivity)
        mDatabaseRef = FirebaseDatabase.getInstance().reference.child("User")

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mSignInClient = GoogleSignIn.getClient(this@MainActivity, gso);

        binding!!.googleLogin.setOnClickListener {
            signIn()
        }

    }
    private fun signIn() {
        // Launches the sign in flow, the result is returned in onActivityResult
        SLoginType = "Email"
        val intent = mSignInClient!!.signInIntent
        //mGoogleApiClient!!.clearDefaultAccountAndReconnect()
        startActivityForResult(intent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            if (task.isSuccessful) {
                // Sign in succeeded, proceed with account
                Toast.makeText(
                    this@MainActivity,
                    "Sucess" + currentuser?.displayName,
                    Toast.LENGTH_SHORT
                ).show()

                val acct = task.result
                firebaseAuthWithGoogle(acct!!.idToken!!)

            } else {
                // Sign in failed, handle failure and update UI
                // ...
                Toast.makeText(this@MainActivity, "Sign in Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")

                    val user = mAuth!!.currentUser!!
                    val name = user.displayName
                    val email = user.email
                    val photo = user.photoUrl.toString()

                    startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                    finish()

                    /*mDatabaseRef!!.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val email1 = snapshot.child("email").value.toString()
                            assert(email != null)
                            if (email == email1) {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Welcome$name",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                val map = HashMap<String, Any>()
                                map["email"] = email!!
                                map["name"] = name!!
                                map["image"] = photo
                                FirebaseDatabase.getInstance().reference
                                    .child("User")
                                    .child(FirebaseAuth.getInstance().currentUser!!.uid)
                                    .setValue(map)
                                    .addOnSuccessListener { aVoid: Void? ->
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Registration successful.",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                        startActivity(
                                            Intent(
                                                this@MainActivity,
                                                HomeActivity::class.java
                                            )
                                        )
                                        finish()
                                    }
                                    .addOnFailureListener { e: Exception ->
                                        Log.d("FailedData", "onFailure: " + e.message)
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Failed" + e.message,
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {}
                    })*/


                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    // ...
                }

                // ...
            }
    }



}