package com.halimjr11.locaroo.view.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.halimjr11.locaroo.ui.atoms.BodyM
import com.halimjr11.locaroo.ui.atoms.TitleXL
import com.halimjr11.locaroo.ui.molecules.PrimaryButton
import com.halimjr11.locaroo.ui.state.UiState
import com.halimjr11.locaroo.ui.theme.LocarooTheme
import com.halimjr11.locaroo.view.viewmodels.auth.LoginViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.loginState.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        LoginScreenContent(
            modifier = modifier,
            onSignIn = { email, password ->
                viewModel.login(email, password)
            },
            onNavigateToRegister = onNavigateToRegister
        )

        when (state) {
            is UiState.Error -> Toast.makeText(
                context,
                (state as UiState.Error).message,
                Toast.LENGTH_SHORT
            ).show()

            is UiState.Loading -> {
                CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
            }

            is UiState.Success -> {
                onNavigateToHome()
            }

            else -> Unit
        }
    }
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    onSignIn: (email: String, password: String) -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(start = 0.dp, end = 0.dp, top = 32.dp, bottom = 0.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleXL(text = "Sign in now")
            Spacer(modifier = Modifier.height(8.dp))
            BodyM(
                text = "Please sign in to continue our app",
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = if (isPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    val icon = if (isPasswordVisible)
                        Icons.Default.Visibility
                    else
                        Icons.Default.VisibilityOff

                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(imageVector = icon, contentDescription = null)
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                text = "Sign in",
                modifier = Modifier.fillMaxWidth(),
                onClick = { onSignIn(email.trim(), password) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Don't have an account?  Sign up",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onNavigateToRegister() },
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginLight() {
    LocarooTheme(darkTheme = false, dynamicColor = false) {
        LoginScreenContent(
            onSignIn = { _, _ -> },
            onNavigateToRegister = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginDark() {
    LocarooTheme(darkTheme = true, dynamicColor = false) {
        LoginScreenContent(
            onSignIn = { _, _ -> },
            onNavigateToRegister = {},
        )
    }
}

