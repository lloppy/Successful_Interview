package com.dkexception.chatgpt.features.chatting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dkexception.chatgpt.R
import com.dkexception.chatgpt.data.models.ChatModel
import com.dkexception.chatgpt.data.models.Response
import com.dkexception.chatgpt.ui.common.ChatGPTScaffold
import com.dkexception.chatgpt.ui.common.ChatListLazyColumn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ChatGPTChatScreen(
    viewModel: ChatGPTChatViewModel = hiltViewModel()
) {
    val state: ChatGPTChatScreenState by viewModel.state.collectAsState()

    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    ChatScreenScaffold(
        state = state,
        onSubmitButtonClicked = {
            viewModel.submitPrompt()
            coroutineScope.launch {
                it.scrollToItem(state.chatList.size - 1)
            }
        },
        onPromptChanged = viewModel::onPromptChanged
    )
}

@Preview
@Composable
private fun ChatScreenScaffold(
    state: ChatGPTChatScreenState = ChatGPTChatScreenState(
        isLoading = false,
        chatList = ChatModel.getDummyChatListForUIPreview().toMutableList()
    ),
    onSubmitButtonClicked: ((LazyListState) -> Unit)? = null,
    onPromptChanged: ((String) -> Unit)? = null
) {
    val listState: LazyListState = rememberLazyListState()

    val expandedState = remember { mutableStateOf(false) }
    // Declaration of the selectedItem mutable state variable
    val selectedItem = remember { mutableStateOf("") }

    ChatGPTScaffold(
        userInputPromptText = state.userEnteredPrompt,
        onSubmitButtonClicked = {
            onSubmitButtonClicked?.invoke(listState)
        },
        onPromptChanged = onPromptChanged,
    ) {
        Column {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
                actions = {
                    IconButton(onClick = { expandedState.value = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                }
            )

            DropdownMenu(
                expanded = expandedState.value,
                onDismissRequest = { expandedState.value = false } ,
                offset = DpOffset(300.dp, -586.dp),

            ) {
                Box(modifier = Modifier.background(colorResource(R.color.standart)))

                DropdownMenuItem(onClick = {
                    selectedItem.value = "Путой"
                    expandedState.value = false
                }) {
                    Text(text = "Путой")
                    Response().query = "\" \""
                }
                DropdownMenuItem(onClick = {
                    selectedItem.value = "Хочу сказать с русского"
                    expandedState.value = false
                }) {
                    Text(text = "Хочу сказать с русского")
                    Response().query = "переведи предложение на китайский и напиши пиньинь: "
                }
                DropdownMenuItem(onClick = {
                    selectedItem.value = "Спрашивают на китайском"
                    expandedState.value = false
                }) {
                    Text(text = "Спрашивают на китайском")
                    Response().query = "\"переведи предложение на русский и напиши на него ответ по китайский с переводом на русский и пиньинь: \""

                }
            }

            ChatListLazyColumn(
                chatList = state.chatList,
                listState = listState,
                isLoading = state.isLoading
            )
        }
    }
}
