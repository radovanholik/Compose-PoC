package com.example.compose_poc.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.example.compose_poc.R
import com.example.compose_poc.ui.common.core.ComposableFragment
import com.example.compose_poc.ui.common.core.ViewEvent
import com.example.compose_poc.ui.theme.AppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : ComposableFragment<LoginViewModel.State, LoginViewModel.Command>() {

    override val viewModel: LoginViewModel by viewModel()

    override fun onCommand(command: LoginViewModel.Command, scaffoldState: ScaffoldState) {
        when (command) {
            LoginViewModel.Command.Navigation.ToDashboard -> {
                findNavController().navigate(LoginFragmentDirections.toFeatureList())
            }
        }
    }

    @Composable
    override fun ComposableContent(
        scaffoldState: ScaffoldState,
        viewState: LoginViewModel.State
    ) {
        AppTheme(scaffoldState = scaffoldState) {
            Column(modifier = Modifier.padding(bottom = 70.dp)) {
                // top section image
                Image(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    painter = painterResource(id = R.drawable.zion_3),
                    contentDescription = "Zion 3",
                    contentScale = ContentScale.Crop
                )

                if (viewState.loading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                    ) {
                        // user name
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = viewState.username,
                            isError = viewState.isLoginSuccessful == false,
                            onValueChange = { sendEvent(Event.Username(it.trim())) },
                            label = {
                                if (viewState.isLoginSuccessful == false) {
                                    Text(text = "Username is 'username'")
                                } else {
                                    Text("Username")
                                }
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            )
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // password
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = viewState.password,
                            isError = viewState.isLoginSuccessful == false,
                            onValueChange = { sendEvent(Event.Password(it.trim())) },
                            visualTransformation = PasswordVisualTransformation(),
                            label = {
                                if (viewState.isLoginSuccessful == false) {
                                    Text(text = "Password is 'password'")
                                } else {
                                    Text(text = "Password")
                                }
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            )
                        )
                    }
                }
            }

            if (!viewState.loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Button(
                        modifier = Modifier.padding(15.dp),
                        onClick = { sendEvent(Event.OnLoginClicked) },
                        enabled = viewState.username.isNotBlank() && viewState.password.isNotBlank()
                    ) {
                        Text("Login")
                    }
                }
            }

            if (viewState.isLoginSuccessful == true) {
                val openDialog = remember { mutableStateOf(true) }
                if (openDialog.value) {

                    AlertDialog(
                        onDismissRequest = { openDialog.value = false },
                        title = {
                            Text(text = "Login")
                        },
                        text = {
                            Text(
                                text = "You've been successfully logged in!"
                            )
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    openDialog.value = false
                                    sendEvent(Event.AlertButtonClicked)
                                }
                            ) {
                                Text("To dashboard")
                            }
                        }
                    )
                }
            }
        }
    }

    sealed class Event : ViewEvent {
        object OnLoginClicked : Event()
        class Username(val value: String): Event()
        class Password(val value: String): Event()
        object AlertButtonClicked : Event()
    }
}