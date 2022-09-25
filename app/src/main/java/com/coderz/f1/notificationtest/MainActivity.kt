package com.coderz.f1.notificationtest

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.coderz.f1.notificationtest.ui.theme.NotificationTestTheme
import com.google.android.material.snackbar.Snackbar

lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    CounterNotificationService(applicationContext).showNotification(Counter.value)
                } else {
                    Snackbar.make(
                        findViewById<View>(android.R.id.content).rootView,
                        getString(R.string.snackbar_text),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }



        setContent {
            NotificationTestTheme {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Button(
                        onClick = {
                            if (ContextCompat.checkSelfPermission(
                                    applicationContext,
                                    POST_NOTIFICATIONS,
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                CounterNotificationService(applicationContext).showNotification(
                                    Counter.value
                                )
                            } else {
                                requestPermissionLauncher.launch(POST_NOTIFICATIONS)
                            }
                        },
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text(getString(R.string.button_text))
                    }
                }
            }
        }


    }


}
