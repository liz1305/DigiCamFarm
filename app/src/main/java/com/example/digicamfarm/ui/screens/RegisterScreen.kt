package com.example.digicamfarm.ui.screens

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
/*import androidx.compose.foundation.layout.matchParentSize*/
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digicamfarm.R
import com.example.digicamfarm.data.entity.User
import com.example.digicamfarm.ui.components.AppTextField
import com.example.digicamfarm.ui.theme.AppGreen
import com.example.digicamfarm.ui.theme.ScreenBg
import com.example.digicamfarm.viewmodel.AuthViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    vm: AuthViewModel,
    onRegistered: () -> Unit,
    onLogin: () -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    if (showDatePicker) {
        val pickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    pickerState.selectedDateMillis?.let { birthDate = formatDate(it) }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = pickerState)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBg)
            .statusBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))
        Image(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = "Logo",
            modifier = Modifier.height(48.dp)
        )
        Spacer(Modifier.height(16.dp))
        Text("Register", fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("Create an account to continue!", fontSize = 14.sp)
        Spacer(Modifier.height(24.dp))

        AppTextField(firstName, { firstName = it }, "First name")
        Spacer(Modifier.height(12.dp))
        AppTextField(lastName, { lastName = it }, "Last name")
        Spacer(Modifier.height(12.dp))
        AppTextField(
            email, { email = it }, "Email",
            keyboardType = KeyboardType.Email
        )
        Spacer(Modifier.height(12.dp))

        // Birth date: read-only field, tapping it opens the date picker
        Box {
            AppTextField(
                value = birthDate,
                onValueChange = {},
                placeholder = "Birth date (dd/mm/yyyy)",
                readOnly = true,
                trailingIcon = {
                    Icon(Icons.Outlined.CalendarToday, contentDescription = "Pick date")
                }
            )
            Box(
                Modifier
                    .matchParentSize()
                    .clickable { showDatePicker = true }
            )
        }
        Spacer(Modifier.height(12.dp))

        AppTextField(
            value = phone,
            onValueChange = { phone = it.filter { c -> c.isDigit() } },
            placeholder = "Phone number",
            prefixText = "+855  ",
            keyboardType = KeyboardType.Phone
        )
        Spacer(Modifier.height(12.dp))

        AppTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Password",
            keyboardType = KeyboardType.Password,
            visualTransformation = if (showPassword) VisualTransformation.None
            else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        if (showPassword) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                        contentDescription = "Show or hide password"
                    )
                }
            }
        )

        error?.let {
            Spacer(Modifier.height(12.dp))
            Text(it, color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
        }

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                val problem = when {
                    listOf(firstName, lastName, email, birthDate, phone, password)
                        .any { it.isBlank() } -> "Please fill in all fields."
                    !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() ->
                        "Please enter a valid email."
                    password.length < 6 -> "Password must be at least 6 characters."
                    else -> null
                }
                error = problem
                if (problem == null) {
                    val user = User(
                        firstName = firstName.trim(),
                        lastName = lastName.trim(),
                        email = email.trim(),
                        birthDate = birthDate,
                        phone = "+855" + phone,
                        password = password
                    )
                    vm.register(user) { success ->
                        if (success) {
                            Toast.makeText(context, "Account created!", Toast.LENGTH_SHORT).show()
                            onRegistered()
                        } else {
                            error = "This email is already registered."
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AppGreen)
        ) {
            Text("Register", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(20.dp))
        Row(horizontalArrangement = Arrangement.Center) {
            Text("Already have an account? ")
            Text("Login", fontWeight = FontWeight.Bold, modifier = Modifier.clickable { onLogin() })
        }
        Spacer(Modifier.height(32.dp))
    }
}

private fun formatDate(millis: Long): String {
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    format.timeZone = TimeZone.getTimeZone("UTC")
    return format.format(Date(millis))
}