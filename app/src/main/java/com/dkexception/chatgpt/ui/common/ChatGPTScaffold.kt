@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.dkexception.chatgpt.ui.common

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkexception.chatgpt.R

@Preview
@Composable
fun ChatGPTScaffold(
    titleText: String = stringResource(R.string.chat_screen_title),
    userInputPromptText: String = "",
    onSubmitButtonClicked: (() -> Unit)? = null,
    onPromptChanged: ((String) -> Unit)? = null,
    content: @Composable (() -> Unit)? = null
) = Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
        createDropDown()

    },
    bottomBar = {
        ChatTextFieldBottomBar(
            currentPromptText = userInputPromptText,
            onSubmitButtonClicked = onSubmitButtonClicked,
            onPromptChanged = onPromptChanged
        )
    }
) { paddingValues ->

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        color = MaterialTheme.colorScheme.inversePrimary
    ) {
        content?.invoke()
    }
}

@Composable
private fun createDropDown() {
    // Context Drop Down Menu
    DropdownMenu(
        expanded = false,
        onDismissRequest = { /* Empty */ }
    ) {
        androidx.compose.material.DropdownMenuItem(onClick = { /* Translate */ }) {
            androidx.compose.material.Text(text = "Translate")
        }
        androidx.compose.material.DropdownMenuItem(onClick = { /* Answer */ }) {
            androidx.compose.material.Text(text = "Answer")
        }
        androidx.compose.material.DropdownMenuItem(onClick = { /* Question */ }) {
            androidx.compose.material.Text(text = "Question")
        }
    }
}

@Composable
private fun ChatTextFieldBottomBar(
    currentPromptText: String,
    onSubmitButtonClicked: (() -> Unit)? = null,
    onPromptChanged: ((String) -> Unit)? = null
) = Card(
    modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
    elevation = CardDefaults.cardElevation(8.dp),
    shape = RoundedCornerShape(10.dp),
    colors = CardDefaults.cardColors(
        containerColor = Color(
            if (isSystemInDarkTheme()) 0xFF414148 else 0xFFFFFFFF
        )
    )
) {

    val keyboardManager = LocalSoftwareKeyboardController.current

    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = currentPromptText,
            onValueChange = { onPromptChanged?.invoke(it) },
            label = { Text(stringResource(R.string.user_query_field_hint)) },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
        )
        Spacer(modifier = Modifier.width(8.dp))
        ElevatedButton(
            onClick = {
                onSubmitButtonClicked?.invoke()
                keyboardManager?.hide()
            }
        ) {
            Text(text = stringResource(R.string.submit), modifier = Modifier.padding(4.dp))
        }
    }
}
