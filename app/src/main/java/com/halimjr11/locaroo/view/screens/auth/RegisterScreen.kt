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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.halimjr11.locaroo.ui.atoms.BodyM
import com.halimjr11.locaroo.ui.atoms.TitleXL
import com.halimjr11.locaroo.ui.molecules.PrimaryButton
import com.halimjr11.locaroo.ui.state.UiState
import com.halimjr11.locaroo.ui.theme.LocarooTheme
import com.halimjr11.locaroo.view.viewmodels.auth.RegisterViewModel

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.registerState.collectAsState()

    when (state) {
        is UiState.Loading -> CircularProgressIndicator()
        is UiState.Error -> Toast.makeText(
            context,
            (state as UiState.Error).message,
            Toast.LENGTH_SHORT
        ).show()

        is UiState.Success -> {
            onNavigateToLogin()
        }
    }

    RegisterScreenContent(
        modifier = modifier,
        onSignUp = { name, email, password ->
            viewModel.register(name, email, password)
        },
        onNavigateToLogin = onNavigateToLogin
    )
}

@Composable
fun RegisterScreenContent(
    modifier: Modifier = Modifier,
    onSignUp: (name: String, email: String, password: String) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(start = 0.dp, end = 0.dp, top = 32.dp, bottom = 0.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleXL(text = "Create account")
            Spacer(modifier = Modifier.height(8.dp))
            BodyM(
                text = "Please sign up to continue our app",
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Full Name") },
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(12.dp))
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
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                text = "Sign up",
                modifier = Modifier.fillMaxWidth(),
                onClick = { onSignUp(name.trim(), email.trim(), password) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Already have an account?  Sign in",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onNavigateToLogin() },
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterLight() {
    LocarooTheme(darkTheme = false, dynamicColor = false) {
        RegisterScreenContent(
            onSignUp = { _, _, _ -> },
            onNavigateToLogin = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterDark() {
    LocarooTheme(darkTheme = true, dynamicColor = false) {
        RegisterScreenContent(
            onSignUp = { _, _, _ -> },
            onNavigateToLogin = {},
        )
    }
}

