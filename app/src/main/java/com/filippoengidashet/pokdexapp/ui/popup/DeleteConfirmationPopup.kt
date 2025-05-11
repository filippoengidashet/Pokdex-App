package com.filippoengidashet.pokdexapp.ui.popup

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties

@Composable
fun DeleteConfirmationPopup(
    message: String,
    onDismiss: (confirmed: Boolean) -> Unit,
) {
    AlertDialog(
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = true
        ),
        title = {
            Text(text = "Please confirm")
        },
        text = {
            Text(text = message)
        },
        dismissButton = {
            TextButton(onClick = { onDismiss(false) }) { Text(text = "No") }
        },
        confirmButton = {
            TextButton(onClick = { onDismiss(true) }) { Text(text = "Yes") }
        },
        onDismissRequest = {
            onDismiss(false)
        },
    )
}
