import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AppGreen = Color(0xFF1F8236)
private val FieldGray = Color(0xFFE6E8E6)

@Composable
fun LoginScreen(onSignIn: () -> Unit, onSignUp: () -> Unit) {
    // These remember what the user types
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FB))
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(48.dp))
        Text("LOGO", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFFE5A93B))
        Spacer(Modifier.height(24.dp))
        Text("Welcome back", fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("Sign in to check what's new !", fontSize = 16.sp)
        Spacer(Modifier.height(40.dp))

        Text("Email", fontWeight = FontWeight.SemiBold, modifier = Modifier.align(Alignment.Start))
        Spacer(Modifier.height(8.dp))
        AuthField(email, { email = it }, "you@example.com", Icons.Outlined.Email)

        Spacer(Modifier.height(20.dp))
        Text("Password", fontWeight = FontWeight.SemiBold, modifier = Modifier.align(Alignment.Start))
        Spacer(Modifier.height(8.dp))
        AuthField(password, { password = it }, "Enter your password", Icons.Outlined.Lock, isPassword = true)

        Spacer(Modifier.height(12.dp))
        Text(
            "Forgot Password?",
            modifier = Modifier
                .align(Alignment.End)
                .clickable { /* later */ }
        )

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onSignIn,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AppGreen)
        ) {
            Text("Sign in", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(24.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            HorizontalDivider(Modifier.weight(1f))
            Text("  or continue with  ", fontSize = 14.sp)
            HorizontalDivider(Modifier.weight(1f))
        }

        Spacer(Modifier.height(20.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            listOf("G", "A", "f").forEach { letter ->
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(androidx.compose.foundation.shape.CircleShape)
                        .background(FieldGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(letter, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
            }
        }

        Spacer(Modifier.height(32.dp))
        Row {
            Text("Don't have an account? ")
            Text("Sign up", fontWeight = FontWeight.Bold, modifier = Modifier.clickable { onSignUp() })
        }
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
private fun AuthField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(placeholder) },
        leadingIcon = { Icon(icon, contentDescription = null) },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = FieldGray,
            unfocusedContainerColor = FieldGray,
            unfocusedBorderColor = Color.Gray,
            focusedBorderColor = AppGreen
        )
    )
}