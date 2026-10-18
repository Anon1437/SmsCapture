package com.example.smscapture

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.smscapture.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private val SMS_PERMISSIONS = arrayOf(
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.READ_SMS
    )
    private val REQ_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.btnRequestPermission.setOnClickListener { requestSmsPermissions() }

        if (hasSmsPermissions()) {
            b.statusText.text = "✅ Permission granted. Waiting for SMS..."
        } else {
            b.statusText.text = "⚠️ Permission not granted. Tap button below."
            requestSmsPermissions()
        }
    }

    private fun hasSmsPermissions(): Boolean =
        SMS_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }

    private fun requestSmsPermissions() {
        ActivityCompat.requestPermissions(this, SMS_PERMISSIONS, REQ_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQ_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                b.statusText.text = "✅ Permission granted. Waiting for SMS..."
                Toast.makeText(this, "Ready to capture SMS", Toast.LENGTH_SHORT).show()
            } else {
                b.statusText.text = "❌ Permission denied. Cannot capture SMS."
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }
}
