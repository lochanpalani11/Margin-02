package com.margin.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.margin.app.ui.theme.LossRed
import com.margin.app.ui.theme.SurfaceBorder
import com.margin.app.ui.theme.SurfaceElevated1
import com.margin.app.ui.theme.TextTertiary

@Composable
fun MarginTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    errorText: String? = null,
    singleLine: Boolean = true,
    minLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
    readOnly: Boolean = false
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = placeholder?.let { { Text(it) } },
            singleLine = singleLine,
            minLines = minLines,
            readOnly = readOnly,
            isError = errorText != null,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            shape = MaterialTheme.shapes.small,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = SurfaceElevated1,
                unfocusedContainerColor = SurfaceElevated1,
                disabledContainerColor = SurfaceElevated1,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = SurfaceBorder,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = TextTertiary,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.fillMaxWidth()
        )
        if (errorText != null) {
            Text(
                text = errorText,
                style = MaterialTheme.typography.bodySmall,
                color = LossRed,
                modifier = Modifier.padding(start = 12.dp, top = 4.dp)
            )
        }
    }
}
