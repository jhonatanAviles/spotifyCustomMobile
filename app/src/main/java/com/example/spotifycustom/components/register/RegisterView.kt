package com.example.spotifycustom.components.register

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.spotifycustom.data.repository.MyRepository
import com.example.spotifycustom.navigation.NavScreen
import com.example.spotifycustom.utils.FirebaseAuthenticationManager
import com.example.spotifycustom.viewmodels.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RegisterView(navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }

    val passwordFocusRequester = remember { FocusRequester() }

    val authManager = FirebaseAuthenticationManager()

    val context = LocalContext.current

    val coroutine = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Create an account",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold
            )
        }

        OutlinedTextField(
            value = email,
            onValueChange = { newEmail ->
                email = newEmail
                isEmailValid = newEmail.isNotBlank()
            },
            label = { Text(text = "Email") },
            isError = !isEmailValid, // Show error state if email is empty
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    // Handle the next action (e.g., focus on the password field)
                    passwordFocusRequester.requestFocus()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { newPassword ->
                password = newPassword
                isPasswordValid = newPassword.isNotBlank()
            },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            isError = !isPasswordValid, // Show error state if password is empty
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Handle the done action (e.g., perform sign-in) only if both fields are valid
                    if (isEmailValid && isPasswordValid) {
                        onSignUpWithEmailClick(authManager, email, password, navController, authViewModel, context, coroutine)
                    }
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp)
                .focusRequester(passwordFocusRequester)
        )

        Button(
            onClick = {
                // Handle the done action (e.g., perform sign-in) only if both fields are valid
                if (isEmailValid && isPasswordValid) {
                    onSignUpWithEmailClick(
                        authManager,
                        email,
                        password,
                        navController,
                        authViewModel,
                        context,
                        coroutine
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = "Create account")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Have an account? ",
                style = MaterialTheme.typography.body1
            )
            TextButton(
                onClick = {
                    // Handle the "Create an account" button click
                    onLogInClick(navController)
                }
            ) {
                Text(
                    text = "Log in",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

fun onSignUpWithEmailClick(
    authManager: FirebaseAuthenticationManager,
    email: String,
    password: String,
    navController: NavController,
    authViewModel: AuthViewModel,
    context: Context,
    coroutine: CoroutineScope,
) {
    // Sign Up Example
    authManager.signUpWithEmail(
        email = email,
        password = password,
        onComplete = { user ->
            coroutine.launch {
                val repo = MyRepository()
                if (user != null) {
                    repo.storeUserInFirestore(user)
                }
            }
            authViewModel.isLoggedIn.value = true
            // Sign-up successful, user is signed in
            // Handle the user object or navigate to the home screen
            navController.navigate(NavScreen.ScreenArtists.route) {
                launchSingleTop = true
            }
        },
        onError = { exception ->
            // Sign-up failed, handle the error
            // Display an error message or take appropriate action
            Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
        }
    )
}

fun onLogInClick(navController: NavController) {
    navController.popBackStack()
    navController.popBackStack()

    navController.navigate(NavScreen.ScreenLogin.route)
}
