package com.sysoliatina.videochat

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONException
import org.json.JSONObject

class JoinActivity : AppCompatActivity() {
    var sampleToken ="6019eb37348339eff4715ee133fc3555bc6cf4cad4e90df747f62936da300a3e"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        val btnCreate = findViewById<Button>(R.id.btnCreateMeeting)
        val btnJoin = findViewById<Button>(R.id.btnJoinMeeting)
        val etMeetingId = findViewById<EditText>(R.id.etMeetingId)

        checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID)
        checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID)

        btnCreate.setOnClickListener { v: View? ->
            // we will explore this method in the next step
            createMeeting(sampleToken)
        }
        btnJoin.setOnClickListener { v: View? ->
            val intent = Intent(this@JoinActivity, MeetingActivity::class.java)
            intent.putExtra("token", sampleToken)
            intent.putExtra("meetingId", etMeetingId.text.toString())
            startActivity(intent)
        }
    }

    private fun createMeeting(token: String) {
        AndroidNetworking.post("https://api.videosdk.live/v1/meetings")
            .addHeaders("Authorization", token)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    try {
                        val meetingId = response.getString("meetingId")
                        val intent = Intent(this@JoinActivity, MeetingActivity::class.java)
                        intent.putExtra("token", sampleToken)
                        intent.putExtra("meetingId", meetingId)
                        startActivity(intent)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                override fun onError(anError: ANError) {
                    anError.printStackTrace()
                    Toast.makeText(
                        this@JoinActivity, anError.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
    }


companion object {
    private const val PERMISSION_REQ_ID = 22
    private val REQUESTED_PERMISSIONS = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA
    )
}

private fun checkSelfPermission(permission: String, requestCode: Int): Boolean {
    if (ContextCompat.checkSelfPermission(this, permission) !=
        PackageManager.PERMISSION_GRANTED)
    {
        ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, requestCode)
        return false
    }
    return true
}
